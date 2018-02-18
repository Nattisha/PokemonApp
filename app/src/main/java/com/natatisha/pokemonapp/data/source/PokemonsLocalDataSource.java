package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.source.sqlite.PokemonsDatabaseHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokemonsLocalDataSource implements PokemonsDataSource.Local {

    private PokemonsDatabaseHelper pokemonsDatabaseHelper;

    @Inject
    public PokemonsLocalDataSource(@NonNull PokemonsDatabaseHelper pokemonsDatabaseHelper) {
        this.pokemonsDatabaseHelper = pokemonsDatabaseHelper;
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList() {
        return Observable.just(pokemonsDatabaseHelper.getAllPokemons());
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return Observable.just(pokemonsDatabaseHelper.getPokemon(id));
    }

    @Override
    public void savePokemon(@NonNull Pokemon pokemon) {
        pokemonsDatabaseHelper.addPokemon(pokemon);
    }

    @Override
    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        pokemonsDatabaseHelper.addPokemons(pokemonList);
    }
}
