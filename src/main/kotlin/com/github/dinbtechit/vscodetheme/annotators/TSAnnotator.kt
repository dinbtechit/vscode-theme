package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
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

    override fun getKeywordKind(element: PsiElement): TextAttributesKey? {
        var kind: TextAttributesKey? = null
        when (element.text) {
            "this", "super" -> kind = THIS_SUPER
            "export", "import", "require", "default", "module", "return" -> kind = MODULE
            "debugger" -> kind = DEBUGGER
            "null", "undefined" -> kind = NULL
            "true", "false" -> kind = PRIMITIVE
            "var", "let", "const" -> kind = VAL
            "function" -> kind = FUNCTION
            "console" -> kind = CONSOLE
            "public", "protected", "private" -> kind = PRIVATE
            "declare" -> kind = DECLARE
            "type", "alias" -> kind = TYPE_ALIAS
            else -> {}
        }

        when (element.elementType?.debugName) {
            "IMPORT_KEYWORD" -> {
                println("TSAnnotator -  Kind = Module")
                kind = MODULE
            }
            "FROM_KEYWORD" -> {
                println("TSAnnotator - Kind = from ${kind}")
                kind = FROM_KEYWORD
            }
            else -> {}
        }

        return kind
    }
}
