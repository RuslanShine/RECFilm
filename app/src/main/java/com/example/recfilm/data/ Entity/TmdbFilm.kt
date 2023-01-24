package com.example.recfilm.data.Entity

import com.google.gson.annotations.SerializedName

data class TmdbFilm(
    @SerializedName(ADULT)
    val adult: Boolean,
    @SerializedName(BACKDROP_PATH)
    val backdropPath: String,
    @SerializedName(GENRE_IDS)
    val genreIds: List<Int>,
    @SerializedName(ID)
    val id: Int,
    @SerializedName(ORIGINAL_LANGUAGE)
    val originalLanguage: String,
    @SerializedName(ORIGINAL_TITLE)
    val originalTitle: String,
    @SerializedName(OVERVIEW)
    val overview: String,
    @SerializedName(POPULARITY)
    val popularity: Double,
    @SerializedName(POSTER_PATH)
    val posterPath: String,
    @SerializedName(RELEASE_DATE)
    val releaseDate: String,
    @SerializedName(TITLE)
    val title: String,
    @SerializedName(VIDEO)
    val video: Boolean,
    @SerializedName(VOTE_AVERAGE)
    val voteAverage: Double,
    @SerializedName(VOTE_COUNT)
    val voteCount: Int
)

private const val ADULT = "adult"
private const val BACKDROP_PATH = "backdrop_path"
private const val GENRE_IDS = "genre_ids"
private const val ID = "id"
private const val ORIGINAL_LANGUAGE = "original_language"
private const val ORIGINAL_TITLE = "original_title"
private const val OVERVIEW = "overview"
private const val POPULARITY = "popularity"
private const val POSTER_PATH = "poster_path"
private const val RELEASE_DATE = "release_date"
private const val TITLE = "title"
private const val VIDEO = "video"
private const val VOTE_AVERAGE = "vote_average"
private const val VOTE_COUNT = "vote_count"