package com.github.dinbtechit.vscodetheme.annotators

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.util.ObjectUtils
import org.intellij.terraform.hcl.psi.HCLDefinedMethodExpression
import org.intellij.terraform.hcl.psi.HCLMethodCallExpression
import org.intellij.terraform.hcl.psi.HCLSelectExpression
import org.intellij.terraform.hil.psi.ILDefinedMethodExpression
import org.intellij.terraform.hil.psi.ILMethodCallExpression
import org.intellij.terraform.hil.psi.ILSelectExpression

class HILAnnotator : Annotator {
    companion object {
        val DEFAULT_KEYWORD: TextAttributesKey =
            ObjectUtils.notNull(
                TextAttributesKey.find("DEFAULT_KEYWORD"),
                DefaultLanguageHighlighterColors.KEYWORD,
            )
        val SECONDARY_KEYWORD: TextAttributesKey =
            TextAttributesKey.createTextAttributesKey("DEFAULT_SECONDARY_KEYWORD", DEFAULT_KEYWORD)

        val SECONDARY_KEYWORD_BG: TextAttributesKey =
            ObjectUtils.notNull(
                TextAttributesKey.find("DEFAULT_SECONDARY_KEYWORD_WITH_BG"),
                DefaultLanguageHighlighterColors.KEYWORD,
            )

        val DEFAULT_FUNCTION_DECLARATION: TextAttributesKey =
            ObjectUtils.notNull(
                TextAttributesKey.find("DEFAULT_FUNCTION_DECLARATION"),
                DefaultLanguageHighlighterColors.KEYWORD,
            )

        // All HCL data type keywords except for `null` as in VSCode, `null` is colored as a primary
        // keyword.
        private val HCL_DATA_TYPES =
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

        private val HCL_CONTROL_FLOW_KEYWORDS = setOf("for", "in", "if")
    }

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val lang = element.language
        println()
        val (kind: TextAttributesKey, targetElement: PsiElement) =
            when {
                // 1. Check if running in batch mode, early exit.
                holder.isBatchMode -> return

                // 2. Check if the element is a method call expression. If so, we know the first
                // element _should_ be a method name
                element is ILMethodCallExpression ->
                    DEFAULT_FUNCTION_DECLARATION to element.firstChild

                // 3. Check if an element is part of a provider-defined function and is NOT the
                // actual end expression. The last expression is handled by step 2.
                element.parent is ILDefinedMethodExpression && element !is ILSelectExpression -> {
                    val text = element.text
                    DEFAULT_FUNCTION_DECLARATION to element
                }

                element !is LeafPsiElement || element.parent is PsiComment -> return

                else ->
                    when (element.text) {
                        in HCL_DATA_TYPES -> SECONDARY_KEYWORD to element
                        in HCL_CONTROL_FLOW_KEYWORDS -> SECONDARY_KEYWORD to element
                        else -> return
                    }
            }

        val range =
            TextRange(targetElement.textRange.startOffset, targetElement.textRange.endOffset)

        try {
            holder
                .newSilentAnnotation(HighlightSeverity.INFORMATION)
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
                     }"""
                    .trimIndent(),
                e,
            )
        }
    }
}
