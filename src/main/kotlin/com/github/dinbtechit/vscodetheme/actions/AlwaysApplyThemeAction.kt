package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.VSCodeTheme
import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class AlwaysApplyThemeAction(
    text: String = "Set as Default",
) : DumbAwareAction(text, "", AllIcons.General.Settings) {

    override fun actionPerformed(e: AnActionEvent) {
        VSCodeThemeManager.getInstance().showThemePopUp()
        //VSCodeStartupNotifyActivity.notification.hideBalloon()
        /*NotificationsManagerImpl.getNotificationsManager().expire(
            VSCodeStartupNotifyActivity.notification
        )*/
    }

}
