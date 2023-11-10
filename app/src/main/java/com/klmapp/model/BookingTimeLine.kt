package com.klmapp.model

data class BookingTimeLine(
    var date: String,
    var time: String,
    var airportName: String,
    var isTimeUpdated: Boolean,
    var updatedTime: String,
    var haveTransferTime: Boolean,
    var transferTime: String
)
