package com.bancopan.ricardonuma.bancopan.game

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bancopan.ricardonuma.bancopan.R
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.tonicartos.superslim.GridSLM
import com.tonicartos.superslim.LinearSLM
import android.widget.ImageView
import android.widget.ProgressBar
import com.bancopan.ricardonuma.bancopan.game.detail.GameDetailActivity
import com.bancopan.ricardonuma.bancopan.webservice.game.Top
import com.squareup.picasso.Picasso
import java.util.*


/**
 * Created by ricardonuma on 31/10/17.
 */

class GameAdapter(private val mContext: Context, gameList: java.util.ArrayList<Top?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mItems: ArrayList<LineItem>

    init {

        mItems = ArrayList()
        setList(gameList)

    }

    fun setList(gameList: ArrayList<Top?>) {

        mItems.clear()

        for (i in 0..gameList.size - 1) {

            mItems.add(LineItem(gameList.get(i), false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_game, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = mItems[position]
        val itemView = holder.itemView

        val lp = GridSLM.LayoutParams.from(itemView.layoutParams)

        lp.setSlm(LinearSLM.ID)

        (holder as ItemViewHolder).bindItem(position, item.`object` as Top?)

        lp.setFirstPosition(item.sectionFirstPosition)
        itemView.layoutParams = lp
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    private class LineItem(var `object`: Any?, var isHeader: Boolean) {

        var sectionFirstPosition: Int = 0
    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var mTextView: TextView
        internal var mImageView: ImageView
        internal var mCardView: CardView
        internal var mProgressBar: ProgressBar

        init {
            mTextView = itemView.findViewById(R.id.tvTitle)
            mImageView = itemView.findViewById(R.id.ivImage)
            mCardView = itemView.findViewById(R.id.cvGame)
            mProgressBar = itemView.findViewById(R.id.progressBar)
        }

        fun bindItem(position: Int, topObject: Top?) {

            mCardView.visibility = View.VISIBLE
            mProgressBar.visibility = View.GONE

            if (topObject != null) {
                with(topObject!!) {

                    if(game != null) {
                        mTextView.text = game!!.name
                        Picasso.with(mContext).load(game!!.box!!.large).into(mImageView)
                    }

                    mCardView.setOnClickListener(View.OnClickListener {

                        val intent = Intent(mContext, GameDetailActivity::class.java)
                        intent.putExtra(GameDetailActivity.EXTRA_TOP, topObject)
                        mContext.startActivity(intent)
                    })
                }
            } else {
                mCardView.visibility = View.GONE
                mProgressBar.visibility = View.VISIBLE
            }
        }
    }

}