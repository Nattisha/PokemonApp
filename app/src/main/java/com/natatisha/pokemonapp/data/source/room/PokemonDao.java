package com.natatisha.pokemonapp.data.source.room;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Flowable;

import static com.natatisha.pokemonapp.utils.Constants.KEY_POKEMON_ID;
import static com.natatisha.pokemonapp.utils.Constants.POKEMONS_TABLE;

@Dao
public interface PokemonDao {

    @Query("SELECT * FROM " + POKEMONS_TABLE)
    DataSource.Factory<Integer, Pokemon> getPokemonsList();

    @Query("SELECT * FROM " + POKEMONS_TABLE + " LIMIT :limit OFFSET :offset")
    DataSource.Factory<Integer, Pokemon> getPokemonsList(int offset, int limit);

    @Query("SELECT * FROM " + POKEMONS_TABLE + " WHERE " + KEY_POKEMON_ID + " = :id")
    Flowable<Pokemon> getPokemon(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePokemon(@NonNull Pokemon pokemon);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePokemonsList(@NonNull List<Pokemon> pokemonList);

    @Query("DELETE FROM " + POKEMONS_TABLE)
    void deleteAll();

}
