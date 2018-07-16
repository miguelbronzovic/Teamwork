package com.teamwork.di.module

import android.app.ProgressDialog
import com.teamwork.R
import com.teamwork.data.api.TeamworkApiService
import com.teamwork.di.scope.ActivityScope
import com.teamwork.ui.activity.TaskActivity
import com.teamwork.ui.adapter.TaskAdapter
import com.teamwork.ui.viewmodel.TaskListViewModel
import dagger.Module
import dagger.Provides

/**
 * Dagger's Task Activity module
 */
@Module
class TaskActivityModule(private val activity: TaskActivity) {
    @Provides
    @ActivityScope
    fun providesTaskListViewModel(teamworkApi: TeamworkApiService) :
            TaskListViewModel {
        return TaskListViewModel(teamworkApi)
    }

    @Provides
    @ActivityScope
    fun providesTaskAdapter(projectListViewModel: TaskListViewModel) : TaskAdapter {
        return TaskAdapter(projectListViewModel)
    }

    @Provides
    @ActivityScope
    fun providesProgressDialog() : ProgressDialog {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage(activity.getString(R.string.loading_dialog_message))
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        return progressDialog
    }
}