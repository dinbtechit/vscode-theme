package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.icons.VSCodeIcons
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class StarGithubRepoAction: DumbAwareAction("Star Repo", "", VSCodeIcons.GitHub) {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open("https://github.com/dinbtechit/vscode-theme")
    }

}
