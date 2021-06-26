package github.luthfipun.midtransandroidsample.repository

import github.luthfipun.midtransandroidsample.domain.model.Order
import github.luthfipun.midtransandroidsample.domain.model.OrderDetail
import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getProducts(): Flow<DataState<List<Product>>>
    suspend fun getTransactions(): Flow<DataState<List<Order>>>
    suspend fun getTransactionDetail(id: Int): Flow<DataState<OrderDetail>>
}