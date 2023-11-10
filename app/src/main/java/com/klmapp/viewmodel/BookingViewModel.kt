package com.klmapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klmapp.data.BookingRepo
import com.klmapp.data.DataState
import com.klmapp.model.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val repo: BookingRepo) : ViewModel() {

    private val _dataStateFlow = MutableStateFlow<DataState<List<Booking>>>(DataState.Loading)
    val dataStateFlow = _dataStateFlow

    init {
        fetchData()
    }

    // Function to fetch and update data
    private fun fetchData() {
        viewModelScope.launch {
            repo.getAllBookingData().collect { dataState ->
                _dataStateFlow.value = dataState
            }
        }
    }

}