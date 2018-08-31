package ricardonuma.com.desafio_android.pullRequest

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ricardonuma.com.desafio_android.R
import ricardonuma.com.desafio_android.Constants
import ricardonuma.com.desafio_android.data.remote.model.pullRequest.PullRequest
import java.text.SimpleDateFormat

/**
 * Created by ricardonuma on 12/18/17.
 */

internal class PullRequestAdapter(private var items: List<PullRequest>?, private val context: Context) : RecyclerView.Adapter<PullRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_pull_request, parent, false)
        return PullRequestViewHolder(v)
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val item = items!![position]

        if (item != null) {

            holder.mTitle.text = item!!.title

            holder.mDate.text = if (item.created_at != null)
                SimpleDateFormat(Constants.BASIC_DATE_PATTERN).format(SimpleDateFormat(Constants.SERVER_DATE_PATTERN_TIME).parse(item.created_at))
            else ""

            holder.mBody.text = item!!.body

            with(item!!) {

                if (user != null) {

                    if (!user!!.avatar_url!!.isNullOrEmpty())
                        com.squareup.picasso.Picasso.with(context)
                                .load(user!!.avatar_url)
                                .placeholder(ricardonuma.com.desafio_android.R.drawable.ic_alert_error)
                                .resize(360, 360)
                                .centerCrop()
                                .into(holder.mImageView)

                    holder.mUsername.text = user!!.login
                }
            }

            holder.mConstraintLayout.setOnClickListener(android.view.View.OnClickListener {
                val intent = android.content.Intent(Intent.ACTION_VIEW, Uri.parse(item.html_url))
                context.startActivity(intent)
            })

        }
    }

    override fun getItemCount(): Int {
        return if (items == null) {
            0
        } else items!!.size
    }

    fun setItems(pullRequestList: List<PullRequest>?) {
        this.items = pullRequestList
        notifyDataSetChanged()
    }
}