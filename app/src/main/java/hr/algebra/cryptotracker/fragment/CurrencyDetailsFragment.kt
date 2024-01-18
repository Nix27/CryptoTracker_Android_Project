package hr.algebra.cryptotracker.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ColorFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.squareup.picasso.Picasso
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.adapter.CommentAdapter
import hr.algebra.cryptotracker.databinding.FragmentCurrencyDetailsBinding
import hr.algebra.cryptotracker.formatter.formatStringTimeToTimeStamp
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.model.CurrencyPrice
import hr.algebra.cryptotracker.viewmodel.CurrenciesViewModel
import hr.algebra.cryptotracker.viewmodel.CurrencyDetailsViewModel
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(CURRENCY_ID)!!

        viewModel.getCurrencyDetails(id)
        viewModel.getCurrencyPrices(id)

        viewModel.currencyDetails.observe(viewLifecycleOwner) {
            if(it != null)  {
                currency = it
                bindCurrency()
            }
        }

        viewModel.currencyPrices.observe(viewLifecycleOwner) {
            if(it != null) setupPriceChart()
        }

        viewModel.comments.observe(viewLifecycleOwner) {
            if(it != null) {
                binding.rvComments.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = CommentAdapter(requireContext(), it)
                }
            }
        }
    }

    private fun setupPriceChart() {
        priceChart = binding.lcPrice

        val entries = getChartPrices(viewModel.currencyPrices.value!!)

        val lineDataSet = LineDataSet(entries, "Price")
        lineDataSet.color = Color.parseColor("#86DE37")
        lineDataSet.lineWidth = 5f
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        val lineData = LineData(lineDataSet)
        priceChart.data = lineData

        val xAxis: XAxis = priceChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.WHITE

        xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return dateFormat.format(Date(value.toLong()))
            }
        }

        val yAxisLeft: YAxis = priceChart.getAxis(YAxis.AxisDependency.LEFT)
        val yAxisRight: YAxis = priceChart.getAxis(YAxis.AxisDependency.RIGHT)
        yAxisLeft.textColor = Color.WHITE
        yAxisRight.textColor = Color.WHITE

        priceChart.description.isEnabled = false
        priceChart.legend.isEnabled = false

        priceChart.invalidate()
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

    private fun getChartPrices(prices: List<CurrencyPrice>) : List<Entry> {
        val chartPrices = mutableListOf<Entry>()

        prices.forEach {
            chartPrices.add(Entry(formatStringTimeToTimeStamp(it.time).toDouble().toFloat(), it.price.toFloat()))
        }

        return chartPrices
    }
}