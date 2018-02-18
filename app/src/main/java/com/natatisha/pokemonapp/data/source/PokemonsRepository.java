package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokemonsRepository {

    private static final int PAGE_SIZE = 30;

    private PokemonsDataSource.Local mLocalDataSource;

    private PokemonsDataSource.Remote mRemoteDataSource;

    private boolean cacheIsDirty = true;

    @Inject
    public PokemonsRepository(@NonNull PokemonsDataSource.Local localDataSource,
                              @NonNull PokemonsDataSource.Remote remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public void refreshData() {
        cacheIsDirty = true;
    }

    public Observable<List<Pokemon>> getPokemonsList(int page) {
        if (cacheIsDirty) {
            return mRemoteDataSource.getPokemonsList(page * PAGE_SIZE, PAGE_SIZE).
                    doOnNext(pokemonList -> mLocalDataSource.savePokemonsList(pokemonList));
        }
        return mLocalDataSource.getPokemonsList();
    }

    public Observable<Pokemon> getPokemon(int id) {
        if (cacheIsDirty) {
            return mRemoteDataSource.getPokemon(id).
                    doOnNext(pokemon -> mLocalDataSource.savePokemon(pokemon));
        }
        return mLocalDataSource.getPokemon(id);
    }

    public void savePokemon(@NonNull Pokemon pokemon) {
        mLocalDataSource.savePokemon(pokemon);
    }

    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        mLocalDataSource.savePokemonsList(pokemonList);
    }
}
