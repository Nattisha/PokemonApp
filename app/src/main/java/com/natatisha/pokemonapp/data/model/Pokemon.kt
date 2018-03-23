package com.natatisha.pokemonapp.data.model

import android.arch.persistence.room.*
import com.natatisha.pokemonapp.utils.Constants.KEY_POKEMON_ID
import com.natatisha.pokemonapp.utils.Constants.POKEMONS_TABLE

@Entity(tableName = POKEMONS_TABLE)
data class Pokemon(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = KEY_POKEMON_ID)
        val id: Int = 0,
        val name: String = "",
        val baseExperience: Int = -1,
        val weight: Int = -1,
        val height: Int = -1,
        @Embedded
        val sprites: PokemonSprites?)

data class PokemonSprites(
        val backDefault: String?,
        val frontDefault: String?
)
