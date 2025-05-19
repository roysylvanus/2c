package com.techadive.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/** I used an OkHttp interceptor to inject the bearer token into the Authorization
 *  header dynamically. This keeps the authentication logic clean and centralized,
 *  making the Retrofit client reusable across secure and public endpoints.
 **/

class AuthInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
}