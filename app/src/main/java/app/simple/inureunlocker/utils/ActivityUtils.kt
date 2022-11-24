package app.simple.inureunlocker.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ComponentInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat.startActivity
import app.simple.inureunlocker.constants.IntentConstants
import app.simple.inureunlocker.utils.NullSafety.isNotNull

object ActivityUtils {
    /**
     * Launch a given activity class
     */
    @Throws
    fun launchPackage(context: Context, packageName: String, packageId: String) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.component = ComponentName(packageName, packageId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(IntentConstants.ACTION_UNLOCK, IntentConstants.EXTRA_UNLOCK_DATA)
        context.startActivity(intent)
    }

    /**
     * Launch a given activity class with [Intent.setAction]
     */
    fun launchPackage(context: Context, packageName: String, packageId: String, action: String) {
        val intent = Intent(IntentConstants.ACTION_UNLOCK)
        intent.component = ComponentName(packageName, packageId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }

    fun isEnabled(context: Context, packageName: String, clsName: String): Boolean {
        val componentName = ComponentName(packageName, clsName)

        return when (context.packageManager.getComponentEnabledSetting(componentName)) {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED -> false
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED -> true
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ->       // We need to get the application info to get the component's default state
                try {
                    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            context.packageManager
                                .getPackageInfo(
                                    packageName,
                                    PackageManager.PackageInfoFlags.of(
                                        (PackageManager.GET_ACTIVITIES or PackageManager.MATCH_DISABLED_COMPONENTS).toLong()
                                    )
                                )
                        } else {
                            @Suppress("DEPRECATION")
                            context.packageManager
                                .getPackageInfo(
                                    packageName,
                                    PackageManager.GET_ACTIVITIES or PackageManager.MATCH_DISABLED_COMPONENTS
                                )
                        }
                    } else {
                        @Suppress("deprecation")
                        context.packageManager.getPackageInfo(
                            packageName, PackageManager.GET_ACTIVITIES
                                    or PackageManager.GET_DISABLED_COMPONENTS
                        )
                    }

                    val components: ArrayList<ComponentInfo> = ArrayList()

                    if (packageInfo.activities.isNotNull()) {
                        for (i in packageInfo.activities) {
                            components.add(i)
                        }
                    }

                    for (componentInfo in components) {
                        if (componentInfo.name == clsName) {
                            return componentInfo.isEnabled
                        }
                    }

                    // the component is not declared in the AndroidManifest
                    false
                } catch (e: PackageManager.NameNotFoundException) {
                    // the package isn't installed on the device
                    false
                }

            else -> {
                try {
                    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            context.packageManager
                                .getPackageInfo(
                                    packageName,
                                    PackageManager.PackageInfoFlags.of(
                                        (PackageManager.GET_ACTIVITIES or PackageManager.MATCH_DISABLED_COMPONENTS).toLong()
                                    )
                                )
                        } else {
                            @Suppress("DEPRECATION")
                            context.packageManager
                                .getPackageInfo(
                                    packageName,
                                    PackageManager.GET_ACTIVITIES or PackageManager.MATCH_DISABLED_COMPONENTS
                                )
                        }
                    } else {
                        @Suppress("deprecation")
                        context.packageManager.getPackageInfo(
                            packageName, PackageManager.GET_ACTIVITIES
                                    or PackageManager.GET_DISABLED_COMPONENTS
                        )
                    }

                    val components: ArrayList<ComponentInfo> = ArrayList()

                    if (packageInfo.activities.isNotNull()) {
                        for (i in packageInfo.activities) {
                            components.add(i)
                        }
                    }

                    for (componentInfo in components) {
                        if (componentInfo.name == clsName) {
                            return componentInfo.isEnabled
                        }
                    }

                    // the component is not declared in the AndroidManifest
                    false
                } catch (e: PackageManager.NameNotFoundException) {
                    // the package isn't installed on the device
                    false
                }
            }
        }
    }
}
