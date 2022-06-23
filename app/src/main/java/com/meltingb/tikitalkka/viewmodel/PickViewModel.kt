package com.meltingb.tikitalkka.viewmodel

import androidx.lifecycle.MutableLiveData
import com.meltingb.base.ui.BaseViewModel

class PickViewModel : BaseViewModel() {

    val topicTypeTextLiveData = MutableLiveData("")
    val chatContentLiveData = MutableLiveData("")
    val chatDescriptionLiveData = MutableLiveData("")
    val nowCountLiveData = MutableLiveData("")
    val totalCountLiveData = MutableLiveData("")

}