package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils
import com.jetbrains.lang.dart.highlight.DartSyntaxHighlighterColors


class DartAnnotator : BaseAnnotator() {
    companion object {
        val KEYWORD: TextAttributesKey = DartSyntaxHighlighterColors.KEYWORD
        val SECONDARY_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DART_SECONDARY_KEYWORD"), KEYWORD
        )
        val SECONDARY_KEYWORD_WITH_BG: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DART_SECONDARY_KEYWORD_WITH_BG"), KEYWORD
        )
    }
    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "new", "return" -> type = SECONDARY_KEYWORD
            "if", "else", "switch", "case", "default", "break", "continue", "assert" -> type = SECONDARY_KEYWORD
            "try", "catch", "finally", "throw", "rethrow" -> type = SECONDARY_KEYWORD
            "for", "while", "do", "in" -> type = SECONDARY_KEYWORD
            "await", "sync", "async", "yield", "on" -> type = SECONDARY_KEYWORD_WITH_BG
            "*" -> when(element.prevSibling.text) {
                "await", "sync", "async", "yield" -> type = SECONDARY_KEYWORD_WITH_BG
            }
            else -> {}
        }


        return type
    }

}
