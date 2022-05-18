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
        AttributesDescriptor("Import specifiers", THIS_SUPER),
        AttributesDescriptor("Keywords//this, super", THIS_SUPER),
        AttributesDescriptor("Keywords//package, module, import, export, return", MODULE),
        AttributesDescriptor("Keywords//if, else, switch, case, default, throw, catch, finally," +
                "yield, break, continue, with", CONDITION_KEYWORD),
        AttributesDescriptor("Keywords//for, while, do", LOOP_KEYWORD),
        AttributesDescriptor("Keywords//from", FROM_KEYWORD),
        AttributesDescriptor("Keywords//debugger", DEBUGGER),
        AttributesDescriptor("Primitives//null, undefined", NULL),
        AttributesDescriptor("Primitives//true, false", PRIMITIVE),
        AttributesDescriptor("Keywords//var, let, const", VAL),
        AttributesDescriptor("Keywords//console", CONSOLE),
        AttributesDescriptor("Keywords//function", FUNCTION)
    )
    companion object {
        val JS_DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
        val JS_KEYWORD: TextAttributesKey = JSAnnotator.JS_KEYWORD
        val JS_NUMBER: TextAttributesKey = JSAnnotator.JS_NUMBER
        val VARIABLE: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.LOCAL_VARIABLE"),
            DefaultLanguageHighlighterColors.LOCAL_VARIABLE
        )
        val FUNCTION: TextAttributesKey = JSAnnotator.FUNCTION
        val THIS_SUPER: TextAttributesKey = JSAnnotator.THIS_SUPER
        val MODULE: TextAttributesKey = JSAnnotator.MODULE
        val IMPORT_SPECIFIER: TextAttributesKey = JSAnnotator.IMPORT_SPECIFIER
        val FROM_KEYWORD: TextAttributesKey = JSAnnotator.FROM_KEYWORD
        val CONDITION_KEYWORD: TextAttributesKey = JSAnnotator.CONDITION_KEYWORD
        val LOOP_KEYWORD: TextAttributesKey = JSAnnotator.LOOP_KEYWORD
        val CONSOLE: TextAttributesKey = JSAnnotator.CONSOLE
        val DEBUGGER: TextAttributesKey = JSAnnotator.DEBUGGER
        val NULL: TextAttributesKey = JSAnnotator.NULL
        val PRIMITIVE: TextAttributesKey = JSAnnotator.PRIMITIVE
        val VAL: TextAttributesKey = JSAnnotator.VAL
        val FUNCTION_NAME: TextAttributesKey = JSAnnotator.FUNCTION
    }

    init {
        JS_DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["string"] = DefaultLanguageHighlighterColors.STRING
        descriptors["condition"] = CONDITION_KEYWORD
        descriptors["loop"] = LOOP_KEYWORD
        descriptors["from"] = FROM_KEYWORD
        descriptors["import_specifier"] = IMPORT_SPECIFIER
        descriptors["keyword"] = JS_KEYWORD
        descriptors["function"] = FUNCTION
        descriptors["function_name"] = FUNCTION_NAME
        descriptors["val"] = VAL
        descriptors["local_variable"] = VARIABLE
        descriptors["this"] = THIS_SUPER
        descriptors["null"] = NULL
        descriptors["primitive"] = PRIMITIVE
        descriptors["debugger"] = DEBUGGER
        descriptors["import"] = MODULE
        descriptors["console"] = CONSOLE
        descriptors["number"] = JS_NUMBER
        descriptors["inst_field"] = DefaultLanguageHighlighterColors.INSTANCE_FIELD
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
        return """<import>import</import> <local_variable>_</local_variable> <from>from</from> <string>'lodash'</string>;
<import>import</import> {<import_specifier>Hello</import_specifier>, <import_specifier>World</import_specifier>} <from>from</from> <string>'./hello/world'</string>;

<function>function</function> <function_name>foo</function_name>() {
  <val>var</val> <local_variable>x</local_variable> = <number>10</number>;
  <this>this</this>.<inst_field>x</inst_field> = <null>null</null>;
  <condition>if</condition> (<local_variable>x</local_variable> === <null>undefined</null>) {
    <console>console</console>.<function>log</function>(<string>'foo'</string>);
    <debugger>debugger</debugger>;
    <keyword>return</keyword> <primitive>false</primitive>;
  }
  <keyword>return</keyword> <primitive>true</primitive>;
  
<import>export</import> <keyword>class</keyword> className {
    <function_name>foo</function_name>() {
       <loop>for</loop>(<val>const</val> <local_variable>bar</local_variable> in foo) {
         
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
