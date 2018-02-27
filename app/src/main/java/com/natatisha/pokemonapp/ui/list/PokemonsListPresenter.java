package com.natatisha.pokemonapp.ui.list;

import com.natatisha.pokemonapp.data.source.PokemonsRepository;
import com.natatisha.pokemonapp.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.natatisha.pokemonapp.utils.Constants.PAGE_SIZE;

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
        loadPokemonsList(forceRefresh, false);
    }

    @Override
    public void loadNextPage() {
        currentPage++;
        loadPokemonsList(true, true);
    }

    @Override
    public void refresh() {
        currentPage = 0;
        repository.clearCache();
        loadPokemonsList(true, false);
    }

    private void loadPokemonsList(boolean forceRefresh, boolean addToExisting) {
        view.showProgress(true);
        if (repository.isLoading())
            return;

        if (forceRefresh) {
            repository.refreshData();
        }
        disposable.add(RxUtils.wrapAsync(repository.getPokemonsList(currentPage))
                .doOnError(throwable -> {
                    view.showProgress(false);
                    view.showPokemonsLoadingError();
                })
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
