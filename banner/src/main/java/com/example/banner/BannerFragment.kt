package com.example.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.banner.databinding.FragmentBannerBinding
import com.example.map.MapContract
import com.example.map.MapPlugin


class BannerFragment : Fragment() {
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
            BannerPlugin.clickButton = true
            (parentFragment as? MapContract)?.getGeoPermissions()
            //childFragmentManager.beginTransaction().replace(R.id.container,MapPlugin.getMapFragment()).addToBackStack(null).commit()
            parentFragmentManager.popBackStack()

        }

    }
    companion object {
        fun newInstance() = BannerFragment()
    }
}