package com.teamwork.ui.viewmodel

import com.teamwork.data.api.TeamworkApiService
import com.teamwork.data.model.Project
import com.teamwork.data.model.Projects
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Exposing state and data from projects on a View
 */
class ProjectListViewModel(private val teamworkApi: TeamworkApiService) {

    val projectListSubject: BehaviorSubject<List<Project>> = BehaviorSubject.create<List<Project>>()
    val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()

    fun fetchProjects() {
        teamworkApi.getProjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Projects> {
                    override fun onSubscribe(d: Disposable) {
                        isLoading.onSubscribe(d)
                        isLoading.onNext(true)
                    }

                    override fun onSuccess(projects: Projects) {
                        isLoading.onNext(false)
                        projects.projects?.let { projectListSubject.onNext(it) }
                    }

                    override fun onError(e: Throwable) {
                        projectListSubject.onError(e)
                        isLoading.onNext(false)
                    }
                })
    }

    fun getItemCount(): Int {
        return if (projectListSubject.hasValue()) projectListSubject.value.size else 0
    }

    fun getProjectAt(position: Int): Project {
        return projectListSubject.value[position]
    }

    fun getProjectViewModelAt(position: Int): ProjectViewModel {
        return ProjectViewModel(getProjectAt(position))
    }
}