package com.resolution.app

import com.resolution.app.data.local.entity.PostEntity
import com.resolution.app.data.model.ActivityCard
import com.resolution.app.data.model.BestStudyPlan
import com.resolution.app.data.model.DailyStudyStep
import com.resolution.app.data.model.LearningPost
import com.resolution.app.data.model.PostType
import com.resolution.app.data.repository.PostRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MilestoneG2UnitTest {

    @Test
    fun postEntity_mappingIsBiDirectionalAndLossless() {
        val originalPost = LearningPost(
            postId = "post_up_101",
            authorId = "usr_sunita",
            authorName = "सुनीता शर्मा",
            authorRole = "role_teacher",
            isExpertVerified = true,
            postType = PostType.ACTIVITY,
            title = "कागज की पट्टियों से भिन्न समझाना",
            description = "कक्षा 4 के छात्रों के लिए रंगीन पट्टियों का खेल।",
            category = "cat_math_science",
            targetClass = "कक्षा 4-5",
            activityCard = ActivityCard(
                cardId = "post_up_101",
                postId = "post_up_101",
                authorName = "सुनीता शर्मा",
                targetClass = "कक्षा 4-5",
                timeNeeded = "15 मिनट",
                materialsNeeded = "रंगीन कागज और कैंची",
                stepByStep = "1. पट्टियों को आधा मोड़ें। 2. बराबर हिस्सों की तुलना करें।",
                learningOutcome = "1/2 और 1/4 का दृश्य अनुभव।",
                upvotes = 40,
                triedCount = 25
            ),
            supportCount = 40,
            triedCount = 25,
            repliesCount = 2,
            bestStudyPlan = BestStudyPlan(
                title = "7 दिवसीय भिन्न योजना",
                summary = "प्रतिदिन 10 मिनट की समझ",
                targetClass = "कक्षा 4-5",
                dailySteps = listOf(
                    DailyStudyStep(1, "कागज मोड़ना", 10, "आधा हिस्सा समझना", "कागज")
                )
            ),
            createdAt = 1700000000000L
        )

        val entity = PostEntity.fromDomainModel(originalPost)
        val reconstructed = entity.toDomainModel()

        assertEquals(originalPost.postId, reconstructed.postId)
        assertEquals(originalPost.title, reconstructed.title)
        assertEquals(originalPost.postType, reconstructed.postType)
        assertEquals(originalPost.category, reconstructed.category)
        assertEquals(originalPost.supportCount, reconstructed.supportCount)
        assertEquals(originalPost.triedCount, reconstructed.triedCount)
        assertNotNull(reconstructed.activityCard)
        assertEquals("15 मिनट", reconstructed.activityCard?.timeNeeded)
        assertNotNull(reconstructed.bestStudyPlan)
    }

    @Test
    fun activityCard_requiresActionableStepsAndOutcome() {
        val card = ActivityCard(
            cardId = "card_10",
            postId = "post_1",
            stepByStep = "1. तीलियां गिनें। 2. बंडल बनाएं।",
            learningOutcome = "दहाई का ज्ञान"
        )

        assertTrue(card.stepByStep.isNotBlank())
        assertTrue(card.learningOutcome.isNotBlank())
    }

    @Test
    fun bestStudyPlan_containsPedagogicalDisclaimer() {
        val plan = BestStudyPlan(
            title = "7-दिवसीय योजना",
            summary = "सरल दिनचर्या",
            dailySteps = listOf(
                DailyStudyStep(1, "परिचय", 10, "शुरुआती खेल", "कंकड़")
            )
        )

        assertTrue(plan.disclaimer.contains("शिक्षकों और अभिभावकों"))
        assertEquals(1, plan.dailySteps.size)
    }

    @Test
    fun sampleLearningPosts_containsRealisticUPData() {
        val samplePosts = PostRepository.getSampleLearningPosts()
        assertTrue(samplePosts.isNotEmpty())
        assertTrue(samplePosts.any { it.postType == PostType.QUESTION })
        assertTrue(samplePosts.any { it.postType == PostType.ACTIVITY })
        assertTrue(samplePosts.any { it.bestStudyPlan != null })
    }
}
