package com.natatisha.pokemonapp.di;

import android.app.Application;

import com.natatisha.pokemonapp.PokemonApp;
import com.natatisha.pokemonapp.data.source.RepositoryModule;
import com.natatisha.pokemonapp.network.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(PokemonApp app);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
