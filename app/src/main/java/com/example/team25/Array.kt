package com.example.team25

import android.sax.Element
import java.lang.Exception
import kotlin.Array

class Array(val size:Int = 0, val name:String, val elements:String = ""):Token() {
    //var array = arrayListOf<Int>()

    var array = elements.split(",").map { i-> i.toInt() }.toIntArray()
    fun getElementsOfArray():IntArray{
        if(size!=0 && size!= array.size)
            throw Exception("Колличество элементов массива не  совпадает")
        if(elements=="" && size!=0){
            var emptyArray:IntArray = IntArray(size)
            for (i in 0 until size){
                emptyArray[i] = 0
            }
            return emptyArray
        }
        return array
    }
    fun changeElement(index:Int, newElement: Int){
        if (index in 0 until size){
            array[index] = newElement
        }
        else throw Exception("Выход за пределы массива")
    }
}