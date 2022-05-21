package com.example.team25

import java.util.*
import kotlin.collections.ArrayDeque

class Program(val listOfToken: MutableList<Token>) {
    var values= mutableMapOf<String,String>()
    var valueForPrint: MutableList<Int> = mutableListOf()
    var arrays = mutableMapOf<String,IntArray>()

    private fun parseExpression(expr: String): Int{
        val expression = normalizeString(expr) + ">"
        var counter = 0
        val steck = ArrayDeque<String>()
        val result = Vector<String>()
        var past = "+"

        steck.addLast("<")
        fun operation1(operator:String){
            steck.addLast(operator)
        }
        fun operation2(){
            val operator = steck.removeLast()
            result.addAll(listOf(operator));
            past = "#"
            counter -= 1
        }
        fun operation3(){
            var operator = steck.removeLast()
        }

        fun forPlus(this_operand:String) {
            val operand = steck.last()
            if(operand =="<" || operand ==">"){operation1(this_operand)}
            if (operand == "+" || operand == "-"){operation2()}
            if (operand == "*"){operation2()}
            if (operand == "/" || operand == "%"){operation2()}
            if (operand == "("){operation1(this_operand)}
        }
        fun forMultiplication(this_operand:String){
            val operand = steck.last()
            if(operand =="<" || operand ==">"){operation1("*")}
            if (operand == "+" || operand == "-"){operation1("*")}
            if (operand == "*"){operation2()}
            if (operand == "/" || operand == "%"){operation2()}
            if (operand == "("){operation1(this_operand)}
        }
        fun forDivision(this_operand:String){
            val operand = steck.last()
            if(operand =="<" || operand ==">"){operation1(this_operand)}
            if (operand == "+" || operand == "-"){operation1(this_operand)}
            if (operand == "*"){operation2()}
            if (operand == "/" || operand == "%"){ operation2()}
            if (operand == "("){operation1(this_operand)}
        }
        fun forEndString(this_operand:String){
            val operand = steck.last()
            if (operand == "+" || operand == "-"){operation2()}
            if (operand == "*"){operation2()}
            if (operand == "/" || operand == "%"){ operation2()}
            if (operand == ")"){print("expression input error")}
            if (operand == "("){print("expression input error")}
        }
        fun forBegin(this_operand:String){
            val operand = steck.last()
            if(operand =="<" || operand ==">"){operation1(this_operand)}
            if (operand == "+" || operand == "-"){operation1(this_operand)}
            if (operand == "*"){operation1(this_operand)}
            if (operand == "/" || operand == "%"){ operation1(this_operand)}
            if (operand == ")"){operation1(this_operand)}
        }
        fun forEnd(this_operand:String){
            val operand = steck.last()
            if(operand =="<" || operand ==">"){print("expression input error")}
            if (operand == "+" || operand == "-"){operation2()}
            if (operand == "*"){operation2()}
            if (operand == "/" || operand == "%"){ operation2()}
            if (operand == "("){operation3()}
        }
        fun forRemainder(this_operand:String){
            val operand = steck.last()
            if(operand =="<" || operand ==">"){operation1(this_operand)}
            if (operand == "+" || operand == "-"){operation1(this_operand)}
            if (operand == "*"){operation2()}
            if (operand == "/" || operand == "%"){ operation2()}
            if (operand == "("){operation1(this_operand)}
        }
        val specials:Map<String, (this_operand:String)->Unit> = mapOf(
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
        val length:Int = expression.length;
        val regex = Regex("[0-9]")
        while (counter < length ) {
            val current:Char = expression[counter]
            val str: String = current.toString()
            val regexResult = regex.containsMatchIn(str)
            if(regexResult){
                if(((counter+1) < length) && (regex.containsMatchIn(expression[counter+1].toString()))){
                    var str2:String = ""
                    while ((counter < length) && (regex.containsMatchIn(expression[counter].toString()))) {
                        str2 += expression[counter].toString()
                        counter += 1
                    }
                    counter -= 1
                    result.addAll(listOf(str2))
                }
                else{result.addAll(listOf(str))}
            }
            else {
                if((!regex.containsMatchIn(past)) && (str == "-") && (past != "#")){result.addAll(listOf("0"))}
                specials[str]?.invoke(str)
            }
            counter += 1
            if (past != "#"){past = str}
        }
        val arrayInvoice = ArrayDeque<Int>()
        fun plus(arrayInvoice:ArrayDeque<Int>){
            val a =
                arrayInvoice.removeLast()
            val b = arrayInvoice.removeLast()
            val c = a+b
            arrayInvoice.addLast(c)
        }
        fun minus(arrayInvoice:ArrayDeque<Int>){
            val a = arrayInvoice.removeLast()
            val b = arrayInvoice.removeLast()
            val c = b-a
            arrayInvoice.addLast(c)
        }
        fun multiplication(arrayInvoice:ArrayDeque<Int>){
            val a = arrayInvoice.removeLast()
            val b = arrayInvoice.removeLast()
            val c = b*a
            arrayInvoice.addLast(c)
        }
        fun division(arrayInvoice:ArrayDeque<Int>){
            val a = arrayInvoice.removeLast()
            val b = arrayInvoice.removeLast()
            val c = b/a
            arrayInvoice.addLast(c)
        }
        fun remainder(arrayInvoice:ArrayDeque<Int>){
            val a = arrayInvoice.removeLast()
            val b = arrayInvoice.removeLast()
            val c = b%a
            arrayInvoice.addLast(c)
        }
        val operand: Map<String, (arrayInvoice:ArrayDeque<Int>) -> Unit> = mapOf(
            "+" to ::plus,
            "-" to ::minus,
            "*" to ::multiplication,
            "/" to ::division,
            "%" to ::remainder
        )
        while(result.size > 0){
            val current2 = result.removeFirst()
            val regexResult = regex.containsMatchIn(current2)
            if(regexResult){
                val parsedInt = current2.toInt()
                arrayInvoice.addLast(parsedInt)
            }
            else{
                operand[current2]?.invoke(arrayInvoice)
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
        var firstExpression = ""
        var secondExpression = ""
        var comparison = ""
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
        return listOf(name, parseExpression(index).toString())
    }
    private fun normalizeString(expression: String):String{
        var newExpression = expression
        //Проверяем на наличие массивов
        var matchResultForArray = Regex("[a-z|A-Z]+(\\w)*(\\[)(([a-z|A-Z]+(\\w)*)|(\\d*))([\\+\\-\\/\\*\\%](([a-z|A-Z]+(\\w)*)|(\\d*)))*(\\])").find(newExpression)
        while(matchResultForArray != null){
            println(matchResultForArray.value)
            val parseString = parseArrayString(matchResultForArray.value)
            val name = parseString[0]
            val index = parseString[1]
            try {
                println(parseExpression(index))
                newExpression = newExpression.replaceFirst(
                    matchResultForArray.value,
                    arrays[name]!![parseExpression(index)].toString()
                )
                println(newExpression)
                matchResultForArray =
                    Regex("[a-z|A-Z]+(\\w)*(\\[)(([a-z|A-Z]+(\\w)*)|(\\d*))([\\+\\-\\/\\*\\%](([a-z|A-Z]+(\\w)*)|(\\d*)))*(\\])").find(
                        newExpression
                    )
            }
            catch (E:Exception){
                throw Exception("Такого элемента не существует")
            }
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
                    values[token.name] = parseExpression(token.expression).toString()
                }
            }
            if (token is Print){
                valueForPrint.add(parseExpression(token.expression))
            }

            if (token is Array){
                token.calculatedSize = parseExpression(token.size)
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
                    //values[token.name] = parseExpression(token.expression).toString()
                }
            }
            if (token is While){
                var boolean = booleanParser(token.boolExpression)
                while (boolean){
                    val whileCodeProgram = Program(token.listOfTokenInWhile)
                    whileCodeProgram.values = values
                    whileCodeProgram.arrays = arrays
                    whileCodeProgram.run()
                    valueForPrint += whileCodeProgram.valueForPrint
                    boolean = booleanParser(token.boolExpression)
                }
            }
            if (token is IfBranch){
                val boolean = booleanParser(token.boolExpression)

                if (boolean){
                    val ifCodeProgram = Program(token.listOfTokenInIfBranch)
                    ifCodeProgram.values = values
                    ifCodeProgram.arrays = arrays
                    ifCodeProgram.run()
                    valueForPrint += ifCodeProgram.valueForPrint
                }
                else{
                    val elseCodeProgram = Program(token.elseBranch.listOfTokenInElseBranch)
                    elseCodeProgram.values = values
                    elseCodeProgram.arrays = arrays
                    elseCodeProgram.run()
                    valueForPrint+= elseCodeProgram.valueForPrint
                }
            }
        }
    }
}