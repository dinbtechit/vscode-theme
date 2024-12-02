package com.github.dinbtechit.vscodetheme.startup

import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.github.dinbtechit.vscodetheme.actions.AlwaysApplyThemeAction
import com.github.dinbtechit.vscodetheme.actions.DonateAction
import com.github.dinbtechit.vscodetheme.actions.StarGithubRepoAction
import com.github.dinbtechit.vscodetheme.actions.WhatsNewAction
import com.github.dinbtechit.vscodetheme.icons.VSCodeIcons
import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.notification.Notification
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity


object DisplayActionType {
    const val DONATION_ONLY = "DONATION_ONLY"
    const val SHOW_ALL_THEMES_FOR_DEFAULT = "SHOW_ALL_THEMES_FOR_DEFAULT"
}

class VSCodeStartupNotifyActivity : ProjectActivity {


    private val updateContent: String by lazy {
        //language=HTML
        """
         If you find this plugin useful consider sponsoring its development to ensure that the project is 
         actively maintained and improved.
        """.trimIndent()
    }

    private val switchThemeQuestion: String by lazy {
        //language=HTML
        """
          Select <b>VSCode Theme(s)</b> from themes.
          <br/>
          $updateContent
        """.trimIndent()
    }

    object Util {
        lateinit var notification: Notification
        var displayActionType = DisplayActionType.DONATION_ONLY
    }

    companion object {
        const val pluginId = "com.github.dinbtechit.vscodetheme"
    }

    override suspend fun execute(project: Project) {
        val settings = VSCodeThemeSettingsStore.instance
        val isReady = VSCodeThemeManager.getInstance().isVSCodeThemeReady()
        if (isReady && getPlugin()?.version != VSCodeThemeSettingsStore.instance.version) {
            settings.version = getPlugin()!!.version
            showNotificationPopup(project)
        }
        // Uncomment for Testing popup
        //showNotificationPopup(project)
    }

    private fun updateMsg(): String {
        val plugin = getPlugin()
        return if (plugin == null) {
            "VSCode Theme installed."
        } else {
            "VSCode Theme updated to ${plugin.version}"
        }
    }

    private fun showNotificationPopup(project: Project) {
        Util.notification = createNotification(
            updateMsg(),
            notificationContent(),
            NotificationType.INFORMATION
        )
        showFullNotification(project, Util.notification)
    }

    private fun notificationContent(): String {
        Util.displayActionType = DisplayActionType.SHOW_ALL_THEMES_FOR_DEFAULT
        return switchThemeQuestion
    }

    private fun getPlugin(): IdeaPluginDescriptor? = PluginManagerCore.getPlugin(PluginId.getId(pluginId))

    private fun createNotification(
        title: String, content: String, type: NotificationType
    ): Notification {
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup("VSCode Theme Notification Group")
            .createNotification(content, type)
            .setTitle(title)
            .setIcon(VSCodeIcons.Logo)
            .addAction(AlwaysApplyThemeAction(text = "Themes"))
            .addAction(DonateAction())
            .addAction(StarGithubRepoAction())
            .addAction(WhatsNewAction())
        // .addAction(DismissNotification(isVSCodeThemeSelected()))

        return notification
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
        } catch (e: Exception) {
            notification.notify(project)
        }
    }
}
