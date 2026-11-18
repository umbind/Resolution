package com.resolution.app

import com.resolution.app.data.model.ActivityCard
import com.resolution.app.data.model.LearningPost
import com.resolution.app.data.model.PostType
import com.resolution.app.ui.navigation.MainDestination
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MilestoneG1UnitTest {

    @Test
    fun navigationDestinations_hasExactlyFourTabs() {
        val destinations = MainDestination.values()
        assertEquals(4, destinations.size)
        assertEquals(MainDestination.FEED, destinations[0])
        assertEquals(MainDestination.POST, destinations[1])
        assertEquals(MainDestination.IMPACT, destinations[2])
        assertEquals(MainDestination.PROFILE, destinations[3])
    }

    @Test
    fun learningPost_questionDefaultsAreValid() {
        val post = LearningPost(
            postId = "post_q1",
            authorName = "सुनीता शर्मा (शिक्षिका)",
            authorRole = "role_teacher",
            postType = PostType.QUESTION,
            title = "कक्षा 5 के बच्चों को भिन्न खेल-खेल में कैसे सिखाएं?",
            description = "बच्चे 2/3 और 3/4 की तुलना करने में भ्रमित होते हैं।",
            category = "cat_math_science",
            targetClass = "कक्षा 4-5"
        )
        assertEquals(PostType.QUESTION, post.postType)
        assertEquals(0, post.supportCount)
        assertEquals(0, post.triedCount)
        assertEquals(0, post.repliesCount)
        assertEquals("कक्षा 4-5", post.targetClass)
    }

    @Test
    fun activityCard_structuredSchemaFieldsAreRetained() {
        val card = ActivityCard(
            cardId = "card_1",
            postId = "post_act1",
            authorName = "रमेश चन्द्र",
            targetClass = "कक्षा 1-3",
            timeNeeded = "15 मिनट",
            materialsNeeded = "माचिस की 50 तीलियां और रबर बैंड",
            stepByStep = "10-10 तीलियों के बंडल बनाकर दहाई की समझ बनाएं।",
            learningOutcome = "स्थानीय मान (दहाई-इकाई) की स्पष्ट समझ।",
            adaptations = "कंकड़ों से भी किया जा सकता है।"
        )
        assertEquals("15 मिनट", card.timeNeeded)
        assertEquals("कक्षा 1-3", card.targetClass)
        assertNotNull(card.materialsNeeded)
        assertTrue(card.stepByStep.isNotBlank())
        assertTrue(card.learningOutcome.isNotBlank())
        assertEquals(0, card.triedCount)
    }

    @Test
    fun postTypeEnum_hasBothTypes() {
        val types = PostType.values()
        assertEquals(2, types.size)
        assertTrue(types.contains(PostType.QUESTION))
        assertTrue(types.contains(PostType.ACTIVITY))
    }
}
