package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.startup.VSCodeStartupNotifyActivity
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class DismissNotification: DumbAwareAction("Dismiss") {
    override fun actionPerformed(e: AnActionEvent) {
        VSCodeStartupNotifyActivity.notification.hideBalloon()
    }
}
