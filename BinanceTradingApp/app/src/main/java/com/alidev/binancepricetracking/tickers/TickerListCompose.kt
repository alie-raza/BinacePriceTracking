package com.alidev.binancepricetracking.tickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alidev.binancepricetracking.viewmodels.TickersViewModel
import com.cuongtd.cryptotracking.ui.TickerCompose

@Composable
fun TickerListCompose(
    tickersViewModel: TickersViewModel,
    onNavigateOrderBook: (String, String) -> Unit
) {
    val tickers by tickersViewModel.tickers.observeAsState(initial = emptyList())
    val isLoading by tickersViewModel.isLoading.observeAsState(initial = true)

    if (isLoading) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            items(items = tickers) { ticker ->
                TickerCompose(ticker, tickersViewModel, onNavigateOrderBook)
            }
        }
    }
}