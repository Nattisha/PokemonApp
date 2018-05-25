package com.natatisha.pokemonapp.data;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.source.PokemonsDataSource;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.natatisha.pokemonapp.utils.Constants.NETWORK_PAGE_SIZE;

public class PageBoundaryCallback extends PagedList.BoundaryCallback<Pokemon> {

    private PokemonsDataSource.Local localDataSource;

    private PokemonsDataSource.Remote remoteDataSource;

    private int lastRequestedPage = 0;

    public PageBoundaryCallback(PokemonsDataSource.Local localDataSource,
                                PokemonsDataSource.Remote remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void onZeroItemsLoaded() {
        getAndSaveData();
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Pokemon itemAtEnd) {
        getAndSaveData();
    }

    private void getAndSaveData() {
        getDataFromApi(lastRequestedPage)
                .doOnComplete(() -> lastRequestedPage++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private Completable getDataFromApi(int page) {
        return remoteDataSource
                .getPokemonsList(page * NETWORK_PAGE_SIZE, NETWORK_PAGE_SIZE)
                .flatMapCompletable(pokemonList -> localDataSource.savePokemonsList(pokemonList));
    }

}
