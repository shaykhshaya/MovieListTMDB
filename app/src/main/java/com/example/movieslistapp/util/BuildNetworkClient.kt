package com.example.movieslistapp.util

import com.example.request_get.NetworkBuilder

object BuildNetworkClient {

    fun build(
        page: String,
        endPoint: String,
        path: String = ""
    ) {
        NetworkBuilder.Build()
            .baseUrl(Constants.BASE_URL)
            .endPoint(endPoint)
            .path(path)
            .query(
                hashMapOf(
                    Constants.KEY_QUERY_PAGE to page,
                    Constants.KEY_QUERY_KEY to Constants.API_KEY
                )
            )
            .create()
    }
}