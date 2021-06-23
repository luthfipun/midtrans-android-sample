package github.luthfipun.midtransandroidsample.ui.fragment.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import github.luthfipun.midtransandroidsample.databinding.FragmentProductBinding
import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.DataState
import github.luthfipun.midtransandroidsample.ui.main.PaymentActivity

@AndroidEntryPoint
class ProductFragment: Fragment(), ProductAdapter.ProductListener {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val productAdapter = ProductAdapter()
    private val viewModel: ProductViewModel by viewModels()

    companion object {
        fun newInstance() = ProductFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
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

    private fun displayData(data: List<Product>) {
        productAdapter.addData(data)
    }

    private fun displayLoading(isDisplayed: Boolean){
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?){
        Toast.makeText(context, message ?: "unknown error", Toast.LENGTH_LONG).show()
    }

    private fun initView() {
        binding.recyclerView.apply {
            hasFixedSize()
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }
        productAdapter.productListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onProductTapped(product: Product) {
        startActivity(
            context?.let { PaymentActivity.createIntent(it, product) }
        )
    }
}