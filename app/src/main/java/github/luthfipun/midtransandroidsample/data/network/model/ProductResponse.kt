package github.luthfipun.midtransandroidsample.data.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.midtransandroidsample.domain.model.Product

data class ProductResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val list: List<ProductDTO>
){
    fun toProductList(): List<Product>{
        return list.map { it.toProduct() }
    }
}