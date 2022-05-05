package com.thunderstorm.app.android.view.settings

import android.preference.SwitchPreference
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.theme.Manrope

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun BasePreference(
    title: String,
    subtitle: String,
    icon: Painter,
    onClick: () -> Unit,
    switchPreference: Boolean = false,
    checkState: MutableState<Boolean>? = null
) {
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    Card(
        backgroundColor = colorResource(id = R.color.interface_gray_alt),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, colorResource(id = R.color.interface_gray)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        onClick = {
            checkState!!.value = !checkState.value
            onClick.invoke()
        },
        interactionSource = interactionSource
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(32.dp),
                tint = MaterialTheme.colors.onBackground
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7F)
                    .padding(top = 15.dp, bottom = 15.dp, start = 20.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = subtitle,
                    style = TextStyle(
                        fontFamily = Manrope,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onBackground.copy(0.7F),
                        lineHeight = 17.sp
                    ),
                )
            }
            if (switchPreference) {
                Switch(
                    checked = checkState!!.value,
                    onCheckedChange = {
                        checkState.value = it
                        onClick.invoke()
                    },
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}