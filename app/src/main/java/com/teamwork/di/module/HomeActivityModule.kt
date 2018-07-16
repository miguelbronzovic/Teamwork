package com.teamwork.di.module

import android.app.ProgressDialog
import com.teamwork.R
import com.teamwork.data.api.TeamworkApiService
import com.teamwork.di.scope.ActivityScope
import com.teamwork.ui.activity.HomeActivity
import com.teamwork.ui.adapter.ProjectAdapter
import com.teamwork.ui.viewmodel.ProjectListViewModel
import dagger.Module
import dagger.Provides

/**
 * Dagger's Home Activity module
 */
@Module
class HomeActivityModule(private val activity: HomeActivity) {

    @Provides @ActivityScope fun providesProjectListViewModel(teamworkApi: TeamworkApiService) :
            ProjectListViewModel {
        return ProjectListViewModel(teamworkApi)
    }

    @Provides @ActivityScope fun providesProjectAdapter(projectListViewModel: ProjectListViewModel) : ProjectAdapter {
        return ProjectAdapter(projectListViewModel)
    }

    @Provides @ActivityScope fun providesProgressDialog() : ProgressDialog {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage(activity.getString(R.string.loading_dialog_message))
        progressDialog.setCancelable(false)
        progressDialog.isIndeterminate = true
        return progressDialog
    }
}