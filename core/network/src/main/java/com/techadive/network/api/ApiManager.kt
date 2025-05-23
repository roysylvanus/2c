package com.techadive.network.api

import com.techadive.network.BuildConfig
import com.techadive.network.retrofit.RetrofitInstance
import com.techadive.network.utils.ApiUtils
import javax.inject.Inject

/**
 * ApiManager provides a lazily initialized instance of [ApiService]
 * using a configured Retrofit client
 **/

class ApiManager @Inject constructor(
    private val retrofitInstance: RetrofitInstance,
) {
    val restApiService: ApiService by lazy {
        retrofitInstance.createRetrofitClient(
            baseUrl = ApiUtils.BASE_URL,
            accessToken = BuildConfig.TMDB_ACCESS_TOKEN
        )
            .create(ApiService::class.java)
    }
}