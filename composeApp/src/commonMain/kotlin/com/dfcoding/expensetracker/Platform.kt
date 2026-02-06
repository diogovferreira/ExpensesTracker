package com.dfcoding.expensetracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform