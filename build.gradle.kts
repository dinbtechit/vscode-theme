import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog")
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea("2025.3.6.1")

        // Bundled IntelliJ Platform plugins
        bundledPlugins("JavaScript", "com.intellij.java", "com.jetbrains.sh", "org.jetbrains.kotlin")

        // Plugins from JetBrains Marketplace
        plugins(
            "io.flutter:97.0.0",
            "Dart:510.0.0",
            "PsiViewer:252.23892.248",
            "org.jetbrains.android:253.33813.55",
            "Pythonid:253.33813.55",
            "PythonCore:253.33813.55",
            "com.jetbrains.php:253.32098.37",
            "org.jetbrains.plugins.go:253.33514.17",
            "com.jetbrains.rust:253.33514.21",
            "org.intellij.plugins.hcl:253.32098.37",
        )

        testFramework(TestFrameworkType.Platform)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

tasks {
    test {
        // The only test sources are commented out; Gradle 9 fails the build when no tests are discovered
        failOnNoDiscoveredTests = false
    }
}
