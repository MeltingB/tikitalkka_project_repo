package com.meltingb.tikitalkka.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.meltingb.base.helpers.debug
import com.meltingb.base.ui.BaseFragment
import com.meltingb.base.ui.utils.setOnSingleClickListener
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
        getChatTopicData()

        initUI()

        viewModel.navigatorLiveData.observe(viewLifecycleOwner, ::navigate)
    }

    private fun navigate(event: HomeViewModel.NavigatorEvent) {
        val topicType =  when (event) {
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

    private fun getChatTopicData() {
        mFirestore.collection(DB_PATH_CHAT_TOPIC).addSnapshotListener { response, error ->
            if (response == null) {
                return@addSnapshotListener
            }

            for (snapshot in response.documents) {
                val item = snapshot.toObject(ChatTopicDTO::class.java)
                debug("item >>> $item")
            }
        }
    }

    private fun insertUser() {
        if (NetworkStatus.checkNetworkState(requireContext())) {
            val userDTO = UserDTO(auth.currentUser!!.uid, DateTime.now().millis)
            mFirestore.collection(DB_PATH_USER).document().set(userDTO)

            AppPreference.set(PREF_KEY_IS_SAVE_USER, true)
        }
    }

    private fun moveChatTopic(topicType: String? = null) {
        // TODO : topicType 에 따라 DB 에서 조회
        val directions = HomeFragmentDirections.actionHomeFragmentToPickFragment(topicType ?: TOPIC_RANDOM)
        findNavController().navigate(directions)
    }

}