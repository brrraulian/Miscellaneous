package com.ricardonuma.desafioandroid.data.remote.model.repository

/**
 * Created by ricardonuma on 30/12/17.
 */

data class ObjectResponse(

        var total_count: Long,
        var incomplete_results: Boolean,
        var items: List<Repository?>

)

