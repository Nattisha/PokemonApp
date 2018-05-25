package com.natatisha.pokemonapp.data;

import android.support.v7.util.DiffUtil;

import com.natatisha.pokemonapp.data.model.Pokemon;

public class PokemonDiffItemCallback extends DiffUtil.ItemCallback<Pokemon> {

    @Override
    public boolean areItemsTheSame(Pokemon oldItem, Pokemon newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(Pokemon oldItem, Pokemon newItem) {
        return oldItem.equals(newItem);
    }
}
