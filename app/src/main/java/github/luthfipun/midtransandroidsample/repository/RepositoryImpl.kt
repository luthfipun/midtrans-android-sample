package github.luthfipun.midtransandroidsample.repository

import github.luthfipun.midtransandroidsample.data.network.RemoteService
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
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}