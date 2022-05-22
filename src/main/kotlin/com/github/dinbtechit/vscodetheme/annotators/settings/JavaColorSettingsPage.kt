package com.github.dinbtechit.vscodetheme.annotators.settings


import com.github.dinbtechit.vscodetheme.annotators.JavaAnnotator
import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.psi.codeStyle.DisplayPriority
import com.intellij.util.ObjectUtils
import gnu.trove.THashMap
import java.util.*
import javax.swing.Icon


class JavaColorSettingsPage : BaseColorSettings() {
    companion object {
        private val ATTRIBUTES: Array<AttributesDescriptor?> = arrayOf(

            AttributesDescriptor("Keywords//new, return", JavaAnnotator.SECONDARY_KEYWORD),
            AttributesDescriptor("Keywords//if, else, switch, case, default, throw, catch, finally," +
                    "yield, break, continue", JavaAnnotator.SECONDARY_KEYWORD),
            AttributesDescriptor("Keywords//for, while, do", JavaAnnotator.SECONDARY_KEYWORD),
        )
        val DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
    }

    init {
        DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["secondaryKeyword"] = JavaAnnotator.SECONDARY_KEYWORD
        return descriptors
    }

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.Java
    }


    override fun getHighlighter(): SyntaxHighlighter {
        val lang: Language = ObjectUtils.notNull(Language.findLanguageByID("JAVA"), Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """
package main.java.com.dinesh;

public class JavaClass {
  JavaClass() {

  }

  public static void method(String val, char primitive) {
    int i=0;
    JavaClass j = <secondaryKeyword>new</secondaryKeyword> JavaClass();
    char c = 'c';
    this.method("", c);
    super.equals();
    <secondaryKeyword>while</secondaryKeyword>(true)
    {
        <secondaryKeyword>if</secondaryKeyword>(i==10)
        <secondaryKeyword>break</secondaryKeyword>;
        i++;
    }
    <secondaryKeyword>try</secondaryKeyword> {
    <secondaryKeyword>throw</secondaryKeyword> <secondaryKeyword>new</secondaryKeyword> Exception();
    } <secondaryKeyword>catch</secondaryKeyword> (Exception e) {
     
    } <secondaryKeyword>finally</secondaryKeyword> {

    }
    <secondaryKeyword>return</secondaryKeyword>;
  }
}            
""".trimIndent()
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
        return "Java (Extra)"
    }

    override fun getPriority(): DisplayPriority {
        return DisplayPriority.KEY_LANGUAGE_SETTINGS
    }

}
