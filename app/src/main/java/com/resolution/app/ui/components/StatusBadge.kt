package com.resolution.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resolution.app.R

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, labelRes) = when (status.lowercase()) {
        "resolved" -> Triple(
            Color(0xFFE8F5E9),
            Color(0xFF2E7D32),
            R.string.status_resolved
        )
        "in_progress" -> Triple(
            Color(0xFFFFF3E0),
            Color(0xFFE65100),
            R.string.status_in_progress
        )
        "solutions_added" -> Triple(
            Color(0xFFE3F2FD),
            Color(0xFF1565C0),
            R.string.status_solutions_added
        )
        else -> Triple(
            Color(0xFFFBE9E7),
            Color(0xFFD84315),
            R.string.status_open
        )
    }

    Box(
        modifier = modifier
            .background(color = bgColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = stringResource(id = labelRes),
            color = textColor,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
        )
    }
}
