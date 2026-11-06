plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKmpLibrary) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.nexusPublish)
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
