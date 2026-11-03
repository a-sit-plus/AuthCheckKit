import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.dokka)
    id("signing")
    `maven-publish`
}

val artifactVersion: String by extra
group = "at.asitplus.authcheckkit"
version = artifactVersion

kotlin {
    android {
        namespace = "at.asitplus.authcheckkit"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.biometric)
        }

        commonMain.dependencies {
            implementation(libs.napier)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


val dokkaOutputDir = rootProject.rootDir.resolve("docs")

dokka {

    moduleName.set(rootProject.name)
    dokkaPublications.html {
        outputDirectory.set(dokkaOutputDir)
    }

    dokkaSourceSets {
        named("commonMain") {
            val moduleDesc = rootProject.rootDir.resolve("Module.md").apply { createNewFile() }
            val style = "<style>\n" +
                    "h1.cover{display: none;}\n" +
                    "  .logo {\n" +
                    "    content: url(\"logo-light.png\");\n" +
                    "  }\n" +
                    " html.theme-dark .logo {\n" +
                    "    content: url(\"logo-dark.png\");\n" +
                    "  }\n" +
                    "</style>"
            val readme = rootProject.rootDir.resolve("README.md").readText()
            moduleDesc.writeText("# Module AuthCheckKit\n$readme\n$style")

            includes.from(moduleDesc)
            suppressObviousFunctions.set(true)
        }

        configureEach {
            if (name != "commonMain") {
                suppress.set(true)
            }
        }
    }
}

val dokkaLogos = listOf("logo-light.png", "logo-dark.png").map { rootProject.rootDir.resolve(it) }
tasks.named("dokkaGenerate") {
    val logos = dokkaLogos
    val outputDir = dokkaOutputDir
    doLast {
        logos.forEach { it.copyTo(outputDir.resolve(it.name), overwrite = true) }
    }
}

tasks.clean {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaGenerate)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

//first sign everything, then publish!
tasks.withType<AbstractPublishToMaven>() {
    tasks.withType<Sign>().forEach {
        dependsOn(it)
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("AuthCheckKit")
                description.set("Multiplatform authentication capabilities check")
                url.set("https://github.com/a-sit-plus/AuthCheckKit")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("md4096")
                        name.set("Michael Dietrich")
                        email.set("michael.dietrich@a-sit.at")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:a-sit-plus/AuthCheckKit.git")
                    developerConnection.set("scm:git:git@github.com:a-sit-plus/AuthCheckKit.git")
                    url.set("https://github.com/a-sit-plus/AuthCheckKit")
                }
            }
        }
    }
    repositories {
        mavenLocal {
            signing.isRequired = false
        }
        maven {
            url = uri(layout.projectDirectory.dir("..").dir("repo"))
            name = "local"
            signing.isRequired = false
        }
    }
}

afterEvaluate {
    val signingTasks = tasks.withType<Sign>()
    if (signingTasks.isNotEmpty()) {
        logger.lifecycle("")
        logger.lifecycle("  Making signing tasks of project ${name} run after publish tasks")
        tasks.withType<AbstractPublishToMaven>().configureEach {
            mustRunAfter(*signingTasks.toTypedArray())
            logger.info("   * $name must now run after ${signingTasks.joinToString { it.name }}")
        }
        logger.info("")
    }
}

signing {
    val signingKeyId: String? by project
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications)
}
