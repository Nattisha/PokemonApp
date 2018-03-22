package com.natatisha.pokemonapp.ui.list;

import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.source.PokemonsRepository;
import com.natatisha.pokemonapp.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

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
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public void setCurrentPage(int page) {
        this.currentPage = page;
    }

    @Override
    public void loadData(boolean forceRefresh) {
        loadPokemonsList(false, forceRefresh, false);
    }

    @Override
    public void loadNextPage() {
        currentPage++;
        loadPokemonsList(false, true, true);
    }

    @Override
    public void refresh() {
        currentPage = 0;
        loadPokemonsList(true, true, false);
    }

    private void loadPokemonsList(boolean cleanCache, boolean forceRefresh, boolean addToExisting) {
        view.showProgress(true);
        if (repository.isLoading())
            return;

        if (forceRefresh) {
            repository.refreshData();
        }

        Observable<List<Pokemon>> observable = cleanCache ?
                repository.clearCache().andThen(repository.getPokemonsList(currentPage)) :
                repository.getPokemonsList(currentPage);
        disposable.add(RxUtils.wrapAsync(observable)
                .doOnError(throwable -> {
                    view.showProgress(false);
                    view.showPokemonsLoadingError();
                })
                .filter(pokemonList -> !pokemonList.isEmpty())
                .subscribe(pokemonList -> {
                    view.showProgress(false);
                    if (addToExisting) {
                        view.addPokemonsList(pokemonList);
                    } else {
                        view.displayPokemonsList(pokemonList);
                    }
                }));
    }

    @Override
    public void unbind() {
        disposable.dispose();
    }
}
