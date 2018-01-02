package com.bancopan.ricardonuma.bancopan.webservice.game

/**
 * Created by ricardonuma on 31/10/17.
 */

data class ObjectResponse(

        var _total: Long,
        var _links: Links,
        var top: List<Top?>

)

