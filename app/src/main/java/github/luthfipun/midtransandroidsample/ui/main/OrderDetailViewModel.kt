package github.luthfipun.midtransandroidsample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import github.luthfipun.midtransandroidsample.domain.model.OrderDetail
import github.luthfipun.midtransandroidsample.domain.util.DataState
import github.luthfipun.midtransandroidsample.repository.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _dataState = MutableLiveData<DataState<OrderDetail>>()
    val dataState: LiveData<DataState<OrderDetail>>
        get() = _dataState

    fun getTransactionDetail(mainStateEvent: MainStateEvent, id: Int){
        viewModelScope.launch {
            when(mainStateEvent){
                MainStateEvent.GetTransactionDetail -> {
                    repository.getTransactionDetail(id)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class MainStateEvent {
        object GetTransactionDetail: MainStateEvent()
    }
}