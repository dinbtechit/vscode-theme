package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiTypeElement
import com.intellij.psi.util.contextOfType
import com.intellij.psi.util.elementType
import com.intellij.util.ObjectUtils

class PyAnnotator : BaseAnnotator() {
    companion object {
        val DEFAULT_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_KEYWORD"), DefaultLanguageHighlighterColors.KEYWORD
        )
        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )

        val TYPE_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.TYPE_KEYWORD",
            DEFAULT_KEYWORD
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "import", "as", "in",
            "async", "continue", "del", "assert", "break", "finally", "for", "from",
            "elif", "else", "if", "except", "pass", "raise", "return", "try", "while",
            "with" -> type = SECONDARY_KEYWORD
            else -> {}
        }

        return type
    }

}
