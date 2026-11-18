package com.resolution.app

import com.resolution.app.data.model.Issue
import com.resolution.app.data.model.Solution
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun defaultIssue_isCreatedWithOpenStatus() {
        val issue = Issue(
            issueId = "iss_101",
            title = "स्कूल में पीने का पानी नहीं है",
            districtId = "sitapur"
        )
        assertEquals("open", issue.status)
        assertEquals(0, issue.supportCount)
    }

    @Test
    fun defaultSolution_isCreatedWithZeroUpvotes() {
        val solution = Solution(
            solutionId = "sol_201",
            issueId = "iss_101",
            whatWillBeDone = "SMC बैठक बुलाकर हैंडपंप मरम्मत की जाएगी",
            whoWillDoIt = "ग्राम पंचायत + SMC"
        )
        assertEquals(0, solution.upvoteCount)
        assertFalse(solution.isAiGenerated)
    }
}
