package com.klmapp.data

import android.content.Context
import com.google.gson.Gson
import com.klmapp.R
import com.klmapp.model.Booking
import com.klmapp.model.BookingList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Implementation of the BookingRepo interface responsible for retrieving booking-related data.
 *
 * @property context The application context used for accessing resources.
 */
class BookingRepoImpl @Inject constructor(private val context: Context) : BookingRepo {
    // Variable to store all booking data after retrieving it
    private lateinit var allBookings: List<Booking>

    /**
     * Retrieves all booking data from a local JSON file and emits it as a Flow with DataState.
     *
     * @return A Flow<DataState<List<Booking>>> representing the state of the booking data retrieval.
     */
    override fun getAllBookingData() = flow {
        // Emit Loading state to notify observers that data retrieval is in progress
        emit(DataState.Loading)
        try {
            // Read raw JSON data from the 'raw' resources folder
            val rawData =
                context.resources.openRawResource(R.raw.data).bufferedReader().use { it.readText() }

            // Parse JSON data using Gson and store it in the allBookings variable
            allBookings = Gson().fromJson(rawData, BookingList::class.java).bookingList

            // Emit Success state with the retrieved booking data
            emit(DataState.Success(allBookings))
        } catch (e: Exception) {
            // Emit Error state if an exception occurs during data retrieval
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO) // Switch to the IO dispatcher for background thread execution

    /**
     * Retrieves booking details for a specific booking ID and emits them as a Flow with DataState.
     *
     * @param id The unique identifier of the booking.
     * @return A Flow<DataState<Booking?>> representing the state of the booking details retrieval.
     */
    override fun getBookingDetails(id: Int) = flow {
        // Emit Loading state to notify observers that data retrieval is in progress
        emit(DataState.Loading)
        try {
            // Find the booking with the specified ID from the stored list of all bookings
            emit(DataState.Success(allBookings.find { it.id == id }))
        } catch (e: Exception) {
            // Emit Error state if an exception occurs during data retrieval
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO) // Switch to the IO dispatcher for background thread execution
}