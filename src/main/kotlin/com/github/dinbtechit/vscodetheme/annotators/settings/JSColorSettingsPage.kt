package com.github.dinbtechit.vscodetheme.annotators.settings


import com.github.dinbtechit.vscodetheme.annotators.JSAnnotator
import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.psi.codeStyle.DisplayPriority
import com.intellij.util.ObjectUtils
import gnu.trove.THashMap
import java.util.*
import javax.swing.Icon


class JSColorSettingsPage : BaseColorSettings() {

    private val JS_ATTRIBUTES: Array<AttributesDescriptor?> = arrayOf(
        AttributesDescriptor("Import specifiers", IMPORT_SPECIFIER),
        AttributesDescriptor("Keywords//from", FROM_KEYWORD),
        AttributesDescriptor("Secondary keywords", SECONDARY_KEYWORDS ),
        AttributesDescriptor("Primitives//null, undefined", NULL),
    )
    companion object {
        val JS_DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
        val JS_KEYWORD: TextAttributesKey = JSAnnotator.JS_KEYWORD
        val JS_NUMBER: TextAttributesKey = JSAnnotator.JS_NUMBER
        val VARIABLE: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.LOCAL_VARIABLE"),
            DefaultLanguageHighlighterColors.LOCAL_VARIABLE
        )

        val SECONDARY_KEYWORDS: TextAttributesKey = JSAnnotator.SECONDARY_KEYWORDS
        val IMPORT_SPECIFIER: TextAttributesKey = JSAnnotator.IMPORT_SPECIFIER
        val FROM_KEYWORD: TextAttributesKey = JSAnnotator.FROM_KEYWORD
        val NULL: TextAttributesKey = JSAnnotator.JS_NULL
    }

    init {
        JS_DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["string"] = DefaultLanguageHighlighterColors.STRING
        descriptors["subKeyword"] = SECONDARY_KEYWORDS
        descriptors["from"] = FROM_KEYWORD
        descriptors["import_specifier"] = IMPORT_SPECIFIER
        descriptors["keyword"] = JS_KEYWORD
        descriptors["local_variable"] = VARIABLE
        descriptors["null"] = NULL
        descriptors["number"] = JS_NUMBER
        descriptors["inst_field"] = DefaultLanguageHighlighterColors.INSTANCE_FIELD
        descriptors["function_name"] = DefaultLanguageHighlighterColors.FUNCTION_CALL
        return descriptors
    }

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.JavaScript
    }


    override fun getHighlighter(): SyntaxHighlighter {
        val lang: Language = ObjectUtils.notNull(Language.findLanguageByID("JavaScript"), Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """<subKeyword>import</subKeyword> <local_variable>_</local_variable> <subKeyword>from</subKeyword> <string>'lodash'</string>;
<subKeyword>import</subKeyword> {<import_specifier>Hello</import_specifier>, <import_specifier>World</import_specifier>} <from>from</from> <string>'./hello/world'</string>;

function <function_name>foo</function_name() {
  var <local_variable>x</local_variable> = <number>10</number>;
  this.<inst_field>x</inst_field> = <null>null</null>;
  <subKeyword>if</subKeyword> (<local_variable>x</local_variable> === <null>undefined</null>) {
    <keyword>console</keyword>.<function_name>log</</function_name>(<string>'foo'</string>);
    <keyword>debugger</keyword>;
    <subKeyword>return</subKeyword> <keyword>false</keyword>;
  }
  <subKeyword>return</subKeyword> <keyword>true</keyword>;
  
<subKeyword>export</subKeyword> <keyword>class</keyword> className {
    <function_name>foo</function_name>() {
       <subKeyword>for</subKeyword>(<keyword>const</val> <local_variable>bar</local_variable> in foo) {
         
       }
    }
}
}
""".trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? {
        return Collections.unmodifiableMap(JS_DESCRIPTORS)
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor?> {
        return JS_ATTRIBUTES
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Javascript (Extra)"
    }

    override fun getPriority(): DisplayPriority {
        return DisplayPriority.KEY_LANGUAGE_SETTINGS
    }

}
