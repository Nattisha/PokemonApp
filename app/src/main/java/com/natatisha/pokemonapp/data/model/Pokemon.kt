package com.natatisha.pokemonapp.data.model

data class Pokemon(
        val id: Int = 0,
        val name: String = "",
        val baseExperience: Int = -1,
        val weight: Int = -1,
        val height: Int = -1,
        val sprites: PokemonSprites)

data class PokemonSprites(
        val backDefault: String?,
        val frontDefault: String?
)
