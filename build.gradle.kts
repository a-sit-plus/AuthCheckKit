plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    id("org.jetbrains.dokka") version "2.0.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

val artifactVersion: String by extra
group = "at.asitplus.authcheckkit"
version = artifactVersion

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(java.net.URI("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(java.net.URI("https://central.sonatype.com/repository/maven-snapshots/"))
        }
    }
}