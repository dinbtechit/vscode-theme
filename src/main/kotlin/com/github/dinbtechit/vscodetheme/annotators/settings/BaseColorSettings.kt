package com.github.dinbtechit.vscodetheme.annotators.settings

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.psi.codeStyle.DisplayPrioritySortable


abstract class BaseColorSettings : ColorSettingsPage, DisplayPrioritySortable {
    open fun getSyntaxHighlighterWithFallback(lang: Language): SyntaxHighlighter {
        return SyntaxHighlighterFactory.getSyntaxHighlighter(lang, null, null)
            ?: return SyntaxHighlighterFactory.getSyntaxHighlighter(Language.ANY, null, null)
    }
}
