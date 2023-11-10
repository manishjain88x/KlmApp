package com.klmapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klmapp.data.BookingRepo
import com.klmapp.data.DataState
import com.klmapp.model.Booking
import com.klmapp.utils.Constants.KEY_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    private val repo: BookingRepo, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _dataStateFlow = MutableStateFlow<DataState<Booking?>>(DataState.Loading)
    val dataStateFlow = _dataStateFlow

    init {
        fetchData()
    }

    // Function to fetch and update data
    private fun fetchData() {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>(KEY_ID)
            id?.let {
                repo.getBookingDetails(it).collect { dataState ->
                    // Update the MutableStateFlow with the collected data
                    _dataStateFlow.value = dataState
                }
            }
        }
    }

}