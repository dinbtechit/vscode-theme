package com.github.dinbtechit.vscodetheme

import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.ui.LafManager
import com.intellij.openapi.extensions.PluginId
import com.intellij.util.containers.ContainerUtil

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
                    LafManager.getInstance().installedLookAndFeels.firstOrNull { it.toString().contains(VSCodeTheme.DARK) }
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
                val convertedSelectedVSCodeTheme = convertOldToNewTheme(selectedVSCodeTheme)

                val vscodeTheme =
                    LafManager.getInstance().installedLookAndFeels.firstOrNull { it.toString().contains(convertedSelectedVSCodeTheme) }
                ContainerUtil.find(LafManager.getInstance().installedLookAndFeels) { it.name === convertedSelectedVSCodeTheme}
                if (vscodeTheme != null) {
                    LafManager.getInstance().currentLookAndFeel = vscodeTheme
                }
                if (always) {
                    val settings = VSCodeThemeSettingsStore.instance
                    settings.alwaysApply = true
                    settings.themeName = selectedVSCodeTheme
                }
            }
        } catch (e: Exception) {
            throw (Error("Unable to select the default theme $selectedVSCodeTheme", e))
        }
    }

    fun isVSCodeThemeSelected(): Boolean {
        val theme = LafManager.getInstance().currentLookAndFeel
        if (theme != null) {
            return theme.toString().contains(VSCodeTheme.DARK) && !theme.toString().contains("Modern")
        }
        return false
    }

    fun isVSCodeDarkModernThemeSelected(): Boolean {
        val theme = LafManager.getInstance().currentLookAndFeel
        return theme?.toString()?.contains(VSCodeTheme.DARK_MODERN) ?: false
    }



    private fun convertOldToNewTheme(theme: String): String {
        return when (theme) {
            "DARK_MODERN" -> "VSCode Dark Modern"
            "DARK" -> "VSCode Dark"
            else -> theme
        }
    }
}
