package com.natatisha.pokemonapp.data.model

data class NamedApiResource(
        val name: String,
        val category: String,
        val id: Int,
        val url: String
)

data class NamedApiResourceList(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<NamedApiResource>
)
