package com.teamwork.di.module

import com.teamwork.BuildConfig
import com.teamwork.TeamWorkApp
import com.teamwork.data.api.TeamworkApiService
import com.teamwork.data.api.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger's modules
 */
@Module
class AppModule(val app: TeamWorkApp) {

    @Provides @Singleton fun provideApp() = app

    @Provides @Singleton fun httpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides @Singleton fun authInterceptor() : AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides @Singleton fun providesHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                                                authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
    }

    @Provides @Singleton fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides @Singleton fun providesTeamworkService(retrofit: Retrofit) : TeamworkApiService {
        return retrofit.create(TeamworkApiService::class.java)
    }
}