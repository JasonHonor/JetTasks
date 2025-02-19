package com.bhavnathacker.jettasks.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue


@ExperimentalComposeUiApi
@Composable
fun InputText(
    modifier: Modifier = Modifier,
    text: String,
    label: String="",
    testTag: String = "",
    maxLine: Int = 1,
    minLine:Int =1,
    onTextChange: (String) -> Unit,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    /*var textFieldValueState = remember {
        mutableStateOf(TextFieldValue(
            text = text,
            selection = TextRange(text.length)
        ))
    }*/
    OutlinedTextField(
        //value = textFieldValueState.value,
        value=text,
        onValueChange = onTextChange,
        //onValueChange = { tfv -> textFieldValueState.value = tfv.copy(text = tfv.text, selection = TextRange(text.length-1), composition = tfv.composition)},
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        maxLines = maxLine,
        minLines = minLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier.testTag(testTag)
        )
}