
package com.example.codeeditor

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Sidebar Menu Item Composable
 * Modern menu item design with hover effects and selection states
 */
@Composable
private fun SidebarMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                if (isSelected) Color(0xFF3C3C3C) else Color.Transparent
            )
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) Color(0xFF007ACC) else Color(0xFFCCCCCC),
                modifier = Modifier.size(20.dp)
            )
            
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            
            Text(
                text = label,
                color = if (isSelected) Color(0xFF007ACC) else Color(0xFFCCCCCC),
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

// Composable drawer UI with options to create, open, and save files
@Composable
fun DrawerContent(
    initialFileName: String,
    context: Context,
    onNewFile: (String) -> Unit,
    onOpenFile: (String) -> Unit,
    onSaveFile: (String) -> Unit,
    onCheckFileExists: (String) -> Boolean
) {
    var fileName = remember { mutableStateOf(initialFileName) }
    var showDialog = remember { mutableStateOf(false) }
    var showSaveDialog = remember { mutableStateOf(false) }
    var showOpenDialog = remember { mutableStateOf(false) }
    var showConfirmDialog = remember { mutableStateOf(false) }
    var pendingFileName = remember { mutableStateOf("") }
    val extensions = listOf(".txt", ".kt", ".java", ".py", ".c", ".cpp")
    var selectedExtension = remember { mutableStateOf(".txt") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .background(Color(0xFF0d47a1))
            .padding(top = 80.dp, bottom = 16.dp, start = 0.dp, end = 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Header section with app name
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0d47a1).copy(alpha = 0.8f))
                .padding(vertical = 20.dp, horizontal = 24.dp)
        ) {
            Text(
                text = "LiteCode",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // New File Menu Item
        SidebarMenuItem(
            icon = Icons.Default.Create,
            label = "New File",
            onClick = { showDialog.value = true },
            isSelected = false
        )

        // Open File Menu Item
        SidebarMenuItem(
            icon = Icons.AutoMirrored.Filled.List,
            label = "Open File",
            onClick = { showOpenDialog.value = true },
            isSelected = false
        )

        // Save File Menu Item
        SidebarMenuItem(
            icon = Icons.Default.MoreVert,
            label = "Save File",
            onClick = { showSaveDialog.value = true },
            isSelected = false
        )
    }

    // Show "New File" dialog
    if (showDialog.value) {

        var expanded = remember { mutableStateOf(false) }


        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    var finalName = fileName.value
                    if (!finalName.endsWith(selectedExtension.value)) {
                        finalName = finalName + selectedExtension.value
                    }
                    onNewFile(finalName)
                    showDialog.value = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) { Text("Cancel") }
            },
            title = { Text("Create New File") },
            text = {
                Column {
                    OutlinedTextField(
                        value = fileName.value ,
                        onValueChange = { fileName.value = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        trailingIcon = { Text(selectedExtension.value) }
                    )
                    Button(onClick = {expanded.value = !expanded.value}) {
                        Text("Select extention")
                        Icon( Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Down")
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        extensions.forEach { ext ->
                            DropdownMenuItem(
                                text = { Text(ext) },
                                onClick = {
                                    selectedExtension.value = ext
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }


            }
        )
    }

    // Show "Save File" dialog
    if (showSaveDialog.value) {
        // Initialize fileName only once when dialog first opens
        LaunchedEffect(Unit) {
            if (fileName.value.isEmpty()) {
                fileName.value = initialFileName
            }
        }
        
        var saveExpanded = remember { mutableStateOf(false) }
        
        AlertDialog(
            onDismissRequest = { showSaveDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    var finalName = fileName.value
                    if (!finalName.endsWith(selectedExtension.value)) {
                        finalName = finalName + selectedExtension.value
                    }

                    // Check if file already exists
                    if (onCheckFileExists(finalName)) {
                        pendingFileName.value = finalName
                        showConfirmDialog.value = true
                        showSaveDialog.value = false
                    } else {
                        onSaveFile(finalName)
                        showSaveDialog.value = false
                    }
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog.value = false }) { Text("Cancel") }
            },
            title = { Text("Save File") },
            text = {
                Column {
                    OutlinedTextField(
                        value = fileName.value,
                        onValueChange = { fileName.value = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("File name") },
                        trailingIcon = { Text(selectedExtension.value) }
                    )
                    Button(
                        onClick = { saveExpanded.value = !saveExpanded.value },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Select extension")
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Down"
                        )
                    }
                    DropdownMenu(
                        expanded = saveExpanded.value,
                        onDismissRequest = { saveExpanded.value = false }
                    ) {
                        extensions.forEach { ext ->
                            DropdownMenuItem(
                                text = { Text(ext) },
                                onClick = {
                                    selectedExtension.value = ext
                                    saveExpanded.value = false
                                }
                            )
                        }
                    }
                }
            }
        )
    }
    
    // Show confirmation dialog for file replacement
    if (showConfirmDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog.value = false },
            title = { Text("File Already Exists") },
            text = { Text("A file named '${pendingFileName.value}' already exists. Do you want to replace it?") },
            confirmButton = {
                TextButton(onClick = {
                    onSaveFile(pendingFileName.value)
                    showConfirmDialog.value = false
                }) { Text("Replace") }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showConfirmDialog.value = false
                    showSaveDialog.value = true // Reopen save dialog
                }) { Text("Cancel") }
            }
        )
    }
    
    // Show "Open File" dialog
    if (showOpenDialog.value) {
        val fileList = context.filesDir.listFiles()?.toList() ?: emptyList()
        val sortedFiles = fileList.sortedWith(compareByDescending { it.lastModified() })
        val filesState = remember { mutableStateOf(sortedFiles) }
        AlertDialog(
            onDismissRequest = { showOpenDialog.value = false },
            title = { Text("Select a file") },
            text = {
                LazyColumn(modifier = Modifier.height(200.dp)) {
                    items(filesState.value) { file ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = file.name,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        onOpenFile(file.name)
                                        showOpenDialog.value = false
                                    }
                            )
                            TextButton(onClick = {
                                file.delete()
                                val updatedFileList = context.filesDir.listFiles()?.toList() ?: emptyList()
                                val updatedSortedFiles = updatedFileList.sortedWith(compareByDescending { it.lastModified() })
                                filesState.value = updatedSortedFiles
                            }) {
                                Text("Delete", color = Color.Red)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showOpenDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
