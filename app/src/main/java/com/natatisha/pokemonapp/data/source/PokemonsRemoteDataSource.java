package com.natatisha.pokemonapp.data.source;

import com.natatisha.pokemonapp.data.model.NamedApiResource;
import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.network.PokemonApiProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PokemonsRemoteDataSource implements PokemonsDataSource.Remote {

    private PokemonApiProvider apiProvider;

    @Inject
    public PokemonsRemoteDataSource(PokemonApiProvider apiProvider) {
        this.apiProvider = apiProvider;
    }

    @Override
    public Observable<List<Pokemon>> getPokemonsList(int offset, int limit) {
        return apiProvider.getPokemonApi().
                getPokemonList(offset, limit).map(namedApiResourceList -> {
            List<Pokemon> result = new ArrayList<>();
            for (NamedApiResource namedApiResource : namedApiResourceList.getResults()) {
                String[] segments = namedApiResource.getUrl().split("/");
                String idStr = segments[segments.length-1];
                int id = Integer.parseInt(idStr);
                result.add(new Pokemon(id,
                        namedApiResource.getUrl(),
                        namedApiResource.getName(),
                        0, 0, 0));
            }
            return result;
        });

    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return apiProvider.getPokemonApi().getPokemon(id);
    }

}
