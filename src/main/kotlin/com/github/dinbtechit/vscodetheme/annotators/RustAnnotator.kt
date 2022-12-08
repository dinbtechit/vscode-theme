package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils


class RustAnnotator : BaseAnnotator() {
    companion object {
        private val DEFAULT_KEYWORD: TextAttributesKey = DefaultLanguageHighlighterColors.KEYWORD
        val TYPE_KEYWORD: TextAttributesKey = DefaultLanguageHighlighterColors.CLASS_NAME
        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "DEFAULT_SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )
        val SECONDARY_KEYWORD_BG: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_SECONDARY_KEYWORD_WITH_BG"), DefaultLanguageHighlighterColors.KEYWORD
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "mut", "match","return" -> type = SECONDARY_KEYWORD
            "if", "else", "switch", "case", "default", "break", "continue", "as" -> type = SECONDARY_KEYWORD
            "try", "catch", "finally", "throw" -> type = SECONDARY_KEYWORD
            "for", "while", "loop", "in" -> type = SECONDARY_KEYWORD
            "await", "async", "yield", "on" -> type = SECONDARY_KEYWORD_BG
            "*" -> if (element.prevSibling != null) {
                when (element.prevSibling.text) {
                    "await", "sync", "async", "yield" -> type = SECONDARY_KEYWORD_BG
                }
            }
            else -> {}
        }

        return type
    }

}
