package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import io.reactivex.Observable;

public interface PokemonsDataSource {

    int PAGE_SIZE = 30;

    void refreshData();

    Observable<List<Pokemon>> getPokemonsList(int page);

    Observable<Pokemon> getPokemon(int id);

    void savePokemon(@NonNull Pokemon pokemon);

    void savePokemonsList(@NonNull List<Pokemon> pokemonList);
}
