package com.resolution.app.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.resolution.app.R
import com.resolution.app.data.model.Issue
import com.resolution.app.data.model.Solution
import com.resolution.app.ui.components.PrimaryButton
import com.resolution.app.ui.components.StatusBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDetailScreen(
    issue: Issue,
    onBack: () -> Unit,
    onShareWhatsApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSupported by remember { mutableStateOf(false) }
    var currentSupportCount by remember { mutableIntStateOf(issue.supportCount) }
    var showAddSolutionSheet by remember { mutableStateOf(false) }

    // Sample initial solutions
    var solutions by remember {
        mutableStateOf(
            listOf(
                Solution(
                    solutionId = "sol_1",
                    issueId = issue.issueId,
                    authorAlias = "सुनीता देवी (शिक्षक)",
                    isExpertVerifiedAuthor = true,
                    whatWillBeDone = "स्कूल प्रबंधन समिति (SMC) की आपात बैठक बुलाकर ग्राम पंचायत से नया हैंडपंप वाशर और पाइप डलवाया जाएगा।",
                    whoWillDoIt = "SMC अध्यक्ष + ग्राम प्रधान",
                    estimatedCostTier = "under_1k",
                    estimatedTimeTier = "days",
                    keyRisks = "पंचायत सचिव की अनुपलब्धता से 1-2 दिन देरी हो सकती है।",
                    workedExample = "पिछले वर्ष प्राथमिक विद्यालय हरदोई में इसी विधि से 24 घंटे में समस्या हल हुई थी।",
                    upvoteCount = 18,
                    triedAndWorkedCount = 3
                )
            )
        )
    }

    // Wizard Form Fields
    var whatWillBeDone by remember { mutableStateOf("") }
    var whoWillDoIt by remember { mutableStateOf("") }
    var costTier by remember { mutableStateOf("under_1k") }
    var timeTier by remember { mutableStateOf("days") }
    var keyRisks by remember { mutableStateOf("") }
    var workedExample by remember { mutableStateOf("") }
    var aiCoachFeedback by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.nav_feed)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onShareWhatsApp) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(id = R.string.action_share_whatsapp),
                            tint = Color(0xFF25D366)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                PrimaryButton(
                    text = stringResource(id = R.string.title_add_solution),
                    onClick = { showAddSolutionSheet = true }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Issue Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            StatusBadge(status = issue.status)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${issue.blockId}, ${issue.districtId}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = issue.title,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = issue.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Support button
                        OutlinedButton(
                            onClick = {
                                isSupported = !isSupported
                                currentSupportCount += if (isSupported) 1 else -1
                            },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Icon(
                                imageVector = if (isSupported) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = null,
                                tint = if (isSupported) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$currentSupportCount " + stringResource(id = R.string.action_support),
                                color = if (isSupported) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // AI 3-Bullet Summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = stringResource(id = R.string.badge_ai_generated),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• मुख्य समस्या: 2 महीने से पेयजल बाधित।\n• प्रभावित: प्राथमिक स्तर के 120 छात्र।\n• तत्काल जरूरत: SMC और ग्राम पंचायत का सहयोग।",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // Section Header: Solutions
            item {
                Text(
                    text = "${solutions.size} प्रस्तावित समाधान",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Solutions List
            items(solutions) { sol ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = sol.authorAlias,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            if (sol.triedAndWorkedCount > 0) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF2E7D32),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${sol.triedAndWorkedCount} ने प्रमाणित किया",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "१. क्या किया जाएगा:",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = sol.whatWillBeDone, style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "२. कौन करेगा:",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = sol.whoWillDoIt, style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "खर्च: < ₹1,000",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "समय: 1-3 दिन",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        if (!sol.workedExample.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "सफल उदाहरण: ${sol.workedExample}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }
            }
        }

        // Add Solution Wizard Sheet
        if (showAddSolutionSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAddSolutionSheet = false },
                sheetState = rememberModalBottomSheetState()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(id = R.string.title_add_solution),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = whatWillBeDone,
                        onValueChange = { whatWillBeDone = it },
                        label = { Text(stringResource(id = R.string.field_what)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = whoWillDoIt,
                        onValueChange = { whoWillDoIt = it },
                        label = { Text(stringResource(id = R.string.field_who)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = stringResource(id = R.string.field_cost), style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected = costTier == "zero", onClick = { costTier = "zero" }, label = { Text("₹0 (निःशुल्क)") })
                        FilterChip(selected = costTier == "under_1k", onClick = { costTier = "under_1k" }, label = { Text("< ₹1,000") })
                        FilterChip(selected = costTier == "1k_to_5k", onClick = { costTier = "1k_to_5k" }, label = { Text("₹1k - ₹5k") })
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = keyRisks,
                        onValueChange = { keyRisks = it },
                        label = { Text(stringResource(id = R.string.field_risks)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = workedExample,
                        onValueChange = { workedExample = it },
                        label = { Text(stringResource(id = R.string.field_example)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // AI Solution Coach Button
                    OutlinedButton(
                        onClick = {
                            aiCoachFeedback = "समाधान मित्र: आपका सुझाव अच्छा है। ग्राम प्रधान और SMC दोनों का नाम जोड़ने से क्रियान्वयन जल्दी संभव होगा।"
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = R.string.btn_ai_coach))
                    }

                    if (aiCoachFeedback != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Text(text = aiCoachFeedback!!, color = Color(0xFF1B5E20), style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    PrimaryButton(
                        text = stringResource(id = R.string.action_submit),
                        onClick = {
                            if (whatWillBeDone.isNotBlank() && whoWillDoIt.isNotBlank()) {
                                solutions = solutions + Solution(
                                    solutionId = "sol_${System.currentTimeMillis()}",
                                    issueId = issue.issueId,
                                    authorAlias = "आप (समुदाय सदस्य)",
                                    whatWillBeDone = whatWillBeDone,
                                    whoWillDoIt = whoWillDoIt,
                                    estimatedCostTier = costTier,
                                    estimatedTimeTier = timeTier,
                                    keyRisks = keyRisks,
                                    workedExample = workedExample
                                )
                                showAddSolutionSheet = false
                            }
                        },
                        enabled = whatWillBeDone.isNotBlank() && whoWillDoIt.isNotBlank()
                    )
                }
            }
        }
    }
}
