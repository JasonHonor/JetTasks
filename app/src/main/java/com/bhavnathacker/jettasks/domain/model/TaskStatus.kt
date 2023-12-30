package com.bhavnathacker.jettasks.domain.model

enum class TaskStatus {
    PENDING, COMPLETED,DELETED;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.name
            }
        }
    }
}