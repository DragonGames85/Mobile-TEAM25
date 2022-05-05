package com.example.team25

class Program(val listOfToken: MutableList<Token>) {
    var values: Map<String, Int> = mutableMapOf()
    var valueForPrint: MutableList<Int> = mutableListOf()
    private fun parseExpression(expression: String): Int{
        return 10;
    }
    fun print(expressionFromPrint: String){
        var ans:Int = parseExpression(expressionFromPrint)
    }



    private fun booleanParser(token: IfBranch):Boolean{
        val listOfComparisons = listOf("!=","==",">=","<=",">","<")
        val booleanExpression = token.boolExpression
        var firstExpression:String = ""
        var secondExpression: String = ""
        var comparison:String = ""
        for(i in listOfComparisons){
            if (booleanExpression.contains(i)){
                comparison = i
                firstExpression = booleanExpression.split(comparison)[0]
                secondExpression = booleanExpression.split(comparison)[1]
            }
        }
       return when(comparison){
           "!=" -> parseExpression(firstExpression)!=parseExpression(secondExpression)
           "==" -> parseExpression(firstExpression)==parseExpression(secondExpression)
           ">=" -> parseExpression(firstExpression)>=parseExpression(secondExpression)
           "<=" -> parseExpression(firstExpression)<=parseExpression(secondExpression)
           ">" -> parseExpression(firstExpression)>parseExpression(secondExpression)
           "<" -> parseExpression(firstExpression)<parseExpression(secondExpression)
           else -> false
        }
    }
    fun run(){
        for(token in listOfToken){
            if(token is Variable){
                values.plus(Pair(token.name, parseExpression(token.expression)))
            }
            if (token is Print){
                valueForPrint.add(parseExpression(token.expression))
            }



            if (token is IfBranch){
                val boolean = booleanParser(token)
                if (boolean){
                    val ifCodeProgram = Program(token.listOfTokenInIfBranch)
                    ifCodeProgram.values = values
                    ifCodeProgram.run()
                    valueForPrint += ifCodeProgram.valueForPrint
                }
                else{
                    val elseCodeProgram = Program(token.elseBranch.listOfTokenInElseBranch)
                    elseCodeProgram.values = values
                    elseCodeProgram.run()
                    valueForPrint+= elseCodeProgram.valueForPrint
                }
            }
        }
    }
}