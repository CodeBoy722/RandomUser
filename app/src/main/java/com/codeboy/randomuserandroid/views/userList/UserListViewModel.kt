package com.codeboy.randomuserandroid.views.userList

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.codeboy.randomuserandroid.domain.models.User
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

    init {
        // get our saved list from storage and show on view while waiting for new data
        getSavedList()
    }

    private val _userListScreenState = MutableStateFlow(UserListUiState())
    val userListScreenState : StateFlow<UserListUiState> = _userListScreenState

    private val _savedUserList = MutableStateFlow(listOf<User>())
    val savedUserList: StateFlow<List<User>> = _savedUserList

    //get the userlist saved in storage
    fun getSavedList(){

    }

    fun saveUserlist(){

    }

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

        when {
            savedUserList.value.isNotEmpty() && userList is DataState.Success -> userList.let {
                val pagingList: MutableList<User> = mutableListOf()
                pagingList.addAll(savedUserList.value)
                pagingList.addAll(userList.extractData!!)

                _userListScreenState.value = _userListScreenState.value.copy(userListState = DataState.Success(pagingList))
            }

            else -> {
                _userListScreenState.value = _userListScreenState.value.copy(
                    userListState = userList
                )
                _savedUserList.value = userList.extractData!!
                saveUserlist()
            }
        }
    }





}