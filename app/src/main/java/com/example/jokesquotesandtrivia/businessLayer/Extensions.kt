package com.example.jokesquotesandtrivia.businessLayer

fun String.cleanText(): String {
    return this.replace("&quot;", "\"")
        .replace("&#039;", "\'")
        .replace("&amp;", "&")
}