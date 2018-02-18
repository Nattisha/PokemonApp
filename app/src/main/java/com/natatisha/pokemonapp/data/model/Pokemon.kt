package com.natatisha.pokemonapp.data.model

data class Pokemon(
        val id: Int = 0,
        val url: String = "azaza",
        val name: String = "",
        val baseExperience: Int = 0,
        val weight: Int = 0,
        val height: Int = 0/*,
        val isDefault: Boolean,
        val order: Int,
        val species: NamedApiResource,
        val abilities: List<PokemonAbility>,
        val forms: List<NamedApiResource>,
        val heldItems: List<PokemonHeldItem>,
        val moves: List<PokemonMove>,
        val stats: List<PokemonStat>,
        val sprites: PokemonSprites,
        val types: List<PokemonType>*/)

data class PokemonAbility(
        val isHidden: Boolean,
        val slot: Int,
        val ability: NamedApiResource
)

data class PokemonHeldItem(
        val item: NamedApiResource,
        val versionDetails: List<PokemonHeldItemVersion>
)

data class PokemonHeldItemVersion(
        val version: NamedApiResource,
        val rarity: Int
)

data class PokemonMove(
        val move: NamedApiResource,
        val versionGroupDetails: List<PokemonMoveVersion>
)

data class PokemonMoveVersion(
        val moveLearnMethod: NamedApiResource,
        val versionGroup: NamedApiResource,
        val levelLearnedAt: Int
)

data class PokemonStat(
        val stat: NamedApiResource,
        val effort: Int,
        val baseStat: Int
)

data class PokemonType(
        val slot: Int,
        val type: NamedApiResource
)

data class PokemonSprites(
        val backDefault: String?,
        val backShiny: String?,
        val frontDefault: String?,
        val frontShiny: String?,
        val backFemale: String?,
        val backShinyFemale: String?,
        val frontFemale: String?,
        val frontShinyFemale: String?
)
