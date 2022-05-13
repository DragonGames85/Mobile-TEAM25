package com.example.team25

import java.util.*
import kotlin.collections.ArrayDeque

class Program(val listOfToken: MutableList<Token>) {
    var values= mutableMapOf<String,Int>()
    var valueForPrint: MutableList<Int> = mutableListOf()

    private fun parseExpression(expr: String): Int{
        val expression = normalizeString(expr)
        var counter = 0
        var steck = ArrayDeque<String>()
        var result = Vector<String>()

        steck.addLast("<")
        fun Operation_1(operator:String){
            steck.addLast(operator)
        }
        fun Operation_2(){
            var operator = steck.removeLast()
            result.addAll(listOf(operator));
            counter = counter - 1
        }
        fun Operation_3(){
            var operator = steck.removeLast()
        }
        fun Operation_4(){
            print(result)
        }
        fun forPlus(this_operand:String) {
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_2()}
            if (operand == "*"){Operation_2()}
            if (operand == "/"){Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        fun forMultiplication(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1("*")}
            if (operand == "+" || operand == "-"){Operation_1("*")}
            if (operand == "*"){Operation_2()}
            if (operand == "/"){Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        fun forDivision(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_1(this_operand)}
            if (operand == "*"){Operation_2()}
            if (operand == "/"){ Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        fun forEndString(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_4()}
            if (operand == "+" || operand == "-"){Operation_2()}
            if (operand == "*"){Operation_2()}
            if (operand == "/"){ Operation_2()}
            if (operand == ")"){print("expression input error")}
            if (operand == "("){print("expression input error")}
        }
        fun forBegin(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_1(this_operand)}
            if (operand == "*"){Operation_1(this_operand)}
            if (operand == "/"){ Operation_1(this_operand)}
            if (operand == ")"){Operation_1(this_operand)}
        }
        fun forEnd(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){print("expression input error")}
            if (operand == "+" || operand == "-"){Operation_2()}
            if (operand == "*"){Operation_2()}
            if (operand == "/"){ Operation_2()}
            if (operand == "("){Operation_3()}
        }
        var specials:Map<String, (this_operand:String)->Unit> = mapOf(
            "+" to ::forPlus,
            "-" to ::forPlus,
            "*" to ::forMultiplication,
            "/" to ::forDivision,
            "<" to ::forEndString,
            ">" to ::forEndString,
            "(" to ::forBegin,
            ")" to ::forEnd
        )
        var length:Int = expression.length;
        val regex = Regex("[0-9]")
        while (counter < length ) {
            var current:Char = expression[counter]
            var str: String = current.toString()
            val regex_result = regex.containsMatchIn(str)
            if(regex_result){
                if(((counter+1) < length) && (regex.containsMatchIn(expression[counter+1].toString()))){
                    var str_2:String = ""
                    while ((counter < length) && (regex.containsMatchIn(expression[counter].toString()))) {
                        str_2 += expression[counter].toString()
                        counter = counter + 1
                    }
                    counter = counter - 1
                    result.addAll(listOf(str_2))
                }
                else{result.addAll(listOf(str))}
            }
            else {
                specials[str]?.invoke(str)
            }
            counter += 1
        }
        var arrayInvoice = ArrayDeque<Int>()
        fun Plus(arrayInvoice:ArrayDeque<Int>){
            var a = arrayInvoice.removeLast()
            var b = arrayInvoice.removeLast()
            var c = a+b
            arrayInvoice.addLast(c)
        }
        fun Minus(arrayInvoice:ArrayDeque<Int>){
            var a = arrayInvoice.removeLast()
            var b = arrayInvoice.removeLast()
            var c = b-a
            arrayInvoice.addLast(c)
        }
        fun Multiplication(arrayInvoice:ArrayDeque<Int>){
            var a = arrayInvoice.removeLast()
            var b = arrayInvoice.removeLast()
            var c = b*a
            arrayInvoice.addLast(c)
        }
        fun Division(arrayInvoice:ArrayDeque<Int>){
            var a = arrayInvoice.removeLast()
            var b = arrayInvoice.removeLast()
            var c = b/a
            arrayInvoice.addLast(c)
        }
        var operand: Map<String, (arrayInvoice:ArrayDeque<Int>) -> Unit> = mapOf(
            "+" to ::Plus,
            "-" to ::Minus,
            "*" to ::Multiplication,
            "/" to ::Division
        )
        while(result.size > 0){
            var current_2 = result.removeFirst()
            val regex_result = regex.containsMatchIn(current_2)
            if(regex_result){
                val parsedInt = current_2.toInt()
                arrayInvoice.addLast(parsedInt)
            }
            else{
                operand[current_2]?.invoke(arrayInvoice)
            }
        }
       return arrayInvoice.last()
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
    private fun normalizeString(expression: String):String{
        val allFinds =  Regex("[a-z|A-Z]+(\\w)*").findAll(expression,0)
        var newExpression = ""
        for (i in allFinds){
            println(i.value)
            println(i.value)
            newExpression = expression.replace(i.value, values[i.value].toString())
        }
        return newExpression
    }
    fun print():String{
        var answer = ""
        for (i in valueForPrint){
            answer+=(i.toString()+"\n")
        }
        return answer
    }
    fun run(){
        for(token in listOfToken){
            if(token is Variable){
                if(values.contains(token.name)){
                    values[token.name] = parseExpression(token.expression)
                }
                else
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