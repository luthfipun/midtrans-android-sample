package github.luthfipun.midtransandroidsample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import github.luthfipun.midtransandroidsample.repository.Repository
import github.luthfipun.midtransandroidsample.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(repositoryImpl: RepositoryImpl): Repository =
        repositoryImpl
}