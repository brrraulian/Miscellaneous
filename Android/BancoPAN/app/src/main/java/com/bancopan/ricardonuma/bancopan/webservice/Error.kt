package com.bancopan.ricardonuma.bancopan.webservice

import com.google.gson.annotations.SerializedName

/**
 * Created by ricardonuma on 11/1/17.
 */

class Error {

    @SerializedName("errors")
    var errors: List<ErrorsBean>? = null
        internal set

    class ErrorsBean {
        /**
         * code : 500
         * message : Usuário ou Senha Inválidos.
         */

        @SerializedName("code")
        var code: String? = null
            internal set
        @SerializedName("message")
        var message: String? = null
            internal set
    }

}