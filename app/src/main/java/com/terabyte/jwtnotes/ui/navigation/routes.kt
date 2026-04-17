package com.terabyte.jwtnotes.ui.navigation

sealed class Route(val route: String) {

    data object CreateNoteRoute : Route("createNote")

    data object UpdateNoteRoute : Route("updateNote")

    data object LoginRoute : Route("login")

    data object RegistrationRoute : Route("registration")

    data object ListNotesRoute : Route("listNotes")
}