package com.example.team25

class IfBranch(val boolExpression:String, var elseBranch:ElseBranch = ElseBranch()):Token() {
    var listOfTokenInIfBranch: MutableList<Token> = mutableListOf()
    fun addNewToken(token:Token){
        listOfTokenInIfBranch.add(token)
    }
}