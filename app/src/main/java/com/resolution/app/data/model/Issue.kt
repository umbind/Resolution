package com.resolution.app.data.model

data class Issue(
    val issueId: String = "",
    val authorId: String = "",
    val authorRole: String = "",
    val authorAlias: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "infrastructure",
    val districtId: String = "",
    val blockId: String = "",
    val schoolNameOrRef: String? = null,
    val photoUrl: String? = null,
    val status: String = "open", // open, solutions_added, in_progress, resolved
    val supportCount: Int = 0,
    val solutionCount: Int = 0,
    val aiSummaryHindi: String? = null,
    val bestPracticalPlanId: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
