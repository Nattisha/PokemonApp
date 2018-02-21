package com.natatisha.pokemonapp.data.source.sqlite;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

public interface PokemonsDatabaseHelper {

    void addPokemon(@NonNull Pokemon pokemon);

    void addPokemons(@NonNull List<Pokemon> pokemon);

    void updatePokemon(@NonNull Pokemon pokemon);

    void deletePokemon(int id);

    Pokemon getPokemon(int id);

    List<Pokemon> getAllPokemons();

    List<Pokemon> getPokemonsList(int offset, int limit);

    boolean isPokemonExists(int id);

    boolean hasFullInfo(int id);

    void deleteAll();

    int getPokemonsCount();

}
