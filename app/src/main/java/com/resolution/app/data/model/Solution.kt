package com.resolution.app.data.model

data class Solution(
    val solutionId: String = "",
    val issueId: String = "",
    val authorId: String = "",
    val authorAlias: String = "",
    val authorRole: String = "",
    val isExpertVerifiedAuthor: Boolean = false,
    
    // Mandatory structured schema
    val whatWillBeDone: String = "",
    val whoWillDoIt: String = "",
    val estimatedCostTier: String = "zero", // zero, under_1k, 1k_to_5k, above_5k
    val estimatedTimeTier: String = "days", // days, weeks, one_month, multiple_months
    val keyRisks: String = "",
    val workedExample: String? = null,
    
    val upvoteCount: Int = 0,
    val triedAndWorkedCount: Int = 0,
    val isAiGenerated: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
