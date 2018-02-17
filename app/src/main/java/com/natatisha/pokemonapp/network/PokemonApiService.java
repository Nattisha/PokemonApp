package com.natatisha.pokemonapp.network;

import com.natatisha.pokemonapp.data.model.NamedApiResourceList;
import com.natatisha.pokemonapp.data.model.Pokemon;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApiService {

    @GET("pokemon/")
    Observable<NamedApiResourceList> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{id}/")
    Observable<Pokemon> getPokemon(@Path("id") int id);

}
