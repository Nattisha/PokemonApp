package com.natatisha.pokemonapp.ui.list;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

public interface PokemonsListContract {

    interface View {

        void setEndOfList(boolean endOfList);

        void showProgress(boolean active);

        void displayPokemonsList(List<Pokemon> pokemonList);

        void addPokemonsList(List<Pokemon> pokemonList);

        void showPokemonsLoadingError();
    }

    interface Presenter {

        void bind(View view);

        int getCurrentPage();

        void setCurrentPage(int page);

        void loadData(boolean forceRefresh);

        void loadNextPage();

        void refresh();

        void unbind();

    }
}
