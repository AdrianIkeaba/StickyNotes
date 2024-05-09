package com.ghostdev.stickynotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ghostdev.stickynotes.database.NotesDB
import com.ghostdev.stickynotes.nav.Destinations
import com.ghostdev.stickynotes.nav.NavGraph
import com.ghostdev.stickynotes.presentation.NoteViewModel
import com.ghostdev.stickynotes.presentation.NotesRepository
import com.ghostdev.stickynotes.theme.StickyNotesTheme
import com.ghostdev.stickynotes.theme.nunitoFont

class ViewNote : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StickyNotesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    //UI
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
fun ViewNoteUI(viewModel: NoteViewModel, controller: NavController, noteId: Int?, noteTitle: String?, noteBody: String?, noteColor: Int?, noteVisibility: Boolean?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
        ){
            Card(modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3B3B3B),
                )
            ) {
                Box(modifier = Modifier.fillMaxSize().clickable { controller.navigate(route = Destinations.Home.toString()) }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowLeft, contentDescription = "Back",
                        tint = Color.White, modifier = Modifier
                            .align(Alignment.Center)
                            .size(35.dp))
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Card(modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF3B3B3B),
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize().clickable { controller.navigate(route = "${Destinations.EditNotes}/$noteId/$noteTitle/$noteBody/$noteColor/$noteVisibility")}) {
                        Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit",
                            tint = Color.White, modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

        Box(modifier = Modifier.wrapContentSize()) {
            Text(
                text = noteTitle!!,
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = nunitoFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp, end = 20.dp)
            )
        }

        Divider(modifier = Modifier.windowInsetsPadding(WindowInsets(left = 20.dp, right = 20.dp)), color = Color(0xFF3B3B3B))

        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = noteBody!!,
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = nunitoFont,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 10.dp, end = 20.dp)
            )
        }

    }
}