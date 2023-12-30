package com.bhavnathacker.jettasks.ui.navigation

enum class TaskScreens {
    ListScreen,
    DetailScreen,
    LoginScreen;
    companion object {
        fun fromRoute(route: String?): TaskScreens =
            when (route?.substringBefore("/")) {
                ListScreen.name -> ListScreen
                DetailScreen.name -> DetailScreen
                null -> ListScreen
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}