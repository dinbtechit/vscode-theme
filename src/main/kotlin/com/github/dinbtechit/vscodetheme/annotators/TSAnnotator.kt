package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.util.ObjectUtils


class TSAnnotator : BaseAnnotator() {
    companion object {
        val KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("TS.KEYWORD"),
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val NUMBER: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("TS.NUMBER"),
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val THIS_SUPER = TextAttributesKey.createTextAttributesKey("TS.THIS_SUPER", KEYWORD)
        val MODULE = TextAttributesKey.createTextAttributesKey("TS.MODULE_KEYWORD", KEYWORD)
        val FROM_KEYWORD = TextAttributesKey.createTextAttributesKey("TS.FROM_KEYWORD")
        val DEBUGGER = TextAttributesKey.createTextAttributesKey("TS.DEBUGGER_STMT", KEYWORD)
        val CONSOLE = TextAttributesKey.createTextAttributesKey("TS.CONSOLE", KEYWORD)
        val NULL = TextAttributesKey.createTextAttributesKey("TS.NULL_UNDEFINED", NUMBER)
        val VAL = TextAttributesKey.createTextAttributesKey("TS.VAR_DEF", KEYWORD)
        val FUNCTION = TextAttributesKey.createTextAttributesKey("TS.FUNCTION", KEYWORD)
        val PRIVATE = TextAttributesKey.createTextAttributesKey("TS.PRIVATE_PUBLIC", KEYWORD)
        val DECLARE = TextAttributesKey.createTextAttributesKey("TS.DECLARE", KEYWORD)
        val TYPE_ALIAS = TextAttributesKey.createTextAttributesKey("TS.TYPE_ALIAS", KEYWORD)
        val PRIMITIVE = TextAttributesKey.createTextAttributesKey("TS.PRIMITIVE", NUMBER)
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null
        when (element.text) {
            "this", "super" -> type = THIS_SUPER
            "export", "import", "require", "default", "module", "return" -> type = MODULE
            "from" -> type = FROM_KEYWORD
            "debugger" -> type = DEBUGGER
            "null", "undefined" -> type = NULL
            "true", "false" -> type = PRIMITIVE
            "var", "let", "const" -> type = VAL
            "function" -> type = FUNCTION
            "console" -> type = CONSOLE
            "public", "protected", "private" -> type = PRIVATE
            "declare" -> type = DECLARE
            "type", "alias" -> type = TYPE_ALIAS
            else -> {}
        }

        return type
    }
}
