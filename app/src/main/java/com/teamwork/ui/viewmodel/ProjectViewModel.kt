package com.teamwork.ui.viewmodel

import com.teamwork.data.model.Project

/**
 * Single VM representation of a project to use on adapters and activities
 */
class ProjectViewModel(val project: Project) {

    val id: String
        get() {
            return project.id
        }

    val name: String
        get() {
            return project.name
        }

    val logo: String?
        get() {
            return project.logo
        }

    val starred: Boolean
        get() {
            return project.starred
        }

    val description: String?
        get() {
            return project.description
        }
}