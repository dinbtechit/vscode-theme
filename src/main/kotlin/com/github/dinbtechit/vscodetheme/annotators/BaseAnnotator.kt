package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.codeInspection.InspectionsBundle
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil


open class BaseAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is LeafPsiElement) {
            if (PsiTreeUtil.getParentOfType(element, PsiComment::class.java) != null) {
                return
            }
            val kind: TextAttributesKey = getKeywordKind(element) ?: return
            val range = TextRange(element.textRange.startOffset, element.textRange.endOffset)
            if (kind.externalName === "JS.FROM_KEYWORD") {
                holder.newSilentAnnotation(
                    HighlightSeverity(
                        "INFORMATION",
                        500,
                        InspectionsBundle.messagePointer("information.severity"),
                        InspectionsBundle.messagePointer("information.severity.capitalized"),
                        InspectionsBundle.messagePointer("information.severity.capitalized"))
                )
                    .range(range)
                    .textAttributes(kind)
                    .create()
            } else {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(range)
                    .textAttributes(kind)
                    .create()
            }
        }
    }

    protected open fun getKeywordKind(element: PsiElement): TextAttributesKey? {
        return TextAttributesKey.createTextAttributesKey("DEFAULT_TEXT")
    }
}
