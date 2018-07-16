package com.teamwork

import android.app.Application
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.teamwork.di.component.AppComponent
import com.teamwork.di.component.DaggerAppComponent
import com.teamwork.di.module.AppModule


class TeamWorkApp : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initPicasso()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    private fun initPicasso() {
        val picasso = Picasso.Builder(this)
                .downloader(OkHttp3Downloader(this, Integer.MAX_VALUE.toLong()))
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .build()
        Picasso.setSingletonInstance(picasso)
    }
}