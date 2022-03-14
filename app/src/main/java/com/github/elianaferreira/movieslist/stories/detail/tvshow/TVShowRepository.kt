package com.github.elianaferreira.movieslist.stories.detail.tvshow

import com.github.elianaferreira.movieslist.utils.BaseRepository
import com.github.elianaferreira.movieslist.utils.RequestManager

interface TVShowRepository: BaseRepository {

    fun getTVShow(showID: String, successCallback: RequestManager.OnSuccessRequestResult<TVShowDetail>, errorCallback: RequestManager.OnErrorRequestResult)
}