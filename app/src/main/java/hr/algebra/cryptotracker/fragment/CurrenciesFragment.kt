package hr.algebra.cryptotracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.abstraction.Navigable
import hr.algebra.cryptotracker.adapter.CurrencyAdapter
import hr.algebra.cryptotracker.api.CryptoFetcher
import hr.algebra.cryptotracker.databinding.FragmentCurrenciesBinding
import hr.algebra.cryptotracker.model.Currency
import hr.algebra.cryptotracker.viewmodel.CurrenciesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrenciesFragment : Fragment(), Navigable {

    private lateinit var binding: FragmentCurrenciesBinding
    private val viewModel = CurrenciesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currencies.observe(viewLifecycleOwner) {
            binding.rvCurrencies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = CurrencyAdapter(requireContext(), it, this@CurrenciesFragment)
            }
        }
    }

    override fun navigate(bundle: Bundle) {
        findNavController().navigate(R.id.action_CurrenciesFragment_to_CurrencyDetailsFragment, bundle)
    }
}