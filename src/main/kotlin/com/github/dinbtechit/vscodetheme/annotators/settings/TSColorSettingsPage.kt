package com.github.dinbtechit.vscodetheme.annotators.settings

import com.github.dinbtechit.vscodetheme.annotators.TSAnnotator
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
import javax.swing.Icon


class TSColorSettingsPage : BaseColorSettings() {


    private val TS_ATTRIBUTES: Array<out AttributesDescriptor?> = arrayOf(
        AttributesDescriptor("Keywords//this, super", THIS_SUPER),
        AttributesDescriptor("Keywords//module, import, export, return", MODULE),
        AttributesDescriptor("Keywords//from", FROM_KEYWORD),
        AttributesDescriptor("Keywords//debugger", DEBUGGER),
        AttributesDescriptor("Primitives//null, undefined", NULL),
        AttributesDescriptor("Keywords//var, let, const", VAL),
        AttributesDescriptor("Keywords//console", CONSOLE),
        AttributesDescriptor("Keywords//function", FUNCTION),
        AttributesDescriptor("Keywords//private, public, protected", PRIVATE),
        AttributesDescriptor("Keywords//declare", DECLARE),
        AttributesDescriptor("Keywords//type, alias", TYPE_ALIAS),
        AttributesDescriptor("Primitives//true, false", PRIMITIVE)
    )

    companion object {
        val TS_DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()

        val TS_KEYWORD: TextAttributesKey = TSAnnotator.KEYWORD
        val TS_NUMBER: TextAttributesKey = TSAnnotator.NUMBER
        val THIS_SUPER: TextAttributesKey = TSAnnotator.THIS_SUPER
        val MODULE: TextAttributesKey = TSAnnotator.MODULE
        val FROM_KEYWORD: TextAttributesKey = TSAnnotator.FROM_KEYWORD
        val DEBUGGER: TextAttributesKey = TSAnnotator.DEBUGGER
        val CONSOLE: TextAttributesKey = TSAnnotator.CONSOLE
        val NULL: TextAttributesKey = TSAnnotator.NULL
        val VAL: TextAttributesKey = TSAnnotator.VAL
        val FUNCTION: TextAttributesKey = TSAnnotator.FUNCTION
        val PRIVATE: TextAttributesKey = TSAnnotator.PRIVATE
        val DECLARE: TextAttributesKey = TSAnnotator.DECLARE
        val TYPE_ALIAS: TextAttributesKey = TSAnnotator.TYPE_ALIAS
        val PRIMITIVE: TextAttributesKey = TSAnnotator.PRIMITIVE
        val FUNCTION_NAME: TextAttributesKey = TSAnnotator.FUNCTION
        val VARIABLE: TextAttributesKey = ObjectUtils.notNull(
            TextAttributesKey.find("JS.LOCAL_VARIABLE"), DefaultLanguageHighlighterColors.LOCAL_VARIABLE
        )
    }

    init {
        TS_DESCRIPTORS.putAll(createAdditionalHlAttrs())
        TS_DESCRIPTORS.putAll(JSColorSettingsPage.JS_DESCRIPTORS)
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["string"] = DefaultLanguageHighlighterColors.STRING
        descriptors["private"] = PRIVATE
        descriptors["declare"] = DECLARE
        descriptors["from"] = FROM_KEYWORD
        descriptors["type"] = TYPE_ALIAS
        descriptors["class"] = DefaultLanguageHighlighterColors.CLASS_NAME
        descriptors["keyword"] = TS_KEYWORD
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
        descriptors["number"] = TS_NUMBER
        descriptors["inst_field"] = DefaultLanguageHighlighterColors.INSTANCE_FIELD
        return descriptors
    }

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.Any_type
    }

    override fun getHighlighter(): SyntaxHighlighter {
        val lang: Language = ObjectUtils.notNull(Language.findLanguageByID("TypeScript"), Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """
<import>import</import> <local_variable>_</local_variable> <from>from</from> <string>'lodash'</string>;
<import>import</import> <local_variable>{ Some, Item }</local_variable> <from>from</import> <string>'../../some/package'</string>;
<import>export</import> <declare>declare</declare> <keyword>interface</keyword> <class>MyInterface</class> <import>from</import> <string>'./myClass'</string>;
<import>export default</import> <class>MyClass</class>;

<type>type</type> <class>MyFoo</class> = <class>MyInterface</class>;

<import>export</import> <keyword>class</keyword> <class>MyType</class> <keyword>extends</keyword> <class>AbstractClass</class> {
    <private>private</private> <local_variable>field</local_variable>: <class>String</class>;
    <private>protected</private> <local_variable>protect</local_variable>: <class>Number</class>;
    <private>public</private> <local_variable>num</local_variable> = <number>10</number>;

    <function_name>foo</function_name>() {
      <val>var</val> <local_variable>x</local_variable> = <number>10</number>;
      <this>this</this>.<inst_field>x</inst_field> = <null>null</null>;
      <keyword>if</keyword> (<local_variable>x</local_variable> === <null>undefined</null>) {
        <console>console</console>.<function>log</function>(<string>'foo'</string>);
        <debugger>debugger</debugger>;
        <keyword>return</keyword> <primitive>false</primitive>;
      }
      <keyword>return</keyword> <primitive>true</primitive>;
    }

}""".trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?> {
        return TS_DESCRIPTORS.toMap()
    }


    override fun getAttributeDescriptors(): Array<out AttributesDescriptor?> {
        return TS_ATTRIBUTES
    }


    override fun getColorDescriptors(): Array<ColorDescriptor?> {
        return ColorDescriptor.EMPTY_ARRAY
    }


    override fun getDisplayName(): String {
        return "Typescript (Extra)"
    }

    override fun getPriority(): DisplayPriority {
        return DisplayPriority.KEY_LANGUAGE_SETTINGS
    }
}
