package com.example.newsinshort.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.data.entity.NewsResponse
import com.example.newsinshort.ui.components.Loader
import com.example.newsinshort.ui.components.NewsRowComponent
import com.example.newsinshort.ui.viewmodel.NewsViewModel
import com.example.utilities.ResourceState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: NewsViewModel = hiltViewModel()) {

    val newsResponse by viewModel.news.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        val rememberPagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f
        ) {
            100
        }
        VerticalPager(
            state = rememberPagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fill,
            pageSpacing = 8.dp

        ) {page->

            when (newsResponse) {
                is ResourceState.Error -> {

                }

                is ResourceState.Loading -> {
                    Loader()
                }

                is ResourceState.Success -> {
                    val result = (newsResponse as ResourceState.Success<NewsResponse>).data

                    if (result.articles.isNotEmpty()){
                        NewsRowComponent(result.articles[page])
                    }

                }
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}