package com.natatisha.pokemonapp.network;

import com.natatisha.pokemonapp.data.model.NamedApiResourceList;
import com.natatisha.pokemonapp.data.model.Pokemon;

import io.reactivex.Observable;

public interface PokemonApi {

    Observable<NamedApiResourceList> getPokemonList(int offset, int limit);

    Observable<Pokemon> getPokemon(int id);

}
