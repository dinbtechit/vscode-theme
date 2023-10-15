package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.VSCodeTheme
import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class AlwaysApplyThemeAction(
    text: String = "Set as Default",
    private val vscodeTheme: String = VSCodeTheme.DARK
) : DumbAwareAction(text) {

    override fun actionPerformed(e: AnActionEvent) {
        VSCodeThemeManager.getInstance().switchToVSCodeTheme(true, vscodeTheme)
        //VSCodeStartupNotifyActivity.notification.hideBalloon()
        /*NotificationsManagerImpl.getNotificationsManager().expire(
            VSCodeStartupNotifyActivity.notification
        )*/
    }

}
