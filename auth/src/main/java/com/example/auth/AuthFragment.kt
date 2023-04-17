package com.example.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.auth.databinding.FragmentAuthBinding
import com.example.firebaseroot.FirebasePlugin

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
            var email = binding.login.toString()
            var password = binding.number.toString()
            Log.d("123","$email, $password")
            viewModel.logIn(email,password)
        }
    }




    companion object {
        fun newInstance() = AuthFragment()
    }
}