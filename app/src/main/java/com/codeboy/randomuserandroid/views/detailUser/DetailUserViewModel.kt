package com.codeboy.randomuserandroid.views.detailUser

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.codeboy.randomuserandroid.domain.models.User
import com.codeboy.randomuserandroid.utils.DataState
import com.codeboy.randomuserandroid.views.BaseViewModel
import com.codeboy.randomuserandroid.views.userList.UserListEvents
import com.codeboy.randomuserandroid.views.userList.UserListUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(
    private val gson: Gson,
    app: Application
) : BaseViewModel(application = app){

    private val _userScreenState = MutableStateFlow(DetailUserState())
    val userScreenState : StateFlow<DetailUserState> = _userScreenState

    fun onEven(even: DetailUserEvents) {

        when (even) {
            is DetailUserEvents.GetUser -> {
                viewModelScope.launch {
                    getRandomUser(even.user)
                }
            }
        }
    }

    private suspend fun getRandomUser(userObject: String){
        _userScreenState.value = _userScreenState.value.copy(
            user = DataState.Loading()
        )
        val user = gson.fromJson(userObject, User::class.java)

        user?.let {
            _userScreenState.value = _userScreenState.value.copy(user = DataState.Success(it))
        }
    }
}