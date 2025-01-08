package com.mymap.geomaticapp.di

import com.mymap.geomaticapp.data.repository.RepositoryImpl
import com.mymap.geomaticapp.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun providesRepository(repositoryImpl: RepositoryImpl): Repository

}