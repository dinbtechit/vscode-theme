package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils
import com.jetbrains.php.lang.psi.elements.*

class PhpAnnotator : BaseAnnotator() {
    companion object {
        val DEFAULT_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_KEYWORD"), DefaultLanguageHighlighterColors.KEYWORD
        )
        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "DEFAULT_SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )

    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        if (element is PsiComment) return null
        when(element.text) {
            "{", "}", "(", ")", ";", ":" -> return null
        }
        when (element.context) {
            is ControlStatement -> type = SECONDARY_KEYWORD
            is PhpSwitch,
            is Else, is PhpBreak, is Try, is PhpReturn -> type = SECONDARY_KEYWORD
            else -> {}
        }

        return type
    }

}
