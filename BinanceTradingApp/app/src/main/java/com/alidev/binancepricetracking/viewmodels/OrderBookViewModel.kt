package com.alidev.binancepricetracking.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alidev.binancepricetracking.models.OrderBook
import com.alidev.binancepricetracking.models.OrderBookSnapshot
import com.alidev.binancepricetracking.models.Ticker
import com.alidev.binancepricetracking.models.Trade
import com.alidev.binancepricetracking.respositories.OrderBookRepository
import com.alidev.binancepricetracking.utils.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun createDumpOrderBookSnapshot() = OrderBookSnapshot(1, listOf(), listOf())
fun createDumpOrderBook() = OrderBook("", 1, "", 1, 1, listOf(), listOf())

class OrderBookViewModel(symbol: String) : ViewModel() {
    private val orderBookRepository = OrderBookRepository()

    private var orderBookSnapshot = createDumpOrderBookSnapshot()
    var bids: List<List<String>> = listOf()
    var asks: List<List<String>> = listOf()


    private var _recentTrades: MutableLiveData<List<Trade>> = MutableLiveData(listOf())
    val recentTrade: LiveData<List<Trade>>
        get() = _recentTrades

    private var _orderBook: MutableLiveData<OrderBook> =
        MutableLiveData(createDumpOrderBook())
    val orderBook: LiveData<OrderBook>
        get() = _orderBook

    private var _ticker: MutableLiveData<Ticker> = MutableLiveData()
    val ticker: LiveData<Ticker>
        get() = _ticker

    init {
        viewModelScope.launch {
            launch {
                createTickerStream(symbol)
            }
            launch {
                createOrderBookStream(symbol)
            }
            createHistoricalTradeStream(symbol)
        }
    }

    private suspend fun createOrderBookStream(symbol: String) {
        orderBookSnapshot = orderBookRepository.getOrderBookSnapshot(symbol)
        bids = orderBookSnapshot.bids
        asks = orderBookSnapshot.asks

        _orderBook.value!!.b = bids
        _orderBook.value!!.a = asks

        orderBookRepository.createDepthStream(viewModelScope, symbol).collect { it ->
            if (it != null) {
                if (it.FinalUpdateId > orderBookSnapshot.lastUpdateId) {
                    if (_orderBook.value!!.b.count() != 0) {
                        bids = _orderBook.value!!.b
                        asks = _orderBook.value!!.a
                    }

                    _orderBook.value = it
                    _orderBook.value!!.b = (_orderBook.value!!.b + bids).distinctBy { it[0] }
                        .filter { it[1].toDouble() != 0.0 }
                    _orderBook.value!!.a = (_orderBook.value!!.a + asks).distinctBy { it[0] }
                        .filter { it[1].toDouble() != 0.0 }
                }
            }
        }
    }

    private suspend fun createHistoricalTradeStream(symbol: String) {
        _recentTrades.value = orderBookRepository.getRecentTrades(symbol)
        orderBookRepository.createHistoricalTradeStream(viewModelScope, symbol).collect {
            if (it != null) {
                _recentTrades.value =
                    listOf(it).plus(_recentTrades.value!!).take(Constants.API_RESULT_LIMIT)
            }
        }
    }

    private suspend fun createTickerStream(symbol: String) {
        orderBookRepository.createTickerStream(viewModelScope, symbol).collect {
            if (it != null) {
                _ticker.value = it
            }
        }
    }
}