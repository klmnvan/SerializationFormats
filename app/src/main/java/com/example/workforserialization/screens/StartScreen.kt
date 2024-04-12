package com.example.workforserialization.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import com.example.rediexpressjc.uicreate.Modifiers
import com.example.workforserialization.models.Book
import com.example.workforserialization.models.Cat
import com.example.workforserialization.serialization.SerializeCsv
import com.example.workforserialization.serialization.SerializeJson
import com.example.workforserialization.ui.theme.Black
import com.example.workforserialization.ui.theme.Gray1
import com.example.workforserialization.ui.theme.Gray2
import com.example.workforserialization.ui.theme.Red
import com.example.workforserialization.ui.theme.White
import com.example.workforserialization.ui.theme.WorkForSerializationTheme
import com.example.workforserialization.uicreate.ButtonBlueGrayMP
import com.example.workforserialization.uicreate.FieldComponent
import com.example.workforserialization.uicreate.TextMedium
import com.example.workforserialization.uicreate.TextMediumCenter
import java.util.LinkedList
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(activity: Activity?){

    var fileName by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Сообщение") }
    var content by remember { mutableStateOf("Сообщение") }
    var cats by remember { mutableStateOf<List<Cat>>(listOf()) }
    var books by remember { mutableStateOf<List<Book>>(listOf()) }
    var buttonIsClicable by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val formats = arrayOf(".json", ".csv", ".xml", ".yaml")
    val tasks = arrayOf("Считать из файла", "Записать в файл")
    val models = arrayOf("Коты", "Книги")
    var expanded by remember { mutableStateOf(false) }
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var format by remember { mutableStateOf("") }
    var task by remember { mutableStateOf(tasks[0]) }
    var model by remember { mutableStateOf(models[0]) }

    LaunchedEffect(fileName, format) {
        if (fileName.isNotEmpty() && format.isNotEmpty()) {
            buttonIsClicable = true
        } else {
            buttonIsClicable = false
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Column(modifier = Modifier.padding(vertical = 30.dp)
            ) {
            TextMediumCenter(
                text = "Сериализация форматов",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 20.dp), 24, Color(Red.value)
            )

            TextMedium("Введите имя файла",
                Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 20.dp), 16, Color(Gray1.value)
            )

            FieldComponent(fileName, { fileName = it }, "file name")

            TextMedium("Выберите формат файла",
                Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 10.dp), 16, Color(Gray1.value)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        value = format,
                        readOnly = true,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 8.dp)
                            .menuAnchor(),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = Color(Black.value)
                        ),
                        placeholder = { TextMedium("Не выбрано", Modifier, 14, Color(Gray2.value)) },
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(5.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(Gray1.value),
                            unfocusedBorderColor = Color(Gray1.value),
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        formats.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    format = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            TextMedium("Выберите действие",
                Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 10.dp), 16, Color(Gray1.value)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded1,
                    onExpandedChange = {
                        expanded1 = !expanded1
                    }
                ) {
                    OutlinedTextField(
                        value = task,
                        readOnly = true,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 8.dp)
                            .menuAnchor(),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = Color(Black.value)
                        ),
                        placeholder = { TextMedium("Не выбрано", Modifier, 14, Color(Gray2.value)) },
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(Gray1.value),
                            focusedBorderColor = Color(Gray1.value),
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded1,
                        onDismissRequest = { expanded1 = false }
                    ) {
                        tasks.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    task = item
                                    expanded1 = false
                                }
                            )
                        }
                    }
                }
            }

            TextMedium("Выберите модель",
                Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 10.dp), 16, Color(Gray1.value)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded2,
                    onExpandedChange = {
                        expanded2 = !expanded2
                    }
                ) {
                    OutlinedTextField(
                        value = model,
                        readOnly = true,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 8.dp)
                            .menuAnchor(),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            color = Color(Black.value)
                        ),
                        placeholder = { TextMedium("Не выбрано", Modifier, 14, Color(Gray2.value)) },
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        shape = RoundedCornerShape(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color(Gray1.value),
                            focusedBorderColor = Color(Gray1.value),
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded2,
                        onDismissRequest = { expanded2 = false }
                    ) {
                        models.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    model = item
                                    expanded2 = false
                                }
                            )
                        }
                    }
                }
            }

            ButtonBlueGrayMP(
                {
                    if(buttonIsClicable){
                        if (task == tasks[0]) {
                            if(models.indexOf(model) == 0){
                                books = emptyList()
                                cats = readFile<Cat>(fileName, formats.indexOf(format), context)
                            }
                            if(models.indexOf(model) == 1){
                                cats = emptyList()
                                books = readFile<Book>(fileName, formats.indexOf(format), context)
                            }
                        }
                        if (task == tasks[1]) {
                            writeFile(fileName, formats.indexOf(format), models.indexOf(model), context)
                        }
                    }
                    else {
                        Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
                    }
                }, Modifiers.MDBtnDefaultMP,
                buttonIsClicable, task)

            if (cats.isNotEmpty()){
                Text(text = "Считанный лист",
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 24.dp),
                    fontSize = 20.sp)
                cats.forEach { cat ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .padding(horizontal = 24.dp),
                        colors = cardColors(
                            containerColor = Color(Gray1.value)
                        ),
                        elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = cat.name.toString(),
                                fontSize = 20.sp,
                                color = Color(White.value))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "${ if(cat.isFluffy == true) "Пушистый" else "Не пушистый"} кот с усами длиной ${cat.mustacheLength} дм.",
                                fontSize = 14.sp,
                                color = Color(White.value))
                        }
                    }

                }
            }
            if (books.isNotEmpty()){
                Text(text = "Считанный лист",
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 24.dp),
                    fontSize = 20.sp)
                books.forEach { book ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .padding(horizontal = 24.dp),
                        colors = cardColors(
                            containerColor = Color(Gray1.value)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = book.title.toString(),
                                fontSize = 20.sp,
                                color = Color(White.value))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Автор: ${book.author}\nЖанр: ${book.genre}\nКоличество страниц: ${book.pageCount}",
                                fontSize = 14.sp,
                                color = Color(White.value))
                        }
                    }

                }
            }
        }
    }

}

