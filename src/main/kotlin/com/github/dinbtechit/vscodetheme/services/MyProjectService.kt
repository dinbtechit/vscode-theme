package com.github.dinbtechit.vscodetheme.services

import com.intellij.openapi.project.Project
import com.github.dinbtechit.vscodetheme.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
