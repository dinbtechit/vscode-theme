package com.github.dinbtechit.vscodetheme.annotators.settings

import com.github.dinbtechit.vscodetheme.annotators.HCLAnnotator
import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.psi.codeStyle.DisplayPriority
import com.intellij.util.ObjectUtils
import java.util.*
import javax.swing.Icon

class HCLColorSettingsPage : BaseColorSettings() {
    companion object {
        private val ATTRIBUTES: Array<AttributesDescriptor?> =
            arrayOf(
                AttributesDescriptor("Secondary Keywords", HCLAnnotator.SECONDARY_KEYWORD),
                AttributesDescriptor("Function Calls", HCLAnnotator.FUNCTION_CALL),
                AttributesDescriptor("Types", HCLAnnotator.TYPE),
            )
        val DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
    }

    init {
        DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = HashMap()
        descriptors["secondaryKeyword"] = HCLAnnotator.SECONDARY_KEYWORD
        descriptors["function"] = HCLAnnotator.FUNCTION_CALL
        descriptors["type"] = HCLAnnotator.TYPE
        return descriptors
    }

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.Config
    }

    override fun getHighlighter(): SyntaxHighlighter {
        val lang: Language = ObjectUtils.notNull(Language.findLanguageByID("HCL"), Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """
               variable "server_count" {
                 description = "Number of servers"
                 type        = <type>number</type>
                 default     = 2
               }
               
               locals {
                 server_names = [
                   <secondaryKeyword>for</secondaryKeyword> i <secondaryKeyword>in</secondaryKeyword> <function>range</function>(var.server_count) : "server-${'$'}{i + 1}"
                 ]
               }
               """
            .trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? {
        return Collections.unmodifiableMap(DESCRIPTORS)
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor?> {
        return ATTRIBUTES
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "HCL (Extra)"
    }

    override fun getPriority(): DisplayPriority {
        return DisplayPriority.KEY_LANGUAGE_SETTINGS
    }
}
