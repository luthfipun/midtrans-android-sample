package github.luthfipun.midtransandroidsample.repository

import github.luthfipun.midtransandroidsample.data.network.RemoteService
import github.luthfipun.midtransandroidsample.domain.model.Order
import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteService: RemoteService
) : Repository {

    override suspend fun getProducts(): Flow<DataState<List<Product>>> = flow {
        emit(DataState.Loading)

        try {
            val getProducts = remoteService.products()
            if (getProducts.status && getProducts.list.isNotEmpty()){
                emit(DataState.Success(getProducts.toProductList()))
            }else {
                emit(DataState.Error(Exception("List product is empty")))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }

    override suspend fun getTransactions(): Flow<DataState<List<Order>>> = flow {
        emit(DataState.Loading)

        // no paging for now
        try {
            val getTransactions = remoteService.transactions(1)
            if (getTransactions.status && getTransactions.page.total > 0){
                emit(DataState.Success(getTransactions.page.toListOrder()))
            }else {
                emit(DataState.Error(Exception("List transaction is empty")))
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}