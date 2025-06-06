package com.example.biofit.ui.activity

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.biofit.R
import com.example.biofit.ui.components.ItemCard
import com.example.biofit.ui.components.SelectionDialog
import com.example.biofit.ui.components.ToggleButtonBar
import com.example.biofit.ui.components.TopBar
import com.example.biofit.ui.components.getStandardPadding
import com.example.biofit.ui.theme.BioFitTheme

class EditExerciseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val initialSelectedOption = intent.getIntExtra("SESSION_TITLE", R.string.morning)
        setContent {
            BioFitTheme {
                EditExerciseScreen(initialSelectedOption)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }
}

@Composable
fun EditExerciseScreen(initialSelectedOption: Int) {
    val context = LocalContext.current
    val activity = context as? Activity

    val standardPadding = getStandardPadding().first
    val modifier = getStandardPadding().second

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
                    start = standardPadding,
                    end = standardPadding,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                onBackClick = { activity?.finish() },
                title = stringResource(R.string.edit_exercise),
                middleButton = null,
                rightButton = {
                    TextButton(
                        onClick = { activity?.finish() }
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                standardPadding = standardPadding
            )

            EditExerciseScreenContent(
                initialSelectedOption = initialSelectedOption,
                standardPadding = standardPadding,
                modifier = modifier
            )
        }
    }
}

@Composable
fun EditExerciseScreenContent(
    initialSelectedOption: Int,
    standardPadding: Dp,
    modifier: Modifier
) {
    var selectedOption by rememberSaveable { mutableIntStateOf(initialSelectedOption) }

    var exerciseName by rememberSaveable { mutableStateOf("") }
    var level by rememberSaveable { mutableStateOf("") }
    var showLevelDialog by rememberSaveable { mutableStateOf(value = false) }
    var time by rememberSaveable { mutableStateOf("") }
    var session by rememberSaveable { mutableStateOf("") }
    var showSessionDialog by rememberSaveable { mutableStateOf(value = false) }
    var calories by rememberSaveable { mutableStateOf("") }
    var intensity by rememberSaveable { mutableStateOf("") }
    var showIntensityDialog by rememberSaveable { mutableStateOf(value = false) }

    val focusManager = LocalFocusManager.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(standardPadding * 2),
    ) {
        item {
            Column(
                modifier = modifier.padding(top = standardPadding)
            ) {
                ToggleButtonBar(
                    options = listOf(R.string.morning, R.string.afternoon, R.string.evening),
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it },
                    standardPadding = standardPadding
                )
            }
        }

        item {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(standardPadding)
            ) {
                OutlinedTextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.enter_exercise_name),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    },
                    prefix = { Text(text = stringResource(R.string.exercise_name)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                ItemCard(
                    onClick = { showLevelDialog = true },
                    modifier = modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = standardPadding,
                                vertical = standardPadding / 4
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (level == "") {
                                stringResource(R.string.select_goal)
                            } else {
                                level
                            },
                            modifier = Modifier.weight(1f),
                            color = if (level == "") {
                                MaterialTheme.colorScheme.outline
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            }
                        )

                        IconButton(onClick = { showLevelDialog = true }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back),
                                contentDescription = stringResource(R.string.level),
                                modifier = Modifier
                                    .size(standardPadding)
                                    .rotate(270f),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                if (showLevelDialog) {
                    SelectionDialog(
                        selectedOption = level,
                        onOptionSelected = { selectedLevel ->
                            level = selectedLevel
                            showLevelDialog = false
                        },
                        onDismissRequest = { showLevelDialog = false },
                        title = R.string.select_level,
                        listOptions = listOf(
                            stringResource(R.string.amateur),
                            stringResource(R.string.professional)
                        ),
                        standardPadding = standardPadding
                    )
                }

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    modifier = modifier,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    prefix = { Text(text = stringResource(R.string.time)) },
                    suffix = { Text(text = stringResource(R.string.min)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                ItemCard(
                    onClick = { showSessionDialog = true },
                    modifier = modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = standardPadding,
                                vertical = standardPadding / 4
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (session == "") {
                                stringResource(R.string.select_session)
                            } else {
                                session
                            },
                            modifier = Modifier.weight(1f),
                            color = if (session == "") {
                                MaterialTheme.colorScheme.outline
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            }
                        )

                        IconButton(onClick = { showSessionDialog = true }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back),
                                contentDescription = stringResource(R.string.session),
                                modifier = Modifier
                                    .size(standardPadding)
                                    .rotate(270f),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                if (showSessionDialog) {
                    SelectionDialog(
                        selectedOption = session,
                        onOptionSelected = { selectedSession ->
                            session = selectedSession
                            showSessionDialog = false
                        },
                        onDismissRequest = { showSessionDialog = false },
                        title = R.string.select_session,
                        listOptions = listOf(
                            stringResource(R.string.morning),
                            stringResource(R.string.afternoon),
                            stringResource(R.string.evening)
                        ),
                        standardPadding = standardPadding
                    )
                }

                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    modifier = modifier,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.End
                    ),
                    prefix = { Text(text = stringResource(R.string.calories) + "*") },
                    suffix = { Text(text = stringResource(R.string.kcal)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                ItemCard(
                    onClick = { showIntensityDialog = true },
                    modifier = modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = standardPadding,
                                vertical = standardPadding / 4
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (intensity == "") {
                                stringResource(R.string.select_intensity)
                            } else {
                                intensity
                            },
                            modifier = Modifier.weight(1f),
                            color = if (intensity == "") {
                                MaterialTheme.colorScheme.outline
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            }
                        )

                        IconButton(onClick = { showIntensityDialog = true }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back),
                                contentDescription = stringResource(R.string.intensity),
                                modifier = Modifier
                                    .size(standardPadding)
                                    .rotate(270f),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                if (showIntensityDialog) {
                    SelectionDialog(
                        selectedOption = intensity,
                        onOptionSelected = { selectedIntensity ->
                            intensity = selectedIntensity
                            showIntensityDialog = false
                        },
                        onDismissRequest = { showIntensityDialog = false },
                        title = R.string.select_intensity,
                        listOptions = listOf(
                            stringResource(R.string.low),
                            stringResource(R.string.medium),
                            stringResource(R.string.high)
                        ),
                        standardPadding = standardPadding
                    )
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.padding(
                    bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding() * 2
                            + standardPadding
                )
            )
        }
    }
}

@Preview(
    device = "id:pixel",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    locale = "vi"
)
@Composable
private fun EditExerciseScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        EditExerciseScreen(
            initialSelectedOption = R.string.morning
        )
    }
}

