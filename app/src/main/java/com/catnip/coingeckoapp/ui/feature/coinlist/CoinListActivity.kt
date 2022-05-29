package com.catnip.coingeckoapp.ui.feature.coinlist

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.coingeckoapp.R
import com.catnip.coingeckoapp.base.arch.BaseActivity
import com.catnip.coingeckoapp.databinding.ActivityCoinListBinding
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinViewParam
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class CoinListActivity :
    BaseActivity<ActivityCoinListBinding, CoinListViewModel>(ActivityCoinListBinding::inflate) {

    override val viewModel: CoinListViewModel by viewModel()

    private val adapter: CoinListAdapter by lazy {
        CoinListAdapter {
            Toast.makeText(this@CoinListActivity, it.id, Toast.LENGTH_SHORT).show()
        }.also {
            binding.rvContent.apply {
                layoutManager = LinearLayoutManager(this@CoinListActivity)
                adapter = it
            }
        }
    }

    override fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.isVisible = isShowLoading
    }

    override fun showContent(isContentVisible: Boolean) {
        binding.rvContent.isVisible = isContentVisible
    }

    override fun showEmptyData(isEmpty: Boolean) {
        binding.tvMessage.isVisible = isEmpty
        binding.tvMessage.text = getText(R.string.empty_coin_list)
    }

    override fun showError(isErrorEnabled: Boolean, exception: Exception?) {
        binding.tvMessage.isVisible = isErrorEnabled
        if (isErrorEnabled) {
            binding.tvMessage.text = getErrorMessageByException(exception)
        }
    }

    override fun initView() {
        adapter
        initSwipeRefresh()
        viewModel.getList()
    }

    private fun initSwipeRefresh() {
        binding.srlContent.setOnRefreshListener {
            binding.srlContent.isRefreshing = false
            viewModel.getList()
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.coinList.observe(this) {
            handleData(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> showData(data: T) {
        super.showData(data)
        adapter.setItems(data as List<CoinViewParam>)
    }

}