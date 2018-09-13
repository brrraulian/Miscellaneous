package com.ricardonuma.desafioandroid.pullRequest

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.ricardonuma.desafioandroid.R
import com.ricardonuma.desafioandroid.base.BaseActivity
import com.ricardonuma.desafioandroid.widget.EmptyLayout
import java.util.ArrayList
import kotlinx.android.synthetic.main.activity_pull_request.*
import com.ricardonuma.desafioandroid.Injection
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ricardonuma on 29/12/17.
 */


class PullRequestActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, PullRequestContract.View {

    companion object {
        val EXTRA_ID_REPOSITORY = "extra_id_repository"
        val EXTRA_LOGIN = "extra_login"
        val EXTRA_NAME = "extra_name"
    }

    internal var mEmptyLayout: EmptyLayout? = null

    internal var mAdapter: PullRequestAdapter? = null

    internal var mListOriginal = ArrayList<PullRequest>()
    internal var mListFiltered = ArrayList<PullRequest>()

    var fromRefresh: Boolean = false
    var isOffline: Boolean = false

    var mIdRepository: Long? = null
    var mLogin: String? = null
    var mName: String? = null

    private var userSearchPresenter: PullRequestContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        if (intent.hasExtra(EXTRA_ID_REPOSITORY) && intent.hasExtra(EXTRA_LOGIN) && intent.hasExtra(EXTRA_NAME)) {
            mIdRepository = intent.getLongExtra(EXTRA_ID_REPOSITORY, 0)
            mLogin = intent.getStringExtra(EXTRA_LOGIN)
            mName = intent.getStringExtra(EXTRA_NAME)
        }

        userSearchPresenter = PullRequestPresenter(Injection.provideUserRepo(), Schedulers.io(),
                AndroidSchedulers.mainThread())
        (userSearchPresenter as PullRequestPresenter).attachView(this)

        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        userSearchPresenter!!.detachView()
    }

    override fun initUI() {
        super.initUI()

        setTitle(mName)

        setSupportActionBar(toolbar as Toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_action_navigation_arrow_back)

        mAdapter = PullRequestAdapter(null, this)

        rv_pullRequestList!!.itemAnimator = DefaultItemAnimator()
        rv_pullRequestList!!.layoutManager = LinearLayoutManager(this)

        rv_pullRequestList!!.adapter = mAdapter

        swipeContainer.setOnRefreshListener(this)

        loadPullRequestList(mLogin, mName, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadPullRequestList(login: String?, name: String?, updateFromSwipe: Boolean) {

        isOffline = false
        userSearchPresenter!!.loadPullRequestList(login, name, updateFromSwipe)
    }

    fun refreshList(listPullRequestObject: List<PullRequest>, isOffline: Boolean) {

        swipeContainer.setRefreshing(false)
        loadData(listPullRequestObject, isOffline)
    }

    fun loadData(listPullRequestObject: List<PullRequest>, isOffline: Boolean) {

        if (isOffline)
            mListOriginal.clear()

        mListOriginal = listPullRequestObject as ArrayList<PullRequest>

        mListFiltered = mListOriginal.clone() as ArrayList<PullRequest>


        mAdapter!!.setItems(mListFiltered)
        mAdapter!!.notifyDataSetChanged()

        setupEmptyView()
    }

    private fun setupEmptyView() {

        if (ll_layout.visibility == View.INVISIBLE)
            ll_layout.visibility = View.VISIBLE

        if (empty_layout == null)
            return

        rv_pullRequestList!!.visibility = if (mListFiltered.size === 0) View.GONE else View.VISIBLE
        empty_layout!!.setVisibility(if (mListFiltered.size === 0) View.VISIBLE else View.GONE)

        mEmptyLayout = EmptyLayout(this, empty_layout, EmptyLayout.EmptyViewType.PULL_REQUEST)
    }

    override fun onRefresh() {
        fromRefresh = true

        loadPullRequestList(mLogin, mName, true)

        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false)
        }
    }

    fun savePullRequest(pullRequest: PullRequest?) {
        pullRequest!!.id_repository = mIdRepository
        pullRequest!!.id_pull_request = pullRequest!!.id
        pullRequest!!.save()

        saveOwner(pullRequest)
    }

    fun saveOwner(pullRequest: PullRequest?) {
        pullRequest!!.user!!.id = pullRequest!!.id
        pullRequest!!.user!!.save()
    }

    override fun showPullRequestResults(pullRequestList: List<PullRequest>?) {

        if (pullRequestList != null) {

            var listPullRequest: List<PullRequest?> = pullRequestList

            for (PullRequest in listPullRequest) {
                savePullRequest(PullRequest)
            }

            refreshList(pullRequestList, false)
        }
    }

    override fun showError(message: String?) {
        APIErrorTreatment(message, getResources().getString(R.string.message_error_load_pull_request))

        refreshList(PullRequest.getListPullRequest(mIdRepository), true)
    }

    override fun showLoading(type: MvpView.ProgressType) {
        if (type == MvpView.ProgressType.PROGRESS_DIALOG)
            onLoadingStart()
    }

    override fun hideLoading() {
        onLoadingFinish()
    }
}