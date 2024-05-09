package com.ghostdev.stickynotes.nav

sealed class Destinations(val route: String) {
    data object Home: Destinations("home")
    data object About: Destinations("about")
    data object Search: Destinations("search")
    data object ViewNotes: Destinations("viewNote")
    data object EditNotes: Destinations("editNote")
}