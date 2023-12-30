package com.bhavnathacker.jettasks.ui.viewmodels

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.UserPreferences.SortOrder
import com.bhavnathacker.jettasks.domain.model.Login
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskPriority
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.bhavnathacker.jettasks.domain.use_cases.TaskUseCases
import com.bhavnathacker.jettasks.domain.use_cases.UserPreferenceUseCases
import com.bhavnathacker.jettasks.ui.events.LoginEvent
import com.bhavnathacker.jettasks.ui.events.TaskDetailEvent
import com.bhavnathacker.jettasks.ui.events.TaskListEvent
import com.bhavnathacker.jettasks.ui.states.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val userPreferenceUseCases: UserPreferenceUseCases
) : ViewModel() {

    private lateinit var userPreferencesFlow: Flow<UserPreferences>
    private lateinit var tasksFlow: Flow<List<Task>>

    init {
        viewModelScope.launch {
            userPreferencesFlow = userPreferenceUseCases.getUserPreferences()
            tasksFlow = taskUseCases.getTasks()
        }
    }

    private val _taskState = mutableStateOf(
        Login(
            "",
            ""
        )
    )

    val taskState: State<Login> = _taskState

    public fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.preauth ->{
                if (_taskState.value.username=="demo") {
                    //do login check
                }
            }
            is LoginEvent.ChangePassword -> _taskState.value =
                taskState.value.copy(password = event.newValue)
            is LoginEvent.ChangeUsername -> _taskState.value =
                taskState.value.copy(username = event.newValue)
            }
        }
}