package com.natatisha.pokemonapp.ui;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PokemonsListModule {

    @Binds
    abstract PokemonsListContract.Presenter providePokemonsListPresenter(PokemonsListPresenter pokemonsListPresenter);

}
