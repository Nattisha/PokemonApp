package com.natatisha.pokemonapp.ui.list;

import android.arch.paging.PagedList;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

public interface PokemonsListContract {

    interface View {

        void showProgress(boolean active);

        void displayPokemonsList(PagedList<Pokemon> pokemonList);

        void showPokemonsLoadingError();
    }

    interface Presenter {

        void bind(View view);

        void loadData(boolean forceRefresh);

        void refresh();

        void unbind();

    }
}
