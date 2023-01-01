package app.simple.inureunlocker.popups

import android.content.ComponentName
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import app.simple.inureunlocker.R
import app.simple.inureunlocker.activities.MainActivityAlias
import app.simple.inureunlocker.extensions.popup.BasePopupWindow
import app.simple.inureunlocker.extensions.popup.PopupLinearLayout

class PopupMainMenu(view: View) : BasePopupWindow() {

    private val hide: TextView

    init {
        val contentView = LayoutInflater.from(view.context)
            .inflate(R.layout.popup_main_menu,
                     PopupLinearLayout(view.context, LinearLayout.VERTICAL), true)

        hide = contentView.findViewById(R.id.hide_app)

        if (view.context.packageManager
                .getComponentEnabledSetting(ComponentName(view.context, MainActivityAlias::class.java))
            == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
            view.context.packageManager
                .getComponentEnabledSetting(ComponentName(view.context, MainActivityAlias::class.java))
            == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
            Log.d("PopupMainMenu", "App is visible")
            hide.setText(R.string.hide_app)
        } else {
            Log.d("PopupMainMenu", "App is hidden")
            hide.setText(R.string.show_app)
        }

        hide.setOnClickListener {
            if (view.context.packageManager
                    .getComponentEnabledSetting(ComponentName(view.context, MainActivityAlias::class.java))
                == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                view.context.packageManager
                    .getComponentEnabledSetting(ComponentName(view.context, MainActivityAlias::class.java))
                == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
                view.context.packageManager
                    .setComponentEnabledSetting(ComponentName(view.context, MainActivityAlias::class.java),
                                                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
                dismiss()
            } else {
                view.context.packageManager
                    .setComponentEnabledSetting(ComponentName(view.context, MainActivityAlias::class.java),
                                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
                dismiss()
            }
        }

        init(contentView, view)
    }
}