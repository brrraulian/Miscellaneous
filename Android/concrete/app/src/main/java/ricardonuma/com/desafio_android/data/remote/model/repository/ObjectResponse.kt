package ricardonuma.com.desafio_android.data.remote.model.repository

/**
 * Created by ricardonuma on 12/18/17.
 */

data class ObjectResponse(

        var total_count: Long,
        var incomplete_results: Boolean,
        var items: List<Repository?>

)

