package com.meltingb.tikitalkka.viewmodel

import androidx.navigation.Navigation
import com.meltingb.base.model.SingleLiveEvent
import com.meltingb.base.ui.BaseViewModel
import com.meltingb.tikitalkka.base.Constants.TOPIC_BALANCE
import com.meltingb.tikitalkka.base.Constants.TOPIC_COUPLE
import com.meltingb.tikitalkka.base.Constants.TOPIC_DATE
import com.meltingb.tikitalkka.base.Constants.TOPIC_FAMILY
import com.meltingb.tikitalkka.base.Constants.TOPIC_FRIEND
import com.meltingb.tikitalkka.base.Constants.TOPIC_RANDOM
import com.meltingb.tikitalkka.base.Constants.TOPIC_WORK

class HomeViewModel: BaseViewModel() {

    val navigatorLiveData : SingleLiveEvent<NavigatorEvent>
        get() = _navigatorLiveData
    private val _navigatorLiveData = SingleLiveEvent<NavigatorEvent>()

    fun movePickView(topic: String) {
        when (topic) {
            TOPIC_RANDOM -> _navigatorLiveData.postValue(NavigatorEvent.MoveRandom)
            TOPIC_FRIEND -> _navigatorLiveData.postValue(NavigatorEvent.MoveFriend)
            TOPIC_COUPLE -> _navigatorLiveData.postValue(NavigatorEvent.MoveCouple)
            TOPIC_FAMILY -> _navigatorLiveData.postValue(NavigatorEvent.MoveFamily)
            TOPIC_DATE -> _navigatorLiveData.postValue(NavigatorEvent.MoveDate)
            TOPIC_WORK -> _navigatorLiveData.postValue(NavigatorEvent.MoveWork)
            TOPIC_BALANCE -> _navigatorLiveData.postValue(NavigatorEvent.MoveBalance)
            else -> return
        }
    }

    sealed class NavigatorEvent {
        object MoveRandom: NavigatorEvent()
        object MoveFriend: NavigatorEvent()
        object MoveCouple: NavigatorEvent()
        object MoveFamily: NavigatorEvent()
        object MoveDate: NavigatorEvent()
        object MoveWork: NavigatorEvent()
        object MoveBalance: NavigatorEvent()
    }

}