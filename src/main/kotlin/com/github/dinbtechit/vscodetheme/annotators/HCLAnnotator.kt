package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.util.ObjectUtils
import org.intellij.terraform.hcl.HCLLanguage
import org.intellij.terraform.hcl.psi.HCLDefinedMethodExpression
import org.intellij.terraform.hcl.psi.HCLMethodCallExpression
import org.intellij.terraform.hcl.psi.HCLSelectExpression
import org.intellij.terraform.hil.psi.ILDefinedMethodExpression
import org.intellij.terraform.hil.psi.ILMethodCallExpression
import org.intellij.terraform.hil.psi.ILSelectExpression

class HCLAnnotator : Annotator {
    companion object {
        val SECONDARY_KEYWORD: TextAttributesKey =
            TextAttributesKey.createTextAttributesKey(
                "HCL.SECONDARY_KEYWORD",
                DefaultLanguageHighlighterColors.KEYWORD,
            )

        val FUNCTION_CALL: TextAttributesKey =
            ObjectUtils.notNull(
                TextAttributesKey.find("HCL.FUNCTION_CALL"),
                DefaultLanguageHighlighterColors.FUNCTION_CALL,
            )

        val TYPE: TextAttributesKey =
            ObjectUtils.notNull(
                TextAttributesKey.find("HCL.TYPE"),
                DefaultLanguageHighlighterColors.KEYWORD,
            )

        private val HCL_TYPES =
            setOf(
                "string",
                "number",
                "bool",
                "list",
                "set",
                "map",
                "object",
                "tuple",
                "any",
                "true",
                "false",
            )

        private val HCL_BOOLEAN_VALUES = setOf("true", "false")

        private val HCL_CONTROL_KEYWORDS = setOf("for", "in", "if")
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // isHcl is used to determine if the language of the element is HCL (true) or HIL (false).
        val isHcl = element.language is HCLLanguage

        val (kind: TextAttributesKey, targetElement: PsiElement) =
            when {
                // 1. Check if running in batch mode, early exit.
                holder.isBatchMode -> return

                // 2. Check if the element is a method call expression. If so, we know the first
                // element _should_ be a method name (handles both HIL and HCL)
                (!isHcl && element is ILMethodCallExpression) ||
                        (isHcl && element is HCLMethodCallExpression) ->
                    FUNCTION_CALL to element.firstChild

                // 3. Check if an element is part of a provider-defined function and is NOT the
                // actual end expression. The last expression is handled by step 2.
                (!isHcl &&
                        element.parent is ILDefinedMethodExpression &&
                        element !is ILSelectExpression) ||
                        (isHcl &&
                                element.parent is HCLDefinedMethodExpression &&
                                element !is HCLSelectExpression) -> FUNCTION_CALL to element

                // 4. At this point, we only care about leaf elements as we will be doing direct
                // text comparison. As such, we can return early for non-leaf elements or for
                // comments.
                element !is LeafPsiElement || element.parent is PsiComment -> return

                // 5. At this point, we shift to direct text comparisons.
                else -> {
                    // println(element.text)
                    when (element.text) {
                        in HCL_CONTROL_KEYWORDS -> SECONDARY_KEYWORD to element
                        in HCL_TYPES -> TYPE to element
                        in HCL_BOOLEAN_VALUES -> TYPE to element
                        else -> return
                    }
                }
            }

        try {
            holder
                .newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(targetElement.textRange)
                .textAttributes(kind)
                .create()
        } catch (e: Exception) {
            throw Exception(
                """{
                     parent: ${element.parent.text},
                     element: ${element.text},
                     kind: $kind,
                     range: ${targetElement.textRange}
                     }"""
                    .trimIndent(),
                e,
            )
        }
    }
}
