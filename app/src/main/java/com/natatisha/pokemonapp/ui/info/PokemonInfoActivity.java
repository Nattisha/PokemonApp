package com.natatisha.pokemonapp.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

import static com.natatisha.pokemonapp.utils.Constants.IS_LOADING_KEY;
import static com.natatisha.pokemonapp.utils.Constants.POKEMON_ID_KEY;
import static com.natatisha.pokemonapp.utils.Constants.POKEMON_NAME_KEY;

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

    @BindView(R.id.pokemon_info_refresher)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    PokemonInfoContract.Presenter presenter;

    private boolean isLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null)
            finish();

        int pokemonId = getIntent().getIntExtra(POKEMON_ID_KEY, 0);
        String pokemonName = getIntent().getStringExtra(POKEMON_NAME_KEY).substring(0, 1).toUpperCase() +
                getIntent().getStringExtra(POKEMON_NAME_KEY).substring(1);
        setContentView(R.layout.pokemon_info_layout);
        getSupportActionBar().setTitle(pokemonName);
        ButterKnife.bind(this);
        swipeRefreshLayout.setRefreshing(isLoading);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadPokemon(pokemonId));
        presenter.bind(this);
        presenter.loadPokemon(pokemonId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_LOADING_KEY, swipeRefreshLayout.isRefreshing());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            isLoading = savedInstanceState.getBoolean(IS_LOADING_KEY);
        }
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
        //load icons here to avoid Context in presenter
        Picasso.with(this).load(pokemon.getSprites().getBackDefault()).into(backIv);
        Picasso.with(this).load(pokemon.getSprites().getFrontDefault()).into(frontIv);
    }

    @Override
    public void showPokemonLoadError() {
        finish();
    }

    @Override
    public void showProgress(boolean active) {
        swipeRefreshLayout.setRefreshing(active);
    }
}
