package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.ecmascript6.psi.ES6ImportSpecifier
import com.intellij.lang.ecmascript6.psi.ES6ImportedBinding
import com.intellij.lang.javascript.JSTokenTypes
import com.intellij.lang.javascript.psi.JSLoopStatement
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
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

        val SECONDARY_KEYWORDS_WITH_BG: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_SECONDARY_KEYWORD_WITH_BG"), JS_KEYWORD
        )

        val FROM_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "JS.FROM_KEYWORD", JS_KEYWORD
        )

    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "null", "undefined" -> type = JS_NULL
            "console" -> type = JS_KEYWORD
            else -> {}
        }

        when (element.elementType) {
            JSTokenTypes.PACKAGE_KEYWORD, JSTokenTypes.EXPORT_KEYWORD, JSTokenTypes.IMPORT_KEYWORD,
            JSTokenTypes.REQUIRE_KEYWORD, JSTokenTypes.MODULE_KEYWORD, JSTokenTypes.RETURN_KEYWORD,
            JSTokenTypes.TRY_KEYWORD, JSTokenTypes.THROW_KEYWORD, JSTokenTypes.CATCH_KEYWORD,
            JSTokenTypes.FINALLY_KEYWORD, JSTokenTypes.YIELD_KEYWORD, JSTokenTypes.BREAK_KEYWORD,
            JSTokenTypes.IF_KEYWORD, JSTokenTypes.ELSE_KEYWORD, JSTokenTypes.SWITCH_KEYWORD,
            JSTokenTypes.CASE_KEYWORD, JSTokenTypes.DEFAULT_KEYWORD,
            JSTokenTypes.CONTINUE_KEYWORD, JSTokenTypes.WITH_KEYWORD,
            JSTokenTypes.FROM_KEYWORD, JSTokenTypes.AWAIT_KEYWORD -> type = SECONDARY_KEYWORDS

            JSTokenTypes.FOR_KEYWORD, JSTokenTypes.WHILE_KEYWORD,
            JSTokenTypes.DO_KEYWORD -> if (element.context is JSLoopStatement) type = SECONDARY_KEYWORDS
        }

        when (element.parent) {
            is ES6ImportSpecifier, is ES6ImportedBinding -> type = IMPORT_SPECIFIER
            else -> {}
        }

        return type
    }
}
