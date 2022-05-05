package com.example.team25

class ElseBranch:Token() {
    var listOfTokenInElseBranch: MutableList<Token> = mutableListOf()
    fun addNewToken(token:Token){
        listOfTokenInElseBranch.add(token)
    }
}