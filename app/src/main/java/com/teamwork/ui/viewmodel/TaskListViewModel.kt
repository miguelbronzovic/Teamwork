package com.teamwork.ui.viewmodel

import com.teamwork.data.api.TeamworkApiService
import com.teamwork.data.model.Task
import com.teamwork.data.model.Tasks
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Exposing state and data from tasks on a View
 */
class TaskListViewModel(private val teamworkApi: TeamworkApiService) {

    val taskListSubject: BehaviorSubject<List<Task>> = BehaviorSubject.create<List<Task>>()
    val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()

    fun fetchTasks(projectId: String) {
        teamworkApi.getTasks(projectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Tasks> {
                    override fun onSubscribe(d: Disposable) {
                        isLoading.onSubscribe(d)
                        isLoading.onNext(true)
                    }

                    override fun onSuccess(tasks: Tasks) {
                        isLoading.onNext(false)
                        taskListSubject.onNext(tasks.taskItems)
                    }

                    override fun onError(e: Throwable) {
                        taskListSubject.onError(e)
                        isLoading.onNext(false)
                    }
                })
    }

    fun getItemCount(): Int {
        return if (taskListSubject.hasValue()) taskListSubject.value.size else 0
    }

    fun getTaskAt(position: Int): Task {
        return taskListSubject.value[position]
    }
}