# Attestr Kotlin Sample App

![Platform](https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![GitHub](https://img.shields.io/github/license/attestr/kotlin-sample-app)
![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/attestr/kotlin-sample-app?include_prereleases)

## Installation

### Maven users

Add this dependency to your project:

```xml
<dependency>
 <groupId>com.attestr</groupId>
 <artifactId>attestr-flowx</artifactId>
 <version>0.3.1</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.attestr:attestr-flowx:0.3.1"
```

## Usage

Create new object of AttestrFlowx

```kotlin
  private var attestrFlowx: AttestrFlowx? = AttestrFlowx()
```

Initialize AttestrFlowx with client_key, handshake_id & activity

```kotlin
/**
 * Initialises an instance of AttestrFlowx
 * @param cl Mandatory client key
 * @param hs Mandatory handshake key
 * @param activity Activity on which flow is to be rendered
 */
attestrFlowx?.init(clientKey, handShakeID, this);
```

Launch AttestrFlowx with locale, retry and queryParameters

```kotlin
/**
 * This function launches the flow with the following specifications
 * @param lc Mandatory language code eg. 'en' for English.
 * @param retry Mandatory parameter to set retry as true if re-running the flow for a previously used handshake.
 * @param qr Optional query parameters.
 */
attestrFlowx?.launch(locale, retry, qr);
```

Implement AttestrFlowXEventListener and define success, error and skip handlers

```kotlin
// Implement AttestrFlowXEventListener 

class MainActivity : AppCompatActivity(), AttestrFlowXListener {

    // Replace following with your own implementations
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
```

## Version Compatibility
Built for Android API 21+ (Lollipop onwards)
 
## License
attestr-android-sdk is distributed under MIT license. Read more in the [LICENSE](LICENSE) file.

## Contact Us
Write to us at [contact@attestr.com](mailto:contact@attestr.com)
