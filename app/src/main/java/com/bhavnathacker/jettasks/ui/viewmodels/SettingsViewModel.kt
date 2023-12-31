package com.bhavnathacker.jettasks.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhavnathacker.jettasks.UserPreferences
import com.bhavnathacker.jettasks.domain.model.Login
import com.bhavnathacker.jettasks.domain.model.Settings
import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import com.bhavnathacker.jettasks.domain.use_cases.TaskUseCases
import com.bhavnathacker.jettasks.domain.use_cases.UserPreferenceUseCases
import com.bhavnathacker.jettasks.ui.events.LoginEvent
import com.bhavnathacker.jettasks.ui.events.SettingsEvent
import com.bhavnathacker.jettasks.ui.states.TaskUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
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

    private fun filterSortTasks(
        tasks: List<Task>,
        showCompleted: Boolean,
        sortOrder: UserPreferences.SortOrder,
        server:String
    ): List<Task> {
        // filter the tasks
        val filteredTasks = if (showCompleted) {
            tasks
        } else {
            tasks.filter { it.status == TaskStatus.PENDING }
        }
        // sort the tasks
        return when (sortOrder) {
            UserPreferences.SortOrder.UNSPECIFIED -> filteredTasks
            UserPreferences.SortOrder.NONE -> filteredTasks
            UserPreferences.SortOrder.BY_DEADLINE -> filteredTasks.sortedBy { it.deadline }
            UserPreferences.SortOrder.BY_PRIORITY -> filteredTasks.sortedBy { it.priority.ordinal }
            UserPreferences.SortOrder.BY_DEADLINE_AND_PRIORITY -> filteredTasks.sortedWith(
                compareBy<Task> { it.deadline }.thenBy { it.priority.ordinal }
            )
            // We shouldn't get any other values
            else -> throw UnsupportedOperationException("$sortOrder not supported")
        }
    }

    private val tasksUiModelFlow = combine(
        tasksFlow,
        userPreferencesFlow
    ) { tasks: List<Task>, userPreferences: UserPreferences ->
        return@combine TaskUiModel(
            tasks = filterSortTasks(
                tasks,
                userPreferences.showCompleted,
                userPreferences.sortOrder,
                userPreferences.server
            ),
            showCompleted = userPreferences.showCompleted,
            sortOrder = userPreferences.sortOrder,
            server = userPreferences.server
        )
    }

    var tasksUiModelStateFlow = tasksUiModelFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, TaskUiModel(
            emptyList(), false, UserPreferences.SortOrder.NONE,""
        )
    )


    private val _taskState = mutableStateOf(
        Settings(
            tasksUiModelStateFlow.value.server,
        )
    )

    val taskState: State<Settings> = _taskState

    public fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeServer -> {
                _taskState.value =
                    taskState.value.copy(server = event.newValue)
                viewModelScope.launch { userPreferenceUseCases.updateServer(event.newValue) }
            }
            is SettingsEvent.SaveServer -> {
                _taskState.value =
                    taskState.value.copy(server = event.newValue)
                viewModelScope.launch { userPreferenceUseCases.updateServer(event.newValue) }
            }
        }
    }
}