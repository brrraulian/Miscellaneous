package com.bancopan.ricardonuma.bancopan.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bancopan.ricardonuma.bancopan.R

/**
 * Created by ricardonuma on 31/10/17.
 */

class EmptyLayout(internal var mContext: Context?, internal var mEmptyView: View, type: EmptyLayout.EmptyViewType) {

    enum class EmptyViewType {
        GENERIC,
        GAME
    }

    internal var mLayout: ConstraintLayout
    internal var mTextViewTitle: TextView
    internal var mTextViewSubtitle: TextView
    internal var mImageIcon: ImageView

    init {

        this.mLayout = mEmptyView.findViewById(R.id.empty_layout)
        this.mTextViewTitle = mEmptyView.findViewById(R.id.text1)
        this.mTextViewSubtitle = mEmptyView.findViewById(R.id.text2)
        this.mImageIcon = mEmptyView.findViewById(R.id.icon)

        if (mContext != null)
            setEmptyView(type)
    }

    private fun setEmptyView(type: EmptyViewType) {
        when (type) {
            EmptyLayout.EmptyViewType.GENERIC -> setEmptyText(R.string.empty_title_generic, R.string.empty_subtitle_generic)

            EmptyLayout.EmptyViewType.GAME -> {

                mLayout.setBackgroundColor(mContext!!.getResources().getColor(R.color.white))
                setEmptyText(R.string.empty_title_game, R.string.empty_subtitle_game)
                setEmptyTextColor(R.color.gray_text, R.color.gray_text)
                mTextViewTitle.visibility = View.GONE
            }

        }
    }

    private fun setEmptyText(title: Int, subtitle: Int) {
        mTextViewTitle.setText(title)
        mTextViewSubtitle.setText(subtitle)
    }

    private fun setEmptyText(title: String, subtitle: String) {
        mTextViewTitle.text = title
        mTextViewSubtitle.text = subtitle
    }


    private fun setEmptyTextColor(resTitleColor: Int, resSubtitleColor: Int) {
        mTextViewTitle.setTextColor(mContext!!.getResources().getColor(resTitleColor))
        mTextViewSubtitle.setTextColor(mContext!!.getResources().getColor(resSubtitleColor))
    }
}
