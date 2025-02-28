package com.example.biofit.view.sub_components

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.biofit.R
import com.example.biofit.view.activity.AIChatbotActivity
import com.example.biofit.view.activity.AddActivity
import com.example.biofit.view.activity.ExerciseActivity
import com.example.biofit.view.activity.UpdateWeightActivity
import com.example.biofit.view.activity.getStandardPadding
import com.example.biofit.view.ui_theme.BioFitTheme

@Composable
fun ActionPopup(
    onDismissPopup: () -> Unit,
    standardPadding: Dp
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val itemPopupList = listOf(
        Pair(R.drawable.ic_exercise_2, R.string.exercise),
        Pair(R.drawable.ic_weight, R.string.weight),
        Pair(R.drawable.ic_drink_water, R.string.drinking_water),
        Pair(R.drawable.ic_chat_bot, R.string.ai_assistant_bionix)
    )
    val sessionPopupList = listOf(
        Pair(R.drawable.ic_morning_2, R.string.morning),
        Pair(R.drawable.ic_afternoon_2, R.string.afternoon),
        Pair(R.drawable.ic_evening_2, R.string.evening),
        Pair(R.drawable.ic_snack_2, R.string.snack)
    )

    var drinkWaterPopupState by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(standardPadding / 2),
            verticalArrangement = Arrangement.spacedBy(standardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!drinkWaterPopupState) {
                Row(
                    modifier = Modifier
                ) {
                    itemPopupList.forEach { (icon, title) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(MaterialTheme.shapes.large)
                                .clickable {
                                    when (title) {
                                        R.string.exercise ->
                                            activity?.let {
                                                val intent =
                                                    Intent(it, ExerciseActivity::class.java)
                                                it.startActivity(intent)
                                            }

                                        R.string.weight ->
                                            activity?.let {
                                                val intent =
                                                    Intent(it, UpdateWeightActivity::class.java)
                                                it.startActivity(intent)
                                            }

                                        R.string.drinking_water ->
                                            drinkWaterPopupState = true

                                        R.string.ai_assistant_bionix ->
                                            activity?.let {
                                                val intent =
                                                    Intent(it, AIChatbotActivity::class.java)
                                                it.startActivity(intent)
                                            }
                                    }

                                    if (title != R.string.drinking_water) {
                                        onDismissPopup()
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(standardPadding / 2)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(
                                        brush = if (title == R.string.ai_assistant_bionix) {
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    Color(0xFFAEEA00),
                                                    MaterialTheme.colorScheme.primary
                                                ),
                                                start = Offset(0f, 0f),
                                                end = Offset(
                                                    Float.POSITIVE_INFINITY,
                                                    Float.POSITIVE_INFINITY
                                                )
                                            )
                                        } else {
                                            SolidColor(
                                                MaterialTheme.colorScheme.secondary.copy(
                                                    alpha = 0.5f
                                                )
                                            )
                                        }
                                    )
                                    .background(
                                        color = if (title == R.string.ai_assistant_bionix) {
                                            MaterialTheme.colorScheme.background.copy(alpha = 0.25f)
                                        } else {
                                            Color.Transparent
                                        }
                                    ),
                                shape = MaterialTheme.shapes.large,
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(icon),
                                        contentDescription = stringResource(R.string.activity),
                                        modifier = Modifier
                                            .padding(standardPadding)
                                            .size(standardPadding * 2),
                                        tint = MaterialTheme.colorScheme.background
                                    )
                                }
                            }

                            if (title != R.string.ai_assistant_bionix) {
                                Text(
                                    text = stringResource(title),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = standardPadding),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = standardPadding),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AnimatedGradientText(
                                        repeatMode = RepeatMode.Reverse,
                                        highlightColor = Color(0xFFAEEA00),
                                        baseColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        text = stringResource(title),
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Row {
                    sessionPopupList.forEach { (icon, title) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(MaterialTheme.shapes.large)
                                .clickable {
                                    when (title) {
                                        R.string.morning ->
                                            activity?.let {
                                                val intent = Intent(it, AddActivity::class.java)
                                                intent.putExtra("SESSION_TITLE", R.string.morning)
                                                it.startActivity(intent)
                                            }

                                        R.string.afternoon ->
                                            activity?.let {
                                                val intent = Intent(it, AddActivity::class.java)
                                                intent.putExtra("SESSION_TITLE", R.string.afternoon)
                                                it.startActivity(intent)
                                            }

                                        R.string.evening ->
                                            activity?.let {
                                                val intent = Intent(it, AddActivity::class.java)
                                                intent.putExtra("SESSION_TITLE", R.string.evening)
                                                it.startActivity(intent)
                                            }

                                        R.string.snack ->
                                            activity?.let {
                                                val intent = Intent(it, AddActivity::class.java)
                                                intent.putExtra("SESSION_TITLE", R.string.snack)
                                                it.startActivity(intent)
                                            }
                                    }

                                    onDismissPopup()
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(standardPadding / 2),
                                shape = MaterialTheme.shapes.large,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondary.copy(
                                        alpha = 0.5f
                                    )
                                )
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(icon),
                                        contentDescription = stringResource(R.string.activity),
                                        modifier = Modifier
                                            .padding(standardPadding)
                                            .size(standardPadding * 2),
                                        tint = MaterialTheme.colorScheme.background
                                    )
                                }
                            }

                            Text(
                                text = stringResource(title),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = standardPadding),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            } else {
                var currentWater by rememberSaveable { mutableStateOf("0.5") }

                Text(
                    text = stringResource(R.string.drinking_water),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(standardPadding / 2),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val waterQuantity = currentWater.toFloatOrNull() ?: 0f
                            if (waterQuantity > 0) {
                                currentWater = (waterQuantity - 1).toString()
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_less_update_weight),
                            contentDescription = stringResource(R.string.drinking_water),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    OutlinedTextField(
                        value = currentWater,
                        onValueChange = { currentWater = it },
                        modifier = Modifier
                            .widthIn(min = 10.dp, max = standardPadding * 10)
                            .width(IntrinsicSize.Min),
                        textStyle = MaterialTheme.typography.displaySmall,
                        suffix = {
                            Text(
                                text = "L",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.displaySmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(
                            onDone = { TODO() },
                            onGo = { TODO() },
                            onNext = { TODO() },
                            onPrevious = { TODO() },
                            onSearch = { TODO() },
                            onSend = { TODO() }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )

                    IconButton(
                        onClick = {
                            val waterQuantity = currentWater.toFloatOrNull() ?: 0f
                            currentWater = (waterQuantity + 1).toString()
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_update_weight),
                            contentDescription = stringResource(R.string.drinking_water),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                Button(
                    onClick = {
                        // xử lý sự kiện lưu nước đã uống vào database
                        onDismissPopup()
                    },
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_UNDEFINED)
@Composable
private fun ActionPopupDarkModePreview() {
    BioFitTheme {
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            enter = slideInVertically { it } + fadeIn() + expandVertically(),
            exit = slideOutVertically { it } + fadeOut() + shrinkVertically()
        ) {
            ActionPopup(
                onDismissPopup = {  },
                standardPadding = getStandardPadding().first
            )
        }
    }
}

@Preview
@Composable
private fun ActionPopupPreview() {
    BioFitTheme {
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            enter = slideInVertically { it } + fadeIn() + expandVertically(),
            exit = slideOutVertically { it } + fadeOut() + shrinkVertically()
        ) {
            ActionPopup(
                onDismissPopup = {  },
                standardPadding = getStandardPadding().first
            )
        }
    }
}