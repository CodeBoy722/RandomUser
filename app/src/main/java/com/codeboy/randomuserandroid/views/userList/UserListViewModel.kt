package com.codeboy.randomuserandroid.views.userList

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.codeboy.randomuserandroid.domain.useCases.UseCaseRandomUsers
import com.codeboy.randomuserandroid.utils.DataState
import com.codeboy.randomuserandroid.views.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val ucRandomUsers: UseCaseRandomUsers,
    private val gson: Gson,
    app: Application
) : BaseViewModel(application = app){

    private val _userListScreenState = MutableStateFlow(UserListUiState())
    val userListScreenState : StateFlow<UserListUiState> = _userListScreenState

    fun onEven(even: UserListEvents) {

        when (even) {
            is UserListEvents.GetRandomUsers -> {
                viewModelScope.launch {
                    getRandomUsers(1,25, "weenect")
                }
            }
        }
    }

    private suspend fun getRandomUsers(page: Int, results: Int, seed: String){

        _userListScreenState.value = _userListScreenState.value.copy(
            userListState = DataState.Loading()
        )

        val userList = ucRandomUsers.invoke(page, results, seed)

        _userListScreenState.value = _userListScreenState.value.copy(
            userListState = userList
        )
    }


}