package com.natatisha.pokemonapp.network;


import com.natatisha.pokemonapp.data.model.NamedApiResourceList;
import com.natatisha.pokemonapp.data.model.Pokemon;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PokemonApiClient implements PokemonApi {

    private PokemonApiService mService;

    @Inject
    public PokemonApiClient(PokemonApiService mService) {
        this.mService = mService;
    }

    @Override
    public Observable<NamedApiResourceList> getPokemonList(int offset, int limit) {
        return mService.getPokemonList(offset, limit);
    }

    @Override
    public Observable<Pokemon> getPokemon(int id) {
        return mService.getPokemon(id);
    }

}
