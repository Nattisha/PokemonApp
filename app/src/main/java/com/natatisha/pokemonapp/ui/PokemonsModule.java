package com.natatisha.pokemonapp.ui;

import com.natatisha.pokemonapp.ui.info.PokemonInfoContract;
import com.natatisha.pokemonapp.ui.info.PokemonInfoPresenter;
import com.natatisha.pokemonapp.ui.list.PokemonsListContract;
import com.natatisha.pokemonapp.ui.list.PokemonsListPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PokemonsModule {

    @Binds
    abstract PokemonsListContract.Presenter providePokemonsListPresenter(PokemonsListPresenter pokemonsListPresenter);

    @Binds
    abstract PokemonInfoContract.Presenter providePokemonInfoPresenter(PokemonInfoPresenter pokemonInfoPresenter);

}
