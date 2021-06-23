package github.luthfipun.midtransandroidsample.data.network

import github.luthfipun.midtransandroidsample.data.network.model.ProductResponse
import retrofit2.http.GET

interface RemoteService {

    @GET("products")
    suspend fun products(): ProductResponse
}