package github.luthfipun.midtransandroidsample.ui.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.midtransandroidsample.databinding.FragmentOrderBinding
import github.luthfipun.midtransandroidsample.domain.model.Order
import github.luthfipun.midtransandroidsample.domain.util.DataState

@AndroidEntryPoint
class OrderFragment: Fragment(), OrderAdapter.Listener {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val orderAdapter = OrderAdapter()
    private val viewModel: OrderViewModel by viewModels()

    companion object {
        fun newInstance() = OrderFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataSate.observe(viewLifecycleOwner, { dataState ->
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
                    displayData(dataState.data)
                }
            }
        })
    }

    private fun displayData(listOrder: List<Order>){
        orderAdapter.addData(listOrder)
    }

    private fun displayLoading(isDisplayed: Boolean){
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?){
        Toast.makeText(context, message ?: "unknown error", Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        binding.recyclerView.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }
        orderAdapter.orderListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onOrderTapped(order: Order) {

    }
}