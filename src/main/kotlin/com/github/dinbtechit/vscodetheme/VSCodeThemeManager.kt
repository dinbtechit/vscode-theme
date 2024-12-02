package com.github.dinbtechit.vscodetheme

import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.impl.SimpleDataContext
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.ProjectManager
import org.jetbrains.kotlin.console.actions.logError

/*enum class VSCodeTheme(val theme: String) {
    UNKNOWN("UNKNOWN"),
    DARK("VSCode Dark"),
    DARK_MODERN("VSCode Dark Modern");
}*/

object VSCodeTheme {
    const val UNKNOWN = "UNKNOWN"
    const val DARK = "VSCode Dark"
    const val DARK_MODERN = "VSCode Dark Modern"
}

class VSCodeThemeManager {
    companion object {
        const val pluginId = "com.github.dinbtechit.vscodetheme"
        private var myObject: VSCodeThemeManager? = null
        fun getInstance(): VSCodeThemeManager {
            if (myObject == null) {
                myObject = VSCodeThemeManager()
            }
            return myObject!!
        }
    }

    private fun getPlugin(): IdeaPluginDescriptor? = PluginManagerCore.getPlugin(PluginId.getId(pluginId))
    fun isVSCodeThemeReady(): Boolean {
      return getPlugin() != null
    }

    fun showThemePopUp() {
        //ShowSettingsUtil.getInstance().showSettingsDialog(null, "preferences.lookFeel")
        val action = ActionManager.getInstance().getAction("ChangeLaf")
        if (action != null) {
            val openProjects = ProjectManager.getInstance().openProjects
            if (openProjects.isNullOrEmpty()) return

            val dataContext: DataContext = SimpleDataContext.getProjectContext(openProjects.first())

            val presentation = Presentation()
            val event = AnActionEvent(
                null,
                dataContext,
                "",
                presentation,
                ActionManager.getInstance(),
                0
            )

            // Perform the action
            action.actionPerformed(event)
        }
        else {
            logger<VSCodeThemeManager>().warn("ChangeLaf action was not found - Unable to show all themes")
        }
    }


}
