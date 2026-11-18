package com.resolution.app.ui.screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resolution.app.R
import com.resolution.app.data.model.LearningPost
import com.resolution.app.data.model.PostType
import com.resolution.app.data.repository.PostRepository
import com.resolution.app.ui.components.PostCard
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onPostClick: (String) -> Unit,
    onCreatePostClick: () -> Unit,
    repository: PostRepository = remember { PostRepository() },
    modifier: Modifier = Modifier
) {
    var selectedPostTypeIndex by remember { mutableStateOf(0) } // 0: All, 1: Questions, 2: Activities
    var selectedCategory by remember { mutableStateOf("cat_all") }
    var posts by remember { mutableStateOf(emptyList<LearningPost>()) }
    val coroutineScope = rememberCoroutineScope()

    val currentPostType = when (selectedPostTypeIndex) {
        1 -> PostType.QUESTION
        2 -> PostType.ACTIVITY
        else -> null
    }

    // Reactively observe repository stream based on selected filters
    LaunchedEffect(currentPostType, selectedCategory) {
        repository.getPostsStream(currentPostType, selectedCategory).collectLatest { updatedList ->
            posts = updatedList
        }
    }

    val categories = listOf(
        "cat_all" to "सभी विषय",
        "cat_math_science" to "गणित व विज्ञान",
        "cat_reading_writing" to "पढ़ना और लिखना",
        "cat_english_lang" to "अंग्रेजी व भाषा",
        "cat_exam_prep" to "परीक्षा तैयारी व तनाव",
        "cat_low_cost_tlm" to "कम खर्च सामग्री / TLM",
        "cat_classroom_teaching" to "कक्षा में तरीके",
        "cat_career_skills" to "करियर और हुनर",
        "cat_parent_support" to "अभिभावक मदद"
    )

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // App Brand Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = stringResource(R.string.app_tagline),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Post Type Tabs (All, Questions, Activities)
                    TabRow(
                        selectedTabIndex = selectedPostTypeIndex,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Tab(
                            selected = selectedPostTypeIndex == 0,
                            onClick = { selectedPostTypeIndex = 0 },
                            text = { Text("सभी", fontWeight = FontWeight.SemiBold) }
                        )
                        Tab(
                            selected = selectedPostTypeIndex == 1,
                            onClick = { selectedPostTypeIndex = 1 },
                            text = { Text("सवाल या चुनौती", fontWeight = FontWeight.SemiBold) }
                        )
                        Tab(
                            selected = selectedPostTypeIndex == 2,
                            onClick = { selectedPostTypeIndex = 2 },
                            text = { Text("आइडिया / गतिविधि", fontWeight = FontWeight.SemiBold) }
                        )
                    }

                    // Horizontal Scrollable Category Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { (catKey, catLabel) ->
                            FilterChip(
                                selected = selectedCategory == catKey,
                                onClick = { selectedCategory = catKey },
                                label = { Text(catLabel, style = MaterialTheme.typography.labelMedium) },
                                shape = RoundedCornerShape(16.dp)
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreatePostClick,
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                text = { Text("नया विचार रखें", fontWeight = FontWeight.Bold) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (posts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "इस विषय पर कोई पोस्ट नहीं है",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "पहला सवाल पूछें या गतिविधि साझा करें!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            } else {
                items(posts, key = { it.postId }) { post ->
                    PostCard(
                        post = post,
                        onClick = { onPostClick(post.postId) },
                        onLikeClick = {
                            coroutineScope.launch { repository.toggleSupport(post.postId) }
                        },
                        onTriedClick = {
                            coroutineScope.launch { repository.markTriedAndWorked(post.postId) }
                        }
                    )
                }
            }
        }
    }
}
