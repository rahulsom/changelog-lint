package com.github.rahulsom.changeloglint

import cx.cad.keepachangelog.ChangelogParser
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class ChangelogLintTask : DefaultTask() {
    @TaskAction
    fun lint() {
        val extension = project.extensions.getByType<ChangelogLintExtension>(ChangelogLintExtension::class.java)

        val file = project.file("CHANGELOG.md")
        val hint = "Please look at https://keepachangelog.com/ for the specification."
        if (!file.exists()) {
            throw GradleException("CHANGELOG.md file not found. $hint")
        }

        val changelog = ChangelogParser().parse(file)

        if (extension.projectName && changelog.projectName.isEmpty()) {
            throw GradleException("CHANGELOG.md lacks project name. $hint")
        }

        if (extension.projectDescription && changelog.description.isEmpty()) {
            throw GradleException("CHANGELOG.md lacks project description. $hint")
        }

        if (extension.versions && changelog.entries.isEmpty()) {
            throw GradleException("CHANGELOG.md lacks versions. $hint")
        }
    }
}