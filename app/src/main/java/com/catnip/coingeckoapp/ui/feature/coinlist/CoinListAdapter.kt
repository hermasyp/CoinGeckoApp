package com.catnip.coingeckoapp.ui.feature.coinlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.coingeckoapp.R
import com.catnip.coingeckoapp.databinding.ItemCoinBinding
import com.catnip.coingeckoapp.ui.viewparams.coin.CoinViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinListAdapter(private val itemClick: (CoinViewParam) -> Unit) :
    RecyclerView.Adapter<CoinListAdapter.CoinViewParamViewHolder>() {
    private var items: MutableList<CoinViewParam> = mutableListOf()

    fun setItems(items: List<CoinViewParam>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<CoinViewParam>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewParamViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewParamViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: CoinViewParamViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class CoinViewParamViewHolder(private val binding: ItemCoinBinding, val itemClick: (CoinViewParam) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: CoinViewParam) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.ivIconCoin.load(image)
                binding.tvCoinName.text = name
                binding.tvCoinSymbol.text = symbol?.uppercase()
                binding.tvCoinPrice.text = itemView.context.getString(
                    R.string.text_placeholder_coin_price,
                    currentPrice.toString()
                )
            }

        }
    }

}