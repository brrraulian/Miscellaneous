package com.ricardonuma.desafioandroid.data.remote.model.repository

import com.orm.SugarRecord
import com.ricardonuma.desafioandroid.data.remote.model.repository.Owner.Companion.getOwnerbyId
import java.io.Serializable


/**
 * Created by ricardonuma on 30/12/17.
 */

class Repository(

        id_repository: Long?,
        name: String?,
        description: String?,
        forks_count: Long?,
        stargazers_count: Long?,
        owner: Owner?

) : Serializable, SugarRecord() {

    constructor() : this(null, null, null, null, null, null)

    var id_repository: Long? = null
    var name: String? = null
    var description: String? = null
    var forks_count: Long? = null
    var stargazers_count: Long? = null
    var owner: Owner? = null

    companion object {

        fun getListRepository(limit: Int): List<Repository> {

            val listRepository = SugarRecord.find<Repository>(Repository::class.java,
                    null, arrayOf(), null, "stargazerscount DESC", limit.toString())

            for (repository in listRepository) {
                repository.owner = getOwnerbyId(repository.id)
            }

            return listRepository
        }

    }
}