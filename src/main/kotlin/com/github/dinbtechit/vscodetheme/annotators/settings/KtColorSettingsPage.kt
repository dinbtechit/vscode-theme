package com.github.dinbtechit.vscodetheme.annotators.settings


import com.github.dinbtechit.vscodetheme.annotators.JavaAnnotator
import com.intellij.lang.Language
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.psi.codeStyle.DisplayPriority
import com.intellij.util.ObjectUtils
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.highlighter.KotlinColorSettingsPage
import java.util.*
import javax.swing.Icon


class KtColorSettingsPage : BaseColorSettings() {

    val kotlinColorSettingsPage = KotlinColorSettingsPage()

    companion object {
        private val ATTRIBUTES: Array<AttributesDescriptor?> = arrayOf(
            AttributesDescriptor("SecondaryKeywords", JavaAnnotator.SECONDARY_KEYWORD),
        )
        val DESCRIPTORS = mutableMapOf<String, TextAttributesKey>()
    }

    init {
        DESCRIPTORS.putAll(createAdditionalHlAttrs())
    }

    private fun createAdditionalHlAttrs(): MutableMap<String, TextAttributesKey> {
        val descriptors: MutableMap<String, TextAttributesKey> = HashMap()
        descriptors["secondaryKeyword"] = JavaAnnotator.SECONDARY_KEYWORD
        return descriptors
    }

    override fun getIcon(): Icon {
        return KotlinFileType.INSTANCE.icon
    }


    override fun getHighlighter(): SyntaxHighlighter {
        val lang: Language = ObjectUtils.notNull(KotlinLanguage.INSTANCE.baseLanguage, Language.ANY)
        return getSyntaxHighlighterWithFallback(lang)
    }

    override fun getDemoText(): String {
        return """
        import java.lang.util.*

        public class FlashMessage {
            
            fun getName(): Integer {
              <secondaryKeyword>if</secondaryKeyword>(){
              }
              <secondaryKeyword>else</secondaryKeyword>(){
              }
              <secondaryKeyword>when</secondaryKeyword>() {
                  is KL -> { }
                  is true -> { }
              }
              with(x) {
              }
              <secondaryKeyword>break</secondaryKeyword>
              <secondaryKeyword>continue</secondaryKeyword>
              <secondaryKeyword>return</secondaryKeyword>
            }
        }
        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? {
        return Collections.unmodifiableMap(DESCRIPTORS).apply {
            with(kotlinColorSettingsPage.additionalInlineElementToDescriptorMap) {
                this?.putAll(this.toMap())
            }
        }
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor?> {
        return ATTRIBUTES
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Kotlin (Extra)"
    }

    override fun getPriority(): DisplayPriority {
        return DisplayPriority.LANGUAGE_SETTINGS
    }
}
