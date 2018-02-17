package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Observable;

public class PokemonsLocalDataSource implements PokemonsDataSource {

    @Override
    public void refreshData() {

    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList(int page) {
        return null;
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return null;
    }

    @Override
    public void savePokemon(@NonNull Pokemon pokemon) {

    }

    @Override
    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {

    }
}
