package com.example.attestrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.attestr.flowx.AttestrFlowx
import com.attestr.flowx.listener.AttestrFlowXListener
import com.example.attestrapp.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity(), View.OnClickListener, AttestrFlowXListener {

    private val TAG = "main_activity"
    private var mainBinding: ActivityMainBinding? = null
    private var handshakeIdEditText: EditText? = null
    private var clientKeyEditText: EditText? = null
    private lateinit var initiateSessionButton: Button
    private val handShakeError = "Enter handshake id"
    private val clientKeyError = "Enter client key"
    private var retrySpinner: Spinner? = null;
    private var isRetry = false
    private var selectedLocale: String? = null
    private var attestrFlowx: AttestrFlowx? = null
    private val languages = arrayOf("en", "hi")
    private val retryMode = booleanArrayOf(true, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding!!.getRoot())
        handshakeIdEditText = mainBinding!!.handshakeIdEditText
        clientKeyEditText = mainBinding!!.clientKeyEditText
        initiateSessionButton = mainBinding!!.initiateSessionButton
        retrySpinner = mainBinding!!.retrySpinner

        initiateSessionButton.setOnClickListener(this)

        val retrySpinnerAdapter = ArrayAdapter(
            this,
            R.layout.spinner_items, resources
                .getStringArray(R.array.retry_array)
        )
        retrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        retrySpinner!!.prompt = "Select Retry"
        retrySpinner!!.adapter = retrySpinnerAdapter
        retrySpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                isRetry = retryMode[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                isRetry = false
            }
        }

        attestrFlowx = AttestrFlowx()
    }

    override fun onClick(v: View) {
        if (v === initiateSessionButton) {
            val handShakeID = handshakeIdEditText!!.text.toString().trim { it <= ' ' }
            val clientKey = clientKeyEditText!!.text.toString().trim { it <= ' ' }
            try {
                if (!TextUtils.isEmpty(handShakeID) && !TextUtils.isEmpty(clientKey)) {
                    attestrFlowx?.init(clientKey, handShakeID, this)
                    attestrFlowx?.launch(
                        null,
                        isRetry,
                        null
                    )
                }
            } catch (e: Exception) {
                Log.d(TAG, "onClick: $e")
            }
            if (TextUtils.isEmpty(handShakeID) && TextUtils.isEmpty(clientKey)) {
                Log.d(TAG, "Hanshake ID & Client Key Null")
                handshakeIdEditText!!.error = handShakeError
                clientKeyEditText!!.error = clientKeyError
            }
            if (TextUtils.isEmpty(handShakeID)) {
                Log.d(TAG, "Hanshake ID Null")
                handshakeIdEditText!!.error = handShakeError
            }
            if (TextUtils.isEmpty(clientKey)) {
                Log.d(TAG, "Client Key Null")
                clientKeyEditText!!.error = clientKeyError
            }
        }
    }


    override fun onFlowXComplete(map: Map<String?, Any>) {
        Toast.makeText(this, "Signature: " + map["signature"], Toast.LENGTH_SHORT).show()
    }

    override fun onFlowXSkip(map: Map<String?, Any?>?) {
        Toast.makeText(this, "Flow skipped", Toast.LENGTH_SHORT).show()
    }

    override fun onFlowXError(map: Map<String?, Any?>) {
        val errorMessage = map["message"] as String?
        Toast.makeText(this, "Error : $errorMessage", Toast.LENGTH_SHORT).show()
    }

}