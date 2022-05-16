package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.ecmascript6.psi.ES6ImportSpecifier
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils


class JSAnnotator : BaseAnnotator() {
    companion object {
        val JS_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.KEYWORD"),
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val JS_IDENTIFIER: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.PARAMETER"),
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )
        val JS_NUMBER: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.NUMBER"),
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val THIS_SUPER: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.THIS_SUPER", JS_KEYWORD)
        val IMPORT_SPECIFIER: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.IMPORT_SPECIFIER", JS_IDENTIFIER)
        val MODULE: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.MODULE_KEYWORD", JS_KEYWORD)
        val FROM_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.FROM_KEYWORD")
        val DEBUGGER: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.DEBUGGER_STMT", JS_KEYWORD)
        val CONSOLE: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.CONSOLE", JS_KEYWORD)
        val NULL: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.NULL_UNDEFINED", JS_NUMBER)
        val VAL: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.VAR_DEF", JS_KEYWORD)
        val FUNCTION: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.FUNCTION", JS_KEYWORD)
        val PRIMITIVE: TextAttributesKey = TextAttributesKey.createTextAttributesKey("JS.PRIMITIVE", JS_NUMBER)
    }

    override fun getKeywordKind(element: PsiElement): TextAttributesKey? {
        var kind: TextAttributesKey? = null
        when (element.text) {
            "this", "super" -> kind = THIS_SUPER
            "export", "import", "require", "module", "return" -> kind = MODULE
            "from" -> kind = FROM_KEYWORD
            "debugger" -> kind = DEBUGGER
            "null", "undefined" -> kind = NULL
            "true", "false" -> kind = PRIMITIVE
            "var", "let", "const" -> kind = VAL
            "function" -> kind = FUNCTION
            "console" -> kind = CONSOLE
            else -> {}
        }

        when (element.parent) {
            is ES6ImportSpecifier -> kind = IMPORT_SPECIFIER
            else -> {}
        }

        return kind
    }
}
