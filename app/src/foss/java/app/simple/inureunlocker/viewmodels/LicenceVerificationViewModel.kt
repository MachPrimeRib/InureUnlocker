package app.simple.inureunlocker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.io.BufferedReader


class LicenceVerificationViewModel(application: Application) : AndroidViewModel(application) {

    private val refunded: MutableLiveData<Boolean> = MutableLiveData()
    private val licenceKey: MutableLiveData<String> = MutableLiveData()

    fun getRefunded(): MutableLiveData<Boolean> {
        return refunded
    }

    fun getLicenceKey(): MutableLiveData<String> {
        return licenceKey
    }

    init {
        verifyLicence("FB4CDA16-0AEE435F-A119BD4C-B4F2499A")
    }

    private fun verifyLicence(licence: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val httpClient = OkHttpClient()
                val request: okhttp3.Request = okhttp3.Request.Builder()
                    .url("https://api.gumroad.com/v2/licenses/verify -d \"product_permalink=inure_unlocker\" -d \"license_key=$licence\"")
                    .build()

                httpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw Exception("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}