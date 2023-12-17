package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.util.ObjectUtils
import com.jetbrains.lang.dart.DartTokenTypes
import com.jetbrains.lang.dart.highlight.DartSyntaxHighlighterColors
import com.jetbrains.lang.dart.psi.DartStringLiteralExpression


class DartAnnotator : BaseAnnotator() {
    companion object {
        val KEYWORD: TextAttributesKey = DartSyntaxHighlighterColors.KEYWORD
        val TYPE_KEYWORD: TextAttributesKey = DartSyntaxHighlighterColors.TYPE_PARAMETER
        val SECONDARY_KEYWORD: TextAttributesKey =
            TextAttributesKey.createTextAttributesKey("DART_SECONDARY_KEYWORD", KEYWORD)
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
            "*" -> if (element.prevSibling != null) {
                when (element.prevSibling.text) {
                    "await", "sync", "async", "yield" -> type = SECONDARY_KEYWORD_WITH_BG
                }
            }

            else -> {}
        }

        when (element.elementType) {
            DartTokenTypes.NEW, DartTokenTypes.RETURN, DartTokenTypes.IF, DartTokenTypes.ELSE,
            DartTokenTypes.IMPORT,
            DartTokenTypes.SWITCH, DartTokenTypes.CASE, DartTokenTypes.DEFAULT,
            DartTokenTypes.BREAK, DartTokenTypes.CONTINUE, DartTokenTypes.ASSERT,
            DartTokenTypes.FOR, DartTokenTypes.WHILE, DartTokenTypes.DO,
            DartTokenTypes.IN, DartTokenTypes.AWAIT, DartTokenTypes.ASYNC, DartTokenTypes.YIELD, DartTokenTypes.ON,
            DartTokenTypes.TRY, DartTokenTypes.CATCH, DartTokenTypes.FINALLY, DartTokenTypes.THROW, DartTokenTypes.RETHROW
            -> type = SECONDARY_KEYWORD
        }

        return type
    }

}
