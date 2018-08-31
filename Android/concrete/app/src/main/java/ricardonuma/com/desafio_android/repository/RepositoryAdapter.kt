package ricardonuma.com.desafio_android.repository

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import ricardonuma.com.desafio_android.R
import ricardonuma.com.desafio_android.data.remote.model.repository.Repository

/**
 * Created by ricardonuma on 12/18/17.
 */

internal class RepositoryAdapter(private var items: List<Repository?>?, private val context: Context) : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val item = items!![position]

        holder.mConstraintLayout.visibility = View.VISIBLE
        holder.mProgressBar.visibility = View.GONE

        if (item != null) {

            holder.mTitle.text = item!!.name
            holder.mDescription.text = item!!.description
            holder.mFork.text = item!!.forks_count.toString()
            holder.mStar.text = item!!.stargazers_count.toString()

            with(item) {

                if (owner != null) {

                    if (!owner!!.avatar_url!!.isNullOrEmpty())
                        Picasso.with(context)
                                .load(owner!!.avatar_url)
                                .placeholder(ricardonuma.com.desafio_android.R.drawable.ic_alert_error)
                                .resize(360, 360)
                                .centerCrop()
                                .into(holder.mImageView)

                    holder.mUsername.text = owner!!.login
                }

                holder.mConstraintLayout.setOnClickListener(android.view.View.OnClickListener {
                    val intent = android.content.Intent(context, ricardonuma.com.desafio_android.pullRequest.PullRequestActivity::class.java)
//                    intent.putExtra(ricardonuma.com.desafio_android.pullRequest.PullRequestActivity.EXTRA_ID_REPOSITORY, item.id)
                    intent.putExtra(ricardonuma.com.desafio_android.pullRequest.PullRequestActivity.EXTRA_LOGIN, item.owner!!.login)
                    intent.putExtra(ricardonuma.com.desafio_android.pullRequest.PullRequestActivity.EXTRA_NAME, item.name)
                    context.startActivity(intent)
                })
            }
        } else {
            holder.mConstraintLayout.visibility = View.GONE
            holder.mProgressBar.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return if (items == null) {
            0
        } else items!!.size
    }

    fun setItems(repositoryList: List<Repository?>) {
        this.items = repositoryList
        notifyDataSetChanged()
    }
}