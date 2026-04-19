package com.terabyte.jwtnotes.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.terabyte.jwtnotes.R
import com.terabyte.jwtnotes.ui.theme.JwtNotesTheme
import com.terabyte.jwtnotes.viewmodel.TokenExpiredViewModel

@Composable
fun TokenExpiredScreen(
    viewModel: TokenExpiredViewModel = hiltViewModel(),
    onLoginAgain: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.session_time_over),
            fontSize = 20.sp
        )
        Text(
            text = stringResource(R.string.you_need_to_log_in_again),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        Button(
            onClick = onLoginAgain,
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.log_in_again))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TokenExpiredScreenPreview() {
    JwtNotesTheme {
        TokenExpiredScreen { }
    }
}

