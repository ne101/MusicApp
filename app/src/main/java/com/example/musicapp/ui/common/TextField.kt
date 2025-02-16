package com.example.musicapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    textPlaceHolder: String,
    maxLine: Int = 1,
    name: (String) -> Unit,
    exit: () -> Unit,
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var focused by remember {
        mutableStateOf(true)
    }

    var currentText by rememberSaveable {
        mutableStateOf("")
    }

    BasicTextField(
        value = currentText,
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusEvent { focused = !focused }
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp,
                Color.Black,
                RoundedCornerShape(16.dp)
            ),
        onValueChange = {
            currentText = it
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
                name(currentText)
            },
        ),
        maxLines = maxLine,
        textStyle = TextStyle(
            fontSize = 16.sp
        ),
        decorationBox = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (currentText.isEmpty()) {
                    TextTrackName(textPlaceHolder, 16, Color.Black)
                }
                it()
                if (currentText.isNotEmpty()) {
                    IconButton(
                        modifier = Modifier.heightIn(max = 24.dp).align(Alignment.CenterEnd),
                        onClick = {
                            currentText = ""
                            name("")
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            exit()
                        },

                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "",
                            tint = Color.Black,
                        )
                    }
                }
            }
        }
    )
}