@Preview(
    device = "id:pixel_9_pro_xl",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun EditExerciseScreenPreviewInLargePhone() {
    BioFitTheme {
        EditExerciseScreen(
            initialSelectedOption = R.string.morning
        )
    }
}

@Preview(
    device = "spec:parent=Nexus 10,orientation=portrait",
    locale = "vi",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun EditExerciseScreenPreviewInTablet() {
    BioFitTheme {
        EditExerciseScreen(
            initialSelectedOption = R.string.morning
        )
    }
}

@Preview(
    device = "spec:parent=pixel,orientation=landscape",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    locale = "vi"
)
@Composable
private fun EditExerciseScreenLandscapeDarkModePreviewInSmallPhone() {
    BioFitTheme {
        EditExerciseScreen(
            initialSelectedOption = R.string.morning
        )
    }
}

@Preview(
    device = "spec:parent=pixel_9_pro_xl,orientation=landscape",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun EditExerciseScreenLandscapePreviewInLargePhone() {
    BioFitTheme {
        EditExerciseScreen(
            initialSelectedOption = R.string.morning
        )
    }
}

@Preview(
    device = "spec:parent=Nexus 10",
    locale = "vi",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun EditExerciseScreenLandscapePreviewInTablet() {
    BioFitTheme {
        EditExerciseScreen(
            initialSelectedOption = R.string.morning
        )
    }
}