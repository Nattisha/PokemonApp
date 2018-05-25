package com.natatisha.pokemonapp.ui.list;

import android.arch.paging.PagedList;

import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.source.PokemonsRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PokemonsListPresenter implements PokemonsListContract.Presenter {

    private PokemonsRepository repository;

    private PokemonsListContract.View view;

    private CompositeDisposable disposable;

    private int currentPage;

    @Inject
    public PokemonsListPresenter(PokemonsRepository repository) {
        this.repository = repository;
        disposable = new CompositeDisposable();
    }

    @Override
    public void bind(PokemonsListContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(boolean forceRefresh) {
        loadPokemonsList(forceRefresh);
    }

    @Override
    public void refresh() {
        currentPage = 0;
        loadPokemonsList(true);
    }

    private void loadPokemonsList(boolean forceRefresh) {
        view.showProgress(true);
        if (repository.isLoading())
            return;

        if (forceRefresh) {
            repository.refreshData();
        }

        disposable.add(repository.getPokemonsList()
                .filter(pokemons -> !pokemons.isEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    view.showProgress(false);
                    view.showPokemonsLoadingError();
                })
                .subscribe(pokemonList -> {
                    view.showProgress(false);
                    view.displayPokemonsList(pokemonList);
                })
        );
    }

    @Override
    public void unbind() {
        disposable.dispose();
    }
}
