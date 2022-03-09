package com.github.elianaferreira.movieslist.utils

import com.github.elianaferreira.movieslist.stories.detail.movie.Genre
import com.github.elianaferreira.movieslist.stories.detail.movie.SpokenLanguage

interface MovieHeader {
    val backdropPath: String
    val overview: String
    val genres: List<Genre>
    val spokenLanguages: List<SpokenLanguage>
    val voteAverage: Double
    val voteCount: Int
}