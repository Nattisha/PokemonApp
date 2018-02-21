package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokemonsRepository {

    private static final int PAGE_SIZE = 30;

    private PokemonsDataSource.Local localDataSource;

    private PokemonsDataSource.Remote remoteDataSource;

    private boolean cacheIsDirty = true;

    @Inject
    public PokemonsRepository(@NonNull PokemonsDataSource.Local localDataSource,
                              @NonNull PokemonsDataSource.Remote remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public void refreshData() {
        cacheIsDirty = true;
    }

    public Observable<List<Pokemon>> getPokemonsList(int page) {
        if (cacheIsDirty) {
            return remoteDataSource.getPokemonsList(page * PAGE_SIZE, PAGE_SIZE).
                    doOnNext(pokemonList -> localDataSource.savePokemonsList(pokemonList));
        }
        return localDataSource.getPokemonsList();
    }

    public Observable<Pokemon> getPokemon(int id) {
        if (cacheIsDirty) {
            return remoteDataSource.getPokemon(id).
                    doOnNext(pokemon -> localDataSource.savePokemon(pokemon));
        }
        return localDataSource.getPokemon(id);
    }

    public void savePokemon(@NonNull Pokemon pokemon) {
        localDataSource.savePokemon(pokemon);
    }

    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        localDataSource.savePokemonsList(pokemonList);
    }
}
