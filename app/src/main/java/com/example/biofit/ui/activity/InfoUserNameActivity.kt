package com.example.biofit.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biofit.R
import com.example.biofit.data.model.dto.UserDTO
import com.example.biofit.data.utils.UserSharedPrefsHelper
import com.example.biofit.ui.components.getStandardPadding
import com.example.biofit.ui.theme.BioFitTheme
import com.example.biofit.view_model.LoginViewModel
import com.example.biofit.view_model.UpdateUserViewModel

class InfoUserNameActivity : ComponentActivity() {
    private var userData: UserDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        userData = UserSharedPrefsHelper.getUserData(this)
        setContent {
            BioFitTheme {
                InfoUserNameScreen(userData ?: UserDTO.default())
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }
}

@Composable
fun InfoUserNameScreen(
    userData: UserDTO,
    updateUserViewModel: UpdateUserViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val standardPadding = getStandardPadding().first

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
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.secondary
                    ),
                    screenWidth = screenWidth,
                    screenHeight = screenHeight,
                    standardPadding = standardPadding
                )

                InfoUserNameContent(standardPadding = standardPadding)
            }

            val userId = userData.userId
            Log.d("UserId", "userId: $userId")
            NextButtonInfoScreen(
                onClick = {
                    Log.d("NextButtonInfoScreen", "updateUserViewModel: $updateUserViewModel")
                    updateUserViewModel.updateUser(context, userId, loginViewModel) {
                        Log.d("NextButtonInfoScreen", "đã lưu dữ liệu và chuyển qua trang")
                        val intent = Intent(context, InfoUserGenderActivity::class.java)
                        context.startActivity(intent)
                    }
                },
                standardPadding = standardPadding
            )
        }
    }
}

@Composable
fun BackgroundInfoScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Image(
            painter = if (isSystemInDarkTheme()) {
                painterResource(id = R.drawable.bg_info_screen_dark_mode)
            } else {
                painterResource(id = R.drawable.bg_info_screen)
            },
            contentDescription = "Information Screen Background",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun NextButtonInfoScreen(
    onClick: () -> Unit,
    standardPadding: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(standardPadding * 2),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(standardPadding * 4)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_next),
                contentDescription = "Next Button",
                modifier = Modifier.size(standardPadding * 4),
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
fun TopBarInfoScreen(
    onBackClick: () -> Unit,
    stepColors: List<androidx.compose.ui.graphics.Color>,
    screenWidth: Int,
    screenHeight: Int,
    standardPadding: Dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BackButton(
            onBackClick,
            standardPadding
        )
        Spacer(modifier = Modifier.width(standardPadding))
        ProgressIndicatorTopBarInfoScreen(
            stepColors,
            screenWidth,
            screenHeight,
            standardPadding
        )
    }
}

@Composable
fun BackButton(
    onBackClick: () -> Unit = {},
    standardPadding: Dp
) {
    IconButton(
        onClick = onBackClick,
        modifier = Modifier.size(standardPadding * 1.5f),
        enabled = true,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back Button",
            modifier = Modifier.size(standardPadding * 1.5f),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ProgressIndicatorTopBarInfoScreen(
    stepColors: List<androidx.compose.ui.graphics.Color>,
    screenWidth: Int,
    screenHeight: Int,
    standardPadding: Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = standardPadding * 2),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        stepColors.forEach { color ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(
                        ratio = 1f / (
                                standardPadding.value /
                                        if (screenWidth > screenHeight || screenWidth > 450) {
                                            250
                                        } else {
                                            100
                                        }
                                )
                    )
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(color = color)
            )
            Spacer(modifier = Modifier.width(standardPadding))
        }
    }
}

@Composable
fun InfoUserNameContent(
    standardPadding: Dp,
    viewModel: UpdateUserViewModel = viewModel(),
) {
    val fullNameState = remember { mutableStateOf(viewModel.fullName.value ?: "") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = stringResource(R.string.what_is_your_name),
                modifier = Modifier.padding(
                    top = standardPadding * 3,
                    bottom = standardPadding
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        }

        item {
            OutlinedTextField(
                value = fullNameState.value,
                onValueChange = {
                    fullNameState.value = it
                    viewModel.fullName.value = it
                    Log.d("InfoUserNameScreen", "Updated fullName: ${viewModel.fullName.value}")
                },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
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
private fun InfoUserNamePortraitScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        InfoUserNameScreen(UserDTO.default())
    }
}

@Preview(
    device = "id:pixel_9_pro_xl",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun InfoUserNamePortraitScreenPreviewInLargePhone() {
    BioFitTheme {
        InfoUserNameScreen(UserDTO.default())
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
private fun InfoUserNamePortraitScreenPreviewInTablet() {
    BioFitTheme {
        InfoUserNameScreen(UserDTO.default())
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
private fun InfoUserNameLandscapeScreenDarkModePreviewInSmallPhone() {
    BioFitTheme {
        InfoUserNameScreen(UserDTO.default())
    }
}

@Preview(
    device = "spec:parent=pixel_9_pro_xl,orientation=landscape",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun InfoUserNameLandscapeScreenPreviewInLargePhone() {
    BioFitTheme {
        InfoUserNameScreen(UserDTO.default())
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
private fun InfoUserNameLandscapeScreenPreviewInTablet() {
    BioFitTheme {
        InfoUserNameScreen(UserDTO.default())
    }
}