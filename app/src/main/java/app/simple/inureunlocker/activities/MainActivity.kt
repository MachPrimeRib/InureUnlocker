package app.simple.inureunlocker.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.simple.inureunlocker.R
import app.simple.inureunlocker.constants.IntentConstants
import app.simple.inureunlocker.utils.ActivityUtils
import app.simple.inureunlocker.utils.MarketUtils

class MainActivity : AppCompatActivity() {

    private lateinit var activate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activate = findViewById(R.id.activate)

        activate.setOnClickListener {
            kotlin.runCatching {
                ActivityUtils.launchPackage(
                    applicationContext,
                    packageName = "app.simple.inure",
                    packageId = "app.simple.inure.activities.app.MainActivity",
                    IntentConstants.ACTION_UNLOCK
                )
            }.getOrElse {
                it.printStackTrace()
                Toast.makeText(baseContext, R.string.app_not_installed, Toast.LENGTH_SHORT).show()
                MarketUtils.openAppOnPlayStore(baseContext, "app.simple.inure")
            }
        }
    }
}