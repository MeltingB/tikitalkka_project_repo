package com.meltingb.tikitalkka.view

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meltingb.base.helpers.debug
import com.meltingb.base.ui.BaseFragment
import com.meltingb.base.utils.AppPreference
import com.meltingb.base.utils.CommonUtil.getVersion
import com.meltingb.base.utils.NetworkStatus
import com.meltingb.tikitalkka.R
import com.meltingb.tikitalkka.base.Constants.PREF_KEY_IS_SAVE_USER
import com.meltingb.tikitalkka.base.Constants.TOPIC_BALANCE
import com.meltingb.tikitalkka.base.Constants.TOPIC_COUPLE
import com.meltingb.tikitalkka.base.Constants.TOPIC_DATE
import com.meltingb.tikitalkka.base.Constants.TOPIC_FAMILY
import com.meltingb.tikitalkka.base.Constants.TOPIC_FRIEND
import com.meltingb.tikitalkka.base.Constants.TOPIC_RANDOM
import com.meltingb.tikitalkka.base.Constants.TOPIC_WORK
import com.meltingb.tikitalkka.databinding.FragmentHomeBinding
import com.meltingb.tikitalkka.databinding.FragmentPickBinding
import com.meltingb.tikitalkka.model.ChatTopicDTO
import com.meltingb.tikitalkka.model.UserDTO
import com.meltingb.tikitalkka.viewmodel.PickViewModel
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class PickFragment : BaseFragment<FragmentPickBinding>(R.layout.fragment_pick) {

    private val viewModel: PickViewModel by viewModel()
    private val args: PickFragmentArgs by navArgs<PickFragmentArgs>()
    private var dataList = listOf<ChatTopicDTO>()

    private var cardPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        dataList = args.dataListModel.datList

        initUI()
    }

    private fun initUI() {
        val topicType = when (args.topicType) {
            TOPIC_RANDOM -> getString(R.string.random_chat)
            TOPIC_FRIEND -> getString(R.string.topic_friend)
            TOPIC_COUPLE -> getString(R.string.topic_couple)
            TOPIC_FAMILY -> getString(R.string.topic_family)
            TOPIC_DATE -> getString(R.string.topic_date)
            TOPIC_WORK -> getString(R.string.topic_work)
            else -> getString(R.string.topic_balance)
        }
        viewModel.topicTypeTextLiveData.value = topicType

        binding.btnStart.setOnClickListener {
            flipCard(requireContext(), binding.clQuestionView, binding.llBackView)
            binding.btnStart.visibility = View.GONE
            binding.llBottomButton.visibility = View.VISIBLE

            setCardContent(cardPosition)
        }

        binding.btnNext.setOnClickListener {
            setCardContent(cardPosition + 1)
            flipCard(requireContext(), binding.clQuestionView, binding.llBackView)
        }

        viewModel.nowCountLiveData.value = "1"
        viewModel.totalCountLiveData.value = dataList.size.toString()
    }

    private fun setCardContent(position: Int) {
        viewModel.chatContentLiveData.value = dataList[position].chatContent
        if (dataList[position].description.isNotEmpty()) {
            viewModel.chatDescriptionLiveData.value = dataList[position].description
            binding.tvDescription.visibility = View.VISIBLE
        } else {
            binding.tvDescription.visibility = View.INVISIBLE
        }
        viewModel.nowCountLiveData.value = (position + 1).toString()
    }

    private fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {
            visibleView.isVisible = true
            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist
            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(inVisibleView)
            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()
            flipInAnimatorSet.doOnEnd {
                inVisibleView.isVisible = false
            }
        } catch (e: Exception) {

        }
    }

}