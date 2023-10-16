package com.github.dinbtechit.vscodetheme.annotators


import com.intellij.lang.Language
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.intellij.util.ObjectUtils
import com.jetbrains.python.psi.*
import com.jetbrains.python.psi.impl.PyImportedModule
import com.jetbrains.python.psi.impl.references.PyImportReference
import com.jetbrains.python.psi.impl.stubs.PyClassElementType


class PyAnnotator : BaseAnnotator() {
    companion object {
        val DEFAULT_KEYWORD: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("DEFAULT_KEYWORD"), DefaultLanguageHighlighterColors.KEYWORD
        )
        val SECONDARY_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.SECONDARY_KEYWORD",
            DEFAULT_KEYWORD
        )

        val SECONDARY_KEYWORD_WITH_BG: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.SECONDARY_KEYWORD_WITH_BG",
            DEFAULT_KEYWORD
        )
        val SECONDARY_KEYWORD_WITH_BG_JUPYTER: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.Jupyter.SECONDARY_KEYWORD_WITH_BG",
            DEFAULT_KEYWORD
        )

        val FUNCTION_WITH_BG: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.FUNCTION_CALL",
            DefaultLanguageHighlighterColors.FUNCTION_CALL
        )
        val FUNCTION_WITH_BG_JUPYTER: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.Jupyter.FUNCTION_CALL",
            DefaultLanguageHighlighterColors.FUNCTION_CALL
        )

        val CLASS_REFERENCE_WITH_BG: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.CLASS_REFERENCE",
            DefaultLanguageHighlighterColors.CLASS_REFERENCE
        )
        val CLASS_REFERENCE_WITH_BG_JUPYTER: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.Jupyter.CLASS_REFERENCE",
            DefaultLanguageHighlighterColors.CLASS_REFERENCE
        )

        val IMPORT_REFERENCE_WITH_BG: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.IMPORTS",
            DefaultLanguageHighlighterColors.CLASS_REFERENCE
        )
        val IMPORT_REFERENCE_WITH_BG_JUPYTER: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
            "PY.Jupyter.IMPORTS",
            DefaultLanguageHighlighterColors.CLASS_REFERENCE
        )
    }

    override fun getKeywordType(element: PsiElement): TextAttributesKey? {
        var type: TextAttributesKey? = null


        if ((element.elementType is PyElementType
                    && (element.parent is PyTargetExpression
                    || element.parent is PyReferenceExpression)) && (element.parent !is PyFunction)
        ) {
            type = DefaultLanguageHighlighterColors.LOCAL_VARIABLE
        }

        if ((element.parent is PyClass && element.nextSibling is PyArgumentList)
            || element.parent.reference?.resolve().elementType is PyClassElementType
        ) {
            type = if(isJupyterNoteBook(element)) CLASS_REFERENCE_WITH_BG_JUPYTER else CLASS_REFERENCE_WITH_BG

        }

        if ((element.parent is PyFunction && element.text != "def")
            || (element.parent.reference?.resolve() is PyFunction && element.text != "def")
        ) {
            type = if(isJupyterNoteBook(element)) FUNCTION_WITH_BG_JUPYTER else FUNCTION_WITH_BG
        }


        if (element.parent.reference is PyImportReference
            || element.parent.reference?.resolve() is PyImportedModule
            || element.parent.reference?.resolve() is PyFile
        ) {
            type =  if(isJupyterNoteBook(element)) IMPORT_REFERENCE_WITH_BG_JUPYTER else IMPORT_REFERENCE_WITH_BG

        }


        when (element.text) {
            "import", "as", "in",
            "continue", "del", "assert", "break", "finally", "for", "from",
            "elif", "else", "if", "except", "pass", "raise", "return", "try", "while",
            "with" -> type = SECONDARY_KEYWORD
            "self" -> type = DEFAULT_KEYWORD
            "async", "await" -> type = if(isJupyterNoteBook(element)) SECONDARY_KEYWORD_WITH_BG_JUPYTER
            else SECONDARY_KEYWORD_WITH_BG
            else -> {}
        }

        return type
    }

    private fun isJupyterNoteBook(element: PsiElement): Boolean {
         return element.containingFile.name.contains(".ipynb")
                 || element.containingFile.language == Language.findLanguageByID("JupyterPython")
    }

}
