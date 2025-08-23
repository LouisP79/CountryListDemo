package com.countrylist.di

import android.content.Context
import android.os.Build
import com.google.gson.Gson
import com.countrylist.BuildConfig
import com.countrylist.preferences.ApplicationPreferences
import com.countrylist.util.core.UtilConnectionInterceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val appModule = module {
    singleOf(::provideGson)
    singleOf(::provideAppPreference)
    singleOf(::provideJacksonConverterFactory)
    singleOf(::provideHttpInterceptor)
    singleOf(::provideOkHttpClient)
    singleOf(::provideRetrofit)
    singleOf(::UtilConnectionInterceptor)
}

//GSON
fun provideGson(): Gson {
    return Gson()
}

//PREFERENCES
//·····································
fun provideAppPreference(context: Context, gson: Gson): ApplicationPreferences {
    return ApplicationPreferences(context, gson)
}
//PREFERENCES
//·····································

//RETROFIT
//·····································
fun provideJacksonConverterFactory(): JacksonConverterFactory {
    return JacksonConverterFactory.create()
}

fun provideHttpInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, connectionInterceptor: UtilConnectionInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(connectionInterceptor)
            .addInterceptor {
                it.proceed(
                        it.request().newBuilder()
                                .header("User-Agent",
                                        "Android ${Build.VERSION.RELEASE}; ${Build.MODEL}")
                                .build()
                )
            }
            .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, jacksonConverterFactory: JacksonConverterFactory): Retrofit {
    return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(okHttpClient)
            .addConverterFactory(jacksonConverterFactory)
            .build()
}
//RETROFIT
//·····································
