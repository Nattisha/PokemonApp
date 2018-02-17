package com.natatisha.pokemonapp.network;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonApiProvider {

    public static final String API_URL = "http://pokeapi.co/api/v2/";

    private Retrofit.Builder mBuilder;

    private Gson gson;

    private PokemonApi mPokemonApi;

    @Inject
    public PokemonApiProvider(Gson gson) {
        this.gson = gson;
        mBuilder = new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public PokemonApi getPokemonApi() {
        if (mPokemonApi == null) {
            mPokemonApi = new PokemonApiClient(createService());
        }
        return mPokemonApi;
    }

    private PokemonApiService createService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return mBuilder
                .client(client)
                .baseUrl(API_URL)
                .build()
                .create(PokemonApiService.class);
    }

}
