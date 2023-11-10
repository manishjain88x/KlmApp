package com.klmapp.data

/**
 * A sealed class representing the different states of data retrieval operations.
 *
 * @param T The type of data associated with the state.
 */
sealed class DataState<out T> {

    /**
     * Represents the Loading state during data retrieval.
     * No specific data is associated with this state.
     */
    object Loading : DataState<Nothing>()

    /**
     * Represents the Success state after successful data retrieval.
     *
     * @param data The retrieved data associated with the Success state.
     */
    data class Success<out T>(val data: T) : DataState<T>()

    /**
     * Represents the Error state when an exception occurs during data retrieval.
     *
     * @param exception The exception associated with the Error state.
     */
    data class Error(val exception: Exception) : DataState<Nothing>()
}

