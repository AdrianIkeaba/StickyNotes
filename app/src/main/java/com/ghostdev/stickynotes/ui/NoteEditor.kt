package com.ghostdev.stickynotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.text.TextStyle
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

class NoteEditor : ComponentActivity() {
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
fun NoteEditorUI(viewModel: NoteViewModel, controller: NavController, noteId: Int?, noteTitle: String?, noteBody: String?, noteColor: Int?, noteVisibility: Boolean?) {
    var titleText by remember {
        if (noteTitle != null) {
            mutableStateOf(noteTitle)
        } else {
            mutableStateOf("")
        }
    }
    var bodyText by remember {
        if (noteBody != null) {
            mutableStateOf(noteBody)
        } else {
            mutableStateOf("")
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        //top bar
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
        ) {
            Card(modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3B3B3B),
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(35.dp)
                    )
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Card(modifier = Modifier
                    .size(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3B3B3B),
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Icon(
                            painter = painterResource(id = R.drawable.visibility_on),
                            contentDescription = "Visibility",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    modifier = Modifier
                        .size(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3B3B3B),
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Icon(
                            painter = painterResource(id = R.drawable.save),
                            contentDescription = "Save",
                            tint = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        //main body
        TextField(value = titleText, placeholder = { Text(text = "Title", fontSize = 30.sp) }, onValueChange = {
                titleText = it
            }, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedLabelColor = Color(0xFF9A9A9A),
            selectionColors = TextSelectionColors(backgroundColor = Color(0xFF37474F), handleColor = Color.White)
        ), textStyle = TextStyle(color = Color.White, fontSize = 30.sp))

        TextField(value = bodyText, placeholder = { Text(text = "Type something...", fontSize = 18.sp) }, onValueChange = {
            bodyText = it
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp), colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedLabelColor = Color(0xFF9A9A9A),
            selectionColors = TextSelectionColors(backgroundColor = Color(0xFF37474F), handleColor = Color.White)
        ), textStyle = TextStyle(color = Color.White, fontSize = 18.sp))
    }
}


@Composable
@Preview
fun SaveChangesDialog() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3B3B3B)
        )) {

        Icon(imageVector = Icons.Filled.Info, contentDescription = "Info", modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 30.dp)
            .size(35.dp), tint = Color(0xFF9A9A9A))

        Text(text = "Save changes ?",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            fontFamily = nunitoFont, fontSize = 22.sp,
            color = Color(0xFF9A9A9A))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {

            FilledTonalButton(onClick = { },
                modifier = Modifier.padding(top = 20.dp).width(110.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF0000)
                ), shape = RoundedCornerShape(8.dp)) {
                    Text(text = "Discard",
                        color = Color.White, fontFamily = nunitoFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold)
            }

            FilledTonalButton(onClick = { },
                modifier = Modifier.padding(top = 20.dp).width(110.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF30BE71)
                ), shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Save",
                    color = Color.White, fontFamily = nunitoFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold)
            }
        }

    }
}
