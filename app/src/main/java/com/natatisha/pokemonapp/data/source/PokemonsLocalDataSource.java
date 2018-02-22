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
    public Observable<List<Pokemon>> getPokemonsList(int offset, int limit) {
        return Observable.just(pokemonsDatabaseHelper.getPokemonsList(offset, limit));
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return Observable.just(pokemonsDatabaseHelper.getPokemon(id));
    }

    @Override
    public void savePokemon(@NonNull Pokemon pokemon) {
        if (pokemonsDatabaseHelper.isPokemonExists(pokemon.getId())) {
            pokemonsDatabaseHelper.updatePokemon(pokemon);
        } else {
            pokemonsDatabaseHelper.addPokemon(pokemon);
        }
    }

    @Override
    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        pokemonsDatabaseHelper.addPokemons(pokemonList);
    }

    @Override
    public boolean hasPokemon(int id) {
        return pokemonsDatabaseHelper.isPokemonExists(id) && pokemonsDatabaseHelper.hasFullInfo(id);
    }

    @Override
    public int getCacheSize() {
        return pokemonsDatabaseHelper.getPokemonsCount();
    }

    @Override
    public void deleteAll() {
        pokemonsDatabaseHelper.deleteAll();
    }
}
