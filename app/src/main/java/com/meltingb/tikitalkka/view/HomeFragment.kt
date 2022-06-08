package com.meltingb.tikitalkka.view

import android.os.Bundle
import android.view.View
import com.meltingb.base.ui.BaseFragment
import com.meltingb.base.utils.CommonUtil.getVersion
import com.meltingb.tikitalkka.R
import com.meltingb.tikitalkka.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.tvVersion.text  = getVersion(requireContext())
    }


}