package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.ecmascript6.psi.ES6FromClause
import com.intellij.lang.ecmascript6.psi.ES6ImportSpecifier
import com.intellij.lang.ecmascript6.psi.ES6ImportSpecifierAlias
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils

class JSAnnotator : BaseAnnotator() {
    companion object {
        val JS_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.KEYWORD"), DefaultLanguageHighlighterColors.KEYWORD
        )
        val JS_NUMBER: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.NUMBER"), JS_KEYWORD
        )

        val JS_NULL: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.NULL_UNDEFINED"), JS_KEYWORD
        )

        val IMPORT_SPECIFIER: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "JS.IMPORT_SPECIFIER", JS_KEYWORD
        )
        val SECONDARY_KEYWORDS: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "JS.SECONDARY_KEYWORDS", JS_KEYWORD
        )

        val FROM_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "JS.FROM_KEYWORD", JS_KEYWORD
        )

    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "package", "export", "import", "require", "module", "return" -> type = SECONDARY_KEYWORDS
            "try", "throw", "catch", "finally", "yield", "break", "continue", "with",
            "if", "else", "switch", "case", "default" -> type = SECONDARY_KEYWORDS
            "for", "while", "do" -> type = SECONDARY_KEYWORDS
            "from" -> if (element.parent is ES6FromClause) type = FROM_KEYWORD
            "null", "undefined" -> type = JS_NULL
            "console" -> type = JS_KEYWORD
            else -> {}
        }

        when (element.parent) {
            is ES6ImportSpecifier -> type = IMPORT_SPECIFIER
            else -> {}
        }

        return type
    }
}
