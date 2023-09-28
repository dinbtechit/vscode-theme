package com.github.dinbtechit.vscodetheme.actions

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class WhatsNewAction: DumbAwareAction("What's New?") {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open("https://github.com/dinbtechit/vscode-theme/blob/main/CHANGELOG.md")
    }

}
