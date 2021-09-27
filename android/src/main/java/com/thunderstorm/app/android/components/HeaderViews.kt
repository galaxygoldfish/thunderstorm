package com.thunderstorm.app.android.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R

@Composable
fun ActionBar(
    text: String,
    backAction: () -> Unit
) {
   Row(
       modifier = Modifier.fillMaxWidth()
           .padding(top = 15.dp, bottom = 10.dp),
       verticalAlignment = Alignment.CenterVertically
   ) {
       IconButton(
           onClick = backAction,
           content = {
               Icon(
                   painter = painterResource(id = R.drawable.ic_back_icon),
                   contentDescription = stringResource(id = R.string.back_button_content_desc),
                   modifier = Modifier.size(24.dp),
                   tint = MaterialTheme.colors.onSurface
               )
           },
           modifier = Modifier.padding(start = 10.dp)
       )
       Text(
           text = text,
           style = MaterialTheme.typography.h4,
           fontSize = 22.sp,
           fontWeight = FontWeight.SemiBold,
           modifier = Modifier.padding(start = 10.dp)
       )
   }
}