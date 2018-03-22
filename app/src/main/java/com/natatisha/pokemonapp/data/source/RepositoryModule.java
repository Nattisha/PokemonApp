package com.natatisha.pokemonapp.data.source;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.natatisha.pokemonapp.data.source.room.PokemonsDataBase;
import com.natatisha.pokemonapp.data.source.sqlite.PokemonsDatabaseHelper;
import com.natatisha.pokemonapp.data.source.sqlite.SQLiteSimpleDataBaseHelper;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.natatisha.pokemonapp.utils.Constants.DB_NAME;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Provides
    static PokemonsDataBase provideRoomDataBase(Context context) {
        return Room.databaseBuilder(context,
                PokemonsDataBase.class, DB_NAME)
                .build();
    }

    @Singleton
    @Binds
    abstract PokemonsDatabaseHelper provideDataBaseHelper(SQLiteSimpleDataBaseHelper helper);

    @Singleton
    @Binds
    abstract PokemonsDataSource.Local provideLocalDataSource(PokemonsLocalDataSource localDataSource);

    @Singleton
    @Binds
    abstract PokemonsDataSource.Remote provideRemoteDataSource(PokemonsRemoteDataSource remoteDataSource);

}
