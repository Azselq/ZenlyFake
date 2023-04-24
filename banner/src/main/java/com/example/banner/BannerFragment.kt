package com.example.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.auth.AuthPlugin
import com.example.banner.databinding.FragmentBannerBinding
import com.example.firebaseroot.FirebaseAuthRepository
import com.example.firebaseroot.FirebasePlugin
import com.example.map.MapContract
import com.example.map.MapPlugin


class BannerFragment : Fragment() {
    private val firebaseAuthRepository: FirebaseAuthRepository = FirebasePlugin.getFirebaseAuthRepository()
    lateinit var binding: FragmentBannerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            (parentFragment as? MapContract)?.getGeoPermissions()
            parentFragmentManager.popBackStack()

        }
        binding.buttonSignOut.setOnClickListener {
            firebaseAuthRepository.exit()
            //parentFragmentManager.popBackStack()
            //childFragmentManager.beginTransaction().replace(R.id.container, AuthPlugin.getAuthFragment()).addToBackStack(null).commit()

        }

    }
    companion object {
        fun newInstance() = BannerFragment()
    }
}