package com.natatisha.pokemonapp.di;

import com.natatisha.pokemonapp.ui.PokemonsModule;
import com.natatisha.pokemonapp.ui.info.PokemonInfoActivity;
import com.natatisha.pokemonapp.ui.list.PokemonsListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = PokemonsModule.class)
    abstract PokemonsListActivity pokemonsListActivity();

    @ContributesAndroidInjector(modules = PokemonsModule.class)
    abstract PokemonInfoActivity pokemonsInfoActivity();
}
