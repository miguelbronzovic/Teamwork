package com.teamwork.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Adding Authorization header for all out going requests
 */
class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val originalRequest = chain!!.request()
        val newRequest = Request.Builder()
                .url(originalRequest.url())
                .addHeader("Authorization", "Basic dHdwX2s5ZWpQODhMY3VvakhqbUZrVUZ1WUlVTllhbGc6WA==")
                .method(originalRequest.method(), originalRequest.body())
                .build()
        return chain.proceed(newRequest)
    }
}