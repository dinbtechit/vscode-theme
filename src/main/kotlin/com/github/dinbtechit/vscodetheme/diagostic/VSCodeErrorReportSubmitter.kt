package com.github.dinbtechit.vscodetheme.diagostic

import com.intellij.ide.BrowserUtil
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.diagnostic.ErrorReportSubmitter
import com.intellij.openapi.diagnostic.IdeaLoggingEvent
import com.intellij.openapi.diagnostic.SubmittedReportInfo
import com.intellij.openapi.extensions.PluginId
import org.apache.commons.lang.StringUtils
import org.apache.http.client.utils.URIBuilder
import java.awt.Component
import java.io.BufferedReader
import java.io.StringReader
import java.net.URI
import java.util.stream.Collectors

class VSCodeErrorReportSubmitter: ErrorReportSubmitter() {

    private val packageAbbreviation: Map<String, String>? = null

    override fun getReportActionText(): String {
        return "Report to VSCode Theme"
    }

    override fun submit(
        events: Array<out IdeaLoggingEvent>,
        additionalInfo: String?,
        parentComponent: Component,
        consumer: com.intellij.util.Consumer<in SubmittedReportInfo>
    ): Boolean {
        getReportIssueUrl(
            getReportTitle(events),
            getReportBody(events, additionalInfo)
        )?.let {
            BrowserUtil.browse(it)
        }
        return true
    }

    private fun getReportTitle(events: Array<out IdeaLoggingEvent>): String {
        val event = events.firstOrNull()
        return event?.throwableText?.lineSequence()?.first()
            ?: event?.message
            ?: "Report bug"
    }

    private fun getReportBody(events: Array<out IdeaLoggingEvent>, additionalInfo: String?): String {
        val javaVendor = System.getProperty("java.vendor")
        val javaVersion = System.getProperty("java.version")

        val osName = System.getProperty("os.name")
        val osArch = System.getProperty("os.arch")

        val appName = ApplicationInfo.getInstance().fullApplicationName

        val plugin = PluginManagerCore.getPlugin(PluginId.getId("com.github.dinbtechit.vscodetheme"))
        val pluginVersion = plugin?.version

        var stackTrace = ""
        for (event in events) {
            val message = event.message
            if (!message.isNullOrBlank()) {
                stackTrace = stackTrace.plus(message).plus("\n")
            }
            val throwableText = event.throwableText
            if (!throwableText.isNullOrBlank()) {
                stackTrace = stackTrace.plus("\n```\n")
                stackTrace= stackTrace.plus(
                    BufferedReader(StringReader(throwableText)).lines()
                        .map { line ->
                            var abbreviated = line
                            packageAbbreviation?.entries?.forEach { entry ->
                                abbreviated = StringUtils.replace(abbreviated, entry.key, entry.value)
                            }
                            abbreviated
                        }.collect(Collectors.joining("\n"))
                )
                stackTrace= stackTrace.plus("\n```\n\n")
            }
        }
        var description = additionalInfo
        if (description.isNullOrBlank()) {
            description = "A clear and concise description of what the bug is."
        }

        return """ 
            |#Describe the bug
            | $description
            |
            |#To Reproduce
            |Steps to reproduce the behavior:
            |1. Go to '...'
            |2. Click on '....'
            |3. Scroll down to '....'
            |4. See error
            |
            |#Expected behavior
            |A clear and concise description of what you expected to happen.
            |
            |#Screenshots
            |If applicable, add screenshots to help explain your problem.
            |
            |#Environment
            |* Java: $javaVendor $javaVersion
            |* OS: $osName $osArch
            |* IDE: $appName
            |* Version: $pluginVersion
            |
            |#Additional context
            |${additionalInfo ?: "Add any other context about the problem here."} 
            |
            |#Stacktrace#
            | $stackTrace
            """.trimMargin()
    }

    private fun getReportIssueUrl(title: String, body: String): URI? {
        val uriBuilder = URIBuilder("https://github.com/dinbtechit/vscode-theme/issues/new")
        uriBuilder.addParameter("title", "[Bug] $title")
        uriBuilder.addParameter("labels", "bug")
        if (body.isNotBlank()) {
            uriBuilder.addParameter("body", body)
        } else {
            uriBuilder.addParameter("template", "bug_report.md")
        }
        return uriBuilder.build()
    }

}
