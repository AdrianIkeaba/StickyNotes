package com.ghostdev.stickynotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ghostdev.stickynotes.R
import com.ghostdev.stickynotes.database.NotesDB
import com.ghostdev.stickynotes.nav.NavGraph
import com.ghostdev.stickynotes.presentation.NoteViewModel
import com.ghostdev.stickynotes.presentation.NotesRepository
import com.ghostdev.stickynotes.theme.StickyNotesTheme
import com.ghostdev.stickynotes.theme.nunitoFont

class Search : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StickyNotesTheme {
                Surface(Modifier.fillMaxSize()) {
                    //MainUI
                    val context = LocalContext.current.applicationContext
                    val db = NotesDB.getInstance(context)
                    val repository = NotesRepository(db)
                    val viewModel = NoteViewModel(repository)

                    NavGraph(viewModel)
                }
            }
        }
    }
}

@Composable
fun SearchUI(viewModel: NoteViewModel, controller: NavController) {
    var searchText by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)) {
            TextField(value = searchText, label = { Text(text = "Search notes", color = Color(0xFFCCCCCC))},
                onValueChange = { searchText = it },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF3B3B3B),
                    unfocusedContainerColor = Color(0xFF3B3B3B),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    cursorColor = Color.White,
                    selectionColors = TextSelectionColors(backgroundColor = Color(0xFF37474F), handleColor = Color.White)
                ),
                trailingIcon = {Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear", tint = Color(0xFFCCCCCC))},
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.search_unavailable),
                contentDescription = "create note",
                modifier = Modifier
                    .sizeIn(minWidth = 300.dp, minHeight = 300.dp)
                    .align(Alignment.Center)
            )
            Text(
                text = "Note not found. Try searching again.",
                color = Color.White,
                fontFamily = nunitoFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 270.dp)
            )
        }

    }
}