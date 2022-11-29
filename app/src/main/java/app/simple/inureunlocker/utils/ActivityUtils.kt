package app.simple.inureunlocker.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent

object ActivityUtils {
    /**
     * Launch a given activity class with [Intent.setAction]
     */
    fun launchPackage(context: Context, packageName: String, packageId: String, action: String) {
        val intent = Intent(action)
        intent.component = ComponentName(packageName, packageId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
}