inline fun <reified T> readFile(path: String, indFormat: Int, context: Context) : List<T>{
    when(indFormat) {
        0 -> {
            val oJson = SerializeJson()
            return oJson.listFromJson<T>(context, "${path}.json")
        }
        1 -> {
            val oCsv = SerializeCsv()
            return oCsv.listFromCsv<T>(context, "${path}.csv")
        }
    }
    return emptyList()
}

fun writeFile(path: String, indFormat: Int, indModel: Int, context: Context){

    val listCats: List<Cat> = LinkedList(
        listOf(
            Cat(UUID.randomUUID().toString(), "Барсик", false, 3),
            Cat(UUID.randomUUID().toString(), "Пушок", true, 5),
            Cat(UUID.randomUUID().toString(), "НеПушок", false, 2),
            Cat(UUID.randomUUID().toString(), "Пушистик", true, 7),
            Cat(UUID.randomUUID().toString(), "Маркиз", true, 6),
        )
    )

    val listBooks: List<Book> = LinkedList(
        listOf(
            Book("Лисья нора", "Нора Сакавич", 404, "Современная проза"),
            Book("Король воронов", "Нора Сакавич", 538, "Современная проза"),
            Book("Свита короля", "Нора Сакавич", 600, "Современная проза"),
            Book("Дом, в котором", "Мариам Петросян", 960, "Современная проза"),
            Book("Тринадцатый сектор", "Вячеслав Шалыгин", 352, "Современные детективы"),
        )
    )

    when(indFormat){
        0 -> {
            val oJson = SerializeJson()
            when(indModel){
                0 -> {
                    oJson.listInJson(context, listCats, "${path}.json")
                }
                1 -> {
                    oJson.listInJson(context, listBooks, "${path}.json")
                }
            }
        }
        1 -> {
            val oCsv = SerializeCsv()
            when(indModel){
                0 -> {
                    oCsv.listInCsv(context, listCats, "${path}.csv")
                }
                1 -> {
                    oCsv.listInCsv(context, listBooks, "${path}.csv")
                }
            }
        }
        2 -> {
            val oJson = SerializeJson()
        }
        3 -> {
            val oJson = SerializeJson()
        }
    }

}





/*Для удобного Preview*/
@Preview(showBackground = true)
@Composable
private fun Preview() {
    WorkForSerializationTheme {
        StartScreen(null)
    }
}