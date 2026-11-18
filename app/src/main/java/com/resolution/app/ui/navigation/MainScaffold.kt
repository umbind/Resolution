package com.resolution.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.resolution.app.R
import com.resolution.app.ui.screens.feed.FeedScreen
import com.resolution.app.ui.screens.impact.ImpactScreen
import com.resolution.app.ui.screens.post.PostIssueScreen
import com.resolution.app.ui.screens.profile.ProfileScreen

enum class MainDestination(
    val titleRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    FEED(R.string.nav_feed, Icons.Filled.Home, Icons.Outlined.Home),
    POST(R.string.nav_post, Icons.Filled.AddCircle, Icons.Outlined.AddCircleOutline),
    IMPACT(R.string.nav_impact, Icons.Filled.EmojiEvents, Icons.Outlined.EmojiEvents),
    PROFILE(R.string.nav_profile, Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun MainScaffold(
    onSignOut: () -> Unit,
    onLanguageChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentDestination by remember { mutableStateOf(MainDestination.FEED) }
    var selectedIssueForDetail by remember { mutableStateOf<com.resolution.app.data.model.Issue?>(null) }

    Scaffold(
        bottomBar = {
            if (selectedIssueForDetail == null) {
                NavigationBar {
                    MainDestination.values().forEach { destination ->
                        val isSelected = currentDestination == destination
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { 
                                selectedIssueForDetail = null
                                currentDestination = destination 
                            },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                    contentDescription = stringResource(id = destination.titleRes)
                                )
                            },
                            label = { Text(text = stringResource(id = destination.titleRes)) }
                        )
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (selectedIssueForDetail != null) {
            com.resolution.app.ui.screens.detail.IssueDetailScreen(
                issue = selectedIssueForDetail!!,
                onBack = { selectedIssueForDetail = null },
                onShareWhatsApp = { /* Share via WhatsApp */ },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            when (currentDestination) {
                MainDestination.FEED -> {
                    FeedScreen(
                        onIssueClick = { issue -> selectedIssueForDetail = issue },
                        onPostClick = { currentDestination = MainDestination.POST },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            MainDestination.POST -> {
                PostIssueScreen(
                    onSubmitSuccess = { currentDestination = MainDestination.FEED },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            MainDestination.IMPACT -> {
                ImpactScreen(modifier = Modifier.padding(innerPadding))
            }
            MainDestination.PROFILE -> {
                ProfileScreen(
                    onSignOut = onSignOut,
                    onLanguageChange = onLanguageChange,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
