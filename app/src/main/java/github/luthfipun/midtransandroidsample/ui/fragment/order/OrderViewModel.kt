package github.luthfipun.midtransandroidsample.ui.fragment.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.luthfipun.midtransandroidsample.domain.model.Order
import github.luthfipun.midtransandroidsample.domain.util.DataState
import github.luthfipun.midtransandroidsample.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<Order>>>()
    val dataSate: LiveData<DataState<List<Order>>>
        get() = _dataState

    init {
        setStateEvent(MainStateEvent.GetTransactions)
    }

    private fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent){
                MainStateEvent.GetTransactions -> {
                    repository.getTransactions()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class MainStateEvent {
        object GetTransactions: MainStateEvent()
    }
}