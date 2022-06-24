package com.github.dinbtechit.vscodetheme.actions

import com.github.dinbtechit.vscodetheme.icons.VSCodeIcons
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class DonateAction: DumbAwareAction("Donate", "", VSCodeIcons.BuyMeCoffee) {

    override fun actionPerformed(e: AnActionEvent) {
        BrowserUtil.open("https://www.buymeacoffee.com/dinbtechit")
    }

}
