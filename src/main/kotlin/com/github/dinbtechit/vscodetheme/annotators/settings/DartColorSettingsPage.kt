package com.github.dinbtechit.vscodetheme.annotators.settings


import com.github.dinbtechit.vscodetheme.annotators.DartAnnotator
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


class DartColorSettingsPage : BaseColorSettings() {
    companion object {
        private val ATTRIBUTES: Array<AttributesDescriptor?> = arrayOf(
            AttributesDescriptor("SecondaryKeywords", DartAnnotator.SECONDARY_KEYWORD),
            AttributesDescriptor("SecondaryKeywordsWithBg", DartAnnotator.SECONDARY_KEYWORD_WITH_BG),
        )
        val DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
    }

    init {
        DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = THashMap()
        descriptors["SecondaryKeywords"] = DartAnnotator.SECONDARY_KEYWORD
        descriptors["SecondaryKeywordsWithBg"] = DartAnnotator.SECONDARY_KEYWORD_WITH_BG
        return descriptors
    }

    override fun getIcon(): Icon {
        return AllIcons.FileTypes.Java
    }


    override fun getHighlighter(): SyntaxHighlighter {
        val lang: Language = ObjectUtils.notNull(Language.findLanguageByID("Dart"), Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """
import 'package:greetings/hello.dart' deferred as hello;
import 'package:lib1/lib1.dart' show foo;
import 'package:lib2/lib2.dart' hide foo;

typedef IntList = List<int>;

Future<String> lookUpVersion() <SecondaryKeywordsWithBg>async</SecondaryKeywordsWithBg> => '1.0.0';

bool isNoble(int atomicNumber) {
  <SecondaryKeywords>assert</SecondaryKeywords>(atomicNumber == 1);
  <SecondaryKeywords>return</SecondaryKeywords> false;
}

Future<void> checkVersion() <SecondaryKeywordsWithBg>async</SecondaryKeywordsWithBg> {
  var version = <SecondaryKeywordsWithBg>await</SecondaryKeywordsWithBg> lookUpVersion();

  <SecondaryKeywordsWithBg>await</SecondaryKeywordsWithBg> for (final request in requestServer) {
    handleRequest(request);
  }
  
  // Do something with version
}

Iterable<void> checkVersion2() <SecondaryKeywordsWithBg>sync*</SecondaryKeywordsWithBg> {
  <SecondaryKeywordsWithBg>yield</SecondaryKeywordsWithBg> lookUpVersion();
}



void tryExample() {

<SecondaryKeywordsWithBg>await</SecondaryKeywordsWithBg> for (var identifier <SecondaryKeywords>in</SecondaryKeywords> expression) {
  // Executes each time the stream emits a value.
}

  <SecondaryKeywords>try</SecondaryKeywords>{

  } <SecondaryKeywordsWithBg>on</SecondaryKeywordsWithBg> Exception <SecondaryKeywords>catch</SecondaryKeywords> (e, s) {
    <SecondaryKeywords>rethrow</SecondaryKeywords>;
  } <SecondaryKeywords>finally</SecondaryKeywords> {

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
        return "Dart (Extra)"
    }

    override fun getPriority(): DisplayPriority {
        return DisplayPriority.KEY_LANGUAGE_SETTINGS
    }

}
