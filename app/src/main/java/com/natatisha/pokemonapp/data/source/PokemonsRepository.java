package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PokemonsRepository {

    private static final int PAGE_SIZE = 30;

    private PokemonsDataSource.Local localDataSource;

    private PokemonsDataSource.Remote remoteDataSource;

    private boolean cacheIsDirty = false;

    private boolean isLoading = false;

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
                    doOnNext(pokemonList -> localDataSource.savePokemonsList(pokemonList).
                            doOnComplete(() -> cacheIsDirty = false));
        } else {
            return localDataSource.getPokemonsList();
        }
    }

    public Observable<Pokemon> getPokemon(int id) {
        isLoading = true;
        if (cacheIsDirty || !localDataSource.hasPokemon(id)) {
            return remoteDataSource.getPokemon(id).
                    doOnNext(pokemon -> localDataSource.savePokemon(pokemon)).
                    doOnComplete(() -> isLoading = false);
        }
        return localDataSource.getPokemon(id).doOnComplete(() -> isLoading = false);
    }

    public int getCacheSize() {
        return localDataSource.getCacheSize();
    }

    public boolean isLoading() {
        return isLoading;
    }
}
