package com.resolution.app.data.model

data class BestStudyPlan(
    val title: String = "",
    val summary: String = "",
    val targetClass: String = "",
    val dailySteps: List<DailyStudyStep> = emptyList(),
    val disclaimer: String = "यह अध्ययन योजना साथी शिक्षकों और अभिभावकों के सफल अनुभवों पर आधारित है।"
)

data class DailyStudyStep(
    val day: Int = 1,
    val title: String = "",
    val timeMinutes: Int = 15,
    val activitySummary: String = "",
    val materialNeeded: String = ""
)
