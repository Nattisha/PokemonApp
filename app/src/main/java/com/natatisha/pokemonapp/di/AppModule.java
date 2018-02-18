package com.natatisha.pokemonapp.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.natatisha.pokemonapp.data.source.sqlite.PokemonsDatabaseHelper;
import com.natatisha.pokemonapp.data.source.sqlite.SQLitePokemonsDataBaseHelper;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);

    @Singleton
    @Provides
    static Gson gson() {
        return new Gson();
    }
}
