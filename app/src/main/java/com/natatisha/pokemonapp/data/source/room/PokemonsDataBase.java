package com.natatisha.pokemonapp.data.source.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.natatisha.pokemonapp.data.model.Pokemon;

import static com.natatisha.pokemonapp.utils.Constants.DB_VERSION;

@Database(entities = {Pokemon.class}, version = DB_VERSION)
public abstract class PokemonsDataBase extends RoomDatabase {

    public abstract PokemonDao pokemonDao();

}
