package com.github.dinbtechit.vscodetheme.annotators.settings


import com.github.dinbtechit.vscodetheme.annotators.PyAnnotator
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.jetbrains.python.highlighting.PythonColorsPage
import gnu.trove.THashMap
import java.util.*


class PyColorSettingsPage : PythonColorsPage() {
    companion object {
        private val ATTRIBUTES: Array<AttributesDescriptor?> = arrayOf(
            AttributesDescriptor("SecondaryKeywords", PyAnnotator.SECONDARY_KEYWORD),
        )
        val DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
    }

    init {
        DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["secondaryKeyword"] = PyAnnotator.SECONDARY_KEYWORD
        return descriptors
    }

    override fun getDisplayName(): String {
        return "${super.getDisplayName()} (Extra)"
    }

    override fun getDemoText(): String {
        return """
        <secondaryKeyword>from</secondaryKeyword> flask <secondaryKeyword>import</secondaryKeyword> work 
        <secondaryKeyword>import</secondaryKeyword> string 
        """.trimIndent() + super.getDemoText()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? {
        DESCRIPTORS.putAll(super.getAdditionalHighlightingTagToDescriptorMap()?.toMutableMap() ?: mutableMapOf())
        return Collections.unmodifiableMap(DESCRIPTORS)
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor?> {
        return ATTRIBUTES
    }
}
