package github.luthfipun.midtransandroidsample.domain.util

import java.text.NumberFormat
import java.util.*

fun currencyID(nominal: Long): String {
    val locale = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(locale)
    return format.format(nominal)
}