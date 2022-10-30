package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.github.dinbtechit.vscodetheme.startup.VSCodeStartupNotifyActivity
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction


class ApplyThemeAction: DumbAwareAction("Apply Once") {
    companion object {
        var enabled = true
    }
    override fun actionPerformed(e: AnActionEvent) {
        VSCodeThemeManager.getInstance().switchToVSCodeTheme()
        VSCodeStartupNotifyActivity.notification.hideBalloon()
        enabled = false
    }

    override fun update(e: AnActionEvent) {
        if (!enabled) {
            e.presentation.isEnabledAndVisible = false
        }
    }
}
