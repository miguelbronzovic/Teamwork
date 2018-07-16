package com.teamwork.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model classes support
 */
data class Projects(@SerializedName("STATUS") val status: String,
                    val projects: List<Project>?) : Serializable

data class Project(val id: String,
                   val starred: Boolean,
                   val status: String,
                   val logo: String?,
                   val startDate: String,
                   val endDate: String,
                   val name: String,
                   val description: String?) : Serializable