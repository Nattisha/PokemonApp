package com.natatisha.pokemonapp.di;

import com.natatisha.pokemonapp.ui.PokemonsListActivity;
import com.natatisha.pokemonapp.ui.PokemonsListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = PokemonsListModule.class)
    abstract PokemonsListActivity pokemonsListActivity();
}
