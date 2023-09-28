package box99.bsport.quiz

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private val webView by lazy {
        findViewById<WebView>(R.id.myWeb)
    }
    private val sharedPref by lazy {
        getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = sharedPref.getString("URL", "")

//        if ((url?.isNotBlank()) == true) {
//            if (isOnline(this)) {
//                openWebFragment(url)
//            } else {
//                openNoConnectionFragment()
//            }
//        } else {
//            if (!checkIsEmu()) {
//                setupFirebase(this)
//            } else {
                openQuizFragment()
//            }

//        }
    }

    private fun openWebFragment(url: String) {
        val bundle = Bundle()
        bundle.putString("URL", url)
        val frag = WebFragment()
        frag.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, frag)
            .commitNow()
    }

    private fun openQuizFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, QuizFragment())
            .commitNow()
    }

    private fun openNoConnectionFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NoConnectionFragment())
            .commitNow()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun setupFirebase(context: Context) {
        val fb = FirebaseApp.initializeApp(this)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig(fb!!)
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        try {
            remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val updated = task.result

                        val url = remoteConfig.getString("url")
                        if (url == "") {
                            openQuizFragment()
                        } else {
                            sharedPref.edit().putString("URL", url).apply()
                            openWebFragment(url)
                        }

                        Log.d("MYCHECK", "Config params updated: $updated")
                    } else {
                        Log.d("MYCHECK", "Fetch failed")
                    }
                }
        } catch (e: Exception) {
            openNoConnectionFragment()
            return
        }



    }


    private fun setupRecycler() {

    }

    private fun checkIsEmu(): Boolean {
        if (BuildConfig.DEBUG) return false // when developer use this build on emulator
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        val brand = Build.BRAND

        var result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || brand.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox") || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox")
                || brand.startsWith("generic") && Build.DEVICE.startsWith("generic"))

        if (result) return true
        result = result or ("google_sdk" == buildProduct)
        return result
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!
            .state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
        return connected
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }
    }
}