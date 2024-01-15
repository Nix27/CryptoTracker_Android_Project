package hr.algebra.cryptotracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.squareup.picasso.Picasso
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.databinding.FragmentCurrencyDetailsBinding
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.viewmodel.CurrenciesViewModel
import hr.algebra.cryptotracker.viewmodel.CurrencyDetailsViewModel
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

const val CURRENCY_ID = "hr.algebra.cryptotracker.fragment.currency_id"
class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding
    private val viewModel = CurrencyDetailsViewModel()
    private lateinit var currency: Currency
    private lateinit var priceChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        setupListeners()
        priceChart = binding.lcPrice
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(CURRENCY_ID)!!
        viewModel.getCurrencyDetails(id)
        viewModel.currencyDetails.observe(viewLifecycleOwner) {
            if(it != null) currency = it
            bindCurrency()
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_CurrencyDetailsFragment_to_CurrenciesFragment)
        }
    }

    private fun bindCurrency() {
        Picasso.get()
            .load(currency.imagePath)
            .error(R.mipmap.ic_launcher)
            .transform(RoundedCornersTransformation(50, 5))
            .into(binding.ivCurrencyPicture)

        binding.tvCurrencySymbol.text = currency.symbol.uppercase()
        binding.tvMarketCapRank.text = currency.market_cap_rank.toString()
        binding.tvMarketCap.text = currency.market_cap.toString()
        binding.tvTotalVolume.text = currency.total_volume.toString()
        binding.tvAllTimeHigh.text = currency.ath.toString()
        binding.tvCirculatingSupply.text = currency.circulating_supply.toString()
        binding.tvTotalSupply.text = currency.total_supply.toString()
        binding.tvMaxSupply.text = if(currency.max_supply != null) currency.max_supply.toString() else ""
    }
}