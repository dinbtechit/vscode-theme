package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.startup.VSCodeStartupNotifyActivity
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class DismissNotification(private val expire: Boolean = false) : DumbAwareAction("Dismiss") {
    override fun actionPerformed(e: AnActionEvent) {
        VSCodeStartupNotifyActivity.notification.hideBalloon()
        if (expire) {
            NotificationsManagerImpl.getNotificationsManager().expire(
                VSCodeStartupNotifyActivity.notification
            )
        }
    }
}
