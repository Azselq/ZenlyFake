package com.example.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.banner.databinding.FragmentBannerBinding
import com.example.firebaseroot.FirebaseAuthRepository
import com.example.firebaseroot.FirebasePlugin


class BannerFragment : Fragment(), BannerContract.Handler {
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
            (parentFragment as? BannerContract)?.getGeoPermissions()
        }

    }
    companion object {
        fun newInstance() = BannerFragment()
    }

    override fun closeBanner() {
        parentFragmentManager.popBackStack()
    }
}