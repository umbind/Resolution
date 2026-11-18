package com.resolution.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.resolution.app.data.model.Issue

@Entity(tableName = "issues")
data class IssueEntity(
    @PrimaryKey val issueId: String,
    val authorId: String,
    val authorRole: String,
    val authorAlias: String,
    val title: String,
    val description: String,
    val category: String,
    val districtId: String,
    val blockId: String,
    val schoolNameOrRef: String?,
    val photoUrl: String?,
    val status: String,
    val supportCount: Int,
    val solutionCount: Int,
    val aiSummaryHindi: String?,
    val bestPracticalPlanId: String?,
    val createdAt: Long
) {
    fun toDomainModel(): Issue = Issue(
        issueId = issueId,
        authorId = authorId,
        authorRole = authorRole,
        authorAlias = authorAlias,
        title = title,
        description = description,
        category = category,
        districtId = districtId,
        blockId = blockId,
        schoolNameOrRef = schoolNameOrRef,
        photoUrl = photoUrl,
        status = status,
        supportCount = supportCount,
        solutionCount = solutionCount,
        aiSummaryHindi = aiSummaryHindi,
        bestPracticalPlanId = bestPracticalPlanId,
        createdAt = createdAt
    )

    companion object {
        fun fromDomainModel(issue: Issue): IssueEntity = IssueEntity(
            issueId = issue.issueId,
            authorId = issue.authorId,
            authorRole = issue.authorRole,
            authorAlias = issue.authorAlias,
            title = issue.title,
            description = issue.description,
            category = issue.category,
            districtId = issue.districtId,
            blockId = issue.blockId,
            schoolNameOrRef = issue.schoolNameOrRef,
            photoUrl = issue.photoUrl,
            status = issue.status,
            supportCount = issue.supportCount,
            solutionCount = issue.solutionCount,
            aiSummaryHindi = issue.aiSummaryHindi,
            bestPracticalPlanId = issue.bestPracticalPlanId,
            createdAt = issue.createdAt
        )
    }
}
