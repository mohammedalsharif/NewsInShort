package com.example.newsinshort.ui.repository

import com.example.newsinshort.data.datasource.NewsDataSource
import com.example.newsinshort.data.entity.NewsResponse
import com.example.utilities.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val dataSource: NewsDataSource) {

    suspend fun getNewsHeadline(country: String): Flow<ResourceState<NewsResponse>> {
        return flow {
            emit(ResourceState.Loading())

            val response = dataSource.getNewsHeadline(country)
            if (response.isSuccessful && response.body() != null) {
                emit(ResourceState.Success(response.body()!!))
            } else {
                emit(ResourceState.Error(response.errorBody()?.string().toString()))
            }

        }.catch { e ->
            emit(ResourceState.Error(e.localizedMessage ?: "Error flow"))
        }


    }

}