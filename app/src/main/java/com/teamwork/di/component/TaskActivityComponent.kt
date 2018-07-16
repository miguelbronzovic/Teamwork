package com.teamwork.di.component

import com.teamwork.di.module.TaskActivityModule
import com.teamwork.di.scope.ActivityScope
import com.teamwork.ui.activity.TaskActivity
import dagger.Component

/**
 * Dagger's Task Activity component
 */
@ActivityScope
@Component(dependencies = [(AppComponent::class)],
        modules = [(TaskActivityModule::class)])
interface TaskActivityComponent {
    fun inject(homeActivity: TaskActivity)
}