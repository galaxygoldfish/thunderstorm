package com.thunderstorm.app.android.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputFieldWithHint(
    hint: String,
    style: TextStyle,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = textFieldValue,
            modifier = modifier,
            textStyle = style,
            cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
            onValueChange = {
                onValueChange.invoke(it)
            }
        )
        if (textFieldValue.text.isEmpty()) {
            Text(
                text = hint,
                style = style,
                modifier = modifier,
                color = MaterialTheme.colors.onBackground.copy(0.5F)
            )
        }
    }
}
