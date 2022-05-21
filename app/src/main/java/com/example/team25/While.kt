package com.example.team25

class While(val boolExpression:String):Token() {
    var listOfTokenInWhile: MutableList<Token> = mutableListOf()
    fun addNewToken(token:Token){
        listOfTokenInWhile.add(token)
    }
}