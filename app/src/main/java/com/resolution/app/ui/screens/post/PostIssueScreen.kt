package com.resolution.app.ui.screens.post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun PostIssueScreen(
    onSubmitSuccess: () -> Unit,
    repository: PostRepository = remember { PostRepository() },
    modifier: Modifier = Modifier
) {
    var postType by remember { mutableStateOf(PostType.QUESTION) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("cat_math_science") }
    var targetClass by remember { mutableStateOf("कक्षा 3-5") }

    // Activity Card specific fields
    var timeNeeded by remember { mutableStateOf("15 मिनट") }
    var materialsNeeded by remember { mutableStateOf("") }
    var stepByStep by remember { mutableStateOf("") }
    var learningOutcome by remember { mutableStateOf("") }
    var adaptations by remember { mutableStateOf("") }

    var isRecording by remember { mutableStateOf(false) }
    var showCoachDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf(
        "cat_math_science" to "गणित और विज्ञान गतिविधियाँ",
        "cat_reading_writing" to "पढ़ना और लिखना",
        "cat_english_lang" to "अंग्रेजी व भाषा सीखें",
        "cat_exam_prep" to "परीक्षा तैयारी व तनाव मुक्ति",
        "cat_low_cost_tlm" to "कम खर्च सामग्री / TLM",
        "cat_classroom_teaching" to "कक्षा में पढ़ाने के तरीके",
        "cat_career_skills" to "करियर और हुनर",
        "cat_parent_support" to "घर पर बच्चों की पढ़ाई में मदद"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_create_post), fontWeight = FontWeight.Bold) }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Anti-Complaint & Safety Banner
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = stringResource(R.string.banner_scope_notice),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Post Type Segmented Control (सवाल vs गतिविधि कार्ड)
            Text(
                text = stringResource(R.string.choose_post_type),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (postType == PostType.QUESTION) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { postType = PostType.QUESTION }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = null,
                            tint = if (postType == PostType.QUESTION) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "सवाल पूछें",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (postType == PostType.QUESTION) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (postType == PostType.ACTIVITY) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { postType = PostType.ACTIVITY }
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = if (postType == PostType.ACTIVITY) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "गतिविधि कार्ड",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (postType == PostType.ACTIVITY) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Voice Recording Action
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(
                        if (isRecording) MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                    )
                    .clickable { isRecording = !isRecording },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Input",
                    tint = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isRecording) stringResource(R.string.listening_voice) else stringResource(R.string.prompt_voice_input),
                style = MaterialTheme.typography.bodySmall,
                color = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Post Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("शीर्षक (Title)") },
                placeholder = {
                    Text(if (postType == PostType.QUESTION) "जैसे: भिन्न कैसे समझाएं?" else "जैसे: माचिस की तीलियों से जोड़-घटाना")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Target Class & Category Pickers
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = targetClass,
                    onValueChange = { targetClass = it },
                    label = { Text("कक्षा / वर्ग") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = if (postType == PostType.ACTIVITY) timeNeeded else "5-10 मिनट",
                    onValueChange = { timeNeeded = it },
                    label = { Text("समय") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (postType == PostType.QUESTION) {
                // Question Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("चुनौती का विवरण") },
                    placeholder = { Text("बच्चे को कहाँ परेशानी हो रही है? विस्तार से लिखें...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            } else {
                // Structured Activity Card Fields
                OutlinedTextField(
                    value = materialsNeeded,
                    onValueChange = { materialsNeeded = it },
                    label = { Text("सामग्री (कम खर्च / TLM)") },
                    placeholder = { Text("उदा: बोतल के ढक्कन, माचिस की तीलियां, कागज (खर्च: ₹0)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = stepByStep,
                    onValueChange = { stepByStep = it },
                    label = { Text("चरणबद्ध तरीका (स्टेप्स)") },
                    placeholder = { Text("1. पहले क्या करेंगे?\n2. बच्चे क्या करेंगे?\n3. निष्कर्ष...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = learningOutcome,
                    onValueChange = { learningOutcome = it },
                    label = { Text("सीखने का प्रतिफल (Learning Outcome)") },
                    placeholder = { Text("इस गतिविधि से बच्चा क्या सीखेगा?") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // AI Coach Pre-submission check button
                OutlinedButton(
                    onClick = { showCoachDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.btn_ai_coach), fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Button
            PrimaryButton(
                text = "चौपाल पर साझा करें",
                onClick = {
                    if (title.isNotBlank()) {
                        coroutineScope.launch {
                            val newPost = LearningPost(
                                authorName = "आप (शिक्षक/अभिभावक)",
                                authorRole = "role_teacher",
                                postType = postType,
                                title = title,
                                description = if (postType == PostType.QUESTION) description else stepByStep,
                                category = selectedCategory,
                                targetClass = targetClass,
                                activityCard = if (postType == PostType.ACTIVITY) {
                                    ActivityCard(
                                        targetClass = targetClass,
                                        timeNeeded = timeNeeded,
                                        materialsNeeded = materialsNeeded,
                                        stepByStep = stepByStep,
                                        learningOutcome = learningOutcome,
                                        adaptations = adaptations
                                    )
                                } else null
                            )
                            repository.addPost(newPost)
                            onSubmitSuccess()
                        }
                    }
                },
                enabled = title.isNotBlank() && (if (postType == PostType.ACTIVITY) stepByStep.isNotBlank() else true),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))
        }

        // AI Activity Coach Dialog
        if (showCoachDialog) {
            AlertDialog(
                onDismissRequest = { showCoachDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI गतिविधि कोच सुझाव")
                    }
                },
                text = {
                    Column {
                        Text(
                            text = "✅ सुरक्षा: गतिविधि में किसी नुकीली वस्तु का प्रयोग नहीं है।",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "💡 सुझाव: चरण 2 में बच्चों को 4-4 के छोटे समूहों में बांटने से गतिविधि और रोचक हो जाएगी।",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "🎯 प्रतिफल: चुनी गई कक्षा (${targetClass}) के लिए उपयुक्त है।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showCoachDialog = false }) {
                        Text("समझ गया")
                    }
                }
            )
        }
    }
}
