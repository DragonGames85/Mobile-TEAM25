package com.example.team25

import android.util.Log
import java.util.*
import kotlin.collections.ArrayDeque

class Program(val listOfToken: MutableList<Token>) {
    var values= mutableMapOf<String,String>()
    var valueForPrint: MutableList<Int> = mutableListOf()
    var arrays = mutableMapOf<String,IntArray>()

    private fun parseExpression(expr: String): Int{
        val expression = normalizeString(expr) + ">"
        Log.i("Parse", expression)
        var counter = 0
        var steck = ArrayDeque<String>()
        var result = Vector<String>()
        var past = "+"

        steck.addLast("<")
        fun Operation_1(operator:String){
            steck.addLast(operator)
        }
        fun Operation_2(){
            var operator = steck.removeLast()
            result.addAll(listOf(operator));
            past = "#"
            counter = counter - 1
        }
        fun Operation_3(){
            var operator = steck.removeLast()
        }

        fun forPlus(this_operand:String) {
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_2()}
            if (operand == "*"){Operation_2()}
            if (operand == "/" || operand == "%"){Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        fun forMultiplication(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1("*")}
            if (operand == "+" || operand == "-"){Operation_1("*")}
            if (operand == "*"){Operation_2()}
            if (operand == "/" || operand == "%"){Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        fun forDivision(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_1(this_operand)}
            if (operand == "*"){Operation_2()}
            if (operand == "/" || operand == "%"){ Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        fun forEndString(this_operand:String){
            var operand = steck.last()
            if (operand == "+" || operand == "-"){Operation_2()}
            if (operand == "*"){Operation_2()}
            if (operand == "/" || operand == "%"){ Operation_2()}
            if (operand == ")"){print("expression input error")}
            if (operand == "("){print("expression input error")}
        }
        fun forBegin(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_1(this_operand)}
            if (operand == "*"){Operation_1(this_operand)}
            if (operand == "/" || operand == "%"){ Operation_1(this_operand)}
            if (operand == ")"){Operation_1(this_operand)}
        }
        fun forEnd(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){print("expression input error")}
            if (operand == "+" || operand == "-"){Operation_2()}
            if (operand == "*"){Operation_2()}
            if (operand == "/" || operand == "%"){ Operation_2()}
            if (operand == "("){Operation_3()}
        }
        fun forRemainder(this_operand:String){
            var operand = steck.last()
            if(operand =="<" || operand ==">"){Operation_1(this_operand)}
            if (operand == "+" || operand == "-"){Operation_1(this_operand)}
            if (operand == "*"){Operation_2()}
            if (operand == "/" || operand == "%"){ Operation_2()}
            if (operand == "("){Operation_1(this_operand)}
        }
        var specials:Map<String, (this_operand:String)->Unit> = mapOf(
            "+" to ::forPlus,
            "-" to ::forPlus,
            "*" to ::forMultiplication,
            "/" to ::forDivision,
            "<" to ::forEndString,
            ">" to ::forEndString,
            "(" to ::forBegin,
            ")" to ::forEnd,
            "%" to ::forRemainder
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
                if((!regex.containsMatchIn(past)) && (str == "-") && (past != "#")){result.addAll(listOf("0"))}
                specials[str]?.invoke(str)
            }
            counter = counter+1
            if (past != "#"){past = str}
        }
        var arrayInvoice = ArrayDeque<Int>()
        fun Plus(arrayInvoice:ArrayDeque<Int>){
            var a =
                arrayInvoice.removeLast()
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
        fun Remainder(arrayInvoice:ArrayDeque<Int>){
            var a = arrayInvoice.removeLast()
            var b = arrayInvoice.removeLast()
            var c = b%a
            arrayInvoice.addLast(c)
        }
        var operand: Map<String, (arrayInvoice:ArrayDeque<Int>) -> Unit> = mapOf(
            "+" to ::Plus,
            "-" to ::Minus,
            "*" to ::Multiplication,
            "/" to ::Division,
            "%" to ::Remainder
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

    private fun booleanParser(booleanExpression: String):Boolean{
        val listOfComparisons = listOf("!=","==",">=","<=",">","<")
        //val booleanExpression = token.boolExpression
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
    private fun parseArrayString(arrayString:String):List<String>{
        val parseList = arrayString.split("[")
        val name = parseList[0]
        var index = ""
        for (i in parseList[1]){
            if (i!=']') index+=i
        }
        return listOf(name, index)
    }
   private fun normalizeString(expression: String):String{
       var newExpression = expression
       //Проверяем на наличие массивов
       var matchResultForArray = Regex("[a-z|A-Z]+(\\w)*(\\[)(([a-z|A-Z]+(\\w)*)|(\\d*))(\\])").find(newExpression)
       while(matchResultForArray  != null){
           val parseString = parseArrayString(matchResultForArray.value)
           val name = parseString[0]
           val index = parseString[1]
           newExpression = newExpression.replaceFirst(matchResultForArray .value, arrays[name]!![parseExpression(index)].toString())
           println(newExpression)
           matchResultForArray  = Regex("[a-z|A-Z]+(\\w)*").find(newExpression)
       }
       //Проверяем на наличие переменных
       var matchResult = Regex("[a-z|A-Z]+(\\w)*").find(newExpression)
       while(matchResult != null){
           newExpression = newExpression.replaceFirst(matchResult.value, values[matchResult.value].toString())
           println(newExpression)
           matchResult = Regex("[a-z|A-Z]+(\\w)*").find(newExpression)
       }
       return newExpression
   }
    fun print():String{
        var answer = ""
        for (i in valueForPrint){
            answer+=(i.toString())
            answer+=" "
        }
        return answer
    }
    fun run(){
        for(token in listOfToken){
            if(token is Variable){
                if(values.contains(token.name)){
                    values[token.name] = parseExpression(token.expression).toString()
                }
                else{
                    Log.i("Token Name",token.name)
                    Log.i("Token Exp",parseExpression(token.expression).toString())
                    values[token.name] = parseExpression(token.expression).toString()
                }
            }
            if (token is Print){
                valueForPrint.add(parseExpression(token.expression))
            }

            if (token is Array){
                arrays[token.name] = token.getElementsOfArray()
            }

            if(token is ArithmeticOperations){
                val nameOfVariableOrArray = token.name
                val expressionValue = parseExpression(token.expression)
                if (nameOfVariableOrArray.contains("[")){
                    val parseString = parseArrayString(nameOfVariableOrArray)
                    val name = parseString[0]
                    val index = parseString[1]
                    arrays[name]!![parseExpression(index)] = expressionValue
                }
                else{
                    values[token.name] = parseExpression(token.expression).toString()
                }
            }
            if (token is While){
                val boolean = booleanParser(token.boolExpression)
                val whileCodeProgram = Program(token.listOfTokenInWhile)
                while (boolean){
                    whileCodeProgram.values = values
                    whileCodeProgram.run()
                    valueForPrint += whileCodeProgram.valueForPrint
                }
            }
            if (token is IfBranch){
                val boolean = booleanParser(token.boolExpression)
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