package com.bigstep.hiltmindorks.di.module

import com.bigstep.hiltmindorks.data.api.ApiHelper
import com.bigstep.hiltmindorks.data.api.ApiHelperImpl
import com.bigstep.hiltmindorks.data.api.ApiService
import com.bigstep.whatsappredesign.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addNetworkInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    requestBuilder.header("dummy_header_1", "lwxjm1ustgsrkqo1sovyts9qh3zh0iz4")
                    requestBuilder.header("dummy_header_2", "lwxjm1ustgsrkqo1sovyts9qh3zh0iz4")
                    requestBuilder.header("dummy_header_3","x3gtndm8mdhwjarvkhtgunu82z2latlr")
                    return chain.proceed(requestBuilder.build())
                }
            })
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}