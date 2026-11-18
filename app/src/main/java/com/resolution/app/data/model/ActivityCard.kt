package com.resolution.app.data.model

data class ActivityCard(
    val cardId: String = "",
    val postId: String = "",
    val authorId: String = "",
    val authorName: String = "",
    val authorRole: String = "",
    val isExpertVerified: Boolean = false,
    val targetClass: String = "",          // कक्षा या आयु वर्ग (उदा: कक्षा 1-3, कक्षा 4-5)
    val timeNeeded: String = "",           // समय (उदा: 5-10 मिनट)
    val materialsNeeded: String = "",      // कम खर्च / TLM सामग्री (उदा: कंकड़, माचिस की तीलियां)
    val stepByStep: String = "",           // चरणबद्ध तरीका
    val learningOutcome: String = "",      // सीखने का प्रतिफल (क्या सीखेगा बच्चा?)
    val adaptations: String? = null,       // वैकल्पिक सुझाव या बदलाव
    val upvotes: Int = 0,
    val triedCount: Int = 0,               // "काम कर गया" सत्यापन संख्या
    val createdAt: Long = System.currentTimeMillis()
)
