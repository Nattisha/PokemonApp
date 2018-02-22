package com.natatisha.pokemonapp.ui.info;

import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

public interface PokemonInfoContract {

    interface View {

        void showPokemon(@NonNull Pokemon pokemon);

        void showPokemonLoadError();

        void showProgress(boolean active);
    }

    interface Presenter {

        void loadPokemon(int id);

        void bind(PokemonInfoContract.View view);

        void unbind();
    }
}
