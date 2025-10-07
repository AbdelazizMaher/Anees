package com.muslim.anees.hilt

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.muslim.anees.data.local.LocalDataSource
import com.muslim.anees.data.local.LocalDataSourceImpl
import com.muslim.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.muslim.anees.data.remote.RemoteDataSource
import com.muslim.anees.data.remote.RemoteDataSourceImpl
import com.muslim.anees.data.repository.Repository
import com.muslim.anees.data.repository.RepositoryImpl
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