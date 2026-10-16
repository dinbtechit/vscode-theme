package com.github.dinbtechit.vscodetheme

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId

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

    fun isVSCodeThemeReady(): Boolean {
      return PluginManagerCore.isLoaded(PluginId.getId(pluginId))
    }
}
