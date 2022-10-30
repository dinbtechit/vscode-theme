package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils
import com.jetbrains.lang.dart.highlight.DartSyntaxHighlighterColors
import com.jetbrains.lang.dart.psi.DartStringLiteralExpression


class DartAnnotator : BaseAnnotator() {
    companion object {
        val KEYWORD: TextAttributesKey = DartSyntaxHighlighterColors.KEYWORD
        val TYPE_KEYWORD: TextAttributesKey = DartSyntaxHighlighterColors.TYPE_PARAMETER
        val SECONDARY_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DART_SECONDARY_KEYWORD"), KEYWORD
        )
        val SECONDARY_KEYWORD_WITH_BG: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DART_SECONDARY_KEYWORD_WITH_BG"), KEYWORD
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        if (element.parent is DartStringLiteralExpression) return null
        when (element.text) {
            "required" -> type = KEYWORD
            "Function" -> type = TYPE_KEYWORD
            "new", "return" -> type = SECONDARY_KEYWORD
            "if", "else", "switch", "case", "default", "break", "continue", "assert" -> type = SECONDARY_KEYWORD
            "try", "catch", "finally", "throw", "rethrow" -> type = SECONDARY_KEYWORD
            "for", "while", "do", "in" -> type = SECONDARY_KEYWORD
            "await", "sync", "async", "yield", "on" -> type = SECONDARY_KEYWORD_WITH_BG
            "*" -> if (element.prevSibling != null) {
                when (element.prevSibling.text) {
                    "await", "sync", "async", "yield" -> type = SECONDARY_KEYWORD_WITH_BG
                }
            }
            else -> {}
        }

        return type
    }

}
