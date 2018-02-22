package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Observable;

public interface PokemonsDataSource {

    interface Local {

        Observable<List<Pokemon>> getPokemonsList();

        Observable<Pokemon> getPokemon(int id);

        void savePokemon(@NonNull Pokemon pokemon);

        void savePokemonsList(@NonNull List<Pokemon> pokemonList);

        boolean hasPokemon(int id);

        int getCacheSize();
    }

    interface Remote {

        Observable<List<Pokemon>> getPokemonsList(int offset, int limit);

        Observable<Pokemon> getPokemon(int id);

    }

}
