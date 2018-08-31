package ricardonuma.com.desafio_android.pullRequest

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ricardonuma.com.desafio_android.R
import ricardonuma.com.desafio_android.widget.SquareImageView

/**
 * Created by ricardonuma on 12/21/17.
 */


internal class PullRequestViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var mTitle: TextView
    var mDate: TextView
    var mBody: TextView

    var mUsername: TextView
    var mImageView: SquareImageView

    var mConstraintLayout: ConstraintLayout

    init {
        mTitle = itemView.findViewById(R.id.tv_title)
        mDate = itemView.findViewById(R.id.tv_date)
        mBody = itemView.findViewById(R.id.tv_body)

        mImageView = itemView.findViewById(R.id.iv_user)
        mUsername = itemView.findViewById(R.id.tv_username)

        mConstraintLayout = itemView.findViewById(R.id.cl_layout)
    }

}