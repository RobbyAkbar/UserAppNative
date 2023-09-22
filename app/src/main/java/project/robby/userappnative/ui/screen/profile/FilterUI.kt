package project.robby.userappnative.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun FilterUI(onClick: (Int) -> Unit) {
    val listItems = arrayOf("All", "Verified", "Not yet Verified")
    var disabledItem by remember { mutableIntStateOf(0) }

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Open Options",
            )
        }

        // drop down menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            listItems.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        onClick(itemIndex)
                        disabledItem = itemIndex
                        expanded = false
                    },
                    enabled = (itemIndex != disabledItem)
                ) {
                    Text(text = itemValue)
                }
            }
        }
    }
}