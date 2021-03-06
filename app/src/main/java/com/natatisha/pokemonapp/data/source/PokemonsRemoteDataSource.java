package com.natatisha.pokemonapp.data.source;

import android.support.annotation.NonNull;

import com.annimon.stream.Stream;
import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.data.model.PokemonSprites;
import com.natatisha.pokemonapp.network.PokemonApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokemonsRemoteDataSource implements PokemonsDataSource.Remote {

    private PokemonApi pokemonApi;

    private int itemsCount;

    @Inject
    public PokemonsRemoteDataSource(PokemonApi api) {
        this.pokemonApi = api;
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList(int offset, int limit) {
        return pokemonApi.
                getPokemonList(offset, limit).map(namedApiResourceList -> {
            itemsCount = namedApiResourceList.getCount();
            List<Pokemon> result = new ArrayList<>();
            Stream.of(namedApiResourceList.getResults()).forEach(namedApiResource ->
                    result.add(getDefaultPokemon(namedApiResource.getName(), namedApiResource.getId())));
            return result;
        });

    }

    @NonNull
    private Pokemon getDefaultPokemon(String name, int id) {
        return new Pokemon(id,
                name,
                0, 0, 0,
                new PokemonSprites(null, null));
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return pokemonApi.getPokemon(id).
                onErrorReturn(throwable -> getDefaultPokemon("azazaz", id));
    }

}
