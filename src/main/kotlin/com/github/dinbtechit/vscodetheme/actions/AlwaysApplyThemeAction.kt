package com.github.dinbtechit.vscodetheme.actions

import com.intellij.icons.AllIcons
import com.intellij.ide.actions.QuickChangeLookAndFeel
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class AlwaysApplyThemeAction(
    text: String = "Set as Default",
) : DumbAwareAction(text, "", AllIcons.General.Settings) {

    override fun actionPerformed(e: AnActionEvent) {
        val action = QuickChangeLookAndFeel()
        action.actionPerformed(e)
        /*NotificationsManagerImpl.getNotificationsManager().expire(
            VSCodeStartupNotifyActivity.notification
        )*/
    }

}
