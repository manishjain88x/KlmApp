package com.klmapp.di

import android.content.Context
import com.klmapp.data.BookingRepo
import com.klmapp.data.BookingRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module responsible for providing dependencies related to repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides a singleton instance of the BookingRepo interface.
     *
     * @param context The application context used for accessing resources.
     * @return A singleton instance of the BookingRepo interface implemented by BookingRepoImpl.
     */
    @Provides
    @Singleton
    fun provideMyDataRepository(@ApplicationContext context: Context): BookingRepo {
        // Instantiate and return a singleton instance of BookingRepoImpl
        return BookingRepoImpl(context)
    }
}
