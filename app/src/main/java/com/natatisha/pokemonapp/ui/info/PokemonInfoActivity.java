package com.natatisha.pokemonapp.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.model.Pokemon;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.natatisha.pokemonapp.utils.Constants.POKEMON_ID_KEY;

public class PokemonInfoActivity extends DaggerAppCompatActivity implements PokemonInfoContract.View {

    @BindView(R.id.front_iv)
    ImageView frontIv;

    @BindView(R.id.back_iv)
    ImageView backIv;

    @BindView(R.id.weight_tv)
    TextView weightTv;

    @BindView(R.id.height_tv)
    TextView heightTv;

    @BindView(R.id.exp_tv)
    TextView experienceTv;

    private int pokemonId;

    @Inject
    PokemonInfoContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            pokemonId = getIntent().getIntExtra(POKEMON_ID_KEY, 0);
        }
        setContentView(R.layout.pokemon_info_layout);
        ButterKnife.bind(this);
        presenter.bind(this);
        presenter.loadPokemon(pokemonId);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void showPokemon(@NotNull Pokemon pokemon) {
        weightTv.setText(String.valueOf(pokemon.getWeight()));
        heightTv.setText(String.valueOf(pokemon.getHeight()));
        experienceTv.setText(String.valueOf(pokemon.getBaseExperience()));
        Picasso.with(this).load(pokemon.getSprites().getBackDefault()).into(backIv);
        Picasso.with(this).load(pokemon.getSprites().getFrontDefault()).into(frontIv);
    }

    @Override
    public void showPokemonLoadError() {

    }
}
