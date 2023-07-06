package com.github.dinbtechit.vscodetheme.annotators.settings


import com.github.dinbtechit.vscodetheme.annotators.PyAnnotator
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.jetbrains.python.highlighting.PythonColorsPage
import java.util.*


class PyColorSettingsPage : PythonColorsPage() {
    companion object {
        private val ATTRIBUTES: Array<AttributesDescriptor?> = arrayOf(
            AttributesDescriptor("Secondary keywords", PyAnnotator.SECONDARY_KEYWORD),
            AttributesDescriptor("Secondary keywords with Bg", PyAnnotator.SECONDARY_KEYWORD_WITH_BG),
            AttributesDescriptor("Import reference with Bg", PyAnnotator.IMPORT_REFERENCE_WITH_BG),
        )
        val DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
    }

    init {
        DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = HashMap()
        descriptors["secondaryKeyword"] = PyAnnotator.SECONDARY_KEYWORD
        descriptors["secondaryKeywordBg"] = PyAnnotator.SECONDARY_KEYWORD_WITH_BG
        descriptors["importReference"] = PyAnnotator.IMPORT_REFERENCE_WITH_BG
        return descriptors
    }

    override fun getDisplayName(): String {
        return "${super.getDisplayName()} (Extra)"
    }

    override fun getDemoText(): String {
        return """
        <secondaryKeyword>from</secondaryKeyword> <importReference>flask</importReference> <secondaryKeyword>import</secondaryKeyword> <importReference>work</importReference> 
        <secondaryKeyword>from</secondaryKeyword> <importReference>Something</importReference>, <importReference>Something2</importReference> <secondaryKeyword>import</secondaryKeyword> <importReference>work</importReference>  
        <secondaryKeyword>import</secondaryKeyword> <importReference>string</importReference>
         
        @<decorator>app</decorator>.<nestedFuncDef>get</nestedFuncDef>("/hello/somename")
        <secondaryKeywordBg>async</secondaryKeywordBg> def <nestedFuncDef>say_bye</nestedFuncDef>(<kwarg>name</kwarg>: <classDef>bool</classDef>):
            <secondaryKeyword>return</secondaryKeyword> {"message": f"bye {<kwarg>name</kwarg>}"}


        @<decorator>app</decorator>.<nestedFuncDef>get</nestedFuncDef>("/hello/{name}")
        <secondaryKeywordBg>async</secondaryKeywordBg> def <nestedFuncDef>say_hello</nestedFuncDef>(<kwarg>name</kwarg>: bool):
            <secondaryKeywordBg>await</secondaryKeywordBg> <nestedFuncDef>say_bye</nestedFuncDef>(<kwarg>name</kwarg>)
            <secondaryKeyword>return</secondaryKeyword> {"message": f"Hello {<kwarg>name</kwarg>}"}

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
