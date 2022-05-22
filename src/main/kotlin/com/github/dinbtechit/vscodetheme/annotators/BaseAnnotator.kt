package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


abstract class BaseAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            if (element is LeafPsiElement) {
                if (PsiTreeUtil.getParentOfType(element, PsiComment::class.java) != null) {
                    return@launch
                }
                val kind: TextAttributesKey = getKeywordType(element) ?: return@launch
                val range = TextRange(element.textRange.startOffset, element.textRange.endOffset)
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(range)
                    .textAttributes(kind)
                    .create()
            }
        }
    }

    protected abstract fun getKeywordType(element: PsiElement): TextAttributesKey?
}
