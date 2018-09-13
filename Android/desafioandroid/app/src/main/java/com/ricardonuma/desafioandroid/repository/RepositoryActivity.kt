package com.ricardonuma.desafioandroid.repository

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.view.View
import kotlinx.android.synthetic.main.activity_repository.*
import com.ricardonuma.desafioandroid.Injection
import com.ricardonuma.desafioandroid.R
import com.ricardonuma.desafioandroid.base.BaseActivity
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.Constants
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import com.ricardonuma.desafioandroid.data.remote.model.repository.Repository
import com.ricardonuma.desafioandroid.data.remote.model.repository.Repository.Companion.getListRepository
import com.ricardonuma.desafioandroid.widget.EmptyLayout
import java.util.ArrayList
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by ricardonuma on 29/12/17.
 */

class RepositoryActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, RepositoryContract.View {

    internal var mEmptyLayout: EmptyLayout? = null

    internal var mAdapter: RepositoryAdapter? = null

    internal var mListOriginal = ArrayList<Repository?>()
    internal var mListFiltered = ArrayList<Repository?>()

    var mLayoutManager: LinearLayoutManager? = null
    var visibleItemCount = 0
    var totalItemCount = 0
    var pastVisiblesItems = 0
    var loading = false

    var page = 1

    var fromRefresh: Boolean = false
    var isOffline: Boolean = false

    private var userSearchPresenter: RepositoryContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)

        userSearchPresenter = RepositoryPresenter(Injection.provideUserRepo(), Schedulers.io(),
                AndroidSchedulers.mainThread())
        (userSearchPresenter as RepositoryPresenter).attachView(this)

        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        userSearchPresenter!!.detachView()
    }

    override fun initUI() {
        super.initUI()

        setTitle(getString(R.string.title_main))

        mAdapter = RepositoryAdapter(null, this)

        rv_repositoryList!!.itemAnimator = DefaultItemAnimator()
        rv_repositoryList!!.layoutManager = LinearLayoutManager(this)

        mLayoutManager = LinearLayoutManager(this)

        rv_repositoryList.setLayoutManager(mLayoutManager)

        rv_repositoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                if (dy > 0) {
                    visibleItemCount = mLayoutManager.let { mLayoutManager -> mLayoutManager!!.getChildCount() }
                    totalItemCount = mLayoutManager.let { mLayoutManager -> mLayoutManager!!.getItemCount() }
                    pastVisiblesItems = mLayoutManager.let { mLayoutManager -> mLayoutManager!!.findFirstVisibleItemPosition() }

                    if (!loading && visibleItemCount + pastVisiblesItems >= totalItemCount &&
                            mListFiltered.size < Constants.LIMIT) {

                        loading = true
                        page++

                        mListFiltered.add(null)

                        mAdapter!!.setItems(mListFiltered)
                        mAdapter.let { mAdapter -> mAdapter!!.notifyItemInserted(mListFiltered.size - 1) }

                        loadRepositoryList(page, true)
                    }
                }
            }
        })

        rv_repositoryList!!.adapter = mAdapter

        swipeContainer.setOnRefreshListener(this)

        loadRepositoryList(page, false)
    }

    private fun loadRepositoryList(page: Int, updateFromSwipe: Boolean) {

        isOffline = false
        userSearchPresenter!!.loadRepositoryList(page, updateFromSwipe)
    }

    fun refreshList(listRepositoryObject: List<Repository?>, isOffline: Boolean) {

        swipeContainer.setRefreshing(false)
        loadData(listRepositoryObject, isOffline)
    }

    fun loadData(listRepositoryObject: List<Repository?>, isOffline: Boolean) {

        if (!loading || isOffline)
            mListOriginal.clear()
        else {

            if (mListFiltered.size > 0)
                mListFiltered.removeAt(mListFiltered.size - 1)

        }

        loading = false

        for (RepositoryObject in listRepositoryObject) {
            mListOriginal.add(RepositoryObject)
        }

        mListFiltered = mListOriginal.clone() as ArrayList<Repository?>

        mAdapter!!.setItems(mListFiltered)
        mAdapter!!.notifyDataSetChanged()

        setupEmptyView()
    }

    private fun setupEmptyView() {

        if (ll_layout.visibility == View.INVISIBLE)
            ll_layout.visibility = View.VISIBLE

        if (empty_layout == null)
            return

        rv_repositoryList!!.visibility = if (mListFiltered.size === 0) View.GONE else View.VISIBLE
        empty_layout!!.setVisibility(if (mListFiltered.size === 0) View.VISIBLE else View.GONE)

        mEmptyLayout = EmptyLayout(this, empty_layout, EmptyLayout.EmptyViewType.REPOSITORY)
    }

    override fun onRefresh() {
        fromRefresh = true

        loadRepositoryList(1, true)

        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false)
        }
    }

    fun saveRepository(repository: Repository?) {
        repository!!.id_repository = repository!!.id
        repository!!.save()

        saveOwner(repository)
    }

    fun saveOwner(repository: Repository?) {
        repository!!.owner!!.id = repository!!.id
        repository!!.owner!!.save()
    }

    override fun showRepositoryResults(objectResponse: ObjectResponse?) {
        if (objectResponse != null && objectResponse.items != null) {

            var listRepository: List<Repository?> = objectResponse.items

            for (Repository in listRepository) {
                saveRepository(Repository)
            }

            refreshList(objectResponse.items, false)
        }
    }

    override fun showError(message: String?) {
        APIErrorTreatment(message, getResources().getString(R.string.message_error_load_repository))

        refreshList(getListRepository(Constants.LIMIT), true)
    }

    override fun showLoading(type: MvpView.ProgressType) {
        if (type == MvpView.ProgressType.PROGRESS_DIALOG)
            onLoadingStart()
    }

    override fun hideLoading() {
        onLoadingFinish()
    }

}