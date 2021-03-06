package com.velvet.data.di

import androidx.room.Room
import com.velvet.data.Settings.DB_NAME
import com.velvet.data.local.CityDatabase
import com.velvet.data.local.TimeStore
import com.velvet.data.network.Network
import com.velvet.data.network.NetworkImpl
import com.velvet.data.repo.Repository
import com.velvet.data.repo.RepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<Repository> {
        RepositoryImpl(
            network = get(),
            dao = get(),
            timeStore = get()
        )
    }

    single {
        TimeStore(androidContext())
    }

    single<Network> {
        NetworkImpl()
    }

    single {
        Room.databaseBuilder(androidContext(), CityDatabase::class.java, DB_NAME).build().cityDao()
    }
}
