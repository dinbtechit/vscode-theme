package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement


abstract class BaseAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {

        if (holder.isBatchMode) return
        if (element is LeafPsiElement) {
            if (element.parent is PsiComment) return
            val kind: TextAttributesKey = getKeywordType(element) ?: return
            val range = TextRange(element.textRange.startOffset, element.textRange.endOffset)
            try {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(range)
                    .textAttributes(kind)
                    .create()
            } catch (e: Exception) {
                throw Exception(
                    """{
                         parent: ${element.parent.text},
                         element: ${element.text}, 
                         kind: $kind, 
                         range: $range 
                         }""".trimIndent(),
                    e
                )
            }
        }
    }

    protected abstract fun getKeywordType(element: PsiElement): TextAttributesKey?
}
