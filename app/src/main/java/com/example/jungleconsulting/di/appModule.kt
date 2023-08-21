package com.example.jungleconsulting.di

import androidx.room.Room
import com.example.jungleconsulting.App
import com.example.jungleconsulting.ImageLoader
import com.example.jungleconsulting.datasource.LocalDataStore
import com.example.jungleconsulting.datasource.RemoteDataStore
import com.example.jungleconsulting.db.AppDb
import com.example.jungleconsulting.mapper.EntityMapper
import com.example.jungleconsulting.remote.services.RepositoryService
import com.example.jungleconsulting.remote.services.UserService
import com.example.jungleconsulting.repository.LocalRepository
import com.example.jungleconsulting.repository.RemoteRepository
import com.example.jungleconsulting.ui.AppViewModel
import com.example.jungleconsulting.util.Constants.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://api.github.com/"

val applicationModule = module {
    single { androidApplication() as App }

    //Retrofit
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    //TopService
    factory { provideUserService(get()) }
    factory { provideRepositoryService(get()) }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDb::class.java,
            DB_NAME
        ).build()
    }

    //Mapper
    single { EntityMapper() }

    //Image
    single { ImageLoader(get()) }

    //Repositories
    single { LocalRepository(get()) }
    single { RemoteRepository(get(), get()) }

    //DataStores
    single { LocalDataStore(get(), get()) }
    single { RemoteDataStore(get(), get()) }

    //ViewModels
    viewModel { AppViewModel(get(), get()) }
}

fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
fun provideRepositoryService(retrofit: Retrofit): RepositoryService = retrofit.create(RepositoryService::class.java)