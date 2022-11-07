package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement


class DefaultAnnotator : BaseAnnotator() {
    companion object {
        private val DEFAULT_KEYWORD: TextAttributesKey = DefaultLanguageHighlighterColors.KEYWORD
        val TYPE_KEYWORD: TextAttributesKey = DefaultLanguageHighlighterColors.CLASS_NAME
        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "DEFAULT_SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )
        val SECONDARY_KEYWORD_BG: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "DEFAULT_SECONDARY_KEYWORD_WITH_BG",
            DEFAULT_KEYWORD
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "new", "return" -> type = SECONDARY_KEYWORD
            "if", "else", "switch", "case", "default", "break", "continue", "assert" -> type = SECONDARY_KEYWORD
            "try", "catch", "finally", "throw" -> type = SECONDARY_KEYWORD
            "for", "while", "do", "in" -> type = SECONDARY_KEYWORD
            else -> {}
        }

        return type
    }

}
