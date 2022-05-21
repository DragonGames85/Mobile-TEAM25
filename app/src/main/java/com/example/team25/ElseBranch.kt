package com.example.team25

import android.util.Log

class ElseBranch:Token() {
    var listOfTokenInElseBranch: MutableList<Token> = mutableListOf()
    fun addNewToken(token:Token){
        Log.i("added: ", token.toString())
        listOfTokenInElseBranch.add(token)
    }
}