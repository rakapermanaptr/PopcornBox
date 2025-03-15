package com.creospace.popcornbox.data.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.creospace.popcornbox.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "6d90617eed798d4678c51320e816bae1"

    @Provides
    @Singleton
    @Named("AuthInterceptor")
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newHttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    @Named("ConnectivityInterceptor")
    fun provideConnectivityInterceptor(@ApplicationContext context: Context): Interceptor =
        Interceptor { chain ->
            if (!isNetworkAvailable(context)) {
                throw NoConnectionException("No internet connection")
            }
            chain.proceed(chain.request())
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("AuthInterceptor") authInterceptor: Interceptor,
        @Named("ConnectivityInterceptor") connectivityInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

class NoConnectionException(message: String): IOException(message)