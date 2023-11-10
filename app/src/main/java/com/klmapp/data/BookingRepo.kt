package com.klmapp.data

import com.klmapp.model.Booking
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the contract for a Booking Repository.
 * A Booking Repository is responsible for providing access to booking-related data.
 */
interface BookingRepo {

    /**
     * Retrieves all booking data as a Flow with the associated DataState.
     * The Flow emits a DataState object containing a List of Booking objects.
     *
     * @return Flow<DataState<List<Booking>>> A Flow emitting the booking data state.
     */
    fun getAllBookingData(): Flow<DataState<List<Booking>>>

    /**
     * Retrieves booking details for a specific booking ID as a Flow with the associated DataState.
     * The Flow emits a DataState object containing a nullable Booking object.
     *
     * @param id The unique identifier of the booking.
     * @return Flow<DataState<Booking?>> A Flow emitting the booking details state.
     */
    fun getBookingDetails(id: Int): Flow<DataState<Booking?>>
}
