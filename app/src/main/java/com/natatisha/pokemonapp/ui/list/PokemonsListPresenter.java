package com.natatisha.pokemonapp.ui.list;

import com.natatisha.pokemonapp.data.source.PokemonsRepository;
import com.natatisha.pokemonapp.ui.list.PokemonsListContract;
import com.natatisha.pokemonapp.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PokemonsListPresenter implements PokemonsListContract.Presenter {

    private PokemonsRepository repository;

    private PokemonsListContract.View view;

    private CompositeDisposable disposable;

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
//        if (repository.isLoading()) return;
        if (forceRefresh) {
            repository.refreshData();
        }
        disposable.add(RxUtils.wrapAsync(repository.getPokemonsList(0))
                .doOnError(throwable -> {
                    view.showProgress(false);
                    view.showPokemonsLoadingError();
                })
                .subscribe(pokemonList -> {
                    view.showProgress(false);
                    view.displayPokemonsList(pokemonList);
                }));
    }

    @Override
    public void unbind() {
        disposable.clear();
        disposable.dispose();
    }
}
