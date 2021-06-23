package github.luthfipun.midtransandroidsample.repository

import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getProducts(): Flow<DataState<List<Product>>>
}