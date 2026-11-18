package com.resolution.app.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
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
import com.resolution.app.data.model.ActivityCard
import com.resolution.app.data.model.LearningPost
import com.resolution.app.data.model.PostType
import com.resolution.app.data.repository.PostRepository
import com.resolution.app.ui.components.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDetailScreen(
    issueId: String,
    onBackClick: () -> Unit,
    repository: PostRepository = remember { PostRepository() },
    modifier: Modifier = Modifier
) {
    var post by remember { mutableStateOf<LearningPost?>(null) }
    var isLiked by remember { mutableStateOf(false) }
    var hasMarkedTried by remember { mutableStateOf(false) }
    var showReplyDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(issueId) {
        post = repository.getPostById(issueId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (post?.postType == PostType.QUESTION) "सवाल का विवरण" else "गतिविधि कार्ड") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share to WhatsApp */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        },
        bottomBar = {
            if (post?.postType == PostType.QUESTION) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 8.dp
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        PrimaryButton(
                            text = "अपना आइडिया या गतिविधि बताएं",
                            onClick = { showReplyDialog = true },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        post?.let { currentPost ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header: Type badge & Author
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (currentPost.postType == PostType.QUESTION) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (currentPost.postType == PostType.QUESTION) Icons.Default.HelpOutline else Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = if (currentPost.postType == PostType.QUESTION) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (currentPost.postType == PostType.QUESTION) "सवाल या चुनौती" else "गतिविधि कार्ड",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentPost.authorName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (currentPost.isExpertVerified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified Educator",
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                // Title & Target Class Chip
                item {
                    Text(
                        text = currentPost.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "लक्षित वर्ग: ${currentPost.targetClass}",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // If Question: Description & AI Best Study Plan
                if (currentPost.postType == PostType.QUESTION) {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "चुनौती का विवरण:",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentPost.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // AI Best Study Plan (श्रेष्ठ 7-दिवसीय अध्ययन योजना)
                    currentPost.bestStudyPlan?.let { plan ->
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFF3E5F5)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = null,
                                            tint = Color(0xFF7B1FA2),
                                            modifier = Modifier.size(22.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = plan.title,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color(0xFF4A148C)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = plan.summary,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF311B92)
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))
                                    Divider(color = Color(0xFFCE93D8).copy(alpha = 0.5f))
                                    Spacer(modifier = Modifier.height(10.dp))

                                    // Daily Micro-Steps
                                    plan.dailySteps.forEach { step ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = Color(0xFF7B1FA2),
                                                modifier = Modifier.size(24.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Text(
                                                        text = "${step.day}",
                                                        color = Color.White,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = step.title,
                                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                                    color = Color(0xFF1A237E)
                                                )
                                                Text(
                                                    text = "${step.activitySummary} (${step.timeMinutes} मिनट | सामग्री: ${step.materialNeeded})",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color(0xFF37474F)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = plan.disclaimer,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF6A1B9A)
                                    )
                                }
                            }
                        }
                    }

                    // Community Activity Responses Section
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "समुदाय के आइडिया व गतिविधियां (${currentPost.repliesCount})",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    // Sample community activity card response
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(1.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "रोटी और बिस्किट बंटवारा खेल",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Text(
                                        text = "सुनीता जी (शिक्षिका)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "सामग्री: गोल कागज या बिस्किट (₹0)",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "विधि: बच्चों को 2 बिस्किट देकर 4 बच्चों में बराबर बांटने को कहें। बच्चा स्वयं तोड़कर आधा (1/2) समझ जाता है।",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFE8F5E9),
                                    modifier = Modifier.clickable { hasMarkedTried = true }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = Color(0xFF2E7D32),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (hasMarkedTried) "काम कर गया (प्रमाणित) +1" else "काम कर गया (28)",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color(0xFF1B5E20),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Full Activity Card Display
                    currentPost.activityCard?.let { card ->
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Time & Target Class
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Timer,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "समय: ${card.timeNeeded}",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Text(
                                            text = "कक्षा: ${card.targetClass}",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "📦 आवश्यक सामग्री (कम खर्च / TLM):",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = card.materialsNeeded,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = "📝 चरणबद्ध तरीका:",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = card.stepByStep,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = "🎯 सीखने का प्रतिफल:",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = card.learningOutcome,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    card.adaptations?.let { adapt ->
                                        Spacer(modifier = Modifier.height(14.dp))
                                        Text(
                                            text = "💡 अन्य सुझाव या बदलाव:",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = adapt,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    // Prominent "मैंने आजमाया, काम कर गया!" Button
                                    Button(
                                        onClick = {
                                            if (!hasMarkedTried) {
                                                hasMarkedTried = true
                                                coroutineScope.launch {
                                                    repository.markTriedAndWorked(currentPost.postId)
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (hasMarkedTried) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (hasMarkedTried) "सफल! आपने इसे प्रमाणित किया" else "मैंने आजमाया, काम कर गया! (${currentPost.triedCount})",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Reply Dialog for Question
        if (showReplyDialog) {
            var replyMethod by remember { mutableStateOf("") }
            AlertDialog(
                onDismissRequest = { showReplyDialog = false },
                title = { Text("अपनी गतिविधि या आइडिया साझा करें") },
                text = {
                    Column {
                        Text(
                            text = "इस सवाल के समाधान के लिए अपनी गतिविधि का तरीका लिखें:",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = replyMethod,
                            onValueChange = { replyMethod = it },
                            placeholder = { Text("जैसे: कागज मोड़कर भिन्न समझाएं...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { showReplyDialog = false }) {
                        Text("दर्ज करें")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showReplyDialog = false }) {
                        Text("रद्द करें")
                    }
                }
            )
        }
    }
}
