package github.luthfipun.midtransandroidsample.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.midtransandroidsample.databinding.ActivityOrderDetailBinding
import github.luthfipun.midtransandroidsample.domain.model.Order
import github.luthfipun.midtransandroidsample.domain.model.OrderDetail
import github.luthfipun.midtransandroidsample.domain.util.DataState
import github.luthfipun.midtransandroidsample.domain.util.currencyID

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val viewModel: OrderDetailViewModel by viewModels()

    private var order: Order? = null

    companion object {
        private const val DATA = "DATA"
        fun createIntent(context: Context, order: Order): Intent {
            return Intent(context, OrderDetailActivity::class.java).apply {
                putExtra(DATA, order)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        order = intent.getSerializableExtra(DATA) as Order
        order?.let { orderItem ->
            viewModel.getTransactionDetail(OrderDetailViewModel.MainStateEvent.GetTransactionDetail, orderItem.id)
            subscribeObservers(orderItem)
        }
    }

    private fun subscribeObservers(order: Order) {
        viewModel.dataState.observe(this, { dataState ->
            when(dataState){
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Success -> {
                    displayLoading(false)
                    displayData(order = order, orderDetail = dataState.data)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayData(order: Order, orderDetail: OrderDetail){
        binding.apply {
            toolbar.title = "Order #${order.id}"
            itemProduct.apply {
                title.text = orderDetail.product.title
                price.visibility = View.INVISIBLE
                Glide.with(this@OrderDetailActivity)
                    .load(orderDetail.product.cover)
                    .into(cover)
            }
            orderId.text = "#${order.id}"
            createdAt.text = order.createdAt
            status.text = order.status.uppercase()
            payment.text = orderDetail.paymentHistory?.paymentType.orEmpty()
            total.text = currencyID(order.total)
        }
    }

    private fun displayLoading(isDisplayed: Boolean){
        binding.content.visibility = if (isDisplayed) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?){
        Toast.makeText(this, message ?: "Unknown Errors", Toast.LENGTH_SHORT).show()
    }
}