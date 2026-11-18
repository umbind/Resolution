package com.resolution.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.resolution.app.data.model.ActivityCard
import com.resolution.app.data.model.BestStudyPlan
import com.resolution.app.data.model.DailyStudyStep
import com.resolution.app.data.model.LearningPost
import com.resolution.app.data.model.PostType

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val postId: String,
    val authorId: String,
    val authorName: String,
    val authorRole: String,
    val isExpertVerified: Boolean,
    val postType: String, // "QUESTION" or "ACTIVITY"
    val title: String,
    val description: String,
    val category: String,
    val targetClass: String,
    val imageUrl: String?,
    
    // Activity Card fields
    val cardMaterials: String?,
    val cardTimeNeeded: String?,
    val cardStepByStep: String?,
    val cardLearningOutcome: String?,
    val cardAdaptations: String?,

    val supportCount: Int,
    val triedCount: Int,
    val repliesCount: Int,

    // AI Best Study Plan fields
    val planTitle: String?,
    val planSummary: String?,
    val planStepsSummary: String?,

    val createdAt: Long
) {
    fun toDomainModel(): LearningPost {
        val pType = if (postType == "ACTIVITY") PostType.ACTIVITY else PostType.QUESTION
        val activityCard = if (pType == PostType.ACTIVITY) {
            ActivityCard(
                cardId = postId,
                postId = postId,
                authorId = authorId,
                authorName = authorName,
                authorRole = authorRole,
                isExpertVerified = isExpertVerified,
                targetClass = targetClass,
                timeNeeded = cardTimeNeeded ?: "10-15 मिनट",
                materialsNeeded = cardMaterials ?: "घरेलू सामग्री",
                stepByStep = cardStepByStep ?: description,
                learningOutcome = cardLearningOutcome ?: "व्यावहारिक समझ",
                adaptations = cardAdaptations,
                upvotes = supportCount,
                triedCount = triedCount,
                createdAt = createdAt
            )
        } else null

        val studyPlan = if (!planTitle.isNullOrBlank()) {
            BestStudyPlan(
                title = planTitle,
                summary = planSummary ?: "",
                targetClass = targetClass,
                dailySteps = listOf(
                    DailyStudyStep(1, "दिन 1: अवधारणा का परिचय", 10, planStepsSummary ?: "खेल-खेल में समझना", "कागज/कंकड़"),
                    DailyStudyStep(2, "दिन 2: व्यावहारिक अभ्यास", 15, "समूह में गतिविधि", "माचिस की तीलियां"),
                    DailyStudyStep(3, "दिन 3: पुनरावृत्ति व प्रश्न", 10, "रिवीजन खेल", "चॉक और स्लेट")
                )
            )
        } else null

        return LearningPost(
            postId = postId,
            authorId = authorId,
            authorName = authorName,
            authorRole = authorRole,
            isExpertVerified = isExpertVerified,
            postType = pType,
            title = title,
            description = description,
            category = category,
            targetClass = targetClass,
            imageUrl = imageUrl,
            activityCard = activityCard,
            supportCount = supportCount,
            triedCount = triedCount,
            repliesCount = repliesCount,
            bestStudyPlan = studyPlan,
            createdAt = createdAt
        )
    }

    companion object {
        fun fromDomainModel(post: LearningPost): PostEntity = PostEntity(
            postId = post.postId,
            authorId = post.authorId,
            authorName = post.authorName,
            authorRole = post.authorRole,
            isExpertVerified = post.isExpertVerified,
            postType = post.postType.name,
            title = post.title,
            description = post.description,
            category = post.category,
            targetClass = post.targetClass,
            imageUrl = post.imageUrl,
            cardMaterials = post.activityCard?.materialsNeeded,
            cardTimeNeeded = post.activityCard?.timeNeeded,
            cardStepByStep = post.activityCard?.stepByStep,
            cardLearningOutcome = post.activityCard?.learningOutcome,
            cardAdaptations = post.activityCard?.adaptations,
            supportCount = post.supportCount,
            triedCount = post.triedCount,
            repliesCount = post.repliesCount,
            planTitle = post.bestStudyPlan?.title,
            planSummary = post.bestStudyPlan?.summary,
            planStepsSummary = post.bestStudyPlan?.dailySteps?.firstOrNull()?.activitySummary,
            createdAt = post.createdAt
        )
    }
}
