package github.luthfipun.midtransandroidsample.data.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.midtransandroidsample.domain.model.Product

data class ProductDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("price")
    val price: Long
){
    fun toProduct() : Product {
        return Product(id, title, cover, price)
    }
}