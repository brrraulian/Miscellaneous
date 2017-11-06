package com.bancopan.ricardonuma.bancopan.game.detail

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.bancopan.ricardonuma.bancopan.R
import com.bancopan.ricardonuma.bancopan.base.BaseActivity
import com.bancopan.ricardonuma.bancopan.webservice.game.Top
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_detail.*

/**
 * Created by ricardonuma on 11/1/17.
 */

class GameDetailActivity : BaseActivity() {

    companion object {
        val EXTRA_TOP = "extra_top"
    }

    var mTop: Top? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        if (intent.hasExtra(EXTRA_TOP)) {
            mTop = intent.getSerializableExtra(EXTRA_TOP) as Top
            initData()
        }

        initUI()
    }

    override fun initUI() {
        super.initUI()

        if (mTop!!.game != null)
            setTitle(mTop!!.game!!.name)

        setSupportActionBar(toolbar as Toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
        with(mTop!!) {

            if (mTop!!.game != null) {
                Picasso.with(baseContext).load(game!!.box!!.large).into(ivImage)
                tvTitle.text = game!!.name
            }

            tvChannels.text = String.format(getString(R.string.game_detail_label_channels_), channels.toString())
            tvViews.text = String.format(getString(R.string.game_detail_label_viewers_), viewers.toString())
        }
    }

}