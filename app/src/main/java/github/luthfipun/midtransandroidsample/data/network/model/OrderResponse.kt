package github.luthfipun.midtransandroidsample.data.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.midtransandroidsample.domain.model.Order

data class OrderResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val page: Page
) {
    data class Page(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("total")
        val total: Int,
        @SerializedName("data")
        val list: List<OrderDTO>?
    ){
        fun toListOrder(): List<Order> {
            return list?.map { it.toOrder() } ?: listOf()
        }
    }
}