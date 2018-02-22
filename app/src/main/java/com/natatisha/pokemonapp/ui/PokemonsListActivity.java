package com.natatisha.pokemonapp.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.natatisha.pokemonapp.R;
import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class PokemonsListActivity extends DaggerAppCompatActivity implements PokemonsListContract.View {

    private static final String TAG = PokemonsListActivity.class.getName();

    private static final String IS_LOADING_KEY = "is_loading";
    private static final String IS_SNACK_BAR_SHOWING_KEY = "is_snackbar_showing";

    @Inject
    PokemonsListContract.Presenter presenter;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler)
    RecyclerView pokemonsRecycler;

    private boolean isSnackBarShowing;

    private PokemonsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        presenter.bind(this);
        presenter.loadData(savedInstanceState == null);
    }

    private void initView() {
        setContentView(R.layout.activity_pokemon);
        ButterKnife.bind(this);
        adapter = new PokemonsRecyclerAdapter(new ArrayList<>());
        pokemonsRecycler.setAdapter(adapter);
        pokemonsRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
//        refreshLayout.setOnRefreshListener(() -> presenter.loadData(true));
        showErrorSnackBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_LOADING_KEY, refreshLayout.isRefreshing());
        outState.putBoolean(IS_SNACK_BAR_SHOWING_KEY, isSnackBarShowing);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            refreshLayout.setRefreshing(savedInstanceState.getBoolean(IS_LOADING_KEY));
            if (savedInstanceState.getBoolean(IS_SNACK_BAR_SHOWING_KEY)) {
                showErrorSnackBar();
            }
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
    public void displayPokemonsList(List<Pokemon> pokemonList) {
        adapter.refreshData(pokemonList);
    }

    @Override
    public void showPokemonsLoadingError() {
        showErrorSnackBar();
    }

    private void showErrorSnackBar() {
        isSnackBarShowing = true;
        Snackbar errorSnackbar = Snackbar.make(refreshLayout, "Error", Snackbar.LENGTH_LONG);
        errorSnackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                isSnackBarShowing = false;
            }
        });
        errorSnackbar.show();
    }

}
