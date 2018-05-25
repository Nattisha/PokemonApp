package com.natatisha.pokemonapp.data.source;

import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.PageBoundaryCallback;
import com.natatisha.pokemonapp.data.model.Pokemon;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static com.natatisha.pokemonapp.utils.Constants.DB_PAGE_SIZE;

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

    public Observable<PagedList<Pokemon>> getPokemonsList() {
        DataSource.Factory<Integer, Pokemon> data = localDataSource.getPokemonsList();
        Observable<PagedList<Pokemon>> result = new RxPagedListBuilder<>(data, DB_PAGE_SIZE)
                .setBoundaryCallback(new PageBoundaryCallback(localDataSource, remoteDataSource))
                .buildObservable()
                .doFinally(() -> isLoading = false);
        return cacheIsDirty ?
                localDataSource.deleteAll()
                        .andThen(result) : result;
    }

    public Observable<Pokemon> getPokemon(int id) {
        isLoading = true;
        if (!localDataSource.hasFullPokemonInfo(id)) {
            return remoteDataSource.getPokemon(id).
                    map(pokemon -> {
                        localDataSource.savePokemon(pokemon);
                        isLoading = false;
                        return pokemon;
                    });
        } else {
            return localDataSource.getPokemon(id).doFinally(() -> isLoading = false);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public Completable clearCache() {
        return localDataSource.deleteAll();
    }
}
