package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.jetbrains.lang.dart.analyzer.DartServerData.DartHighlightRegion
import com.jetbrains.lang.dart.ide.annotator.DartAnnotator


abstract class BaseAnnotator : Annotator {

    companion object {
        val DART_HIGHLIGHTING = Key.create<List<DartHighlightRegion>>("DART_HIGHLIGHTING")
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        if (holder.isBatchMode) return
        val session = holder.currentAnnotationSession
        val notYetAppliedHighlighting: List<DartHighlightRegion>? = session.getUserData(DART_HIGHLIGHTING)
        if (element is LeafPsiElement) {
            if (element.parent is PsiComment) return
            val kind: TextAttributesKey = getKeywordType(element) ?: return
            val range = TextRange(element.textRange.startOffset, element.textRange.endOffset)
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(range)
                .textAttributes(kind)
                .needsUpdateOnTyping()
                .create()
        }

    }

    protected abstract fun getKeywordType(element: PsiElement): TextAttributesKey?
}
