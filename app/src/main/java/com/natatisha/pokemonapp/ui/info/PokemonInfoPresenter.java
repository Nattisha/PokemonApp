package com.natatisha.pokemonapp.ui.info;

import com.natatisha.pokemonapp.data.source.PokemonsRepository;
import com.natatisha.pokemonapp.utils.RxUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PokemonInfoPresenter implements PokemonInfoContract.Presenter {

    private PokemonInfoContract.View view;

    private PokemonsRepository repository;

    private CompositeDisposable compositeDisposable;

    @Inject
    public PokemonInfoPresenter(PokemonsRepository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadPokemon(int id) {
        compositeDisposable.add(RxUtils.wrapAsync(repository.getPokemon(id)).
                doOnError(throwable -> view.showPokemonLoadError()).
                subscribe(pokemon -> view.showPokemon(pokemon)));
    }

    @Override
    public void bind(@NotNull PokemonInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
