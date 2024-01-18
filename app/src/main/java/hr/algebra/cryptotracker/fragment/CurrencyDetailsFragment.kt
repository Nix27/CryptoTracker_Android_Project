package hr.algebra.cryptotracker.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.squareup.picasso.Picasso
import hr.algebra.cryptotracker.LOGGED_USER
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.adapter.CommentAdapter
import hr.algebra.cryptotracker.databinding.FragmentCurrencyDetailsBinding
import hr.algebra.cryptotracker.formatter.formatStringTimeToTimeStamp
import hr.algebra.cryptotracker.framework.getStringPreference
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.model.CurrencyPrice
import hr.algebra.cryptotracker.viewmodel.CurrenciesViewModel
import hr.algebra.cryptotracker.viewmodel.CurrencyDetailsViewModel
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val CURRENCY_ID = "hr.algebra.cryptotracker.fragment.currency_id"
class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding
    private val currenciesViewModel: CurrenciesViewModel by activityViewModels()
    private val currencyDetailsViewModel = CurrencyDetailsViewModel()
    private lateinit var currency: Currency
    private lateinit var priceChart: LineChart
    private lateinit var currencyId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyId = arguments?.getString(CURRENCY_ID)!!

        currencyDetailsViewModel.getComments(currencyId)

        currenciesViewModel.currencies.observe(viewLifecycleOwner) {
            currency = currenciesViewModel.currencies.value!!.find { it.id == currencyId }!!
            bindCurrency()
            currencyDetailsViewModel.getCurrencyPrices(currencyId)
        }

        currencyDetailsViewModel.currencyPrices.observe(viewLifecycleOwner) {
            if(it != null) setupPriceChart()
        }

        currencyDetailsViewModel.comments.observe(viewLifecycleOwner) {
            if(it != null) {
                if(it.isEmpty()) {
                    binding.rvComments.visibility = View.GONE
                    binding.tvNoComments.visibility = View.VISIBLE
                } else {
                    binding.tvNoComments.visibility = View.GONE
                    binding.rvComments.visibility = View.VISIBLE

                    binding.rvComments.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = CommentAdapter(requireContext(), it)
                    }
                }
            }
        }
    }

    private fun setupPriceChart() {
        priceChart = binding.lcPrice

        val entries = getChartPrices(currencyDetailsViewModel.currencyPrices.value!!)

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
            private val dateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
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
        binding.btnPost.setOnClickListener {
            if(requireContext().getStringPreference(LOGGED_USER)?.isEmpty() == true) {
                findNavController().navigate(R.id.action_to_LoginFragment)
            } else {
                val newCommentText = binding.etComment.text.toString().trim()
                currencyDetailsViewModel.addNewComment(currencyId, newCommentText, requireContext().getStringPreference(LOGGED_USER)!!)
                binding.etComment.text.clear()
            }
        }

        binding.etComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnPost.isEnabled = text?.trim()?.isNotEmpty() ?: false
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_to_CurrenciesFragment)
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
        binding.tvTotalVolume.text = String.format("%.5f", currency.total_volume)
        binding.tvAllTimeHigh.text = currency.ath.toString()
        binding.tvCirculatingSupply.text = String.format("%.5f", currency.circulating_supply)
        binding.tvTotalSupply.text = String.format("%.5f", currency.total_supply)
        binding.tvMaxSupply.text = if(currency.max_supply != null) String.format("%.5f", currency.max_supply) else ""
    }

    private fun getChartPrices(prices: List<CurrencyPrice>) : List<Entry> {
        val chartPrices = mutableListOf<Entry>()

        prices.forEach {
            chartPrices.add(Entry(formatStringTimeToTimeStamp(it.time).toDouble().toFloat(), it.price.toFloat()))
        }

        return chartPrices
    }
}