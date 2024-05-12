package com.ghostdev.stickynotes.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import com.ghostdev.stickynotes.nav.Destinations
import com.ghostdev.stickynotes.nav.NavGraph
import com.ghostdev.stickynotes.presentation.NoteViewModel
import com.ghostdev.stickynotes.presentation.NotesRepository
import com.ghostdev.stickynotes.theme.StickyNotesTheme
import com.ghostdev.stickynotes.theme.nunitoFont
import com.ghostdev.stickynotes.theme.primary
import com.ghostdev.stickynotes.theme.secondary2
import com.ghostdev.stickynotes.theme.tertiary
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var context: Context
    private lateinit var db: NotesDB
    private lateinit var repository: NotesRepository
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StickyNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primary) {
                    //MainUI
                    context = LocalContext.current.applicationContext
                    db = NotesDB.getInstance(context)
                    repository = NotesRepository(db)
                    viewModel = NoteViewModel(repository)

                    NavGraph(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainUI(viewModel: NoteViewModel, controller: NavController) {
    val notes by viewModel.notes.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)
        ){
            Text(text = "Notes", modifier = Modifier.weight(1f),
                color = Color.White, fontSize = 40.sp, fontFamily = nunitoFont, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.requiredWidthIn(min = 10.dp, max = 300.dp))

            Card(modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3B3B3B),
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    IconButton(onClick = { controller.navigate(Destinations.Search.toString()) },
                        modifier = Modifier.align(Alignment.Center), enabled = true, content = {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search",
                            tint = Color.White, modifier = Modifier.align(Alignment.Center))
                    })
                }
            }

            Spacer(modifier = Modifier.width(15.dp))

            Card(modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF3B3B3B),
                )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info",
                        tint = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        ) {
            //Render list of notes
            if (notes.isEmpty()) {
                NoNotes()
            } else {
                LazyColumn {
                    items(notes.size, key = {it}) {index ->
                        SwipeToDeleteContainer(
                            item = index,
                            onDelete = {
                                viewModel.deleteNote(notes[it])
                                viewModel.fetchNotes()
                            }
                        ) {
                            key(it) {
                                NoteCard(id = notes[it].id, title = notes[it].title, body = notes[it].body, visibility = notes[it].visible, color = notes[it].color, controller)
                            }
                        }
                    }
                }
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 40.dp, end = 20.dp)) {
        FloatingActionButton(onClick = { controller.navigate(Destinations.EditNotes.toString()) },
            modifier = Modifier.align(Alignment.BottomEnd),
            containerColor = Color(0xFF3B3B3B)) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Create Note FAB",
                tint = Color.White, modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun NoteCard(id: Int, title: String, body: String, visibility: Boolean, color: Int, controller: NavController) {
    Spacer(modifier = Modifier.size(8.dp))
    Card(modifier = Modifier
        .clickable { controller.navigate("${Destinations.ViewNotes}/$id/$title/$body/$color/$visibility") }
        .fillMaxWidth(1f)
        .heightIn(min = 100.dp, max = 250.dp),
        shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(
            containerColor = Color(color))
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            Column {
                Text(text = title,
                    color = Color.White, fontSize = 28.sp,
                    fontFamily = nunitoFont,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp))

                Text(text = body,
                    color = Color.White, fontSize = 18.sp,
                    fontFamily = nunitoFont,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
@Preview
fun NoNotes() {
    Box(modifier = Modifier
        .fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.createnote),
            contentDescription = "create note",
            modifier = Modifier
                .sizeIn(minWidth = 300.dp, minHeight = 300.dp)
                .align(Alignment.Center)
        )
        Text(
            text = "Create your first note!",
            color = Color.White,
            fontFamily = nunitoFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 300.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmValueChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                DeleteBackground(swipeDismissState = state)
            },
            dismissContent = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .heightIn(min = 100.dp, max = 250.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = secondary2)
                ) {
                    content(item)
                }
            },
            directions = setOf(DismissDirection.EndToStart)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(swipeDismissState: DismissState) {
    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Red
    } else secondary2

    Card(modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = color
        ), shape = RoundedCornerShape(15.dp))
    {
        Box(modifier = Modifier.fillMaxSize().padding(end = 20.dp),
            contentAlignment = Alignment.CenterEnd) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Swipe delete icon",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}