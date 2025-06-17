package com.example.meta_habit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//Color default
val background = Color(0xFFF3F2F3)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFFFFEB3B)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

//Person color

val OrangeLight = Color(0xFFF8C8A5)
val BlueLight = Color(0xFFA8D4F8)
val GreenLight = Color(0xFFD8FAB5)
val RedLight = Color(0xFFF6A9A7)

val Gray50 = Color(0xFFD7D8DE)

@Composable
fun getColorsTextField(
    baseColor: Color
): TextFieldColors{
    return TextFieldDefaults.colors(
        // Colores del contenedor (fondo)
        focusedContainerColor =  MaterialTheme.colorScheme.background,
        unfocusedContainerColor = Color.Transparent,//MaterialTheme.colorScheme.background, // Un verde muy claro
        disabledContainerColor = Color.Transparent,//Color(0xFFF5F5F5), // Gris claro para deshabilitado
        errorContainerColor = Color(0xFFFFEBEE),   // Rosa muy claro para error

        // Colores del texto de entrada
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.DarkGray,
        disabledTextColor = Color.Gray,
        errorTextColor = Color.Red,

        // Colores del cursor
        cursorColor = Color.Blue, // El cursor cuando está enfocado
        errorCursorColor = Color.Red, // El cursor cuando hay un error

        // Colores del label (etiqueta)
        focusedLabelColor = Color.Blue,
        unfocusedLabelColor = Color.Transparent,
        disabledLabelColor = Color.LightGray,
        errorLabelColor = Color.Red,

        // Colores del placeholder
        focusedPlaceholderColor = Color.LightGray,
        unfocusedPlaceholderColor = Color.LightGray,
        disabledPlaceholderColor = Color.LightGray,
        errorPlaceholderColor = Color.Red,

        // Colores de los íconos (leadingIcon y trailingIcon)
        focusedLeadingIconColor = Color.Blue,
        unfocusedLeadingIconColor = Color.Gray,
        disabledLeadingIconColor = Color.LightGray,
        errorLeadingIconColor = Color.Red,

        focusedTrailingIconColor = Color.Blue,
        unfocusedTrailingIconColor = Color.Gray,
        disabledTrailingIconColor = Color.LightGray,
        errorTrailingIconColor = Color.Red,

        // Colores de la línea indicadora (subrayado)
        focusedIndicatorColor = Color.Blue,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Red,

        // Colores de la línea de ayuda (helper text)
        focusedSupportingTextColor = Color.DarkGray,
        unfocusedSupportingTextColor = Color.Gray,
        disabledSupportingTextColor = Color.LightGray,
        errorSupportingTextColor = Color.Red,

        // Colores del prefijo y sufijo
        focusedPrefixColor = Color.DarkGray,
        unfocusedPrefixColor = Color.Gray,
        disabledPrefixColor = Color.LightGray,
        errorPrefixColor = Color.Red,

        focusedSuffixColor = Color.DarkGray,
        unfocusedSuffixColor = Color.Gray,
        disabledSuffixColor = Color.LightGray,
        errorSuffixColor = Color.Red
    )
}