package com.example.biofit.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.biofit.data.model.dto.UserDTO
import com.example.biofit.data.utils.UserSharedPrefsHelper
import com.example.biofit.ui.components.getStandardPadding
import com.example.biofit.ui.theme.BioFitTheme
import com.example.biofit.view_model.LoginViewModel
import com.example.biofit.view_model.UpdateUserViewModel

class InfoUserHeightAndWeightActivity : ComponentActivity() {
    private var userData: UserDTO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        userData = UserSharedPrefsHelper.getUserData(this)
        setContent {
            BioFitTheme {
                InfoUserHeightAndWeightScreen(userData ?: UserDTO.default())
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }
}

@Composable
fun InfoUserHeightAndWeightScreen(
    userData: UserDTO,
    viewModel: UpdateUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val standardPadding = getStandardPadding().first
    val modifier = getStandardPadding().second

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box {
            BackgroundInfoScreen()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding(),
                        start = standardPadding,
                        end = standardPadding,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarInfoScreen(
                    onBackClick = { activity?.finish() },
                    stepColors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    ),
                    screenWidth,
                    screenHeight,
                    standardPadding
                )
                InfoUserHeightAndWeightContent(
                    standardPadding,
                    modifier
                )
            }

            val userId = userData.userId
            NextButtonInfoScreen(
                onClick = {
                    viewModel.updateUser(context, userId, loginViewModel) {
                        val intent = Intent(context, InfoUserTargetActivity::class.java)
                        context.startActivity(intent)
                    }
                },
                standardPadding = standardPadding
            )
        }
    }
}

@Composable
fun InfoUserHeightAndWeightContent(
    standardPadding: Dp,
    modifier: Modifier,
    viewModel: UpdateUserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    // Lấy dữ liệu từ ViewModel
    val height = viewModel.height.value?.toString() ?: ""
    val weight = viewModel.weight.value?.toString() ?: ""
    val focusManager = LocalFocusManager.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = stringResource(R.string.what_is_your_height_and_weight),
                modifier = Modifier.padding(top = standardPadding * 3),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        }

        item {
            Text(
                text = stringResource(R.string.description_height_and_weight),
                modifier = Modifier.padding(bottom = standardPadding),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }

        item {
            OutlinedTextField(
                value = height,
                onValueChange = { viewModel.height.value = it.toFloatOrNull() },
                modifier = modifier,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.figure_stand),
                        contentDescription = stringResource(R.string.height),
                        modifier = Modifier.size(standardPadding * 1.5f)
                    )
                },
                prefix = { Text(text = stringResource(R.string.height)) },
                suffix = { Text(text = stringResource(R.string.cm)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { viewModel.weight.value = it.toFloatOrNull() },
                modifier = modifier.padding(top = standardPadding),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.scalemass),
                        contentDescription = stringResource(R.string.weight),
                        modifier = Modifier.size(standardPadding * 1.5f)
                    )
                },
                prefix = { Text(text = stringResource(R.string.weight)) },
                suffix = { Text(text = stringResource(R.string.kg)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(onGo = { /*TODO*/ }),
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )
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
private fun InfoUserHeightAndWeightPortraitScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        InfoUserHeightAndWeightScreen(UserDTO.default())
    }
}

@Preview(
    device = "id:pixel_9_pro_xl",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun InfoUserHeightAndWeightPortraitScreenPreviewInLargePhone() {
    BioFitTheme {
        InfoUserHeightAndWeightScreen(UserDTO.default())
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
private fun InfoUserHeightAndWeightPortraitScreenPreviewInTablet() {
    BioFitTheme {
        InfoUserHeightAndWeightScreen(UserDTO.default())
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
private fun InfoUserHeightAndWeightLandscapeScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        InfoUserHeightAndWeightScreen(UserDTO.default())
    }
}

@Preview(
    device = "spec:parent=pixel_9_pro_xl,orientation=landscape",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun InfoUserHeightAndWeightLandscapeScreenPreviewInLargePhone() {
    BioFitTheme {
        InfoUserHeightAndWeightScreen(UserDTO.default())
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
private fun InfoUserHeightAndWeightLandscapeScreenPreviewInTablet() {
    BioFitTheme {
        InfoUserHeightAndWeightScreen(UserDTO.default())
    }
}