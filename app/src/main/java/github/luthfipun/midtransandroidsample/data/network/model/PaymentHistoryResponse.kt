package github.luthfipun.midtransandroidsample.data.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.midtransandroidsample.domain.model.OrderDetail

data class PaymentHistoryResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("product")
        val product: ProductDTO,
        @SerializedName("payment_history")
        val paymentHistory: PaymentHistoryDTO?
    ) {
        fun toOrderDetail(): OrderDetail {
            return OrderDetail(
                product = product.toProduct(),
                paymentHistory = paymentHistory?.toPaymentHistory()
            )
        }
    }
}