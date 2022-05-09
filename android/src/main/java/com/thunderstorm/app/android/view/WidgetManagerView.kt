package com.thunderstorm.app.android.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.getViewModel
import com.thunderstorm.app.android.theme.ThunderstormTheme
import com.thunderstorm.app.android.updateAllWidgets
import com.thunderstorm.app.android.viewmodel.WidgetManagerViewModel
import com.thunderstorm.app.android.widget.updateWidgetData
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext

@Composable
fun WidgetManagerView(navController: NavController) {
    val viewModel = navController.context.getViewModel(WidgetManagerViewModel::class.java)
    ThunderstormTheme {
        LaunchedEffect(true) {
            viewModel.fetchCurrentWidgets(navController.context)
        }
        Scaffold(
            topBar = {
                ActionBar(
                    text = stringResource(id = R.string.widget_manage_actionbar_title),
                    backAction = {
                        navController.popBackStack()
                    }
                )
            }
        ) {
            viewModel.currentWidgetsList.let {
                if (it.isNotEmpty()) {
                    LazyColumn {
                        itemsIndexed(viewModel.currentWidgetsList) { _, item ->
                            WidgetManagerListItem(
                                widgetID = item,
                                viewModel = viewModel
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.widget_missing_image),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.padding(horizontal = 40.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.widget_manage_nothing_here),
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.fillMaxHeight(0.3F))
                        }

                    }
                }
            }

        }
        CityChangeDialog(
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun WidgetManagerListItem(widgetID: Int, viewModel: WidgetManagerViewModel) {
    val dataStore = DataStore(LocalContext.current.applicationContext as SharedContext)
    val cityName = dataStore.getString("WIDGET_${widgetID}_CITY_TITLE")
    var cardExpanded by remember { mutableStateOf(false) }
    var enableDarkMode by remember { mutableStateOf(dataStore.getBoolean("WIDGET_${widgetID}_DARK_THEME")) }
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        border = BorderStroke(3.dp, MaterialTheme.colors.primaryVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .clickable {
                cardExpanded = !cardExpanded
            }
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Image(
                        painter = painterResource(
                            id = dataStore.getInteger("WIDGET_${widgetID}_ICON_RESOURCE")
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .size(30.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column {
                        Text(
                            text = String.format(
                                stringResource(id = R.string.widget_manage_card_title),
                                cityName
                            ),
                            modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                            style = MaterialTheme.typography.subtitle2 + TextStyle(fontSize = 17.sp)
                        )
                        Text(
                            text = String.format(
                                stringResource(id = R.string.widget_manage_card_subtitle),
                                widgetID.toString()
                            ),
                            modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_down_circle),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }
            AnimatedVisibility(visible = cardExpanded) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.widget_manage_preference_dark_title)
                        )
                        Switch(
                            checked = enableDarkMode,
                            onCheckedChange = { newValue ->
                                enableDarkMode = newValue
                                dataStore.putBoolean("WIDGET_${widgetID}_DARK_THEME", newValue)
                                context.updateAllWidgets()
                            }
                        )
                    }
                    Button(
                        onClick = {
                            viewModel.apply {
                                showingCityDialog = true
                                currentWidgetID = widgetID
                            }
                        },
                        shape = RoundedCornerShape(80.dp),
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 20.dp, end = 20.dp, top = 10.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.widget_manage_change_city_button),
                            color = MaterialTheme.colors.background
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CityChangeDialog(
    viewModel: WidgetManagerViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    AnimatedVisibility(visible = viewModel.showingCityDialog) {
        Dialog(
            onDismissRequest = {
                viewModel.showingCityDialog = false
            }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        if (MaterialTheme.colors.isLight) {
                            MaterialTheme.colors.background
                        } else {
                            MaterialTheme.colors.secondary
                        }
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        border = BorderStroke(3.dp, MaterialTheme.colors.primaryVariant),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxHeight(0.8F)
            ) {
                CityAddView(
                    navController = navController,
                    widgetVersion = true,
                    configOnBackPress = {
                        navController.popBackStack()
                    },
                    configOnFinish = { addCityViewModel ->
                        DataStore(context.applicationContext as SharedContext).apply {
                            viewModel.currentWidgetID?.let { ID ->
                                putString(
                                    key = "WIDGET_${ID}_CITY_TITLE",
                                    value = addCityViewModel.currentSelectedCity!!.name.split(",")[0]
                                )
                                putString(
                                    key = "WIDGET_${ID}_SERVICE_URL",
                                    value = addCityViewModel.currentSelectedCity!!.url!!
                                )
                                putBoolean(
                                    key = "WIDGET_${ID}_DARK_THEME",
                                    value = true
                                )
                                updateWidgetData(context, ID)
                                context.updateAllWidgets()
                            }
                        }
                        addCityViewModel.apply {
                            showDoneDialog = false
                            currentSearchQuery = TextFieldValue()
                            citySearchResult.clear()
                            viewModel.showingCityDialog = false
                        }
                    }
                )
            }
        }
    }
}