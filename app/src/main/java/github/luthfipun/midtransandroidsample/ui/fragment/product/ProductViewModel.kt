package github.luthfipun.midtransandroidsample.ui.fragment.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.luthfipun.midtransandroidsample.domain.model.Product
import github.luthfipun.midtransandroidsample.domain.util.DataState
import github.luthfipun.midtransandroidsample.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<Product>>>()
    val dataState: LiveData<DataState<List<Product>>>
        get() = _dataState

    init {
        setStateEvent(ProductStateEvent.GetProducts)
    }

    private fun setStateEvent(productStateEvent: ProductStateEvent){
        viewModelScope.launch {
            when(productStateEvent){
                ProductStateEvent.GetProducts -> {
                    repository.getProducts()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class ProductStateEvent {
        object GetProducts: ProductStateEvent()
    }
}