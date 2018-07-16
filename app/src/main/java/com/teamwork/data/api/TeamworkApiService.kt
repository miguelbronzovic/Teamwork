package com.teamwork.data.api

import com.teamwork.data.model.Projects
import com.teamwork.data.model.Tasks
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

const val PROJECTS = "/projects.json?status=ALL"
const val ALL_TASKS = "/projects/{id}/tasks.json"

interface TeamworkApiService {

    @GET(PROJECTS)
    fun getProjects(): Single<Projects>

    @GET(ALL_TASKS)
    fun getTasks(@Path("id") projectId: String): Single<Tasks>
}