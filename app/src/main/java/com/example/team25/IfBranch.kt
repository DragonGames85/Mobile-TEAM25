package com.example.team25

import android.util.Log

class IfBranch(val boolExpression:String, var elseBranch:ElseBranch = ElseBranch()):Token() {
    var listOfTokenInIfBranch: MutableList<Token> = mutableListOf()
    fun addNewToken(token:Token){
        listOfTokenInIfBranch.add(token)
    }
    fun giveList(): MutableList<Token> {
        return listOfTokenInIfBranch
    }
    fun NewList(x: MutableList<Token>) {
        listOfTokenInIfBranch = x
    }
    fun giveName(): String {
        return boolExpression
    }
}