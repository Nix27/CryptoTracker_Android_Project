package hr.algebra.cryptotracker.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.algebra.cryptotracker.R
import hr.algebra.cryptotracker.databinding.FragmentRegisterBinding
import hr.algebra.cryptotracker.enums.UserResponse
import hr.algebra.cryptotracker.model.User
import hr.algebra.cryptotracker.viewmodel.UserViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel = UserViewModel()

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
        viewModel.response.observe(viewLifecycleOwner) {
            when(viewModel.response.value) {
                UserResponse.SUCCESS -> findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
                else -> Toast.makeText(requireContext(), viewModel.response.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if(username.isNotEmpty() && password.isNotEmpty()){
                viewModel.registerUser(username, password)
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