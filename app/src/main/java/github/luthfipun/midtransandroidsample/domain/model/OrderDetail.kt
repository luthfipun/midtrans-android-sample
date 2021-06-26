package github.luthfipun.midtransandroidsample.domain.model

data class OrderDetail(
    val product: Product,
    val paymentHistory: PaymentHistory?
)