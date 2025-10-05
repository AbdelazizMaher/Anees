package com.example.anees.hilt

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.local.LocalDataSourceImpl
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.data.remote.RemoteDataSource
import com.example.anees.data.remote.RemoteDataSourceImpl
import com.example.anees.data.repository.Repository
import com.example.anees.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingModule {

    @Binds
    @Singleton
    abstract fun bindISharedPreferences(
        sharedPreferencesImpl: SharedPreferencesImpl
    ): ISharedPreferences

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl
    ): Repository



}