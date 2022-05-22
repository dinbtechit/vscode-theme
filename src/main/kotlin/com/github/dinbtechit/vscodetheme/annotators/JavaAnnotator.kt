package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiTypeElement
import com.intellij.util.ObjectUtils

class JavaAnnotator : BaseAnnotator() {
    companion object {
        val DEFAULT_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
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
    }
    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "new", "return" -> type = SECONDARY_KEYWORD
            "if", "else", "switch", "case", "default", "break", "continue" -> type = SECONDARY_KEYWORD
            "try", "catch", "finally", "throw" -> type = SECONDARY_KEYWORD
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
