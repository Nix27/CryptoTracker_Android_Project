package hr.algebra.cryptotracker.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.abstraction.Navigable
import hr.algebra.cryptotracker.fragment.CURRENCY_ID
import hr.algebra.cryptotracker.model.Currency
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CurrencyAdapter(
    private val context: Context,
    private val currencies: List<Currency>,
    private val navigable: Navigable
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    class ViewHolder(currencyView: View) : RecyclerView.ViewHolder(currencyView) {
        private val ivCurrencyLogo = currencyView.findViewById<ImageView>(R.id.ivCurrencyLogo)
        private val tvCurrencySymbol = currencyView.findViewById<TextView>(R.id.tvCurrencySymbol)
        private val tvCurrentPrice = currencyView.findViewById<TextView>(R.id.tvCurrentPrice)

        fun bind(currency: Currency) {
            tvCurrencySymbol.text = currency.symbol.uppercase()
            tvCurrentPrice.text = currency.current_price.toString()
            Picasso.get()
                .load(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivCurrencyLogo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.currency_cardview, parent, false)
        )
    }

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            navigable.navigate(Bundle().apply {
                putString(CURRENCY_ID, currencies[position].id)
            })
        }

        holder.bind(currencies[position])
    }

}