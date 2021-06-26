package github.luthfipun.midtransandroidsample.domain.model

import java.io.Serializable

data class Order(
    val id: Int,
    val status: String,
    val total: Long,
    val createdAt: String
): Serializable