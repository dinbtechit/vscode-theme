package com.github.dinbtechit.vscodetheme.startup

import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.github.dinbtechit.vscodetheme.actions.AlwaysApplyThemeAction
import com.github.dinbtechit.vscodetheme.actions.ApplyThemeAction
import com.github.dinbtechit.vscodetheme.actions.DonateAction
import com.github.dinbtechit.vscodetheme.icons.VSCodeIcons
import com.github.dinbtechit.vscodetheme.services.ApplicationService
import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.ui.LafManager
import com.intellij.notification.Notification
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationsManagerImpl
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.BalloonLayoutData
import com.intellij.ui.awt.RelativePoint
import java.awt.Point

class VSCodeStartupNotifyActivity : StartupActivity {

    private val updateContent: String by lazy {
        //language=HTML
        """
          If you enjoy this plugin support my work by Buying a Coffee. It helps to maintain this plugin and motivates me to add more features.
        """.trimIndent()
    }

    private val switchThemeQuestion: String by lazy {
        //language=HTML
        """
          Set <b>VSCode Theme</b> as default theme.
          <br/>
          If you find this plugin useful support my work by Buying a Coffee. It helps to maintain this plugin and motivates me to add more features.
        """.trimIndent()
    }

    companion object {
        const val pluginId = "com.github.dinbtechit.vscodetheme"
        lateinit var notification: Notification
    }

    override fun runActivity(project: Project) {
        val settings = VSCodeThemeSettingsStore.instance
        val isReady = VSCodeThemeManager.getInstance().isVSCodeThemeReady();
        if (isReady && getPlugin()?.version != VSCodeThemeSettingsStore.instance.version) {
            settings.version = getPlugin()!!.version
            if (settings.alwaysApply) {
                VSCodeThemeManager.getInstance().switchToVSCodeTheme()
            } else if (settings.showNotificationOnUpdate) {
                showUpdate(project)
            }
        }
    }

    private fun updateMsg(): String {
        val plugin = getPlugin()
        return if (plugin == null) {
            "VSCode Theme installed."
        } else {
            "VSCode Theme updated to ${plugin.version}"
        }
    }

    private fun isVSCodeThemeSelected() = LafManager.getInstance().currentLookAndFeel.name == "VSCode Dark"

    private fun showUpdate(project: Project) {
        notification = createNotification(
            updateMsg(),
            if (!isVSCodeThemeSelected()) switchThemeQuestion else updateContent,
            NotificationType.INFORMATION
        )
        showFullNotification(project, notification)
    }

    private fun getPlugin(): IdeaPluginDescriptor? = PluginManagerCore.getPlugin(PluginId.getId(pluginId))

    private fun createNotification(
        title: String, content: String, type: NotificationType
    ): Notification {
        return NotificationGroupManager.getInstance()
            .getNotificationGroup("VSCode Theme Notification Group")
            .createNotification(content, type)
            .setTitle(title)
            .setIcon(VSCodeIcons.Logo).apply {
                if (!isVSCodeThemeSelected()) {
                    addAction(AlwaysApplyThemeAction())
                    addAction(ApplyThemeAction())
                }
            }
            .addAction(DonateAction())
            // .addAction(DismissNotification(isVSCodeThemeSelected()))
    }

    private fun showFullNotification(project: Project, notification: Notification) {
        try {
//            val frame = WindowManager.getInstance().getIdeFrame(project)
//            if (frame == null) {
//                notification.notify(project)
//                return
//            }
//            val bounds = frame.component.bounds
//            val target = RelativePoint(frame.component, Point(bounds.x + bounds.width, 20))
//            NotificationsManagerImpl.createBalloon(
//                frame,
//                notification,
//                true, // showCallout
//                false, // hideOnClickOutside
//                BalloonLayoutData.fullContent(),
//                ApplicationService.INSTANCE
//            ).show(target, Balloon.Position.atLeft)
            notification.notify(project)
        } catch(e: Exception) {
            notification.notify(project)
        }
    }
}
