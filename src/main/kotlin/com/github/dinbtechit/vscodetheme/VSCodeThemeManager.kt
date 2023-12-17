package com.github.dinbtechit.vscodetheme

import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.DataManager
import com.intellij.ide.actions.SettingsEntryPointAction
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.ui.*
import com.intellij.ide.ui.laf.UIThemeLookAndFeelInfoImpl
import com.intellij.ide.ui.laf.UiThemeProviderListManager
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.ProjectManager
import java.util.*
import javax.swing.UIDefaults

import javax.swing.UIManager

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
                    LafManager.getInstance().installedThemes.firstOrNull { (it as UIThemeLookAndFeelInfoImpl).theme.name == VSCodeTheme.DARK }
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
                    LafManager.getInstance().installedThemes.firstOrNull { it.name == convertedSelectedVSCodeTheme }

                if (vscodeTheme != null) {
                    LafManager.getInstance().currentUIThemeLookAndFeel = vscodeTheme
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

    private fun convertOldToNewTheme(theme: String): String {
        return when (theme) {
            "DARK_MODERN" -> "VSCode Dark Modern"
            "DARK" -> "VSCode Dark"
            else -> theme
        }

    }
}
