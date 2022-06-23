import java.util.Properties

val config: Properties = Properties()
file("../gradle.properties").inputStream().use(config::load)

plugins {
    java
    id("org.jetbrains.intellij") version "1.6.0"
}

group = config["pluginGroup"] as String
version = config["pluginVersion"] as String

intellij {
    version.set(config["platformVersion"] as String)
    type.set("CL")
    plugins.set(listOf("com.intellij.cidr.base", "com.intellij.cidr.lang"))
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly("org.jetbrains:annotations:${config["annotationsVersion"]}")
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = config["javaVersion"] as String
        targetCompatibility = config["javaVersion"] as String
    }

    patchPluginXml {
        sinceBuild.set(config["pluginSinceBuild"] as String)
        untilBuild.set(config["pluginUntilBuild"] as String)
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    instrumentCode {
        enabled = false // Don't instrument code for performance
    }

    buildSearchableOptions {
        enabled = false;
    }

    runIde {
        enabled = false // Don't run CLion as we only need its libraries/dependencies
    }
}
