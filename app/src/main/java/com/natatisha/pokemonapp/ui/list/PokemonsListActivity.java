package com.natatisha.pokemonapp.ui.list;

import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.PokemonDiffItemCallback;
import com.natatisha.pokemonapp.data.model.Pokemon;
import com.natatisha.pokemonapp.ui.info.PokemonInfoActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.natatisha.pokemonapp.utils.Constants.CURRENT_PAGE_KEY;
import static com.natatisha.pokemonapp.utils.Constants.IS_LOADING_KEY;
import static com.natatisha.pokemonapp.utils.Constants.IS_SNACK_BAR_SHOWING_KEY;
import static com.natatisha.pokemonapp.utils.Constants.NETWORK_PAGE_SIZE;
import static com.natatisha.pokemonapp.utils.Constants.PAGINATION_MARGIN;
import static com.natatisha.pokemonapp.utils.Constants.POKEMON_ID_KEY;
import static com.natatisha.pokemonapp.utils.Constants.POKEMON_NAME_KEY;

public class PokemonsListActivity extends DaggerAppCompatActivity implements PokemonsListContract.View {

    private static final String TAG = PokemonsListActivity.class.getName();

    @Inject
    PokemonsListContract.Presenter presenter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView pokemonsRecycler;

    private boolean isSnackBarShowing;

    private boolean isLoading;

    private PokemonsRecyclerAdapter adapter;

    private static DiffUtil.ItemCallback<Pokemon> POKEMONS_COMPARATOR =
            new PokemonDiffItemCallback();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        bindPresenter(savedInstanceState);
    }

    private void initView() {
        setContentView(R.layout.activity_pokemon);
        ButterKnife.bind(this);
        adapter = new PokemonsRecyclerAdapter(POKEMONS_COMPARATOR, this::showPokemonInfoScreen);
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setRefreshing(isLoading);
        refreshLayout.setOnRefreshListener(this::fullRefresh);
    }

    private void bindPresenter(Bundle savedInstanceState) {
        presenter.bind(this);
        presenter.loadData(savedInstanceState == null);
    }

    private void fullRefresh() {
        pokemonsRecycler.scrollToPosition(0);
        presenter.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pokemons_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            fullRefresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void showProgress(boolean active) {
        refreshLayout.setRefreshing(active);
    }

    @Override
    public void displayPokemonsList(PagedList<Pokemon> pokemonList) {
        adapter.submitList(pokemonList);
    }

    @Override
    public void showPokemonsLoadingError() {
        showErrorSnackBar();
    }

    private void showPokemonInfoScreen(Pokemon pokemon) {
        Intent intent = new Intent(this, PokemonInfoActivity.class);
        intent.putExtra(POKEMON_ID_KEY, pokemon.getId());
        intent.putExtra(POKEMON_NAME_KEY, pokemon.getName());
        startActivity(intent);
    }

    private void showErrorSnackBar() {
        isSnackBarShowing = true;
        Snackbar errorSnackbar = Snackbar.make(refreshLayout, R.string.S_ERROR, Snackbar.LENGTH_LONG);
        errorSnackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                isSnackBarShowing = false;
            }
        });
        errorSnackbar.show();
    }

    private void saveState(Bundle outState) {
        outState.putBoolean(IS_LOADING_KEY, refreshLayout.isRefreshing());
        outState.putBoolean(IS_SNACK_BAR_SHOWING_KEY, isSnackBarShowing);
    }

    private void restoreState(Bundle savedInstanceState) {
        isLoading = savedInstanceState.getBoolean(IS_LOADING_KEY);
        if (savedInstanceState.getBoolean(IS_SNACK_BAR_SHOWING_KEY)) {
            showErrorSnackBar();
        }
    }

}
