package com.github.dinbtechit.vscodetheme

import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.ui.LafManager

class VSCodeThemeManager {
    companion object {
        private var myObject: VSCodeThemeManager? = null
        fun getInstance(): VSCodeThemeManager {
            if (myObject == null) {
                myObject = VSCodeThemeManager()
            }
            return myObject!!
        }
    }

    fun switchToVSCodeTheme(always: Boolean = false) {
        if (always) {
            val settings = VSCodeThemeSettingsStore.instance
            settings.alwaysApply = true
            settings.showNotificationOnUpdate = false
        }
        val vscodeTheme = LafManager.getInstance().installedLookAndFeels.first { it.name == "VSCode Dark" }
        LafManager.getInstance().currentLookAndFeel = vscodeTheme
    }
}
