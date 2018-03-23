package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.source.room.PokemonDao;
import com.natatisha.pokemonapp.data.source.room.PokemonsDataBase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class PokemonsLocalDataSource implements PokemonsDataSource.Local {

    private PokemonDao pokemonDao;

    @Inject
    public PokemonsLocalDataSource(@NonNull PokemonsDataBase pokemonDb) {
        this.pokemonDao = pokemonDb.pokemonDao();
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList() {
        return pokemonDao.getPokemonsList().toObservable();
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList(int offset, int limit) {
        return pokemonDao.getPokemonsList().toObservable();
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return pokemonDao.getPokemon(id).toObservable();
    }

    @Override
    public void savePokemon(@NonNull Pokemon pokemon) {
        pokemonDao.savePokemon(pokemon);
    }

    @Override
    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        pokemonDao.savePokemonsList(pokemonList);
    }

    @Override
    public boolean hasFullPokemonInfo(int id) {
        Pokemon pokemon = pokemonDao.getPokemon(id).blockingFirst();
        return pokemon != null && pokemon.getSprites() != null && pokemon.getSprites().getBackDefault() != null
                && !pokemon.getSprites().getBackDefault().isEmpty();
    }

    @Override
    public Completable deleteAll() {
        return Completable.fromRunnable(() -> pokemonDao.deleteAll());
    }
}

