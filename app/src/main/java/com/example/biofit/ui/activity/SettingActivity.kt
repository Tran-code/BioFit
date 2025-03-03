package com.example.biofit.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.biofit.R
import com.example.biofit.navigation.MainActivity
import com.example.biofit.ui.components.ItemCard
import com.example.biofit.ui.components.MainCard
import com.example.biofit.ui.components.SelectionDialog
import com.example.biofit.ui.components.SubCard
import com.example.biofit.ui.components.TopBar
import com.example.biofit.ui.components.getStandardPadding
import com.example.biofit.ui.theme.BioFitTheme
import java.util.Calendar

class SettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BioFitTheme {
                SettingScreen()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }
}

@Composable
fun SettingScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val standardPadding = getStandardPadding().first
    val modifier = getStandardPadding().second

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
                    start = standardPadding,
                    end = standardPadding,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                onBackClick = { activity?.finish() }, // Xử lý sự kiện khi người dùng nhấn nút Back
                onHomeClick = {
                    activity?.let {
                        val intent = Intent(it, MainActivity::class.java)
                        it.startActivity(intent)
                    }
                },
                title = stringResource(R.string.setting),
                middleButton = null,
                rightButton = {
                    TextButton(
                        onClick = { TODO() } // Xử lý sự kiện khi người dùng nhấn nút Save
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                standardPadding = standardPadding
            )
            SettingContent(
                screenWidth,
                screenHeight,
                standardPadding,
                modifier
            )
        }
    }
}

