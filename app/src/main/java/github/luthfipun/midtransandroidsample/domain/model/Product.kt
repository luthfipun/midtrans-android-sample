package github.luthfipun.midtransandroidsample.domain.model

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val cover: String,
    val price: Long
): Serializable