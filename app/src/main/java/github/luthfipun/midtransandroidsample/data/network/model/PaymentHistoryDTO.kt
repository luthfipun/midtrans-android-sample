package github.luthfipun.midtransandroidsample.data.network.model

import com.google.gson.annotations.SerializedName
import github.luthfipun.midtransandroidsample.domain.model.PaymentHistory

data class PaymentHistoryDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("payment_type")
    val paymentType: String?
    // Add more what you need
){
    fun toPaymentHistory(): PaymentHistory {
        return PaymentHistory(id ?: 0, paymentType.orEmpty())
    }
}