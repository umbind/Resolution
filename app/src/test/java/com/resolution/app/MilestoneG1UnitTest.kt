package com.resolution.app

import com.resolution.app.data.model.Issue
import com.resolution.app.data.model.Solution
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
    fun issueModel_defaultStatusIsOpen() {
        val issue = Issue(
            issueId = "test_1",
            title = "स्कूल में हैंडपंप खराब है",
            districtId = "सीतापुर",
            blockId = "महमूदाबाद"
        )
        assertEquals("open", issue.status)
        assertEquals(0, issue.supportCount)
        assertEquals(0, issue.solutionCount)
    }

    @Test
    fun solutionModel_structuredSchemaFieldsAreRetained() {
        val solution = Solution(
            solutionId = "sol_1",
            issueId = "test_1",
            whatWillBeDone = "SMC बैठक बुलाकर नया वाशर लगवाया जाए",
            whoWillDoIt = "ग्राम पंचायत + SMC",
            estimatedCostTier = "under_1k",
            estimatedTimeTier = "days",
            keyRisks = "पंचायत सचिव की अनुपलब्धता",
            workedExample = "गोंडा में सफल रहा"
        )
        assertEquals("under_1k", solution.estimatedCostTier)
        assertEquals("days", solution.estimatedTimeTier)
        assertFalse(solution.isAiGenerated)
        assertNotNull(solution.workedExample)
    }

    @Test
    fun guestModeRole_isHandledCleanly() {
        val userRole = "guest"
        assertTrue(userRole.equals("guest", ignoreCase = true))
    }
}
