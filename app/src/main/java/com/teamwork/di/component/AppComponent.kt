package com.teamwork.di.component

import com.teamwork.TeamWorkApp
import com.teamwork.data.api.TeamworkApiService
import com.teamwork.di.module.AppModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun app(): TeamWorkApp

    fun teamWorkApi(): TeamworkApiService
}