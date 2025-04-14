package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiTypeElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.util.ObjectUtils
import org.jetbrains.kotlin.lexer.KtTokens

class RubyAnnotator2 : BaseAnnotator() {
    companion object {
        private val DEFAULT_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_KEYWORD"), DefaultLanguageHighlighterColors.KEYWORD
        )
        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "JAVA.SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )

        val TYPE_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "JAVA.TYPE_KEYWORD",
            DEFAULT_KEYWORD
        )
        val SECONDARY_KEYWORD_BG: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_SECONDARY_KEYWORD_WITH_BG"), DefaultLanguageHighlighterColors.KEYWORD
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null

        if ((element as LeafPsiElement).elementType == KtTokens.REGULAR_STRING_PART) return null

        when (element.text) {
            "return", "as" -> type = SECONDARY_KEYWORD
            "if", "else", "when", "default", "break", "continue" -> type = SECONDARY_KEYWORD
            "try", "finally", "throw" -> type = SECONDARY_KEYWORD
            "catch" -> type = SECONDARY_KEYWORD_BG
            "for", "while", "do" -> type = SECONDARY_KEYWORD
            else -> {}
        }

        when (element.parent) {
            is PsiTypeElement -> type = TYPE_KEYWORD
            else -> {}
        }


        return type
    }

}
