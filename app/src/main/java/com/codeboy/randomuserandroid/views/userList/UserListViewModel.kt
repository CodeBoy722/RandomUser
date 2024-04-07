package com.codeboy.randomuserandroid.views.userList

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.codeboy.randomuserandroid.domain.models.PagingData
import com.codeboy.randomuserandroid.domain.models.User
import com.codeboy.randomuserandroid.domain.useCases.UseCaseRandomUsers
import com.codeboy.randomuserandroid.utils.DataState
import com.codeboy.randomuserandroid.utils.UserDataStoreUtil
import com.codeboy.randomuserandroid.views.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val ucRandomUsers: UseCaseRandomUsers,
    private val gson: Gson,
    app: Application
) : BaseViewModel(application = app){

    private var pagingData: PagingData = PagingData()

    private val _userListScreenState = MutableStateFlow(UserListUiState())
    val userListScreenState : StateFlow<UserListUiState> = _userListScreenState

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        // get first data
        onEven(UserListEvents.GetRandomUsers)
    }

    fun onEven(even: UserListEvents) {

        when (even) {
            is UserListEvents.GetRandomUsers -> {
                viewModelScope.launch {
                    getRandomUsers(pagingData.page, pagingData.results, pagingData.seed)
                }
            }

            is UserListEvents.GetNextUsers -> {
                pagingData.page += 1
                viewModelScope.launch {
                    getNextRandomUsers(pagingData.page, pagingData.results, pagingData.seed)
                }
            }

            is UserListEvents.GetSavedUsers -> {
                val paging = UserDataStoreUtil(getApplication()).getLastPagingData()
                pagingData = paging
                val savedUsers = even.savedUsers
                _userListScreenState.value = _userListScreenState.value.copy(userListState = DataState.Success(savedUsers))
            }

            is UserListEvents.RefreshUsers -> TODO()
        }
    }

    private suspend fun getRandomUsers(page: Int, results: Int, seed: String){
        _userListScreenState.value = _userListScreenState.value.copy(
            userListState = DataState.Loading()
        )
        val userList = ucRandomUsers.invoke(page, results, seed)
        _userListScreenState.value = _userListScreenState.value.copy(userListState = userList)
        saveData()
    }

    private suspend fun getNextRandomUsers(page: Int, results: Int, seed: String){
        // get next page
        val nextPageList = ucRandomUsers.invoke(page, results, seed)

        if (nextPageList is DataState.Success){
            val prevList = userListScreenState.value.userListState.extractData
            val combinedList = mutableListOf<User>()

            combinedList.addAll(prevList!!)
            combinedList.addAll(nextPageList.extractData!!)
            _userListScreenState.value = _userListScreenState.value.copy(userListState = DataState.Success(combinedList))
            saveData()
        }else{
            _userListScreenState.value = _userListScreenState.value.copy(userListState = DataState.Failure())
        }
    }

    private fun saveData(){
        val lastList =  userListScreenState.value.userListState.extractData
        if(!lastList.isNullOrEmpty()){
            UserDataStoreUtil(getApplication()).saveLastUserList(lastList)
            UserDataStoreUtil(getApplication()).saveLastPaging(pagingData)
        }
    }


}