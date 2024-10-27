package com.example.movieexplorer.presentation.onboarding

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieexplorer.R
import com.example.movieexplorer.ui.theme.MovieExplorerTheme

@Composable
fun OnboardingScreen(onGetStartedClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 76.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_image),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .size(500.dp)
        )
        Text(
            buildAnnotatedString
            {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp
                    )

                ) {
                    append(stringResource(R.string.onboarding) + "\n")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontSize = 16.sp,

                        )
                ) {
                    append(stringResource(R.string.watch_everything_you_want_for_free))
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 96.dp)
        )
        Button(
            onClick = {
                onGetStartedClick()
                val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("hasSeenOnboarding", true)
                    apply()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 96.dp),
        ) {

            Text(
                text = stringResource(R.string.enter_now),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    MovieExplorerTheme {
        OnboardingScreen(onGetStartedClick = {})
    }

}
