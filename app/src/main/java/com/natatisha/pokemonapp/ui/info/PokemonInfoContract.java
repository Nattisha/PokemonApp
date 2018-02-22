package com.natatisha.pokemonapp.ui.info;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

public interface PokemonInfoContract {

    interface View {

        void showPokemon(@NonNull Pokemon pokemon);

        void showPokemonLoadError();
    }

    interface Presenter {

        void loadPokemon(int id);

        void bind(PokemonInfoContract.View view);

        void unbind();
    }
}
