package com.natatisha.pokemonapp.data.model

data class NamedApiResourceList(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<NamedApiResource>
)
