package com.example.team25

import android.sax.Element
import java.lang.Exception
import kotlin.Array

class Array(val size:String = "", val name:String, val elements:String = ""):Token() {
    //var array = arrayListOf<Int>()
    var array = elements.split(",").map { i-> i.toInt() }.toIntArray()
    var calculatedSize = size.toInt();
    fun getElementsOfArray():IntArray{
        if(calculatedSize !=0 && calculatedSize != array.size)
            throw Exception("Колличество элементов массива не совпадает")
        if(elements=="" && calculatedSize !=0){
            var emptyArray:IntArray = IntArray(calculatedSize )
            for (i in 0 until calculatedSize ){
                emptyArray[i] = 0
            }
            return emptyArray
        }
        return array
    }
    fun changeElement(index:Int, newElement: Int){
        if (index in 0 until calculatedSize ){
            array[index] = newElement
        }
        else throw Exception("Выход за пределы массива")
    }
}