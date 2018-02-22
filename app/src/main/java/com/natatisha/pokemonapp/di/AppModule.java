package com.natatisha.pokemonapp.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.natatisha.pokemonapp.network.PokemonApi;
import com.natatisha.pokemonapp.network.PokemonApiClient;
import com.natatisha.pokemonapp.network.PokemonApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.natatisha.pokemonapp.utils.Constants.API_URL;

@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);

    @Singleton
    @Provides
    static Gson gson() {
        return new Gson();
    }

    @Singleton
    @Provides
    static Retrofit.Builder retBuilder(Gson gson) {
        return new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create(gson)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    @Singleton
    @Provides
    static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    static PokemonApiService apiService(OkHttpClient client, Retrofit.Builder builder) {
        return builder
                .client(client)
                .baseUrl(API_URL)
                .build()
                .create(PokemonApiService.class);
    }

    @Binds
    abstract PokemonApi pokemonApi(PokemonApiClient client);
}
