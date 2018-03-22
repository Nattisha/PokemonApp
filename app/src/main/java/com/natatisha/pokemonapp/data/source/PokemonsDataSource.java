package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface PokemonsDataSource {

    interface Local {

        Observable<List<Pokemon>> getPokemonsList();

        Observable<List<Pokemon>> getPokemonsList(int offset, int limit);

        Observable<Pokemon> getPokemon(int id);

        void savePokemon(@NonNull Pokemon pokemon);

        void savePokemonsList(@NonNull List<Pokemon> pokemonList);

        boolean hasFullPokemonInfo(int id);

        Completable deleteAll();
    }

    interface Remote {

        int getItemsCount();

        Observable<List<Pokemon>> getPokemonsList(int offset, int limit);

        Observable<Pokemon> getPokemon(int id);

    }

}
