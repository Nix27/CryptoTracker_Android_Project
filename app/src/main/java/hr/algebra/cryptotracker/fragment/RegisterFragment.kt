package hr.algebra.cryptotracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.databinding.FragmentRegisterBinding
import hr.algebra.cryptotracker.enums.CustomResponse
import hr.algebra.cryptotracker.viewmodel.UserViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val userViewModel = UserViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.response.observe(viewLifecycleOwner) {
            when(userViewModel.response.value) {
                CustomResponse.SUCCESS -> {
                    findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
                }
                else -> Toast.makeText(requireContext(), userViewModel.response.value!!.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(username.isNotEmpty() && password.isNotEmpty()){
                userViewModel.registerUser(username, password)
            } else {
                if(username.isEmpty()) binding.etUsername.error = getString(R.string.user_name_is_required)
                if(password.isEmpty()) binding.etPassword.error = getString(R.string.password_is_required)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }
    }
}