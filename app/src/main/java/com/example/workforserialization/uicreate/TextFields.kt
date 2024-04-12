package com.example.workforserialization.uicreate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.workforserialization.ui.theme.Black
import com.example.workforserialization.ui.theme.Gray1
import com.example.workforserialization.ui.theme.Gray2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldComponent(value: String, input: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { input(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp),
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            color = Color(Black.value)
        ),
        placeholder = { TextMedium(placeholder, Modifier, 14, Color(Gray2.value)) },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(5.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color(Gray1.value),
            focusedBorderColor = Color(Gray1.value),
        )
    )
}

