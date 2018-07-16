package com.teamwork.data.model

import com.google.gson.annotations.SerializedName

/**
 * Model classes support
 */
data class Tasks(@SerializedName("STATUS") val status: String,
                 @SerializedName("todo-items") val taskItems: List<Task>)

data class Task(val id: String,
                val description: String?,
                val content: String,
                @SerializedName("creator-avatar-url")val avatarUrl: String?)