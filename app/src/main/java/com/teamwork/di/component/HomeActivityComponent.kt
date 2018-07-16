package com.teamwork.di.component

import com.teamwork.di.module.HomeActivityModule
import com.teamwork.di.scope.ActivityScope
import com.teamwork.ui.activity.HomeActivity
import dagger.Component

/**
 * Dagger's Home Activity component
 */
@ActivityScope
@Component(dependencies = [(AppComponent::class)],
        modules = [(HomeActivityModule::class)])
interface HomeActivityComponent {
    fun inject(homeActivity: HomeActivity)
}

