package com.natatisha.pokemonapp.network;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class NetworkModule {

    @Singleton
    @Provides
    static Gson gson() {
        return new Gson();
    }

    @Singleton
    @Provides
    static PokemonApiProvider apiProvider(Gson gson) {
        return new PokemonApiProvider(gson);
    }

}
