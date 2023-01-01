package app.simple.inureunlocker.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.simple.inureunlocker.R
import app.simple.inureunlocker.constants.IntentConstants
import app.simple.inureunlocker.popups.PopupMainMenu
import app.simple.inureunlocker.utils.ActivityUtils
import app.simple.inureunlocker.utils.AppUtils
import app.simple.inureunlocker.utils.IntentHelper.asUri
import app.simple.inureunlocker.utils.IntentHelper.openInBrowser
import app.simple.inureunlocker.utils.MarketUtils

class MainActivity : AppCompatActivity() {

    private lateinit var activate: Button
    private lateinit var rate: Button
    private lateinit var menu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activate = findViewById(R.id.activate)
        rate = findViewById(R.id.rate)
        menu = findViewById(R.id.menu)

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

        rate.setOnClickListener {
            if (AppUtils.isGithubFlavor()) {
                // Open GumRoad link in Browser
                getString(R.string.gumroad_link).asUri().openInBrowser(baseContext)
            } else {
                MarketUtils.openAppOnPlayStore(baseContext, packageName)
            }
        }

        menu.setOnClickListener {
            PopupMainMenu(it)
        }
    }
}