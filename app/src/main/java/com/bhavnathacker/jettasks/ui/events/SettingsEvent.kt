package com.bhavnathacker.jettasks.ui.events

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskPriority
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import java.util.*

sealed class SettingsEvent {
    data class ChangeServer(val newValue: String):SettingsEvent()
    data class SaveServer(val newValue: String):SettingsEvent()
}