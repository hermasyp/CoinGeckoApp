package com.catnip.coingeckoapp.ui.feature.detailcoin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.catnip.coingeckoapp.R
import com.catnip.coingeckoapp.base.arch.BaseActivity
import com.catnip.coingeckoapp.databinding.ActivityCoinDetailBinding
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinDetailViewParam
import com.catnip.coingeckoapp.utils.textFromHtml
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinDetailActivity :
    BaseActivity<ActivityCoinDetailBinding, CoinDetailViewModel>(ActivityCoinDetailBinding::inflate) {


    override val viewModel: CoinDetailViewModel by viewModel()

    lateinit var id: String

    companion object {
        const val ARG_INTENT_ID_COIN = "ARG_INTENT_ID_COIN"

        fun startActivity(context: Context?, id: String?) {
            context?.startActivity(Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(ARG_INTENT_ID_COIN,id)
            })
        }
    }

    override fun initView() {
        viewModel.getDetail(id)
    }

    override fun onRetrieveIntentData(extras: Bundle?) {
        id = extras?.getString(ARG_INTENT_ID_COIN).orEmpty()
    }

    override fun observeData() {
        super.observeData()
        viewModel.coinDetail.observe(this) {
            handleData(it)
        }
    }

    override fun showContent(isVisible: Boolean) {
        binding.groupContent.isVisible = isVisible
    }

    override fun showLoading(isVisible: Boolean) {
        binding.pbLoading.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, exception: Exception?) {
        binding.tvErrorMessage.visibility = if (isErrorEnabled) View.VISIBLE else View.GONE
        binding.tvErrorMessage.text = getErrorMessageByException(exception)
    }

    override fun <T> showData(data: T) {
        if (data is CoinDetailViewParam) {
            binding.ivIconCoin.load(data.imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.tvCoinSymbol.text = data.symbol.uppercase()
            binding.tvCoinName.text = data.name
            binding.tvCoinPrice.text = getString(
                R.string.text_placeholder_coin_price,
                data.currentPrice
            )
            binding.tvDescCoin.textFromHtml(data.description)
            generateChips(data.categories)
        }
    }


    private fun generateChips(categories: List<String?>?) {
        categories?.filter { !it.isNullOrEmpty() }?.forEach {
            binding.cgCategory.addView(
                Chip(this).apply {
                    text = it
                    isClickable = false
                })
        }
    }

}