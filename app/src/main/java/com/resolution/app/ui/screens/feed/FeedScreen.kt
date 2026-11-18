package com.resolution.app.ui.screens.feed

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resolution.app.R
import com.resolution.app.data.model.Issue
import com.resolution.app.ui.components.IssueCard

data class CategoryFilter(val id: String, val titleRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onIssueClick: (Issue) -> Unit,
    onPostClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("all") }

    val categories = listOf(
        CategoryFilter("all", R.string.cat_all),
        CategoryFilter("infrastructure", R.string.cat_infrastructure),
        CategoryFilter("teaching", R.string.cat_teaching),
        CategoryFilter("mid_day_meal", R.string.cat_mid_day_meal),
        CategoryFilter("materials", R.string.cat_materials),
        CategoryFilter("safety", R.string.cat_safety)
    )

    // Sample initial seed issues for UP education
    val sampleIssues = remember {
        listOf(
            Issue(
                issueId = "iss_101",
                title = "प्राथमिक विद्यालय में पीने का पानी नहीं है, हैंडपंप 2 महीने से सूखा है",
                description = "सीतापुर के महमूदाबाद ब्लॉक में प्राथमिक विद्यालय में बच्चे घर से पानी लाते हैं या प्यासे रहते हैं।",
                districtId = "सीतापुर",
                blockId = "महमूदाबाद",
                category = "infrastructure",
                status = "solutions_added",
                supportCount = 24,
                solutionCount = 3
            ),
            Issue(
                issueId = "iss_102",
                title = "बालिका शौचालय का दरवाजा टूटा हुआ है",
                description = "कक्षा 6 से 8 की छात्राएं स्कूल आने में संकोच करती हैं। तत्काल मरम्मत की आवश्यकता है।",
                districtId = "बाराबंकी",
                blockId = "फतेहपुर",
                category = "safety",
                status = "open",
                supportCount = 42,
                solutionCount = 1
            ),
            Issue(
                issueId = "iss_103",
                title = "120 बच्चों पर केवल 1 शिक्षक हैं, गणित और विज्ञान की कक्षाएं बाधित",
                description = "उच्च प्राथमिक विद्यालय में शिक्षक की कमी के कारण सभी कक्षाओं को एक साथ बैठाना पड़ता है।",
                districtId = "हरदोई",
                blockId = "संडीला",
                category = "teaching",
                status = "open",
                supportCount = 57,
                solutionCount = 4
            )
        )
    }

    val filteredIssues = if (selectedCategory == "all") {
        sampleIssues
    } else {
        sampleIssues.filter { it.category == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = stringResource(id = R.string.app_tagline),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onPostClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = stringResource(id = R.string.prompt_voice_input)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Category Filter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat.id,
                        onClick = { selectedCategory = cat.id },
                        label = { Text(text = stringResource(id = cat.titleRes)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Issues Feed List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredIssues) { issue ->
                    IssueCard(
                        issue = issue,
                        onClick = { onIssueClick(issue) },
                        onSupportToggle = { /* Handled in Repository */ },
                        onShareWhatsApp = { /* Triggers WhatsApp Intent */ }
                    )
                }
            }
        }
    }
}
