package com.example.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseroot.FirebaseAuthRepository
import com.example.firebaseroot.FirebasePlugin
import com.example.settings.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(), SettingsContract.Handler  {
    private val firebaseAuthRepository: FirebaseAuthRepository = FirebasePlugin.getFirebaseAuthRepository()
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signOut.setOnClickListener {
            firebaseAuthRepository.exit()
            (parentFragment as? SettingsContract)?.buttonSelector()
            (parentFragment as? SettingsContract)?.openAuthScreen()
        }
    }
    companion object {
        fun newInstance() = SettingsFragment()
    }
    override fun closeBanner() {
        parentFragmentManager.popBackStack()
    }

}