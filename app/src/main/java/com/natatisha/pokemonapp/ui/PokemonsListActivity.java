package com.natatisha.pokemonapp.ui;

import android.os.Bundle;
import android.util.Log;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.source.PokemonsRepository;
import com.natatisha.pokemonapp.utils.RxUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public class PokemonsListActivity extends DaggerAppCompatActivity {

    private static final String TAG = PokemonsListActivity.class.getName();

    @Inject
    PokemonsRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(
                RxUtils.wrapAsync(repository.
                        getPokemonsList(1)).doOnNext(pokemon -> {
                }).subscribe(pokemons -> Log.v(TAG, pokemons.get(3).getName() + "")));
    }

}
