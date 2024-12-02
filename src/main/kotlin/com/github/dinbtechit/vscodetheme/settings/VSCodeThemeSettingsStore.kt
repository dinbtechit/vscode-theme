package com.github.dinbtechit.vscodetheme.settings

import com.github.dinbtechit.vscodetheme.VSCodeTheme
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nullable

@State(name = "VSCodeThemeSettings", storages = [(Storage("vscode_theme.xml"))])
class VSCodeThemeSettingsStore: PersistentStateComponent<VSCodeThemeSettingsStore> {

    companion object {
        val instance: VSCodeThemeSettingsStore
            get() = ApplicationManager.getApplication().getService(VSCodeThemeSettingsStore::class.java)
    }
    var isVSCodeEnabled = true
    var showNotificationOnUpdate = true
    var version = "unknown"

    @Nullable
    override fun getState() = this

    override fun loadState(state: VSCodeThemeSettingsStore) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
