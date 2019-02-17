package com.github.rahulsom.changeloglint

class ChangelogLintExtension {
    var projectName: Boolean = true
    var projectDescription: Boolean = true

    var versions: Boolean = true

    var versionName: Boolean = true
    var versionRegex: String = "[^ ]+"
    var versionDescription: Boolean = true
    var versionDate: Boolean = true
    var dateRegex: String = "\\d{4}-\\d{2}-\\d{2}"

    var sections: Boolean = true
    var sectionInList: List<String> = listOf()

    var items: Boolean = true
}