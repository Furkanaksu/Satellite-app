package com.furkan.satellite_app.utils.helper

import android.content.Context
import android.graphics.Typeface
import android.text.Html
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.furkan.satellite_app.R

fun isSearchable(input: String): Boolean {
    val letterCount = input.count()

    return letterCount == 0 || letterCount > 2
}

infix fun Context.isActive(status: Boolean): String {
    return if (status) {
        this.getString(R.string.active)
    } else {
        this.getString(R.string.passive)
    }
}

fun TextView.setTypeFace(style: TextStyle) {
    when (style) {
        TextStyle.NORMAL -> {
            this.setTypeface(null, Typeface.NORMAL)
        }
        TextStyle.BOLD -> {
            this.setTypeface(null, Typeface.BOLD)
        }
    }
}

@SuppressWarnings("deprecation")
fun TextView.partialBold(boldString: String, regularText: String?) {
    this.text =
        Html.fromHtml(
            "<b>".plus(boldString).plus("</b>".plus(regularText)),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
}

enum class TextStyle {
    NORMAL,
    BOLD
}

