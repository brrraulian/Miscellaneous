package com.bancopan.ricardonuma.bancopan.webservice.status

/**
 * Created by ricardonuma on 31/10/17.
 */

enum class ServerStatusEnum private constructor(code: Int) {

    STATUS_OK(200),
    STATUS_CREATED(201),
    STATUS_FORBIDDEN(403),
    STATUS_NOT_FOUND(404),
    STATUS_NOT_ACCEPTABLE(406),
    STATUS_LAST_PAGE(500);

    var code: Int = 0
        internal set

    init {
        this.code = code
    }

}