package hr.algebra.cryptotracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.databinding.FragmentCurrencyDetailsBinding

const val CURRENCY_ID = "hr.algebra.cryptotracker.fragment.currency_id"
class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_CurrencyDetailsFragment_to_CurrenciesFragment)
        }
    }
}