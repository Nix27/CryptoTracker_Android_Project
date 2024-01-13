package hr.algebra.cryptotracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.adapter.CurrencyAdapter
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.databinding.FragmentCurrenciesBinding
import hr.algebra.cryptotracker.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrenciesFragment : Fragment() {
    private lateinit var binding: FragmentCurrenciesBinding
    private lateinit var currencies: List<Currency>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.spCurrencyChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                refreshRecyclerView(adapterView?.getItemAtPosition(position).toString(), 1)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshRecyclerView("usd", 1)
    }

    private fun refreshRecyclerView(vsCurrency: String, page: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            currencies = CryptoFetcher(requireContext()).fetchCryptoCurrencies(vsCurrency, page)
            binding.rvCurrencies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = CurrencyAdapter(requireContext(), currencies)
            }
        }
    }
}