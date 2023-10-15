package com.github.dinbtechit.vscodetheme

import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.ui.LafManager
import com.intellij.openapi.extensions.PluginId

/*enum class VSCodeTheme(val theme: String) {
    UNKNOWN("UNKNOWN"),
    DARK("VSCode Dark"),
    DARK_MODERN("VSCode Dark Modern");
}*/

object VSCodeTheme {
    const val UNKNOWN = "UNKNOWN"
    const val DARK = "VSCode Dark"
    const val DARK_MODERN = "VSCode Dark Modern"
}

class VSCodeThemeManager {
    companion object {
        const val pluginId = "com.github.dinbtechit.vscodetheme"
        private var myObject: VSCodeThemeManager? = null
        fun getInstance(): VSCodeThemeManager {
            if (myObject == null) {
                myObject = VSCodeThemeManager()
            }
            return myObject!!
        }
    }

    private fun getPlugin(): IdeaPluginDescriptor? = PluginManagerCore.getPlugin(PluginId.getId(pluginId))
    fun isVSCodeThemeReady(): Boolean {
        try {
            if (getPlugin()?.isEnabled != null) {
                val vscodeTheme =
                    LafManager.getInstance().installedLookAndFeels.first { it.name == VSCodeTheme.DARK }
                return vscodeTheme != null
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }

    fun switchToVSCodeTheme(always: Boolean = false, selectedVSCodeTheme: String = VSCodeTheme.DARK) {
        try {
            if (isVSCodeThemeReady()) {
                val vscodeTheme =
                    LafManager.getInstance().installedLookAndFeels.first { it.name == selectedVSCodeTheme }
                LafManager.getInstance().currentLookAndFeel = vscodeTheme
                if (always) {
                    val settings = VSCodeThemeSettingsStore.instance
                    settings.alwaysApply = true
                    settings.themeName = selectedVSCodeTheme
                }
            }
        } catch (e: Exception) {
            throw(Error("Unable to select the default theme $selectedVSCodeTheme", e))
        }
    }
}
