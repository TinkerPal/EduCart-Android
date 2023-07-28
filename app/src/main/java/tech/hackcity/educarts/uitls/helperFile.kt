package tech.hackcity.educarts.uitls

/**
 *Created by Victor Loveday on 7/7/23
 */

fun removeSpacesFromString(input: String): String {
    val hasSpaces = input.contains(" ")

    return if (hasSpaces) {
        input.replace(" ", "")
    } else {
        input
    }
}
