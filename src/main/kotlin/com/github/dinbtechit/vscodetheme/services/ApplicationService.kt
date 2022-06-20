package com.github.dinbtechit.vscodetheme.services

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager

class ApplicationService: Disposable {

    override fun dispose() = Unit

    companion object {
        val INSTANCE: ApplicationService = ApplicationManager.getApplication().getService(ApplicationService::class.java)
    }

}
