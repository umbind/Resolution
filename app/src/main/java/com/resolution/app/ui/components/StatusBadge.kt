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
    val bgColor: Color
    val textColor: Color
    val labelRes: Int

    when (status.lowercase()) {
        "resolved" -> {
            bgColor = Color(0xFFE8F5E9)
            textColor = Color(0xFF2E7D32)
            labelRes = R.string.status_resolved
        }
        "in_progress" -> {
            bgColor = Color(0xFFFFF3E0)
            textColor = Color(0xFFE65100)
            labelRes = R.string.status_in_progress
        }
        "solutions_added" -> {
            bgColor = Color(0xFFE3F2FD)
            textColor = Color(0xFF1565C0)
            labelRes = R.string.status_solutions_added
        }
        else -> {
            bgColor = Color(0xFFFBE9E7)
            textColor = Color(0xFFD84315)
            labelRes = R.string.status_open
        }
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
