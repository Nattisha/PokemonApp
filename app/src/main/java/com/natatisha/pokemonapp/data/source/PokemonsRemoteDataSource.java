package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.NamedApiResource;
import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.network.PokemonApiProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokemonsRemoteDataSource implements PokemonsDataSource {

    private PokemonApiProvider apiProvider;

    @Inject
    public PokemonsRemoteDataSource(PokemonApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

    @Override
    public void refreshData() {
        //do nothing
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList(int page) {
        return apiProvider.getPokemonApi().
                getPokemonList(page * PAGE_SIZE, PAGE_SIZE).map(namedApiResourceList -> {
            List<Pokemon> result = new ArrayList<>();
            for (NamedApiResource namedApiResource : namedApiResourceList.getResults()) {
                result.add(new Pokemon(namedApiResource.getId(), namedApiResource.getName()));
            }
            return result;
        });
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return apiProvider.getPokemonApi().getPokemon(id);
    }

    @Override
    public void savePokemon(@NonNull Pokemon pokemon) {
        //do nothing
    }

    @Override
    public void savePokemonsList(@NonNull List<Pokemon> pokemonList) {
        //do nothing
    }
}
