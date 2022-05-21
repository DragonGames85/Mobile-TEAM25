package com.example.team25
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.team25.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    @SuppressLint("SetTextI18n")
    var turn = false
    var Inputs = arrayOfNulls<String>(1000)
    var InputIndex = 0
    var Oks = 0
    var InputHiders = 101
    var stepCons =250f
    val PosledovatelY_copy = arrayOfNulls<Float>(1000)
    val PosledovatelText_copy = arrayOfNulls<String>(1000)
    val PosledovatelType_copy = arrayOfNulls<Int>(1000)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.launch -> {
                if (i != 0 && PosledovatelY[0] != null) {
                    for (t in 0 until i) {
                        PosledovatelY_copy[t] = PosledovatelY[t]
                        PosledovatelText_copy[t] = PosledovatelText[t]
                        PosledovatelType_copy[t] = PosledovatelType[t]
                    }
                    for (t in 0..i - 1) {
                        for (k in t + 1 until i) {
                            if (PosledovatelY_copy[t]!! >= PosledovatelY_copy[k]!!) {
                                val tempY = PosledovatelY_copy[k]
                                PosledovatelY_copy[k] = PosledovatelY_copy[t]
                                PosledovatelY_copy[t] = tempY
                                val tempText = PosledovatelText_copy[k]
                                PosledovatelText_copy[k] = PosledovatelText_copy[t]
                                PosledovatelText_copy[t] = tempText
                                val tempType = PosledovatelType_copy[k]
                                PosledovatelType_copy[k] = PosledovatelType_copy[t]
                                PosledovatelType_copy[t] = tempType
                            }
                        }
                    }
                    stepCons =250f
                    val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
                    for (t in 0 until i) {
                        if (PosledovatelType_copy[t] == 10) {
                            val EtInputer = EditText(this)
                            val InputingVar = TextView(this)
                            val ImageInputerOk = ImageView(this)
                            ImageInputerOk.setId(InputHiders)
                            InputHiders++
                            InputingVar.setId(InputHiders)
                            InputHiders++
                            EtInputer.setId(InputHiders)
                            InputHiders++
                            EtInputer.hint = "8"
                            deleters+=3
                            ImageInputerOk.visibility = View.GONE
                            EtInputer.visibility = View.GONE
                            InputingVar.visibility = View.GONE

                            ImageInputerOk.setImageResource(R.drawable.ic_ok)
                            binding.printer.text = ""
                            ImageInputerOk.setOnClickListener(){
                                val Temp = ImageInputerOk.getId()
                                val temp2 = InputingVar.getId()
                                if (Inputs[Temp] != "True") {
                                    Inputs[Temp] = "True"
                                    InputIndex++
                                    PosledovatelText_copy[t] = PosledovatelText_copy[t] + " " + EtInputer.text
                                    Oks++
                                    Inputs[temp2] = PosledovatelText_copy[t]
                                    if (Oks == (InputHiders-101)/3) ProgramStart()
                                    binding.printer.y = binding.printer.y+150
                                }
                                else {
                                    binding.printer.text = ""
                                    PosledovatelText_copy[t] = Inputs[temp2] + " " + EtInputer.text
                                    if (Oks == (InputHiders-101)/3) ProgramStart()
                                }
                            }

                            InputingVar.text = "Введите значение "+ PosledovatelText_copy[t]
                            InputingVar.y = stepCons
                            ImageInputerOk.y = stepCons+50
                            EtInputer.y = stepCons+30
                            InputingVar.x = 0f
                            EtInputer.x = InputingVar.x+125

                            mainLayout.addView(InputingVar)
                            mainLayout.addView(ImageInputerOk,100,100)
                            mainLayout.addView(EtInputer)
                            stepCons+=150
                        }
                    }
                    if (Oks == (InputHiders-101)/3) ProgramStart()
                }
            }
            R.id.clear -> {
                val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
                if (i != 0) mainLayout.removeViewsInLayout(22,deleters)
                PosledovatelY = arrayOfNulls<Float>(1000)
                PosledovatelText = arrayOfNulls<String>(1000)
                PosledovatelType = arrayOfNulls<Int>(1000)
                i=0
                deleters=0
                stepY = 250f
                binding.TVposled.text = ""
                Inputs = arrayOfNulls<String>(1000)
                InputIndex = 0
                Oks = 0
                InputHiders = 101
                stepCons =250f
            }
            R.id.console -> {
                if (!turn) {
                    turn = true
                    var ViewGoner : View
                    for (y in 0..100) {
                        if (binding.mainLay.getViewById(y) != null && binding.mainLay.getViewById(y).visibility == View.VISIBLE) {
                        ViewGoner = binding.mainLay.getViewById(y)
                        ViewGoner.visibility = View.GONE
                        }
                    }
                    for (y in 101..300) {
                        if (binding.mainLay.getViewById(y) != null && binding.mainLay.getViewById(y).visibility == View.GONE) {
                            ViewGoner = binding.mainLay.getViewById(y)
                            ViewGoner.visibility = View.VISIBLE
                        }
                    }
                    binding.TVposled.visibility = View.GONE
                    binding.printer.visibility = View.VISIBLE
                    binding.imgconsole.visibility = View.VISIBLE
                    binding.button13.visibility  = View.GONE
                    binding.button14.visibility  = View.GONE
                    binding.button15.visibility  = View.GONE
                    binding.button16.visibility  = View.GONE

                    binding.button3.visibility  = View.GONE
                    binding.button9.visibility  = View.GONE
                    binding.button4.visibility  = View.GONE
                    binding.varButton2.visibility  = View.GONE
                }
                else if (turn) {
                    var ViewGoner : View
                    for (y in 0..100) {
                        if (binding.mainLay.getViewById(y) != null && binding.mainLay.getViewById(y).visibility == View.GONE) {
                        ViewGoner = binding.mainLay.getViewById(y)
                        ViewGoner.visibility = View.VISIBLE
                    }}
                    for (y in 101..300) {
                        if (binding.mainLay.getViewById(y) != null && binding.mainLay.getViewById(y).visibility == View.VISIBLE) {
                            ViewGoner = binding.mainLay.getViewById(y)
                            ViewGoner.visibility = View.GONE
                        }
                    }
                    binding.imgconsole.visibility = View.GONE
                    binding.button13.visibility  = View.VISIBLE
                    binding.button14.visibility  = View.VISIBLE
                    binding.button15.visibility  = View.VISIBLE
                    binding.button16.visibility  = View.VISIBLE

                    binding.button3.visibility  = View.VISIBLE
                    binding.button9.visibility  = View.VISIBLE
                    binding.button4.visibility  = View.VISIBLE
                    binding.varButton2.visibility  = View.VISIBLE
                    binding.printer.visibility = View.GONE
                    turn = false
                }
            }
        }
        return true
    }

    var i: Int = 0
    var hider = 50
    var PosledovatelY = arrayOfNulls<Float>(1000)
    var PosledovatelText = arrayOfNulls<String>(1000)
    var PosledovatelType = arrayOfNulls<Int>(1000)
    var stepY =250f
    var deleters = 0

    fun remove(arr: IntArray, index: Int): IntArray {
        if (index < 0 || index >= arr.size) {
            return arr
        }

        val result = arr.toMutableList()
        result.removeAt(index)
        return result.toIntArray()
    }

    fun showPosl(){
        var checker = true
        for (t in 0..i - 1) {if (PosledovatelType[t] == null) checker = false}
        if (checker) {
            binding.TVposled.visibility = View.VISIBLE
            binding.TVposled.text = "Блоки готовы"
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)
    fun createVar(view : View) {
        val ImageVar = ImageView(this)
        val ImageVarOk = ImageView(this)
        val EtVar = EditText(this)
        deleters+=3
        EtVar.hint = "a = 10"
        ImageVar.setId(i+1)
        ImageVarOk.setId(hider)
        hider++
        EtVar.setId(hider)
        hider++
        i++
        var dy = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2
                EtVar.x=ImageVar.x+250
                EtVar.y = ImageVar.y+10
                ImageVarOk.x = ImageVar.x+150
                ImageVarOk.y = ImageVar.y+25
                stepY = ImageVar.y + 150
                val ide = ImageVar.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageVarOk.setOnClickListener() {
            val ide = ImageVar.getId()
            PosledovatelY[ide-1] = ImageVar.y
            PosledovatelText[ide-1] = EtVar.text.toString()
            PosledovatelType[ide-1] = 0
            showPosl()
        }
        ImageVar.setImageResource(R.drawable.ic_var)
        ImageVarOk.setImageResource(R.drawable.ic_ok)
        ImageVar.setOnTouchListener(listener)
        ImageVar.y = stepY
        ImageVarOk.x = ImageVar.x+150
        ImageVarOk.y = ImageVar.y+25
        EtVar.x=ImageVar.x+250
        EtVar.y = ImageVar.y+10
        mainLayout.addView(ImageVarOk,100,100)
        mainLayout.addView(ImageVar,150,150)
        mainLayout.addView(EtVar)
        stepY = ImageVar.y + 150
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createAri(view : View) {
        val ImageAri = ImageView(this)
        val ImageAriOk = ImageView(this)
        val EtAri = EditText(this)
        deleters+=3
        EtAri.hint = "a = b + 5"
        ImageAri.setId(i+1)
        EtAri.setId(hider)
        hider++
        ImageAriOk.setId(hider)
        hider++
        i++
        var dy = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2

                EtAri.x=ImageAri.x+250
                EtAri.y = ImageAri.y+10

                ImageAriOk.x = ImageAri.x+150
                ImageAriOk.y = ImageAri.y+25

                val ide = ImageAri.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
            }
        )
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageAriOk.setOnClickListener() {
            val ide = ImageAri.getId()
            PosledovatelY[ide-1] = ImageAri.y
            PosledovatelText[ide-1] = EtAri.text.toString()
            PosledovatelType[ide-1] = 1
            showPosl()
        }
        ImageAri.setImageResource(R.drawable.ic_ari)
        ImageAriOk.setImageResource(R.drawable.ic_ok)
        ImageAri.setOnTouchListener(listener)
        ImageAri.y = stepY
        ImageAriOk.x = ImageAri.x+150
        ImageAriOk.y = ImageAri.y+25
        EtAri.x=ImageAri.x+250
        EtAri.y = ImageAri.y+10
        mainLayout.addView(ImageAriOk,100,100)
        mainLayout.addView(ImageAri,150,150)
        mainLayout.addView(EtAri)
        stepY = ImageAri.y + 150
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createIf(view : View) {
        val ImageIf = ImageView(this)
        val ImageIfOk = ImageView(this)

        val ImageBegin = ImageView(this)
        val ImageEnd = ImageView(this)

        val EtIf = EditText(this)

        deleters+=5

        ImageIf.setImageResource(R.drawable.ic_if)
        ImageIfOk.setImageResource(R.drawable.ic_ok)
        ImageBegin.setImageResource(R.drawable.begin)
        ImageEnd.setImageResource(R.drawable.end)

        EtIf.hint = "a > 5"

        ImageIf.setId(i+1)
        i++
        ImageBegin.setId(i+1)
        i++
        ImageEnd.setId(i+1)
        i++
        EtIf.setId(hider)
        hider++
        ImageIfOk.setId(hider)
        hider++
        var dy = 0f
        var d2y = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2

                EtIf.x = ImageIf.x+250
                EtIf.y = ImageIf.y+10

                ImageIfOk.x = ImageIf.x+150
                ImageIfOk.y = ImageIf.y+25

                ImageBegin.x = ImageIf.x+100
                ImageBegin.y = ImageIf.y+100

                ImageEnd.x = ImageIf.x+100
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100

                val ide = ImageIf.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                if (ImageBegin.y+100 < motionEvent.rawY + d2y) view.y = motionEvent.rawY + d2y
                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                d2y =view.y - motionEvent.rawY
            }
            true
        })

        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageIfOk.setOnClickListener() {
            val ide = ImageIf.getId()
            PosledovatelY[ide-1] = ImageIf.y
            PosledovatelText[ide-1] = "${EtIf.text}"
            PosledovatelType[ide-1] = 2
            val ide1 = ImageBegin.getId()
            PosledovatelY[ide1-1] = ImageBegin.y
            PosledovatelText[ide1-1] = "{"
            PosledovatelType[ide1-1] = 3
            val ide2 = ImageEnd.getId()
            PosledovatelY[ide2-1] = ImageEnd.y
            PosledovatelText[ide2-1] = "}"
            PosledovatelType[ide2-1] = 4
            showPosl()
        }

        ImageIf.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageIf.y = stepY

        ImageIfOk.x = ImageIf.x+150
        ImageIfOk.y = ImageIf.y+25

        ImageBegin.x = ImageIf.x+100
        ImageBegin.y = ImageIf.y+100

        ImageEnd.x = ImageIf.x+100
        ImageEnd.y = ImageIf.y+200

        EtIf.x = ImageIf.x+250
        EtIf.y = ImageIf.y+10

        mainLayout.addView(ImageIfOk,100,100)
        mainLayout.addView(ImageIf,150,150)
        mainLayout.addView(ImageBegin,125,125)
        mainLayout.addView(ImageEnd,125,125)
        mainLayout.addView(EtIf)

        stepY = ImageEnd.y + 50
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createElse(view : View) {
        val ImageElse = ImageView(this)
        val ImageElseOk = ImageView(this)
        val ImageBegin = ImageView(this)
        val ImageEnd = ImageView(this)
        ImageBegin.setImageResource(R.drawable.begin)
        ImageEnd.setImageResource(R.drawable.end)
        deleters+=4
        ImageElse.setId(i+1)
        i++
        ImageBegin.setId(i+1)
        i++
        ImageEnd.setId(i+1)
        i++
        ImageElseOk.setId(hider)
        hider++
        var dy = 0f
        var d2y = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2
                ImageElseOk.x = ImageElse.x+150
                ImageElseOk.y = ImageElse.y+25
                ImageBegin.x = ImageElse.x+100
                ImageBegin.y = ImageElse.y+100
                ImageEnd.x = ImageElse.x+100
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100
                val ide = ImageElse.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                if (ImageBegin.y+100 < motionEvent.rawY + d2y) view.y = motionEvent.rawY + d2y

                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                d2y =view.y - motionEvent.rawY
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageElseOk.setOnClickListener() {
            val ide = ImageElse.getId()
            PosledovatelY[ide-1] = ImageElse.y
            PosledovatelText[ide-1] = "Else"
            PosledovatelType[ide-1] = 5
            val ide1 = ImageBegin.getId()
            PosledovatelY[ide1-1] = ImageBegin.y
            PosledovatelText[ide1-1] = "{"
            PosledovatelType[ide1-1] = 6
            val ide2 = ImageEnd.getId()
            PosledovatelY[ide2-1] = ImageEnd.y
            PosledovatelText[ide2-1] = "}"
            PosledovatelType[ide2-1] = 7
            showPosl()
        }
        ImageElse.setImageResource(R.drawable.ic_else)
        ImageElseOk.setImageResource(R.drawable.ic_ok)
        ImageElse.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageElse.y = stepY
        ImageElseOk.x = ImageElse.x+150
        ImageElseOk.y = ImageElse.y+25
        ImageBegin.x = ImageElse.x+100
        ImageBegin.y = ImageElse.y+100

        ImageEnd.x = ImageElse.x+100
        ImageEnd.y = ImageElse.y+200

        mainLayout.addView(ImageElseOk,100,100)
        mainLayout.addView(ImageElse,150,150)
        mainLayout.addView(ImageBegin,125,125)
        mainLayout.addView(ImageEnd,125,125)
        stepY = ImageEnd.y + 50
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createInput(view : View) {
        val ImageInput = ImageView(this)
        val ImageInputOk = ImageView(this)
        val EtInput = EditText(this)
        deleters+=3
        EtInput.hint = "a"
        ImageInput.setId(i+1)
        ImageInputOk.setId(hider)
        hider++
        EtInput.setId(hider)
        hider++
        i++
        var dy = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2
                EtInput.x=ImageInput.x+300
                EtInput.y = ImageInput.y+35
                ImageInputOk.x = ImageInput.x+200
                ImageInputOk.y = ImageInput.y+50

                val ide = ImageInput.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageInputOk.setOnClickListener() {
            val ide = ImageInput.getId()
            PosledovatelY[ide-1] = ImageInput.y
            PosledovatelText[ide-1] = EtInput.text.toString()
            PosledovatelType[ide-1] = 10
            showPosl()
        }
        ImageInput.setImageResource(R.drawable.input_icon)
        ImageInputOk.setImageResource(R.drawable.ic_ok)
        ImageInput.setOnTouchListener(listener)
        ImageInput.y = stepY
        ImageInputOk.x = ImageInput.x+200
        ImageInputOk.y = ImageInput.y+50
        EtInput.x=ImageInput.x+300
        EtInput.y = ImageInput.y+25
        mainLayout.addView(ImageInputOk,100,100)
        mainLayout.addView(ImageInput,200,200)
        mainLayout.addView(EtInput)
        stepY = ImageInput.y + 150
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createWhile(view : View) {
        val ImageWhile = ImageView(this)
        val ImageWhileOk = ImageView(this)
        val ImageBegin = ImageView(this)
        val ImageEnd = ImageView(this)
        val EtWhile = EditText(this)
        EtWhile.hint = "a < 10"
        deleters+=5
        ImageWhile.setId(i+1)
        i++
        ImageBegin.setId(i+1)
        i++
        ImageEnd.setId(i+1)
        i++
        EtWhile.setId(hider)
        hider++
        ImageWhileOk.setId(hider)
        hider++
        var dy = 0f
        var d2y = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2

                EtWhile.x = ImageWhile.x+250
                EtWhile.y = ImageWhile.y+10

                ImageWhileOk.x = ImageWhile.x+150
                ImageWhileOk.y = ImageWhile.y+25

                ImageBegin.x = ImageWhile.x+100
                ImageBegin.y = ImageWhile.y+100

                ImageEnd.x = ImageWhile.x+100
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100

                val ide = ImageWhile.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                if (ImageBegin.y+100 < motionEvent.rawY + d2y) view.y = motionEvent.rawY + d2y

                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                d2y =view.y - motionEvent.rawY
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageWhileOk.setOnClickListener() {
            val ide = ImageWhile.getId()
            PosledovatelY[ide-1] = ImageWhile.y
            PosledovatelText[ide-1] = "${EtWhile.text}"
            PosledovatelType[ide-1] = 11
            val ide1 = ImageBegin.getId()
            PosledovatelY[ide1-1] = ImageBegin.y
            PosledovatelText[ide1-1] = "{"
            PosledovatelType[ide1-1] = 12
            val ide2 = ImageEnd.getId()
            PosledovatelY[ide2-1] = ImageEnd.y
            PosledovatelText[ide2-1] = "}"
            PosledovatelType[ide2-1] = 13
            showPosl()
        }
        ImageWhile.setImageResource(R.drawable.ic_while)
        ImageWhileOk.setImageResource(R.drawable.ic_ok)
        ImageBegin.setImageResource(R.drawable.begin)
        ImageEnd.setImageResource(R.drawable.end)
        ImageWhile.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageWhile.y = stepY
        ImageWhileOk.x = ImageWhile.x+150
        ImageWhileOk.y = ImageWhile.y+25
        ImageBegin.x = ImageWhile.x+100
        ImageBegin.y = ImageWhile.y+100
        ImageEnd.x = ImageWhile.x+100
        ImageEnd.y = ImageWhile.y+200
        EtWhile.x = ImageWhile.x+250
        EtWhile.y = ImageWhile.y+10

        mainLayout.addView(ImageWhileOk,100,100)
        mainLayout.addView(ImageWhile,150,150)
        mainLayout.addView(ImageBegin,125,125)
        mainLayout.addView(ImageEnd,125,125)
        mainLayout.addView(EtWhile)
        stepY = ImageEnd.y + 50
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createArr(view : View) {
        val ImageArr = ImageView(this)
        val ImageArrOk = ImageView(this)
        val EtArr = EditText(this)
        deleters+=3
        EtArr.hint = "Array[100]"
        ImageArr.setId(i+1)
        EtArr.setId(hider)
        hider++
        ImageArrOk.setId(hider)
        hider++
        i++
        var dy = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width/2
                EtArr.x=ImageArr.x+250
                EtArr.y = ImageArr.y+10
                ImageArrOk.x = ImageArr.x+150
                ImageArrOk.y = ImageArr.y+25

                val ide = ImageArr.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageArrOk.setOnClickListener() {
            val ide = ImageArr.getId()
            PosledovatelY[ide-1] = ImageArr.y
            PosledovatelText[ide-1] = EtArr.text.toString()
            PosledovatelType[ide-1] = 14
            showPosl()
        }
        ImageArr.setImageResource(R.drawable.ic_mas)
        ImageArrOk.setImageResource(R.drawable.ic_ok)
        ImageArr.setOnTouchListener(listener)
        ImageArr.y = stepY
        ImageArrOk.x = ImageArr.x+150
        ImageArrOk.y = ImageArr.y+25
        EtArr.x=ImageArr.x+250
        EtArr.y = ImageArr.y+10
        mainLayout.addView(ImageArrOk,100,100)
        mainLayout.addView(ImageArr,150,150)
        mainLayout.addView(EtArr)
        stepY = ImageArr.y + 150
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createPrint(view : View) {
        val ImagePrint = ImageView(this)
        val ImagePrintOk = ImageView(this)
        val EtPrint = EditText(this)
        deleters+=3
        EtPrint.hint = "a"
        ImagePrint.setId(i+1)

        ImagePrintOk.setId(hider)
        hider++
        EtPrint.setId(hider)
        hider++

        i++
        var dy = 0f
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                binding.mainLay.requestDisallowInterceptTouchEvent(true)
                view.y = motionEvent.rawY + dy
                view.x = motionEvent.rawX - view.width / 2
                EtPrint.x=ImagePrint.x+250
                EtPrint.y = ImagePrint.y+10
                ImagePrintOk.x = ImagePrint.x+150
                ImagePrintOk.y = ImagePrint.y+25
                val ide = ImagePrint.getId()
                PosledovatelY[ide-1] = view.y
            }
            else if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                dy =view.y - motionEvent.rawY
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImagePrintOk.setOnClickListener() {
            val ide = ImagePrint.getId()
            PosledovatelY[ide-1] = ImagePrint.y
            PosledovatelText[ide-1] = "${EtPrint.text}"
            binding.scrolling.setNestedScrollingEnabled(true)
            PosledovatelType[ide-1] = 15
            showPosl()
        }
        ImagePrint.setImageResource(R.drawable.ic_print)
        ImagePrintOk.setImageResource(R.drawable.ic_ok)
        ImagePrint.setOnTouchListener(listener)
        ImagePrint.y = stepY
        ImagePrintOk.x = ImagePrint.x+150
        ImagePrintOk.y = ImagePrint.y+25
        EtPrint.x=ImagePrint.x+250
        EtPrint.y = ImagePrint.y+10
        mainLayout.addView(ImagePrintOk,100,100)
        mainLayout.addView(ImagePrint,150,150)
        mainLayout.addView(EtPrint)
        stepY = ImagePrint.y + 150
    }

    fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start
    fun HideAuthors(view: View) {
        binding.Author1.visibility  = View.GONE
        binding.Author2.visibility  = View.GONE
        binding.Author3.visibility  = View.GONE
        binding.Author1text.visibility  = View.GONE
        binding.Author2text.visibility  = View.GONE
        binding.Author3text.visibility  = View.GONE
        binding.AuthorButton.visibility  = View.GONE
        binding.AuthorText.visibility  = View.GONE

        binding.button13.visibility  = View.VISIBLE
        binding.button14.visibility  = View.VISIBLE
        binding.button15.visibility  = View.VISIBLE
        binding.button16.visibility  = View.VISIBLE

        binding.button3.visibility  = View.VISIBLE
        binding.button9.visibility  = View.VISIBLE
        binding.button4.visibility  = View.VISIBLE
        binding.varButton2.visibility  = View.VISIBLE
        binding.scroll.visibility = View.VISIBLE
    }
    fun ProgramStart(){
        var delete_i = 0
        val ArrTok: MutableList<Token> = mutableListOf()
        var FunctArray = IntArray(1000)
        var FunctArrayCounter = 0
        val FinalTokens : MutableList<Token> = mutableListOf()
        for (t in 0..i - 1) {
            when (PosledovatelType_copy[t]) {
                0 -> {
                    var Name = ""
                    var Exp = ""
                    var variable: Variable
                    val BlockString = PosledovatelText_copy[t].toString()
                    var equallyMeet = false
                    for (d in 0 until BlockString.length) {
                        if (!equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Name += BlockString[d]
                        if (!equallyMeet && BlockString[d] == ',') {
                            variable = Variable(Name, "0")
                            ArrTok.add(variable)
                            Name = ""
                        }
                        if (equallyMeet && BlockString[d] == ',') {
                            variable = Variable(Name, Exp)
                            ArrTok.add(variable)
                            Name = ""
                            Exp = ""
                            equallyMeet = false
                        }
                        if (BlockString[d] == '=') equallyMeet = true
                        if (equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Exp += BlockString[d]
                    }
                    if (!equallyMeet) {
                        variable = Variable(Name, "0")
                        ArrTok.add(variable)
                    }
                    else {
                        variable = Variable(Name, Exp)
                        ArrTok.add(variable)
                    }
                    FunctArray[FunctArrayCounter] = 0
                    FunctArrayCounter+=1
                }
                1 -> {
                    var Name = ""
                    var Exp = ""
                    var variable: ArithmeticOperations
                    val BlockString = PosledovatelText_copy[t].toString()
                    var equallyMeet = false
                    for (d in 0 until BlockString.length) {
                        if (!equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Name += BlockString[d]
                        if (!equallyMeet && BlockString[d] == ',') {
                            variable = ArithmeticOperations(Name, "0")
                            ArrTok.add(variable)
                            Name = ""
                        }
                        if (equallyMeet && BlockString[d] == ',') {
                            variable = ArithmeticOperations(Name, Exp)
                            ArrTok.add(variable)
                            Name = ""
                            Exp = ""
                            equallyMeet = false
                        }
                        if (BlockString[d] == '=') equallyMeet = true
                        if (equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Exp += BlockString[d]
                    }
                    if (!equallyMeet) {
                        variable = ArithmeticOperations(Name, "0")
                        ArrTok.add(variable)
                    }
                    else {
                        variable = ArithmeticOperations(Name, Exp)
                        ArrTok.add(variable)
                    }
                    FunctArray[FunctArrayCounter] = 0
                    FunctArrayCounter+=1
                }
                2 -> {
                    FunctArray[FunctArrayCounter] = 1
                    ArrTok.add(IfBranch(PosledovatelText_copy[t].toString()))
                    FunctArrayCounter += 1
                } // if
                4 -> {
                    FunctArray[FunctArrayCounter] = 55
                    FunctArrayCounter += 1
                    delete_i++
                    ArrTok.add(Token())
                } // if end
                5 -> {
                    FunctArray[FunctArrayCounter] = 2
                    ArrTok.add(ElseBranch())
                    FunctArrayCounter += 1
                } // else
                7 -> {
                    FunctArray[FunctArrayCounter] = 55
                    FunctArrayCounter += 1
                    ArrTok.add(Token())
                    delete_i++
                } // else end
                10 -> {
                    var Name = ""
                    var Exp = ""
                    val Bstr = PosledovatelText_copy[t].toString()
                    var space = false
                    for (d in 0 until Bstr.length) {
                        if (Bstr[d] == ' ') space=true
                        else if (space) Exp+=Bstr[d]
                        else if (!space) Name+=Bstr[d]
                    }
                    ArrTok.add(Variable(Name,Exp))
                    FunctArray[FunctArrayCounter] = 0
                    FunctArrayCounter += 1
                } // input
                11 -> {
                    FunctArray[FunctArrayCounter] = 4
                    ArrTok.add(While(PosledovatelText_copy[t].toString()))
                    FunctArrayCounter += 1
                } // while
                13 -> {
                    FunctArray[FunctArrayCounter] = 55
                    FunctArrayCounter += 1
                    delete_i++
                    ArrTok.add(Token())
                } // while end
                14 -> {
                    var Name = ""
                    var Size = ""
                    var SizeInt = ""
                    var Exp = ""
                    var Arr: Array
                    val BlockString = PosledovatelText_copy[t].toString()
                    var equallyMeet = false
                    var sizeMeet = false
                    for (d in 0 until BlockString.length) {
                        if (!equallyMeet && !sizeMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=' && BlockString[d] != '[' && BlockString[d] != ']') Name += BlockString[d]
                        if (sizeMeet && BlockString[d] != '[' && BlockString[d] != ']') Size+=BlockString[d]
                        if (BlockString[d] == '[') sizeMeet = true
                        if (BlockString[d] == ']') {
                            sizeMeet = false
                            SizeInt = Size
                        }
                        if (BlockString[d] == '=') {
                            equallyMeet = true
                        }
                        if (equallyMeet && BlockString[d] != '}' && BlockString[d] != '{'&& BlockString[d] != '='&& BlockString[d] != ' ') {
                            Exp += BlockString[d]
                        }
                        if (equallyMeet && BlockString[d] == '}') {
                            Arr = Array(SizeInt,Name,Exp)
                            ArrTok.add(Arr)
                        }
                    }
                    FunctArray[FunctArrayCounter] = 5
                    FunctArrayCounter+=1
                }//arr
                15 -> {
                    ArrTok.add(Print(PosledovatelText_copy[t].toString()))
                    FunctArray[FunctArrayCounter] = 6
                    FunctArrayCounter += 1
                }
            }
        }
        var IndexAdder = 0
        var Index = 1
        val AdderIndexes = arrayOfNulls<Int>(1000)
        var Cycle = ""
        AdderIndexes[0] = (-1)
        var b = 0
        while (b < FunctArrayCounter) {
            when (FunctArray[b]) {
                0 -> FinalTokens.add(ArrTok[b])
                1 -> {
                    IndexAdder = b
                    AdderIndexes[Index] = IndexAdder
                    Index+=1
                    var start = b+1
                    Cycle = "IF"
                    while (IndexAdder != -1) {
                        when (FunctArray[start]){
                            0 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            1 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "IF"
                                start+=1
                            }
                            2 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "ELSE"
                                start+=1
                            }
                            3 -> when (Cycle) {
                                "ELSE" -> {
                                    val Block = ArrTok[IndexAdder] as ElseBranch
                                    Block.addNewToken(ArrTok[start])
                                    ArrTok[IndexAdder] = Block
                                    ArrTok.removeAt(start)
                                    FunctArray = remove(FunctArray,start)
                                    FunctArrayCounter--
                                }
                                "IF" -> {
                                    val Block = ArrTok[IndexAdder] as IfBranch
                                    Block.addNewToken(ArrTok[start])
                                    ArrTok[IndexAdder] = Block
                                    ArrTok.removeAt(start)
                                    FunctArray = remove(FunctArray,start)
                                    FunctArrayCounter--
                                }
                                "WHILE" -> {
                                    val Block = ArrTok[IndexAdder] as While
                                    Block.addNewToken(ArrTok[start])
                                    ArrTok[IndexAdder] = Block
                                    ArrTok.removeAt(start)
                                    FunctArray = remove(FunctArray,start)
                                    FunctArrayCounter--
                                }
                            }
                            4 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "WHILE"
                                start+=1
                            }
                            5 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            6 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            55 -> {
                                IndexAdder = AdderIndexes[Index-2]!!
                                Index-=1
                                if (IndexAdder != (-1)) {
                                    when (FunctArray[IndexAdder]) {
                                        1 -> {
                                            val Block = ArrTok[IndexAdder] as IfBranch
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "IF"
                                        }
                                        2 -> {
                                            val Block = ArrTok[IndexAdder] as ElseBranch
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "ELSE"
                                        }
                                        4 -> {
                                            val Block = ArrTok[IndexAdder] as While
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "WHILE"
                                        }
                                    }

                                }
                            }
                        }
                    }
                    FinalTokens.add(ArrTok[b])
                } //if
                2 -> {
                    IndexAdder = b
                    AdderIndexes[Index] = IndexAdder
                    Index+=1
                    var start = b+1
                    Cycle = "ELSE"
                    while (IndexAdder != -1) {
                        when (FunctArray[start]){
                            0 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            1 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "IF"
                                start+=1
                            }
                            2 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "ELSE"
                                start+=1
                            }
                            3 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            4 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "WHILE"
                                start+=1
                            }
                            5 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            6 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            55 -> {
                                IndexAdder = AdderIndexes[Index-2]!!
                                Index-=1
                                if (IndexAdder != (-1)) {
                                    when (FunctArray[IndexAdder]) {
                                        1 -> {
                                            val Block = ArrTok[IndexAdder] as IfBranch
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "IF"
                                        }
                                        2 -> {
                                            val Block = ArrTok[IndexAdder] as ElseBranch
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "ELSE"
                                        }
                                        4 -> {
                                            val Block = ArrTok[IndexAdder] as While
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "WHILE"
                                        }
                                    }

                                }
                            }
                        }
                    }
                    FinalTokens.add(ArrTok[b])
                } //else
                3 -> FinalTokens.add(ArrTok[b]) // input
                4 -> {
                    IndexAdder = b
                    AdderIndexes[Index] = IndexAdder
                    Index+=1
                    var start = b+1
                    Cycle = "WHILE"
                    while (IndexAdder != -1) {
                        when (FunctArray[start]){
                            0 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            1 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "IF"
                                start+=1
                            }
                            2 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "ELSE"
                                start+=1
                            }
                            3 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            4 -> {
                                IndexAdder = start
                                AdderIndexes[Index] = IndexAdder
                                Index+=1
                                Cycle = "WHILE"
                                start+=1
                            }
                            5 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            6 -> {
                                when (Cycle) {
                                    "ELSE" -> {
                                        val Block = ArrTok[IndexAdder] as ElseBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "IF" -> {
                                        val Block = ArrTok[IndexAdder] as IfBranch
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                    "WHILE" -> {
                                        val Block = ArrTok[IndexAdder] as While
                                        Block.addNewToken(ArrTok[start])
                                        ArrTok[IndexAdder] = Block
                                        ArrTok.removeAt(start)
                                        FunctArray = remove(FunctArray,start)
                                        FunctArrayCounter--
                                    }
                                }
                            }
                            55 -> {
                                IndexAdder = AdderIndexes[Index-2]!!
                                Index-=1
                                if (IndexAdder != (-1)) {
                                    when (FunctArray[IndexAdder]) {
                                        1 -> {
                                            val Block = ArrTok[IndexAdder] as IfBranch
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "IF"
                                        }
                                        2 -> {
                                            val Block = ArrTok[IndexAdder] as ElseBranch
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "ELSE"
                                        }
                                        4 -> {
                                            val Block = ArrTok[IndexAdder] as While
                                            Block.addNewToken(ArrTok[start-1])
                                            ArrTok[IndexAdder] = Block
                                            ArrTok.removeAt(start-1)
                                            FunctArray = remove(FunctArray,start-1)
                                            FunctArrayCounter--
                                            Cycle = "WHILE"
                                        }
                                    }

                                }
                            }
                        }
                    }
                    FinalTokens.add(ArrTok[b])
                } // while
                5 -> FinalTokens.add(ArrTok[b])
                6 -> FinalTokens.add(ArrTok[b])
            }
            b++
        }
        b=0
        var IndexIf = 0
        lateinit var IfBlock: IfBranch
        while (b < FinalTokens.size) {
            if (FinalTokens[b] is IfBranch) {
                IfBlock = FinalTokens[b] as IfBranch
                IndexIf = b
            }
            if (FinalTokens[b] is ElseBranch) {
                val ElseBlock = FinalTokens[b] as ElseBranch
                val IfNewBlock = IfBranch(IfBlock.giveName(),ElseBlock)
                IfNewBlock.NewList(IfBlock.giveList())
                FinalTokens[IndexIf]=IfNewBlock
                FinalTokens.removeAt(b)
            }
            b++
        }
        val program = Program(FinalTokens)
        program.run()
        binding.printer.text = program.print()
    }
}