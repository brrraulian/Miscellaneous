package com.bancopan.ricardonuma.bancopan.game

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.view.View
import android.widget.Toast
import com.bancopan.ricardonuma.bancopan.R
import com.bancopan.ricardonuma.bancopan.base.BaseActivity
import com.bancopan.ricardonuma.bancopan.base.BaseView
import com.bancopan.ricardonuma.bancopan.webservice.game.ObjectResponse
import com.bancopan.ricardonuma.bancopan.widget.EmptyLayout
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.activity_game_list.*
import java.util.*
import com.bancopan.ricardonuma.bancopan.webservice.Constants
import com.bancopan.ricardonuma.bancopan.webservice.game.Top
import com.bancopan.ricardonuma.bancopan.webservice.game.Top.Companion.getListTop


/**
 * Created by ricardonuma on 31/10/17.
 */

class GameListActivity : BaseActivity(), GameView, SwipeRefreshLayout.OnRefreshListener {


    internal var mEmptyLayout: EmptyLayout? = null

    internal var mAdapter: GameAdapter? = null

    internal var mListOriginal = ArrayList<Top?>()
    internal var mListFiltered = ArrayList<Top?>()

    var mLayoutManager: GridLayoutManager? = null
    private var offset = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisiblesItems = 0
    var loading = false

    var mPresenter: GamePresenterImpl by Delegates.notNull()

    var fromRefresh: Boolean = false
    var isOffline: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        offset = 0

        mPresenter = GamePresenterImpl(this, this)

        initUI()
    }

    override fun initUI() {
        super.initUI()

        mAdapter = GameAdapter(this, mListFiltered)

        rvGameList!!.itemAnimator = DefaultItemAnimator()
        rvGameList!!.layoutManager = LinearLayoutManager(this)

        mLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        rvGameList.setLayoutManager(mLayoutManager)

        rvGameList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = mLayoutManager.let { mLayoutManager -> mLayoutManager!!.getChildCount() }
                    totalItemCount = mLayoutManager.let { mLayoutManager -> mLayoutManager!!.getItemCount() }
                    pastVisiblesItems = mLayoutManager.let { mLayoutManager -> mLayoutManager!!.findFirstVisibleItemPosition() }

                    if (!loading && visibleItemCount + pastVisiblesItems >= totalItemCount &&
                            mListFiltered.size % Constants.LIMIT == 0 && offset < Constants.TOTAL) {

                        loading = true
                        offset += Constants.LIMIT

                        var i = 0
                        while (i < 2) {
                            mListFiltered.add(null)
                            i++
                        }

                        mAdapter.let { mAdapter -> mAdapter!!.setList(mListFiltered) }
                        mAdapter.let { mAdapter -> mAdapter!!.notifyItemInserted(mListFiltered.size - 1) }

                        loadGameList(Constants.LIMIT, offset, true)
                    }
                }
            }
        })

        rvGameList!!.adapter = mAdapter

        swipeContainer.setOnRefreshListener(this)

        loadGameList(Constants.LIMIT, offset, false)
    }

    private fun loadGameList(limit: Int, offset: Int, updateFromSwipe: Boolean) {

        isOffline = false
        mPresenter.loadGameList(limit, offset, updateFromSwipe)
    }

    fun refreshList(listTopObject: List<Top?>, isOffline: Boolean) {

        swipeContainer.setRefreshing(false)
        loadData(listTopObject, isOffline)
    }

    fun loadData(listTopObject: List<Top?>, isOffline: Boolean) {

        if (!loading || isOffline)
            mListOriginal.clear()
        else {

            if (mListFiltered.size > 0) {

                var i = 0
                while (i < 2) {
                    mListFiltered.removeAt(mListFiltered.size - 1)
                    i++
                }

                mAdapter!!.setList(mListFiltered)
                mAdapter!!.notifyDataSetChanged()
            }
        }

        loading = false

        for (topObject in listTopObject) {
            mListOriginal.add(topObject)
        }

        mListFiltered = mListOriginal.clone() as ArrayList<Top?>


        mAdapter!!.setList(mListFiltered)
        mAdapter!!.notifyDataSetChanged()

        setupEmptyView()
    }

    private fun setupEmptyView() {

        if (layout.visibility == View.INVISIBLE)
            layout.visibility = View.VISIBLE

        if (empty_layout == null)
            return

        rvGameList!!.visibility = if (mListFiltered.size === 0) View.GONE else View.VISIBLE
        empty_layout!!.setVisibility(if (mListFiltered.size === 0) View.VISIBLE else View.GONE)

        mEmptyLayout = EmptyLayout(this, empty_layout, EmptyLayout.EmptyViewType.GAME)
    }

    override fun onRefresh() {
        fromRefresh = true

        loadGameList(if (mListFiltered.size > 0) mListFiltered.size else Constants.LIMIT, 0, true)

        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false)
        }
    }

    override fun showProgress(type: BaseView.ProgressType) {
        if (type == BaseView.ProgressType.PROGRESS_DIALOG)
            onLoadingStart()
    }

    override fun hideProgress() {
        onLoadingFinish()
    }

    override fun onConnectionFailed() {
        Toast.makeText(this, R.string.message_no_connection, Toast.LENGTH_LONG).show()

        if (!fromRefresh)
            refreshList(getListTop(Constants.LIMIT, offset), true)

        fromRefresh = false
    }

    override fun onAuthError() {

    }

    override fun onLoadGameListSuccess(objectResponse: ObjectResponse?) {

        if (objectResponse != null && objectResponse.top != null) {

            var listTop: List<Top?> = objectResponse.top

            for (top in listTop) {
                saveTop(top)
            }

            refreshList(objectResponse.top, false)
        }
    }

    override fun onLoadGameListError(msg: String?) {

        APIErrorTreatment(msg, getResources().getString(R.string.message_error_load_game))

        refreshList(getListTop(Constants.LIMIT, offset), true)
    }

    fun saveTop(top: Top?) {
        top!!.id = top.game!!._id
        top.save()

        saveGame(top)
    }

    fun saveGame(top: Top?) {
        top!!.game!!.id = top!!.game!!._id
        top!!.game!!.save()

        saveBox(top)
        saveLogo(top)
    }

    fun saveBox(top: Top?) {
        top!!.game!!.box!!.id = top!!.game!!._id
        top!!.game!!.box!!.save()
    }

    fun saveLogo(top: Top?) {
        top!!.game!!.logo!!.id = top!!.game!!._id
        top!!.game!!.logo!!.save()
    }
}