package com.natatisha.pokemonapp.di;

import android.app.Application;
import android.content.Context;

import com.natatisha.pokemonapp.data.source.sqlite.PokemonsDatabaseHelper;
import com.natatisha.pokemonapp.data.source.sqlite.SQLitePokemonsDataBaseHelper;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);

    @Binds
    abstract PokemonsDatabaseHelper provideDataBaseHelper(SQLitePokemonsDataBaseHelper helper);
}
