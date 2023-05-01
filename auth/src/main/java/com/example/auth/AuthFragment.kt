package com.example.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.auth.databinding.FragmentAuthBinding
import com.example.firebaseroot.FirebasePlugin
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    lateinit var binding: FragmentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply.setOnClickListener {
            val email = binding.login.text.toString()
            val password = binding.number.text.toString()
            Log.d("123","$email, $password")
            if(email.isNullOrEmpty() and password.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Введите данные ", Toast.LENGTH_LONG).show()
            }else{
                viewModel.logIn(email,password)
            }
        }
        lifecycleScope.launch {
            viewModel.actions.collect{
               when(it){
                   AuthViewModel.Action.OpenMainScreen -> (parentFragment as? AuthContract)?.openMainScreen()
               }
            }
        }
    }

    companion object {
        fun newInstance() = AuthFragment()
    }
}