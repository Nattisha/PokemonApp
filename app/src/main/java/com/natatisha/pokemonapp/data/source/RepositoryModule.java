package com.natatisha.pokemonapp.data.source;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract PokemonsDataSource.Local provideLocalDataSource(PokemonsLocalDataSource localDataSource);

    @Singleton
    @Binds
    abstract PokemonsDataSource.Remote provideRemoteDataSource(PokemonsRemoteDataSource remoteDataSource);

}
