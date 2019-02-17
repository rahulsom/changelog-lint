import org.asciidoctor.gradle.AsciidoctorTask

plugins {
    id("com.gradle.build-scan") version "2.1"
    id("me.champeau.buildscan-recipes") version "0.2.3"
    id("org.jetbrains.kotlin.jvm").version("1.3.20")
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.10.1"
//    id("nebula.release") version "9.1.0"
    id("org.asciidoctor.convert") version "1.5.9.2"
    id("org.ajoberstar.git-publish") version "2.0.0"
    groovy
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(gradleApi())
    implementation("cx.cad.keepachangelog:changelog-parser:0.1.3")

    testImplementation(gradleTestKit())
    testImplementation("org.spockframework:spock-core:1.2-groovy-2.5")
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}

buildScanRecipes {
    recipes("git-commit", "git-status", "teamcity", "gc-stats")
}

pluginBundle {
    website = "https://github.com/rahulsom/changelog-lint"
    vcsUrl = "https://github.com/rahulsom/changelog-lint"
    description = "Lints CHANGELOG.md based on the guide in https://keepachangelog.com/"
    tags = listOf("changelog", "lint")
}

gradlePlugin {
    plugins {
        create("changelogLintPlugin") {
            id = "com.github.rahulsom.changeloglint"
            displayName = "Changelog Lint Plugin"
            description = "Lints CHANGELOG.md based on the guide in https://keepachangelog.com/"
            implementationClass = "com.github.rahulsom.changeloglint.ChangelogLintPlugin"
        }
    }
}

tasks.withType<AsciidoctorTask> {
    attributes(
        mapOf(
            "toc" to "left",
            "icons" to "font",
            "docinfo" to "shared"
        )
    )
    inputs.dir("build/output")
    dependsOn("test")
}

//tasks.getByName("final").dependsOn("publishPlugins", "gitPublishPush")
tasks.getByName("gitPublishCopy").dependsOn("asciidoctor")

gitPublish {
    repoUri.set("git@github.com:rahulsom/changelog-lint.git")
    branch.set("gh-pages")

    contents {
        from("build/asciidoc/html5")
    }
}