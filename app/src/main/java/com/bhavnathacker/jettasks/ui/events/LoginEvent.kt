package com.bhavnathacker.jettasks.ui.events

import com.bhavnathacker.jettasks.domain.model.Task
import com.bhavnathacker.jettasks.domain.model.TaskPriority
import com.bhavnathacker.jettasks.domain.model.TaskStatus
import java.util.*

sealed class LoginEvent {
    data class preauth(val mode:Int):LoginEvent()
    data class ChangeUsername(val newValue: String):LoginEvent()
    data class ChangePassword(val newValue: String):LoginEvent()
}