package github.luthfipun.midtransandroidsample.ui.fragment.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.luthfipun.midtransandroidsample.databinding.ItemOrderBinding
import github.luthfipun.midtransandroidsample.domain.model.Order
import github.luthfipun.midtransandroidsample.domain.util.currencyID

class OrderAdapter: RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private val listOrder = mutableListOf<Order>()
    var orderListener: Listener? = null

    interface Listener {
        fun onOrderTapped(order: Order)
    }

    fun addData(items: List<Order>){
        listOrder.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOrder[position], orderListener)
    }

    override fun getItemCount() = listOrder.size

    class ViewHolder(
        private val itemOrderBinding: ItemOrderBinding
    ): RecyclerView.ViewHolder(itemOrderBinding.root) {
        fun bind(order: Order, orderListener: Listener?) {
            itemOrderBinding.idOrder.text = order.id.toString()
            itemOrderBinding.createdAt.text = order.createdAt
            itemOrderBinding.status.text = order.status.uppercase()
            itemOrderBinding.total.text = currencyID(order.total)
            itemOrderBinding.container.setOnClickListener {
                orderListener?.onOrderTapped(order)
            }
        }
    }
}