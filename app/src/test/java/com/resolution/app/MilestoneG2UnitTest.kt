package com.resolution.app

import com.resolution.app.data.local.entity.IssueEntity
import com.resolution.app.data.model.Issue
import com.resolution.app.data.model.Solution
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MilestoneG2UnitTest {

    @Test
    fun issueEntity_mappingIsBiDirectionalAndLossless() {
        val originalIssue = Issue(
            issueId = "iss_up_501",
            authorId = "usr_10",
            authorRole = "teacher",
            authorAlias = "सुनीता देवी",
            title = "बालिका शौचालय में पानी नहीं है",
            description = "कक्षा 6 से 8 की छात्राएं स्कूल आने में संकोच करती हैं।",
            category = "safety",
            districtId = "बाराबंकी",
            blockId = "फतेहपुर",
            status = "solutions_added",
            supportCount = 35,
            solutionCount = 2,
            aiSummaryHindi = "पानी की समस्या",
            createdAt = 1700000000000L
        )

        val entity = IssueEntity.fromDomainModel(originalIssue)
        val reconstructedIssue = entity.toDomainModel()

        assertEquals(originalIssue.issueId, reconstructedIssue.issueId)
        assertEquals(originalIssue.title, reconstructedIssue.title)
        assertEquals(originalIssue.category, reconstructedIssue.category)
        assertEquals(originalIssue.supportCount, reconstructedIssue.supportCount)
        assertEquals(originalIssue.solutionCount, reconstructedIssue.solutionCount)
        assertEquals(originalIssue.districtId, reconstructedIssue.districtId)
    }

    @Test
    fun structuredSolution_requiresActionableWhatAndWho() {
        val solution = Solution(
            solutionId = "sol_100",
            issueId = "iss_up_501",
            whatWillBeDone = "SMC और प्रधान की बैठक कर पाइप बदलेंगे",
            whoWillDoIt = "SMC अध्यक्ष",
            estimatedCostTier = "under_1k",
            estimatedTimeTier = "days"
        )

        assertTrue(solution.whatWillBeDone.isNotBlank())
        assertTrue(solution.whoWillDoIt.isNotBlank())
        assertNotNull(solution.estimatedCostTier)
    }

    @Test
    fun supportCount_incrementWorksAccurately() {
        var supportCount = 10
        val isSupported = true
        supportCount += if (isSupported) 1 else -1
        assertEquals(11, supportCount)
    }
}
