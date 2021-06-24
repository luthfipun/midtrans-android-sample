package github.luthfipun.midtransandroidsample.domain.model

data class Order(
    val id: Int,
    val status: String,
    val total: Long,
    val createdAt: String
)