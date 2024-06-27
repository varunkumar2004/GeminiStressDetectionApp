package com.varunkumar.geminiapi.presentation.features.profile_feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.varunkumar.geminiapi.presentation.Routes
import com.varunkumar.geminiapi.presentation.features.sign_in_feature.UserData
import com.varunkumar.geminiapi.presentation.screens.ProfileHeader
import com.varunkumar.geminiapi.presentation.screens.ProfileStats
import com.varunkumar.geminiapi.ui.theme.customTopAppBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                ),
                title = { Text(text = "Summary", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onSignOut) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                    }
                }
            )
        }
    ) {
        val fModifier = Modifier.fillMaxWidth()

        Row(
            modifier = fModifier
                .padding(it)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (userData?.profilePictureUrl != null) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop
                )
            }

            if (userData?.username != null) {
                Text(text = userData.username)
            }

//            ProfileHeader(modifier = fModifier) {
//                navController.navigate(Routes.Login.route)
//            }
//
//            ProfileStats(modifier = fModifier, metrics = metrics)
        }
    }
}