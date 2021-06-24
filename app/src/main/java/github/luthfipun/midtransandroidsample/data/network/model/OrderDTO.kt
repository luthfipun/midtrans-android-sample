package github.luthfipun.midtransandroidsample.data.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.midtransandroidsample.domain.model.Order

data class OrderDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Long,
    @SerializedName("created_at")
    val createdAt: String
){
    fun toOrder(): Order {
        return Order(id, status, total, createdAt)
    }
}