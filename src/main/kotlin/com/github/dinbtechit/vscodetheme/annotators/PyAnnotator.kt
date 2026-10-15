package com.github.dinbtechit.vscodetheme.annotators


import com.intellij.lang.Language
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiInvalidElementAccessException
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.elementType
import com.intellij.util.ObjectUtils
import com.jetbrains.python.PyTokenTypes
import com.jetbrains.python.psi.*
import com.jetbrains.python.psi.impl.PyImportedModule
import com.jetbrains.python.psi.impl.references.PyImportReference


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
        if (!element.isValid) return null

        // Leave function definitions and special (dunder) names to the Python highlighter so the
        // "Function Definition", "Nested Function Definition" and "Special Names" options take effect
        val parent = element.parent
        if (parent is PyFunction && element.elementType == PyTokenTypes.IDENTIFIER) return null
        if (element.text.let { it.length > 4 && it.startsWith("__") && it.endsWith("__") }) return null

        var type: TextAttributesKey? = null
        val resolved = parent.safeResolve()

        if ((element.elementType is PyElementType
                    && (parent is PyTargetExpression
                    || parent is PyReferenceExpression)) && (parent !is PyFunction)
        ) {
            type = DefaultLanguageHighlighterColors.LOCAL_VARIABLE
        }

        if ((parent is PyClass && element.nextSibling is PyArgumentList)
            || resolved is PyClass
        ) {
            type = if(isJupyterNoteBook(element)) CLASS_REFERENCE_WITH_BG_JUPYTER else CLASS_REFERENCE_WITH_BG

        }

        if (resolved is PyFunction && element.text != "def") {
            type = if(isJupyterNoteBook(element)) FUNCTION_WITH_BG_JUPYTER else FUNCTION_WITH_BG
        }


        if (parent.reference is PyImportReference
            || resolved is PyImportedModule
            || resolved is PyFile
        ) {
            type =  if(isJupyterNoteBook(element)) IMPORT_REFERENCE_WITH_BG_JUPYTER else IMPORT_REFERENCE_WITH_BG

        }


        when (element.text) {
            "import", "as", "in",
            "continue", "del", "assert", "break", "finally", "for", "from", "yield",
            "elif", "else", "if", "except", "pass", "raise", "return", "try", "while",
            "with" -> type = SECONDARY_KEYWORD
            "self" -> type = DEFAULT_KEYWORD
            "await" ->
                type =
                    if (isJupyterNoteBook(element)) SECONDARY_KEYWORD_WITH_BG_JUPYTER
                    else SECONDARY_KEYWORD_WITH_BG
            "async" -> {
                type =
                    generateSequence(element.nextSibling) { it.nextSibling }
                        .firstOrNull { it !is PsiWhiteSpace }
                        ?.let { nextWord ->
                            when {
                                nextWord.elementType == PyTokenTypes.DEF_KEYWORD -> DEFAULT_KEYWORD
                                isJupyterNoteBook(element) -> SECONDARY_KEYWORD_WITH_BG_JUPYTER
                                else -> SECONDARY_KEYWORD_WITH_BG
                            }
                        }
                        ?: if (isJupyterNoteBook(element)) SECONDARY_KEYWORD_WITH_BG_JUPYTER
                        else SECONDARY_KEYWORD_WITH_BG
            }
            else -> {}
        }

        when(element.elementType) {
            PyTokenTypes.RETURN_KEYWORD -> type = SECONDARY_KEYWORD
        }

        return type
    }

    // Third-party references (e.g. Django) may throw while resolving against PSI that was invalidated mid-highlighting
    private fun PsiElement.safeResolve(): PsiElement? = try {
        reference?.resolve()?.takeIf { it.isValid }
    } catch (_: PsiInvalidElementAccessException) {
        null
    }

    private fun isJupyterNoteBook(element: PsiElement): Boolean {
         return element.containingFile.name.contains(".ipynb")
                 || element.containingFile.language == Language.findLanguageByID("JupyterPython")
    }
}
