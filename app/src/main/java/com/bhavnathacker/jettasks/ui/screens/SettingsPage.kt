package com.bhavnathacker.jettasks.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bhavnathacker.jettasks.ui.viewmodels.LoginViewModel

@ExperimentalComposeUiApi
@Composable
fun SettingPage(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
)
{
}