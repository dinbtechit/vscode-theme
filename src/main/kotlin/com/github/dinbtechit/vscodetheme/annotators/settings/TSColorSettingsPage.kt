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
import com.intellij.util.PlatformUtils
import gnu.trove.THashMap
import javax.swing.Icon


class TSColorSettingsPage : BaseColorSettings() {


    private val TS_ATTRIBUTES: Array<out AttributesDescriptor?>

    private val TS_DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()

    private val TS_KEYWORD: TextAttributesKey = TSAnnotator.KEYWORD
    private val TS_NUMBER: TextAttributesKey = TSAnnotator.NUMBER

    val THIS_SUPER: TextAttributesKey = TSAnnotator.THIS_SUPER
    private val MODULE: TextAttributesKey = TSAnnotator.MODULE
    private val DEBUGGER: TextAttributesKey = TSAnnotator.DEBUGGER
    val CONSOLE: TextAttributesKey = TSAnnotator.CONSOLE
    private val NULL: TextAttributesKey = TSAnnotator.NULL
    private val VAL: TextAttributesKey = TSAnnotator.VAL
    val FUNCTION: TextAttributesKey = TSAnnotator.FUNCTION

    private val PRIVATE: TextAttributesKey = TSAnnotator.PRIVATE
    private val DECLARE: TextAttributesKey = TSAnnotator.DECLARE
    private val TYPE_ALIAS: TextAttributesKey = TSAnnotator.TYPE_ALIAS
    private val PRIMITIVE: TextAttributesKey = TSAnnotator.PRIMITIVE
    private val FUNCTION_NAME: TextAttributesKey = TSAnnotator.FUNCTION
    private val VARIABLE: TextAttributesKey = ObjectUtils.notNull(
        TextAttributesKey.find("JS.LOCAL_VARIABLE"), DefaultLanguageHighlighterColors.LOCAL_VARIABLE
    )

    init {
        TS_ATTRIBUTES = arrayOf(
            AttributesDescriptor("Keywords//this, super", THIS_SUPER),
            AttributesDescriptor("Keywords//module, import, export, from, return", MODULE),
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

        TS_DESCRIPTORS.putAll(createAdditionalHlAttrs())
        TS_DESCRIPTORS.putAll(JSColorSettingsPage.JS_DESCRIPTORS)
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["string"] = DefaultLanguageHighlighterColors.STRING
        descriptors["private"] = PRIVATE
        descriptors["declare"] = DECLARE
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
        val lang: Language = ObjectUtils.notNull(Language.findLanguageByID("JavaScript"), Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """
<import>import</import> <local_variable>_</local_variable> <import>from</import> <string>'lodash'</string>;
<import>import</import> <local_variable>{ }</local_variable> <import>from</import> <string>'../../some/package'</string>;
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
        return if (PlatformUtils.isWebStorm()) DisplayPriority.KEY_LANGUAGE_SETTINGS else DisplayPriority.LANGUAGE_SETTINGS
    }
}
