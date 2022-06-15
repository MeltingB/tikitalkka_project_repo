package com.meltingb.tikitalkka.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.meltingb.base.ui.BaseViewModel

class PickViewModel: BaseViewModel() {

    val topicTypeTextLiveData = MutableLiveData("")
    val chatContentLiveData = MutableLiveData("")
    val chatDescriptionLiveData = MutableLiveData("")
    val nowCountLiveData = MutableLiveData("")
    val totalCountLiveData = MutableLiveData("")

    fun moveBack(view: View) {
        view.findNavController().popBackStack()
    }

}