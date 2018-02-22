package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.source.sqlite.PokemonsDatabaseHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
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
    public Completable savePokemon(@NonNull Pokemon pokemon) {
        return Completable.fromRunnable(() -> pokemonsDatabaseHelper.addPokemon(pokemon));
    }

    @Override
    public Completable savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        return Completable.fromRunnable(() -> pokemonsDatabaseHelper.addPokemons(pokemonList));
    }

    @Override
    public boolean hasPokemon(int id) {
        return pokemonsDatabaseHelper.isPokemonExists(id) && pokemonsDatabaseHelper.hasFullInfo(id);
    }

    @Override
    public int getCacheSize() {
        return pokemonsDatabaseHelper.getPokemonsCount();
    }
}
