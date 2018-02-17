package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Observable;

public class PokemonsRepository implements PokemonsDataSource {

    private PokemonsDataSource mLocalDataSource;

    private PokemonsDataSource mRemoteDataSource;

    private boolean cacheIsDirty = true;

    public PokemonsRepository(@NonNull PokemonsDataSource localDataSource,
                              @NonNull PokemonsDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void refreshData() {
        cacheIsDirty = true;
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList(int page) {
        if (cacheIsDirty) {
            return mRemoteDataSource.getPokemonsList(page).
                    doOnNext(pokemonList -> {
                        mLocalDataSource.savePokemonsList(pokemonList);
                    });
        }
        return mLocalDataSource.getPokemonsList(page);
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        if (cacheIsDirty) {
            return mRemoteDataSource.getPokemon(id).
                    doOnNext(pokemon -> {
                        mLocalDataSource.savePokemon(pokemon);
                    });
        }
        return mLocalDataSource.getPokemon(id);
    }

    @Override
    public void savePokemon(@NonNull Pokemon pokemon) {
        mLocalDataSource.savePokemon(pokemon);
    }

    @Override
    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        mLocalDataSource.savePokemonsList(pokemonList);
    }
}
