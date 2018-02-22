package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

import static com.natatisha.pokemonapp.utils.Constants.PAGE_SIZE;

@Singleton
public class PokemonsRepository {

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
                    map(pokemonList -> {
                        Observable.create(subscriber -> {
                            localDataSource.savePokemonsList(pokemonList);
                            cacheIsDirty = false;
                            setLoadingCompleted(subscriber);
                        }).subscribe();
                        return pokemonList;
                    });
        }
        return localDataSource.getPokemonsList(page * PAGE_SIZE, PAGE_SIZE);
    }

    public Observable<Pokemon> getPokemon(int id) {
        isLoading = true;
        if (!localDataSource.hasPokemon(id)) {
            return remoteDataSource.getPokemon(id).
                    map(pokemon -> {
                        Observable.create(subscriber -> {
                            localDataSource.savePokemon(pokemon);
                            setLoadingCompleted(subscriber);
                        }).subscribe();
                        return pokemon;
                    });
        } else {
            return localDataSource.getPokemon(id).doOnComplete(() -> isLoading = false);
        }
    }

    private void setLoadingCompleted(ObservableEmitter<Object> subscriber) {
        isLoading = false;
        subscriber.onComplete();
    }

    public int getCacheSize() {
        return localDataSource.getCacheSize();
    }

    public int getPokemonsCount(){
        return remoteDataSource.getItemsCount();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void clearCache(){
        localDataSource.deleteAll();
    }
}
