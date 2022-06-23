package com.meltingb.tikitalkka.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
import com.meltingb.tikitalkka.model.ChatTopicDTO
import com.meltingb.tikitalkka.model.ChatTopicList
import com.meltingb.tikitalkka.model.UserDTO
import com.meltingb.tikitalkka.viewmodel.HomeViewModel
import org.joda.time.DateTime
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private val viewModel: HomeViewModel by viewModel()

    companion object {
        const val DB_PATH_USER = "user"
        const val DB_PATH_CHAT_TOPIC = "chat_topic"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        auth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()

        if (!AppPreference.get(PREF_KEY_IS_SAVE_USER, false)) {
            insertUser() // 사용자 AUTH 저장 (추후 확정성 고려)
        }

        initUI()
        viewModel.navigatorLiveData.observe(viewLifecycleOwner, ::navigate)
    }

    private fun navigate(event: HomeViewModel.NavigatorEvent) {
        val topicType = when (event) {
            HomeViewModel.NavigatorEvent.MoveRandom -> null
            HomeViewModel.NavigatorEvent.MoveFriend -> TOPIC_FRIEND
            HomeViewModel.NavigatorEvent.MoveCouple -> TOPIC_COUPLE
            HomeViewModel.NavigatorEvent.MoveFamily -> TOPIC_FAMILY
            HomeViewModel.NavigatorEvent.MoveDate -> TOPIC_DATE
            HomeViewModel.NavigatorEvent.MoveWork -> TOPIC_WORK
            HomeViewModel.NavigatorEvent.MoveBalance -> TOPIC_BALANCE
        }
        moveChatTopic(topicType)
    }

    private fun initUI() {
        binding.tvVersion.text = getVersion(requireContext())
    }

    private fun getChatTopicDataByCode(
        topicCode: String,
        result: (MutableList<ChatTopicDTO>) -> Unit
    ) {
        val dataList = mutableListOf<ChatTopicDTO>()
        mFirestore.collection(DB_PATH_CHAT_TOPIC)
            .whereEqualTo("topicCode", topicCode)
            .addSnapshotListener { response, error ->
                if (response == null) {
                    return@addSnapshotListener
                }

                for (snapshot in response.documents) {
                    val item = snapshot.toObject(ChatTopicDTO::class.java)!!
                    dataList.add(item)
                }
                result(dataList)
            }
    }

    private fun insertUser() {
        if (NetworkStatus.checkNetworkState(requireContext())) {
            val userDTO = UserDTO(auth.currentUser!!.uid, DateTime.now().millis)
            mFirestore.collection(DB_PATH_USER).document().set(userDTO)

            AppPreference.set(PREF_KEY_IS_SAVE_USER, true)
        }
    }

    private fun moveChatTopic(topicCode: String? = null) {
        if (topicCode != null) {
            getChatTopicDataByCode(topicCode) { dataList ->
                // 주제 카드 화면으로 이동
                val directions = HomeFragmentDirections.actionHomeFragmentToPickFragment(
                    topicCode ?: TOPIC_RANDOM, ChatTopicList(dataList.shuffled())
                )
                findNavController().navigate(directions)
            }
        } else {
            // TODO : 랜덤 픽 구현필요
        }
    }

}