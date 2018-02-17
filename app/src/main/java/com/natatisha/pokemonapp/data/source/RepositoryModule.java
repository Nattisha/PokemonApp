package com.natatisha.pokemonapp.data.source;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract PokemonsDataSource provideLocalDataSource(PokemonsLocalDataSource localDataSource);

    @Singleton
    @Binds
    @Remote
    abstract PokemonsDataSource provideRemoteDataSource(PokemonsRemoteDataSource remoteDataSource);

}
