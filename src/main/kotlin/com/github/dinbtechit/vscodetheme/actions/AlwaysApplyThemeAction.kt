package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.github.dinbtechit.vscodetheme.startup.VSCodeStartupNotifyActivity
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class AlwaysApplyThemeAction: DumbAwareAction("Always") {

    override fun actionPerformed(e: AnActionEvent) {
        VSCodeThemeManager.getInstance().switchToVSCodeTheme(true)
        VSCodeStartupNotifyActivity.notification.hideBalloon()
        NotificationsManagerImpl.getNotificationsManager().expire(
            VSCodeStartupNotifyActivity.notification
        )
    }

}
