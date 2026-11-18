package com.resolution.app.data.model

data class LearningPost(
    val postId: String = "",
    val authorId: String = "",
    val authorName: String = "",
    val authorRole: String = "",
    val isExpertVerified: Boolean = false,
    val postType: PostType = PostType.QUESTION,
    val title: String = "",
    val description: String = "",
    val category: String = "cat_math_science",
    val targetClass: String = "कक्षा 3-5",
    val imageUrl: String? = null,
    val activityCard: ActivityCard? = null,
    val supportCount: Int = 0,
    val triedCount: Int = 0,
    val repliesCount: Int = 0,
    val bestStudyPlan: BestStudyPlan? = null,
    val createdAt: Long = System.currentTimeMillis()
)