@Composable
fun HomeButton(
    onHomeClick: () -> Unit = {},
    standardPadding: Dp
) {
    IconButton(
        onClick = onHomeClick,
        modifier = Modifier.size(standardPadding * 1.5f),
        enabled = true,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Back Button",
            modifier = Modifier.size(standardPadding * 1.5f),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SettingContent(
    screenWidth: Int,
    screenHeight: Int,
    standardPadding: Dp,
    modifier: Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(standardPadding * 2)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = standardPadding),
                horizontalAlignment = if (screenWidth > screenHeight) {
                    Alignment.CenterHorizontally
                } else {
                    Alignment.Start
                }
            ) {
                Text(
                    text = stringResource(R.string.my_profile),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(standardPadding),
                verticalArrangement = Arrangement.spacedBy(standardPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var userName by rememberSaveable { mutableStateOf(value = "User name") } // Thay tên người dùng từ database vào User name
                var gender by rememberSaveable { mutableStateOf("") } // Thay giới tính từ database vào Gender
                var showGenderDialog by rememberSaveable { mutableStateOf(false) }
                var dateOfBirth by rememberSaveable { mutableStateOf("") } // Thay ngày sinh từ database vào dd / mm / yyyy
                var showDatePicker by rememberSaveable { mutableStateOf(false) }
                var height by rememberSaveable { mutableStateOf("hhh") } // Thay chiều cao từ database vào hhh
                var weight by rememberSaveable { mutableStateOf("ww") } // Thay cân nặng từ database vào ww
                var email by rememberSaveable { mutableStateOf(value = "biofit@example.com") } // Thay email từ database vào Email

                val focusManager = LocalFocusManager.current

                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    modifier = modifier,
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.End
                    ),
                    prefix = {
                        Text(
                            text = stringResource(R.string.name),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                ItemCard(
                    onClick = { showGenderDialog = true },
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
                            text = stringResource(R.string.gender),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = if (gender == "") {
                                stringResource(R.string.select_gender)
                            } else {
                                gender
                            },
                            modifier = Modifier.weight(1f),
                            color = if (gender == "") {
                                MaterialTheme.colorScheme.outline
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.bodySmall
                        )

                        IconButton(onClick = { showGenderDialog = true }) {
                            Image(
                                painter = painterResource(R.drawable.btn_back),
                                contentDescription = stringResource(R.string.gender),
                                modifier = Modifier.rotate(270f)
                            )
                        }
                    }
                }

                if (showGenderDialog) {
                    SelectionDialog(
                        selectedOption = gender,
                        onOptionSelected = { selectedGender ->
                            gender = selectedGender
                            showGenderDialog = false
                        },
                        onDismissRequest = { showGenderDialog = false },
                        title = R.string.select_gender,
                        listOptions = listOf(
                            stringResource(R.string.male),
                            stringResource(R.string.female)
                        ),
                        standardPadding = standardPadding
                    )
                }

                ItemCard(
                    onClick = { showDatePicker = true },
                    modifier = modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = standardPadding, vertical = standardPadding / 4),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.date_of_birth),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            text = dateOfBirth.ifEmpty {
                                stringResource(R.string.select_date_of_birth)
                            },
                            modifier = Modifier.weight(1f),
                            color = if (dateOfBirth.isEmpty()) {
                                MaterialTheme.colorScheme.outline
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.bodySmall
                        )

                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = stringResource(R.string.date_of_birth),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                if (showDatePicker) {
                    val context = LocalContext.current
                    val calendar = Calendar.getInstance()
                    LaunchedEffect(Unit) {
                        DatePickerDialog(
                            context,
                            { _, selectedYear, selectedMonth, selectedDay ->
                                dateOfBirth = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                                showDatePicker = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                }

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    modifier = modifier,
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.End
                    ),
                    prefix = {
                        Text(
                            text = stringResource(R.string.height),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    suffix = {
                        Text(
                            text = stringResource(R.string.cm),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = modifier,
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.End
                    ),
                    prefix = {
                        Text(
                            text = stringResource(R.string.weight),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    suffix = {
                        Text(
                            text = stringResource(R.string.kg),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = modifier,
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        textAlign = TextAlign.End
                    ),
                    prefix = {
                        Text(
                            text = stringResource(R.string.email),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(onGo = { TODO() }),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubCard(
                    modifier = modifier
                ) {
                    Column(
                        modifier = Modifier.padding(standardPadding),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.calorie_intake_target),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleSmall
                            )

                            IconButton(
                                onClick = { TODO() } // Xử lý sự kiện khi người dùng nhấn icon Info
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = stringResource(R.string.calorie_intake_target),
                                )
                            }
                        }

                        val textWithIcon = buildAnnotatedString {
                            append(
                                stringResource(
                                    R.string.estimate_calories_needed_for_daily_activities
                                ) + " "
                            )
                            appendInlineContent("fireIcon", "[icon]")
                        }

                        val inlineContent = mapOf(
                            "fireIcon" to InlineTextContent(
                                placeholder = Placeholder(
                                    width = 16.sp,
                                    height = 16.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                )
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_fire),
                                    contentDescription = stringResource(
                                        R.string.calorie_intake_target
                                    ),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )

                        Text(
                            text = textWithIcon,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outline,
                            inlineContent = inlineContent,
                            style = MaterialTheme.typography.bodySmall
                        )

                        Row(
                            modifier = Modifier.padding(top = standardPadding),
                            horizontalArrangement = Arrangement.spacedBy(standardPadding),
                        ) {
                            MainCard(
                                modifier = Modifier.weight(1f)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(standardPadding),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val caloOfDaily by rememberSaveable {
                                        mutableStateOf(value = "__")
                                    } // Thay caloOfDaily từ database vào value

                                    Text(
                                        text = caloOfDaily,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = stringResource(R.string.calo_day),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            MainCard(
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(standardPadding),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    val caloOfWeekly by rememberSaveable {
                                        mutableStateOf(value = "__")
                                    } // Thay caloOfWeekly từ database vào value

                                    Text(
                                        text = caloOfWeekly,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = stringResource(R.string.calo_week),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.basal_metabolic_rate_bmr),
                                color = MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.labelSmall
                            )

                            TextButton(
                                onClick = { TODO() },
                            ) {
                                Text(
                                    text = stringResource(R.string.learn_more),
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                            val caloOfDailyBMR by rememberSaveable {
                                mutableStateOf(value = "__")
                            } // Thay caloOfDailyBMR từ database vào value

                            Text(
                                text = caloOfDailyBMR + " " +
                                        stringResource(R.string.calo_day),
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.total_energy_expenditure_tdee),
                                color = MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.labelSmall
                            )

                            TextButton(
                                onClick = { TODO() },
                            ) {
                                Text(
                                    text = stringResource(R.string.learn_more),
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                            val caloOfWeeklyBMR by rememberSaveable {
                                mutableStateOf(value = "__")
                            } // Thay caloOfDailyBMR từ database vào value

                            Text(
                                text = caloOfWeeklyBMR + " " +
                                        stringResource(R.string.calo_week),
                                modifier = Modifier.weight(1f),
                                color = MaterialTheme.colorScheme.outline,
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubCard(
                    modifier = modifier
                ) {
                    Column(
                        modifier = Modifier.padding(standardPadding),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.bmi_index),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleSmall
                            )

                            IconButton(
                                onClick = { TODO() } // Xử lý sự kiện khi người dùng nhấn icon Info
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = stringResource(R.string.bmi_index),
                                )
                            }
                        }

                        val bmiIndex by rememberSaveable {
                            mutableStateOf(value = "__")
                        } // Thay bmiIndex từ database vào value
                        val bmiCategory by rememberSaveable {
                            mutableStateOf(value = "__")
                        } // Thay bmiCategory từ database vào value

                        val textWithIcon = buildAnnotatedString {
                            append(
                                stringResource(R.string.your_bmi_is) + " " +
                                        bmiIndex + ", " +
                                        stringResource(R.string.you_are_classified_as) + " " +
                                        bmiCategory + "."
                            )
                            appendInlineContent("fireIcon", "[icon]")
                        }

                        val inlineContent = mapOf(
                            "fireIcon" to InlineTextContent(
                                placeholder = Placeholder(
                                    width = 16.sp,
                                    height = 16.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                )
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_fire),
                                    contentDescription = stringResource(
                                        R.string.bmi_index
                                    ),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )

                        Text(
                            text = textWithIcon,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outline,
                            inlineContent = inlineContent,
                            style = MaterialTheme.typography.bodySmall
                        )

                        BMIBar(
                            18.5f,
                            standardPadding
                        ) // Thay bmiIndex từ database vào parameter 1

                        MainCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = standardPadding)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(standardPadding),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(standardPadding),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Check Circle Icon",
                                        tint = MaterialTheme.colorScheme.inversePrimary,
                                    )

                                    val estimatedWeight by rememberSaveable {
                                        mutableStateOf(value = "__")
                                    } // Thay estimatedWeight từ database vào value

                                    Text(
                                        text = stringResource(R.string.your_best_weight_is_estimated_to_be) +
                                                estimatedWeight +
                                                stringResource(R.string.kg),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }
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


@Composable
fun BMIBar(
    bmi: Float,
    standardPadding: Dp
) {
    val minBmi = 15f
    val maxBmi = 35f

    val bmiSegments = listOf(
        18.5f to Color(0xFFAEEA00),
        24.9f to Color(0xFF00C853),
        29.9f to Color(0xFFFFAB00),
        maxBmi to Color(0xFFDD2C00)
    )

    val bmiPercentage = ((bmi - minBmi) / (maxBmi - minBmi)).coerceIn(0f, 1f)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(
                modifier =
                if (bmiPercentage != 0f) {
                    Modifier.weight(bmiPercentage)
                } else {
                    Modifier
                }
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.bmi_index),
                tint = Color.Red
            )

            Spacer(
                modifier =
                if (bmiPercentage != 1f) {
                    Modifier.weight(1f - bmiPercentage)
                } else {
                    Modifier
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = standardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var accumulatedWeight = 0f
            val weightCategories = listOf(
                stringResource(R.string.underweight),
                stringResource(R.string.healthy_weight),
                stringResource(R.string.overweight),
                stringResource(R.string.obese)
            )

            bmiSegments.forEachIndexed { index, (threshold, color) ->
                val segmentWidthWeight =
                    ((threshold - (if (index == 0) {
                        minBmi
                    } else {
                        bmiSegments[index - 1].first
                    })) / (maxBmi - minBmi))

                accumulatedWeight += segmentWidthWeight

                Surface(
                    modifier = Modifier
                        .weight(segmentWidthWeight),
                    shape = when (index) {
                        0 -> MaterialTheme.shapes.extraLarge.copy(
                            topEnd = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )

                        bmiSegments.size - 1 -> MaterialTheme.shapes.extraLarge.copy(
                            topStart = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
                        )

                        else -> RectangleShape
                    },
                    color = color
                ) {
                    Text(
                        text = weightCategories[index],
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = standardPadding / 2)
        ) {
            val bmiLabels = listOf("15", "18.5", "24.9", "29.9", "35")

            bmiLabels.forEachIndexed { index, label ->
                val weightModifier = if (index != 0) {
                    val currentBmi = label.toFloat()
                    val previousBmi = bmiLabels[index - 1].toFloat()
                    val weight = ((currentBmi - previousBmi) / (maxBmi - minBmi)).coerceIn(0f, 1f)
                    Modifier.weight(weight)
                } else {
                    Modifier
                }

                Text(
                    text = label,
                    modifier = weightModifier,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelSmall
                )
            }
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
private fun SettingPortraitScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        SettingScreen()
    }
}

@Preview(
    device = "id:pixel_9_pro_xl",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SettingPortraitScreenPreviewInLargePhone() {
    BioFitTheme {
        SettingScreen()
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
private fun SettingPortraitScreenPreviewInTablet() {
    BioFitTheme {
        SettingScreen()
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
private fun SettingLandscapeScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        SettingScreen()
    }
}

@Preview(
    device = "spec:parent=pixel_9_pro_xl,orientation=landscape",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SettingLandscapeScreenPreviewInLargePhone() {
    BioFitTheme {
        SettingScreen()
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
private fun SettingLandscapeScreenPreviewInTablet() {
    BioFitTheme {
        SettingScreen()
    }
}