package com.example.biofit.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biofit.R
import com.example.biofit.data.model.dto.ExerciseDTO
import com.example.biofit.data.model.dto.ExerciseDetailDTO
import com.example.biofit.data.utils.UserSharedPrefsHelper
import com.example.biofit.ui.components.getStandardPadding
import com.example.biofit.ui.theme.BioFitTheme
import com.example.biofit.view_model.ExerciseViewModel

class RegisterSuccessfullyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BioFitTheme {
                RegisterSuccessfullyScreen()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }
}

@Composable
fun RegisterSuccessfullyScreen() {
    val standardPadding = getStandardPadding().first

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        RegisterSuccessfullyContent(standardPadding)
    }
}

@Composable
fun RegisterSuccessfullyContent(
    standardPadding: Dp,
    exerciseViewModel: ExerciseViewModel = viewModel()
) {
    val context = LocalContext.current
    val userId = UserSharedPrefsHelper.getUserId(context)
    val baseExercise = listOf(
        ExerciseDTO(0L, userId, "Pull-ups", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 30f))),
        ExerciseDTO(0L, userId, "Squats", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 50f))),
        ExerciseDTO(0L, userId, "Lunges", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 45f))),
        ExerciseDTO(0L, userId, "Plank", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 25f))),
        ExerciseDTO(0L, userId, "Deadlifts", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 60f))),
        ExerciseDTO(0L, userId, "Bench Press", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 55f))),
        ExerciseDTO(0L, userId, "Overhead Press", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 50f))),
        ExerciseDTO(0L, userId, "Bicep Curls", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 35f))),
        ExerciseDTO(0L, userId, "Triceps Dips", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 40f))),
        ExerciseDTO(0L, userId, "Jump Rope", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 60f))),
        ExerciseDTO(0L, userId, "Burpees", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 70f))),
        ExerciseDTO(0L, userId, "Mountain Climbers", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 50f))),
        ExerciseDTO(0L, userId, "Sit-ups", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 40f))),
        ExerciseDTO(0L, userId, "Crunches", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 35f))),
        ExerciseDTO(0L, userId, "Russian Twists", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 40f))),
        ExerciseDTO(0L, userId, "Leg Raises", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 35f))),
        ExerciseDTO(0L, userId, "Jump Squats", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 55f))),
        ExerciseDTO(0L, userId, "Box Jumps", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 60f))),
        ExerciseDTO(0L, userId, "Calf Raises", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 30f))),
        ExerciseDTO(0L, userId, "Kettlebell Swings", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 55f))),
        ExerciseDTO(0L, userId, "Medicine Ball Slams", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 50f))),
        ExerciseDTO(0L, userId, "Dumbbell Rows", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 45f))),
        ExerciseDTO(0L, userId, "Lat Pulldown", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 50f))),
        ExerciseDTO(0L, userId, "Face Pulls", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 40f))),
        ExerciseDTO(0L, userId, "Side Plank", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 20f))),
        ExerciseDTO(0L, userId, "Flutter Kicks", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 30f))),
        ExerciseDTO(0L, userId, "Bicycle Crunches", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 35f))),
        ExerciseDTO(0L, userId, "Superman Exercise", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 25f))),
        ExerciseDTO(0L, userId, "Reverse Crunches", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 35f))),
        ExerciseDTO(0L, userId, "Glute Bridges", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 40f))),
        ExerciseDTO(0L, userId, "Hip Thrusts", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 8f, 50f))),
        ExerciseDTO(0L, userId, "Wall Sit", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 30f))),
        ExerciseDTO(0L, userId, "Farmers Walk", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 10f, 55f))),
        ExerciseDTO(0L, userId, "Jumping Jacks", listOf(ExerciseDetailDTO(0L, 0L, 0, 0, 5f, 50f)))
    )
    baseExercise.forEach { exercise ->
        exerciseViewModel.createExercise(exercise)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
                start = standardPadding,
                end = standardPadding,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.register_successfully),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(standardPadding))

        val context = LocalContext.current
        val activity = context as? Activity

        GetStartedButton(
            onCLick = {
                activity?.let {
                    val intent = Intent(it, InfoUserNameActivity::class.java)
                    it.startActivity(intent)
                    it.finish()
                }
            },
            standardPadding = standardPadding
        )
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
private fun RegisterSuccessfullyPortraitScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        RegisterSuccessfullyScreen()
    }
}

@Preview(
    device = "id:pixel_9_pro_xl",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun RegisterSuccessfullyPortraitScreenPreviewInLargePhone() {
    BioFitTheme {
        RegisterSuccessfullyScreen()
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
private fun RegisterSuccessfullyPortraitScreenPreviewInTablet() {
    BioFitTheme {
        RegisterSuccessfullyScreen()
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
private fun RegisterSuccessfullyLandscapeScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        RegisterSuccessfullyScreen()
    }
}

@Preview(
    device = "spec:parent=pixel_9_pro_xl,orientation=landscape",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun RegisterSuccessfullyLandscapeScreenPreviewInLargePhone() {
    BioFitTheme {
        RegisterSuccessfullyScreen()
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
private fun RegisterSuccessfullyLandscapeScreenPreviewInTablet() {
    BioFitTheme {
        RegisterSuccessfullyScreen()
    }
}