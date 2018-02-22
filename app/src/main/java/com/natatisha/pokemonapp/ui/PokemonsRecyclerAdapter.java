package com.natatisha.pokemonapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonsRecyclerAdapter extends RecyclerView.Adapter<PokemonsRecyclerAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonsList;

    public PokemonsRecyclerAdapter(List<Pokemon> pokemonsList) {
        this.pokemonsList = pokemonsList;
    }

    public void refreshData(List<Pokemon> pokemonList){
        this.pokemonsList = pokemonList;
        notifyDataSetChanged();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pokemon_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        holder.pokemonNameTv.setText(pokemonsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pokemonsList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pokemonIv)
        ImageView pokemonIv;

        @BindView(R.id.pokemonNameTv)
        TextView pokemonNameTv;

        PokemonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
