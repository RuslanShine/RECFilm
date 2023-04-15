package com.example.remote_module.entity


import com.google.gson.annotations.SerializedName

data class TmdbResultsDto(
    @SerializedName(PAGE)
    val page: Int,
    @SerializedName(RESULTS)
    val tmdbFilms: List<TmdbFilm>,
    @SerializedName(TOTAL_PAGES)
    val totalPages: Int,
    @SerializedName(TOTAL_RESULTS)
    val totalResults: Int,
)

private const val PAGE = "page"
private const val RESULTS = "results"
private const val TOTAL_PAGES = "total_pages"
private const val TOTAL_RESULTS = "total_results"
