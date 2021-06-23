package github.luthfipun.midtransandroidsample.ui.fragment.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.luthfipun.midtransandroidsample.databinding.ItemProductBinding
import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.currencyID

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var listProduct = mutableListOf<Product>()
    var productListener: ProductListener? = null

    interface ProductListener {
        fun onProductTapped(product: Product)
    }

    fun addData(items: List<Product>){
        listProduct.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listProduct[position], productListener)
    }

    override fun getItemCount() = listProduct.size

    class ViewHolder(
        private val itemProductBinding: ItemProductBinding
    ): RecyclerView.ViewHolder(itemProductBinding.root) {
        fun bind(product: Product, productListener: ProductListener?) {
            itemProductBinding.title.text = product.title
            itemProductBinding.price.text = currencyID(product.price)
            Glide.with(itemView.context)
                .load(product.cover)
                .into(itemProductBinding.cover)

            itemProductBinding.container.setOnClickListener {
                productListener?.onProductTapped(product)
            }
        }
    }
}