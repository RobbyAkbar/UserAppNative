package project.robby.userappnative.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

fun intersectsOtherLine(
    startY: Float,
    endY: Float,
    drawnYCoordinates: Set<Float>
): Boolean {
    for (y in drawnYCoordinates) {
        if (startY < y && endY > y) {
            return true
        }
        if (startY > y && endY < y) {
            return true
        }
    }
    return false
}

fun DrawScope.drawRandomCurvedLine(
    startY: Float,
    endY: Float,
    curveX: Float,
    curveY: Float,
    canvas: DrawScope
) {
    val path = Path()

    path.moveTo(0f, startY)
    path.quadraticBezierTo(curveX, curveY, canvas.size.width, endY)

    drawPath(
        path = path,
        color = Color.White,
        style = Stroke(width = 2.dp.toPx())
    )
}