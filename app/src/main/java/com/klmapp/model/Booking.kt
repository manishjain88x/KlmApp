package com.klmapp.model

import com.google.gson.annotations.SerializedName

data class Booking(
    @SerializedName("id") var id: Int,
    @SerializedName("destination") var name: String,
    @SerializedName("dateRange") var date: String,
    @SerializedName("numberOfPassengers") var noOfPersons: Int,
    @SerializedName("imageUrl") var url: String,
    var tripType: String,
    var departureTime: String,
    var bookingReferenceNumber: String,
    var totalTripDuration: String,
    var bookingTimeLine: List<BookingTimeLine>
)
