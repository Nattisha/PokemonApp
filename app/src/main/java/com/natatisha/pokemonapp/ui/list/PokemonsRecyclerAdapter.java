package com.natatisha.pokemonapp.ui.list;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.model.Pokemon;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PokemonsRecyclerAdapter extends PagedListAdapter<Pokemon, PokemonsRecyclerAdapter.PokemonViewHolder> {

    private PokemonClickListener clickListener;

    protected PokemonsRecyclerAdapter(@NonNull DiffUtil.ItemCallback<Pokemon> diffCallback,
                                      PokemonClickListener clickListener) {
        super(diffCallback);
        this.clickListener = clickListener;
    }

    protected PokemonsRecyclerAdapter(@NonNull AsyncDifferConfig<Pokemon> config,
                                      PokemonClickListener clickListener) {
        super(config);
        this.clickListener = clickListener;
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PokemonViewHolder holder = new PokemonViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pokemon_list_item, parent, false));
        holder.container.setOnClickListener(view -> clickListener.onClick(getItem(holder.getAdapterPosition())));
        return holder;
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {
        Pokemon pokemon = getItem(position);
        if (pokemon != null) {
            String name = pokemon.getName().substring(0, 1).toUpperCase() + pokemon.getName().substring(1);
            holder.pokemonNameTv.setText(name);
        }
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
