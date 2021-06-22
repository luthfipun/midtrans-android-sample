package github.luthfipun.midtransandroidsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import github.luthfipun.midtransandroidsample.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity(), TransactionFinishedCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPhonePermission()
        setupMidtrans()
        binding.btnOrder.setOnClickListener {
            setupOrders()
        }
    }

    private fun setupOrders() {
        val transactionRequest = TransactionRequest("TRX-001", 10000.00)

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
            ItemDetails("1", 10000.0, 1, "Martabak Enak")
        )

        transactionRequest.customerDetails = customerDetails
        transactionRequest.itemDetails = itemDetails as ArrayList<ItemDetails>?

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)

        Log.e("ENOG", "starting orders...")
    }

    private fun setupMidtrans() {

        val key = "SB-Mid-client-KKI3tFxIxaOTiXrG"
        val url = "http://test-midtrans-server.wflixy.com/api/"

        SdkUIFlowBuilder.init()
            .setClientKey(key)
            .setContext(this)
            .setTransactionFinishedCallback(this)
            .setMerchantBaseUrl(url)
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()
    }

    private fun checkPhonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 101)
        }
    }

    override fun onTransactionFinished(p0: TransactionResult?) {
        when {
            p0?.response != null -> {

                when(p0.status){
                    TransactionResult.STATUS_SUCCESS -> {
                        Log.e("ENOG", "DONE : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("DONE \n Transaction ID : ${p0.response.transactionId}")
                    }
                    TransactionResult.STATUS_PENDING -> {
                        Log.e("ENOG", "PENDING : ${p0.response.transactionStatus} | ${p0.response.transactionId}")
                        toast("PENDING \n Transaction ID : ${p0.response.transactionId}")
                    }
                    TransactionResult.STATUS_FAILED -> {
                        Log.e("ENOG", "FAILED : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("FAILED \n Transaction ID : ${p0.response.statusMessage}")
                    }
                    TransactionResult.STATUS_INVALID -> {
                        Log.e("ENOG", "INVALID : ${p0.response.transactionStatus} | ${p0.response.statusMessage}")
                        toast("INVALID \n Transaction ID : ${p0.response.statusMessage}")
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
    }

    private fun toast(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}