package com.natatisha.pokemonapp.data.model

data class Pokemon(
        val id: Int,
        val name: String) {
    constructor(
            id: Int,
            name: String,
            baseExperience: Int,
            height: Int,
            isDefault: Boolean,
            order: Int,
            weight: Int,
            species: NamedApiResource,
            abilities: List<PokemonAbility>,
            forms: List<NamedApiResource>,
            heldItems: List<PokemonHeldItem>,
            moves: List<PokemonMove>,
            stats: List<PokemonStat>,
            types: List<PokemonType>,
            sprites: PokemonSprites) : this(id, name) {

    }
}

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
