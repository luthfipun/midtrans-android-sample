package github.luthfipun.midtransandroidsample.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import github.luthfipun.midtransandroidsample.databinding.ActivityPaymentBinding
import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.Constant
import github.luthfipun.midtransandroidsample.domain.util.currencyID
import java.util.ArrayList

class PaymentActivity : AppCompatActivity(), TransactionFinishedCallback {

    private lateinit var binding: ActivityPaymentBinding
    private var product: Product? = null

    companion object {
        private const val DATA = "DATA"
        fun createIntent(context: Context, product: Product): Intent {
            return Intent(context, PaymentActivity::class.java).apply {
                putExtra(DATA, product)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupMidtrans()
        initView()
    }

    private fun initView() {
        product = intent.getSerializableExtra(DATA) as Product
        product?.let { productItem ->
            binding.itemCard.apply {
                title.text = productItem.title
                price.text = currencyID(productItem.price)
                Glide.with(this@PaymentActivity)
                    .load(productItem.cover)
                    .into(cover)
            }
            binding.btnPay.setOnClickListener {
                setupOrders(productItem)
            }
        }
    }

    private fun setupOrders(product: Product) {
        val transactionRequest = TransactionRequest("TRX-${System.currentTimeMillis()}", product.price.toDouble())

        // fake customer details

        val customerDetails = CustomerDetails()
        customerDetails.firstName = "Udin"
        customerDetails.lastName = "Tea"
        customerDetails.email = "udin@gmail.com"
        customerDetails.phone = "+628123456789"
        customerDetails.customerIdentifier = "ID-123"

        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Jl. A Yani No.10"
        shippingAddress.city = "Bandung"
        shippingAddress.postalCode = "55012"
        customerDetails.shippingAddress = shippingAddress

        val billingAddress = BillingAddress()
        billingAddress.address = "Jl. A Yani No.10"
        billingAddress.city = "Bandung"
        billingAddress.postalCode = "55012"
        customerDetails.billingAddress = billingAddress

        val itemDetails = mutableListOf<ItemDetails>()
        itemDetails.add(
            ItemDetails(product.id.toString(), product.price.toDouble(), 1, product.title)
        )

        transactionRequest.customerDetails = customerDetails
        transactionRequest.itemDetails = itemDetails as ArrayList<ItemDetails>?

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)

        Log.e("ENOG", "starting orders...")
    }

    private fun setupMidtrans() {

        SdkUIFlowBuilder.init()
            .setClientKey(Constant.MIDTRANS_KEY)
            .setContext(this)
            .setTransactionFinishedCallback(this)
            .setMerchantBaseUrl(Constant.BASE_URL)
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()
    }

    override fun onTransactionFinished(p0: TransactionResult?) {
        when {
            p0?.response != null -> {

                when(p0.status){
                    TransactionResult.STATUS_SUCCESS -> {
                        Log.e("ENOG", "DONE : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("Payment Successfully")
                    }
                    TransactionResult.STATUS_PENDING -> {
                        Log.e("ENOG", "PENDING : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("Payment Pending")
                    }
                    TransactionResult.STATUS_FAILED -> {
                        Log.e("ENOG", "FAILED : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("Payment Failed")
                    }
                    TransactionResult.STATUS_INVALID -> {
                        Log.e("ENOG", "INVALID : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("Payment Invalid")
                    }
                }

            }
            p0?.isTransactionCanceled == true -> {
                toast("Transaction Cancelled")
            }
            else -> {
                toast("Transaction Failed")
            }
        }
        finish()
    }

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}