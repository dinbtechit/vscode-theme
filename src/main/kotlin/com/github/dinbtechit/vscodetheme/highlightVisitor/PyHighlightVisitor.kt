package com.github.dinbtechit.vscodetheme.highlightVisitor

import com.github.dinbtechit.vscodetheme.annotators.PyAnnotator
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.lang.ASTNode
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.elementType
import com.jetbrains.python.PyTokenTypes
import com.jetbrains.python.PythonFileType

class PyHighlightVisitor : HighlightVisitor {
    private var highlightHolder: HighlightInfoHolder? = null
    override fun suitableForFile(p0: PsiFile): Boolean {
        return p0.fileType is PythonFileType
    }

    override fun visit(element: PsiElement) {
        if (highlightHolder == null) return
        if(element.elementType == PyTokenTypes.RETURN_KEYWORD) {
           highlightHolder!!.add(createInfoHighlight(element.node, PyAnnotator.SECONDARY_KEYWORD_WITH_BG))
        }
    }

    private fun createInfoHighlight(
        node: ASTNode,
        textAttributesKey: TextAttributesKey,
    ): HighlightInfo? {
        with(node.textRange) {
            return HighlightInfo.newHighlightInfo(HighlightInfoType.INFORMATION)
                .range(this)
                .textAttributes(textAttributesKey)
                .severity(HighlightSeverity.INFORMATION)
                .create()
        }
    }

    override fun analyze(
        psiFile: PsiFile,
        updateWholeFile: Boolean,
        holder: HighlightInfoHolder,
        action: Runnable
    ): Boolean {
        highlightHolder = holder
        try {
            action.run()
        } catch (_: Throwable) {
        } finally {
            highlightHolder = null
        }
        return true
    }

    override fun clone(): HighlightVisitor {
        return PyHighlightVisitor()
    }
}