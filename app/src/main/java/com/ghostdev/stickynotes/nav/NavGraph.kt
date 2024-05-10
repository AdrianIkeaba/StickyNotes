package com.ghostdev.stickynotes.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ghostdev.stickynotes.presentation.NoteViewModel
import com.ghostdev.stickynotes.ui.MainUI
import com.ghostdev.stickynotes.ui.NoteEditorUI
import com.ghostdev.stickynotes.ui.SearchUI
import com.ghostdev.stickynotes.ui.ViewNoteUI

@Composable
fun NavGraph(viewModel: NoteViewModel) {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = Destinations.Home.toString()) {
        composable(route = Destinations.Home.toString()) {
            MainUI(viewModel = viewModel, controller = controller)
        }

        composable(route = Destinations.Search.toString()) {
            SearchUI(viewModel = viewModel, controller = controller)
        }

        composable(route = "${Destinations.EditNotes}/{noteId}/{noteTitle}/{noteBody}/{noteColor}/{noteVisibility}",
            arguments = listOf(
                navArgument("noteId") {type = NavType.IntType},
                navArgument("noteTitle") {type = NavType.StringType},
                navArgument("noteBody") {type = NavType.StringType},
                navArgument("noteColor") {type = NavType.IntType},
                navArgument("noteVisibility") {type = NavType.BoolType}
            )) {
            val noteId = it.arguments?.getInt("noteId")
            val noteTitle = it.arguments?.getString("noteTitle")
            val noteBody = it.arguments?.getString("noteBody")
            val noteColor = it.arguments?.getInt("noteColor")
            val noteVisibility = it.arguments?.getBoolean("noteVisibility")
            NoteEditorUI(viewModel = viewModel, controller = controller, noteId, noteTitle, noteBody, noteColor, noteVisibility)
        }

        composable(route = "${Destinations.EditNotes}") {
            NoteEditorUI(viewModel = viewModel, controller = controller, null, null, null, null, null)
        }

        composable(route = "${Destinations.ViewNotes}/{noteId}/{noteTitle}/{noteBody}/{noteColor}/{noteVisibility}",
            arguments = listOf(
                navArgument("noteId") {type = NavType.IntType},
                navArgument("noteTitle") {type = NavType.StringType},
                navArgument("noteBody") {type = NavType.StringType},
                navArgument("noteColor") {type = NavType.IntType},
                navArgument("noteVisibility") {type = NavType.BoolType}
            )) {
            val noteId = it.arguments?.getInt("noteId")
            val noteTitle = it.arguments?.getString("noteTitle")
            val noteBody = it.arguments?.getString("noteBody")
            val noteColor = it.arguments?.getInt("noteColor")
            val noteVisibility = it.arguments?.getBoolean("noteVisibility")
            ViewNoteUI(viewModel = viewModel, controller = controller, noteId, noteTitle, noteBody, noteColor, noteVisibility)
        }
    }
}