package com.terabyte.jwtnotes.ui.navigation

sealed class Route(val route: String) {

    data object CreateNoteRoute : Route("createNote")

    data object CreateNoteBdUiRoute : Route("createNoteBdUi")

    data class UpdateNoteRoute(val noteId: Int) : Route("updateNote/$noteId") {
        companion object {
            const val ROUTE_PLACEHOLDER = "updateNote/{noteId}"
        }
    }

    data object LoginRoute : Route("login")

    data object RegistrationRoute : Route("registration")

    data object ListNotesRoute : Route("listNotes")

    data object TokenExpiredRoute : Route("tokenExpired")
}