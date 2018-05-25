package com.natatisha.pokemonapp.data.source;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface PokemonsDataSource {

    interface Local {

        DataSource.Factory<Integer, Pokemon>  getPokemonsList();

        DataSource.Factory<Integer, Pokemon> getPokemonsList(int offset, int limit);

        Observable<Pokemon> getPokemon(int id);

        void savePokemon(@NonNull Pokemon pokemon);

        Completable savePokemonsList(@NonNull List<Pokemon> pokemonList);

        boolean hasFullPokemonInfo(int id);

        Completable deleteAll();
    }

    interface Remote {

        Observable<List<Pokemon>> getPokemonsList(int offset, int limit);

        Observable<Pokemon> getPokemon(int id);

    }

}
