package com.natatisha.pokemonapp.utils;

public interface Constants {

    String API_URL = "http://pokeapi.co/api/v2/";

    String CURRENT_PAGE_KEY = "current_page_key";
    String IS_LOADING_KEY = "is_loading";
    String POKEMON_ID_KEY = "pokemon_id";
    String POKEMON_NAME_KEY = "pokemon_name";
    String IS_SNACK_BAR_SHOWING_KEY = "is_snackbar_showing";

    int PAGINATION_MARGIN = 10;
    int NETWORK_PAGE_SIZE = 30;
    int DB_PAGE_SIZE = 50;


    int DB_VERSION = 1;
    String DB_NAME = "pokemons_db";
    String POKEMONS_TABLE = "pokemons";
    String KEY_POKEMON_ID = "pokemon_id";
}
