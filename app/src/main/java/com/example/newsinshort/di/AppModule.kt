package com.example.newsinshort.di

import com.example.newsinshort.data.api.ApiService
import com.example.newsinshort.data.datasource.NewsDataSource
import com.example.newsinshort.data.datasource.NewsDataSourceImpl
import com.example.newsinshort.ui.repository.NewsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.sql.DataSource


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(provideOkhttpClient())
            .addConverterFactory(provideMoshi())
            .build()

    }



    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .build()
    }


    fun provideMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideNewsDataSource(apiService: ApiService): NewsDataSource {
        return NewsDataSourceImpl(apiService)

    }

    @Singleton
    @Provides
    fun provideNewsRepository(dataSource: NewsDataSource):NewsRepository {
        return NewsRepository(dataSource)
    }
}