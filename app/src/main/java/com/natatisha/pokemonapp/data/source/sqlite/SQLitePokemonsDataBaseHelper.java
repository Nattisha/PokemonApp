package com.natatisha.pokemonapp.data.source.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.natatisha.pokemonapp.data.model.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * Class which executes SQLite operations.
 * Different query styles (raw + using Android wrapper classes) are made by purpose
 */
public class SQLitePokemonsDataBaseHelper extends SQLiteOpenHelper implements PokemonsDatabaseHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "pokemons_db";

    private static final String POKEMONS_TABLE = "pokemons";
    private static final String KEY_POKEMON_ID = "pokemon_id";
    private static final String KEY_POKEMON_URL = "pokemon_url";
    private static final String KEY_POKEMON_NAME = "pokemon_name";
    private static final String KEY_POKEMON_WEIGHT = "pokemon_weight";
    private static final String KEY_POKEMON_HEIGHT = "pokemon_height";
    private static final String KEY_POKEMON_EXPERIENCE = "pokemon_experience";

    @Inject
    public SQLitePokemonsDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_POKEMONS_TABLE = "CREATE TABLE " + POKEMONS_TABLE + "("
                + KEY_POKEMON_ID + " INTEGER PRIMARY KEY,"
                + KEY_POKEMON_URL + " TEXT,"
                + KEY_POKEMON_NAME + " TEXT,"
                + KEY_POKEMON_HEIGHT + " INTEGER,"
                + KEY_POKEMON_WEIGHT + " INTEGER,"
                + KEY_POKEMON_EXPERIENCE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_POKEMONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", POKEMONS_TABLE));
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addPokemon(@NonNull Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getPokemonContentValues(pokemon);

        db.insert(POKEMONS_TABLE, null, values);
        db.close();
    }

    @Override
    public void addPokemons(@NonNull List<Pokemon> pokemons) {
        SQLiteDatabase db = this.getWritableDatabase();
        String rawQuery = insertPokemonQuery() + " VALUES ";
        for (int i = 0; i < pokemons.size(); i++) {
            boolean isLast = i == pokemons.size() - 1;
            rawQuery = rawQuery.concat(getPokemonContentValuesStr(pokemons.get(i))).concat(isLast ? ";" : ",");
        }
        Cursor cursor = db.rawQuery(rawQuery, null);
        cursor.close();
    }

    @Override
    public void updatePokemon(@NonNull Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getPokemonContentValues(pokemon);
        db.update(POKEMONS_TABLE, values, KEY_POKEMON_ID + " = ?", new String[]{String.valueOf(pokemon.getId())});
        db.close();
    }

    @Override
    public void deletePokemon(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POKEMONS_TABLE, KEY_POKEMON_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POKEMONS_TABLE, null, null);
        db.close();
    }

    @Override
    public int getPokemonsCount() {
        String countQuery = "SELECT * FROM " + POKEMONS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public Pokemon getPokemon(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(POKEMONS_TABLE, new String[]{
                        KEY_POKEMON_ID,
                        KEY_POKEMON_URL,
                        KEY_POKEMON_NAME,
                        KEY_POKEMON_WEIGHT,
                        KEY_POKEMON_HEIGHT,
                        KEY_POKEMON_EXPERIENCE}, KEY_POKEMON_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        Pokemon pokemon = new Pokemon();
        if (cursor != null && cursor.moveToFirst()) {
            pokemon = getPokemonFromCursor(cursor);
            cursor.close();
        }

        return pokemon;
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> result = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + POKEMONS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(getPokemonFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return result;
    }

    @Override
    public List<Pokemon> getPokemonsList(int offset, int limit) {
        List<Pokemon> result = new ArrayList<>();
        String selectQuery = String.format(Locale.US,
                "SELECT * FROM %s LIMIT %d OFFSET %d;", POKEMONS_TABLE, limit, offset);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                result.add(getPokemonFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    @Override
    public boolean isPokemonExists(int id) {
        String selectQuery = String.format(Locale.US,
                "SELECT * FROM %s WHERE %s =%d", POKEMONS_TABLE, KEY_POKEMON_ID, id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean exists = cursor.getCount() <= 0;
        cursor.close();
        return exists;
    }

    @Override
    public boolean hasFullInfo(int id) {
        Pokemon pokemon = getPokemon(id);
        return pokemon.getHeight() >= 0 && pokemon.getWeight() >= 0 && pokemon.getBaseExperience() >= 0;
    }

    //<editor-fold desc="utils methods">

    @NonNull
    private Pokemon getPokemonFromCursor(Cursor cursor) {
        return new Pokemon(cursor.getInt(cursor.getColumnIndex(KEY_POKEMON_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_POKEMON_URL)),
                cursor.getString(cursor.getColumnIndex(KEY_POKEMON_NAME)),
                cursor.getInt(cursor.getColumnIndex(KEY_POKEMON_WEIGHT)),
                cursor.getInt(cursor.getColumnIndex(KEY_POKEMON_HEIGHT)),
                cursor.getInt(cursor.getColumnIndex(KEY_POKEMON_EXPERIENCE)));
    }

    private String insertPokemonQuery() {
        return String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s)",
                POKEMONS_TABLE,
                KEY_POKEMON_ID,
                KEY_POKEMON_URL,
                KEY_POKEMON_NAME,
                KEY_POKEMON_WEIGHT,
                KEY_POKEMON_HEIGHT,
                KEY_POKEMON_EXPERIENCE);
    }

    private String getPokemonContentValuesStr(@NonNull Pokemon pokemon) {
        return String.format(Locale.US,
                "(%d, %s, %s, %d, %d, %d)",
                pokemon.getId(),
                toSQLStr(pokemon.getUrl()),
                toSQLStr(pokemon.getName()),
                pokemon.getWeight(),
                pokemon.getHeight(),
                pokemon.getBaseExperience());
    }

    @NonNull
    private ContentValues getPokemonContentValues(@NonNull Pokemon pokemon) {
        ContentValues values = new ContentValues();
        values.put(KEY_POKEMON_ID, pokemon.getId());
        values.put(KEY_POKEMON_URL, pokemon.getUrl());
        values.put(KEY_POKEMON_NAME, pokemon.getName());
        values.put(KEY_POKEMON_WEIGHT, pokemon.getWeight());
        values.put(KEY_POKEMON_HEIGHT, pokemon.getHeight());
        values.put(KEY_POKEMON_EXPERIENCE, pokemon.getBaseExperience());
        return values;
    }

    private String toSQLStr(String inputStr) {
        return "'".concat(inputStr).concat("'");
    }

    //</editor-fold>

}
