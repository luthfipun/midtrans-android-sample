package github.luthfipun.midtransandroidsample.data.network

import github.luthfipun.midtransandroidsample.data.network.model.OrderResponse
import github.luthfipun.midtransandroidsample.data.network.model.PaymentHistoryResponse
import github.luthfipun.midtransandroidsample.data.network.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteService {
    @GET("products")
    suspend fun products(): ProductResponse

    @GET("transaction")
    suspend fun transactions(
        @Query("page") page: Int
    ): OrderResponse

    @GET("transaction/{id}")
    suspend fun transactionDetail(
        @Path("id") id: Int
    ): PaymentHistoryResponse
}