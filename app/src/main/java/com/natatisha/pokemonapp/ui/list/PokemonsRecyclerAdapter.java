package com.natatisha.pokemonapp.ui.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonsRecyclerAdapter extends RecyclerView.Adapter<PokemonsRecyclerAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonsList;

    private PokemonClickListener clickListener;

    public PokemonsRecyclerAdapter(List<Pokemon> pokemonsList, PokemonClickListener clickListener) {
        this.pokemonsList = pokemonsList;
        this.clickListener = clickListener;
    }

    public void refreshData(List<Pokemon> pokemonList) {
        this.pokemonsList = pokemonList;
        notifyDataSetChanged();
    }

    public void addItemsToList(List<Pokemon> pokemonList) {
        this.pokemonsList.addAll(pokemonList);
        notifyDataSetChanged();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PokemonViewHolder holder = new PokemonViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pokemon_list_item, parent, false));
        holder.container.setOnClickListener(view -> clickListener.onClick(pokemonsList.get(holder.getAdapterPosition())));
        return holder;
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        String name = pokemonsList.get(position).getName().substring(0, 1).toUpperCase() + pokemonsList.get(position).getName().substring(1);
        holder.pokemonNameTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return pokemonsList.size();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pokemonNameTv)
        TextView pokemonNameTv;

        @BindView(R.id.pokemonCv)
        ViewGroup container;

        PokemonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
