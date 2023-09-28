package com.github.dinbtechit.vscodetheme.startup

import com.github.dinbtechit.vscodetheme.VSCodeTheme
import com.github.dinbtechit.vscodetheme.VSCodeThemeManager
import com.github.dinbtechit.vscodetheme.actions.AlwaysApplyThemeAction
import com.github.dinbtechit.vscodetheme.actions.DonateAction
import com.github.dinbtechit.vscodetheme.actions.StarGithubRepoAction
import com.github.dinbtechit.vscodetheme.actions.WhatsNewAction
import com.github.dinbtechit.vscodetheme.icons.VSCodeIcons
import com.github.dinbtechit.vscodetheme.settings.VSCodeThemeSettingsStore
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.ide.ui.LafManager
import com.intellij.notification.Notification
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity


class VSCodeStartupNotifyActivity : StartupActivity {

    enum class DisplayActionType {
        DONATION_ONLY,
        SHOW_ALL_THEMES_FOR_DEFAULT,
        SHOW_NEW_DARK_MODERN_THEME
    }

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
          Set one of the <b>VSCode Theme(s)</b> as a default theme.
          <br/>
          $updateContent
        """.trimIndent()
    }

    private val tryNewDarkModernThemeQuestion: String by lazy {
        //language=HTML
        """
          Would you like to set the <b>VSCode Dark Modern</b> as a default theme?
          <br/>
          $updateContent
        """.trimIndent()
    }

    companion object {
        const val pluginId = "com.github.dinbtechit.vscodetheme"
        lateinit var notification: Notification
        var displayActionType: DisplayActionType = DisplayActionType.DONATION_ONLY
    }

    override fun runActivity(project: Project) {
        val settings = VSCodeThemeSettingsStore.instance
        val isReady = VSCodeThemeManager.getInstance().isVSCodeThemeReady()
        if (isReady && getPlugin()?.version != VSCodeThemeSettingsStore.instance.version) {
            settings.version = getPlugin()!!.version
            if (settings.alwaysApply) {
                if (settings.themeName != VSCodeTheme.UNKNOWN) {
                    VSCodeThemeManager.getInstance().switchToVSCodeTheme(selectedVSCodeTheme = settings.themeName)
                }
                showNotificationPopup(project)
            } else if (settings.showNotificationOnUpdate) {
                showNotificationPopup(project)
            }
        }
        // Uncomment for Testing popup
        // showNotificationPopup(project)

    }

    private fun updateMsg(): String {
        val plugin = getPlugin()
        return if (plugin == null) {
            "VSCode Theme installed."
        } else {
            "VSCode Theme updated to ${plugin.version}"
        }
    }

    private fun isVSCodeThemeSelected() = LafManager.getInstance().currentLookAndFeel.name == VSCodeTheme.DARK.theme
    private fun isVSCodeDarkModernThemeSelected() =
        LafManager.getInstance().currentLookAndFeel.name == VSCodeTheme.DARK_MODERN.theme

    private fun showNotificationPopup(project: Project) {
        notification = createNotification(
            updateMsg(),
            notificationContent(),
            NotificationType.INFORMATION
        )
        showFullNotification(project, notification)
    }

    private fun notificationContent(): String {
        if (!isVSCodeThemeSelected() && !isVSCodeDarkModernThemeSelected()) {
            displayActionType = DisplayActionType.SHOW_ALL_THEMES_FOR_DEFAULT
            return switchThemeQuestion
        } else if (isVSCodeThemeSelected()) {
            displayActionType = DisplayActionType.SHOW_NEW_DARK_MODERN_THEME
            return tryNewDarkModernThemeQuestion
        }
        displayActionType = DisplayActionType.DONATION_ONLY
        return updateContent
    }

    private fun getPlugin(): IdeaPluginDescriptor? = PluginManagerCore.getPlugin(PluginId.getId(pluginId))

    private fun createNotification(
        title: String, content: String, type: NotificationType
    ): Notification {
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup("VSCode Theme Notification Group")
            .createNotification(content, type)
            .setTitle(title)
            .setIcon(VSCodeIcons.Logo).apply {
                if (displayActionType == DisplayActionType.SHOW_ALL_THEMES_FOR_DEFAULT) {
                    addAction(DefaultActionGroup("Show All", false).apply {
                        add(
                            AlwaysApplyThemeAction(
                                text = VSCodeTheme.DARK_MODERN.theme,
                                vscodeTheme = VSCodeTheme.DARK_MODERN
                            )
                        )
                        add(AlwaysApplyThemeAction(text = VSCodeTheme.DARK.theme, vscodeTheme = VSCodeTheme.DARK))
                    })
                } else if (displayActionType == DisplayActionType.SHOW_NEW_DARK_MODERN_THEME) {
                    addAction(AlwaysApplyThemeAction(text = "Switch Now", vscodeTheme = VSCodeTheme.DARK_MODERN))
                }
            }
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
