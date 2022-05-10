package com.example.team25
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
//        binding.bNav?.selectedItemId = R.id.item3
//        supportFragmentManager.beginTransaction().replace(R.id.place_holder, authors()).commit()
//        binding.bNav?.setOnItemSelectedListener {
//            when(it.itemId) {
//                R.id.item2 -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.place_holder, MainActivity()).commit()
//                }
//
//                R.id.item3 -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.place_holder, authors()).commit()
//                }
//            }
//            true
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.launch -> {
                if (i != 0 && PosledovatelY[0] != null) {
                    val PosledovatelY_copy = arrayOfNulls<Float>(1000)
                    val PosledovatelText_copy = arrayOfNulls<String>(1000)
                    val PosledovatelType_copy = arrayOfNulls<Int>(1000)
                    for (t in 0..i - 1) {
                        PosledovatelY_copy[t] = PosledovatelY[t]
                        PosledovatelText_copy[t] = PosledovatelText[t]
                        PosledovatelType_copy[t] = PosledovatelType[t]
                    }
                    for (t in 0..i - 1) {
                        for (k in t + 1..i - 1) {
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
//                    for (t in 0..i - 1) {
//                        Toast.makeText(
//                            this,
//                            "${PosledovatelText_copy[t]}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }

                    val ArrTok: MutableList<Token> = mutableListOf()
                    val IfBlockTokens: MutableList<Token> = mutableListOf()

                    val ElseBlockTokens: MutableList<Token> = mutableListOf()
                    val ForBlockTokens: MutableList<Token> = mutableListOf()
                    val WhileBlockTokens: MutableList<Token> = mutableListOf()

                    var IfName = ""
                    var ElseName = ""
                    var ForName = ""
                    var WhileName = ""
                    var IfBranchOn = false
                    var ElseBranchOn = false
                    var ForBranchOn = false
                    var WhileBranchOn = false

                    var IfBlock = IfBranch(IfName)

                    for (t in 0..i - 1) {
                        when (PosledovatelType_copy[t]) {
                            0 -> { // VAR
                                var Name = ""
                                var Exp = ""
                                var variable : Variable
                                var BlockString = PosledovatelText_copy[t].toString()
                                var equallyMeet = false
                                if (!IfBranchOn) {
                                    for (d in 0 until BlockString.length) {
                                        if (!equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Name+=BlockString[d]
                                        if (!equallyMeet && BlockString[d] == ',') {
                                            variable = Variable(Name,"0")
                                            ArrTok.add(variable)
                                            Name = ""
                                        }
                                        if (equallyMeet && BlockString[d] == ',') {
                                            variable = Variable(Name,Exp)
                                            ArrTok.add(variable)
                                            Name = ""
                                            Exp = ""
                                            equallyMeet=false
                                        }
                                        if (BlockString[d] == '=') equallyMeet=true
                                        if (equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Exp+=BlockString[d]
                                        }
                                    if (!equallyMeet) {
                                        variable = Variable(Name,"0")
                                        ArrTok.add(variable)}
                                    else {
                                        variable = Variable(Name,Exp)
                                        Log.i("Var Name",Name)
                                        Log.i("Var Exp",Exp)
                                        ArrTok.add(variable)
                                    }
                                }
                                else {
                                    for (d in 0 until BlockString.length) {
                                        if (!equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Name+=BlockString[d]
                                        if (!equallyMeet && BlockString[d] == ',') {
                                            variable = Variable(Name,"0")
                                            IfBlockTokens.add(variable)
                                            Name = ""
                                        }
                                        if (equallyMeet && BlockString[d] == ',') {
                                            variable = Variable(Name,Exp)
                                            IfBlockTokens.add(variable)
                                            Name = ""
                                            Exp = ""
                                            equallyMeet=false
                                        }
                                        if (BlockString[d] == '=') equallyMeet=true
                                        if (equallyMeet && BlockString[d] != ',' && BlockString[d] != ' ' && BlockString[d] != '=') Exp+=BlockString[d]
                                    }
                                    if (!equallyMeet) {
                                        variable = Variable(Name,"0")
                                        IfBlock.addNewToken(variable)}
                                    else {
                                        variable = Variable(Name,Exp)
                                        IfBlock.addNewToken(variable)
                                    }
                                }
                            }
                            1 -> {
                                // пусто
                            } // ARI
                            2 -> {
                                IfName = PosledovatelType_copy[t].toString()
                            }
                            3 -> {
                                IfBranchOn = true
                            }
                            4 -> {
                                IfBranchOn = false
                                IfBlockTokens.clear()
                            }
                            5 -> {
                                ElseName = PosledovatelType_copy[t].toString()
                            }
                            6 -> {
                                ElseBranchOn = true
                            }
                            7 -> {
                                ElseBranchOn = false
                            }
                            8 -> {
                                ForName = PosledovatelType_copy[t].toString()
                            }
                            9 -> {
                                ForBranchOn = true
                            }
                            10 -> {
                                ForBranchOn = false
                            }
                            11 -> {
                                WhileName = PosledovatelType_copy[t].toString()
                            }
                            12 -> {
                                WhileBranchOn = true
                            }
                            13 -> {
                                WhileBranchOn = false
                            }
                            14 -> {}//arr
                            15 -> {
                                val p = Print(PosledovatelText_copy[t].toString())
                                ArrTok.add(p)
                                Log.i("EXPR", ArrTok.toString())
                                val program = Program(ArrTok)
                                program.run()
                                Toast.makeText(
                                    this,
                                    program.print(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            }
            R.id.clear -> {
                val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
                if (i != 0) mainLayout.removeViewsInLayout(18,deleters)
                PosledovatelY = arrayOfNulls<Float>(1000)
                PosledovatelText = arrayOfNulls<String>(1000)
                i=0
                deleters=0
                stepY = 0f
            }
        }
        return true
    }

    public var i: Int = 0
    public var arr = arrayOfNulls<Button>(1000)
    public var PosledovatelY = arrayOfNulls<Float>(1000)
    public var PosledovatelText = arrayOfNulls<String>(1000)
    public var PosledovatelType = arrayOfNulls<Int>(1000)
    public var stepY =0f
    public var deleters = 0

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)
    fun createVar(view : View) {
        val ImageVar = ImageView(this)
        val ImageVarOk = ImageView(this)
        val EtVar = EditText(this)

        deleters+=3
        EtVar.hint = "a = 10"
        ImageVar.setId(i+1)
        i++
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2
                EtVar.x=ImageVar.x+300
                EtVar.y = ImageVar.y+35
                ImageVarOk.x = ImageVar.x+200
                ImageVarOk.y = ImageVar.y+50

                stepY = ImageVar.y + 150

                val ide = ImageVar.getId()
                PosledovatelY[ide-1] = view.y
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageVarOk.setOnClickListener() {
            val ide = ImageVar.getId()
            PosledovatelY[ide-1] = ImageVar.y
            PosledovatelText[ide-1] = EtVar.text.toString()
            PosledovatelType[ide-1] = 0
        }
        ImageVar.setImageResource(R.drawable.ic_var)
        ImageVarOk.setImageResource(R.drawable.ic_ok)
        ImageVar.setOnTouchListener(listener)
        ImageVar.y = stepY
        ImageVarOk.x = ImageVar.x+200
        ImageVarOk.y = ImageVar.y+50
        EtVar.x=ImageVar.x+300
        EtVar.y = ImageVar.y+35
        mainLayout.addView(ImageVarOk,100,100)
        mainLayout.addView(ImageVar,200,200)
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
        i++
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2

                EtAri.x=ImageAri.x+300
                EtAri.y = ImageAri.y+35

                ImageAriOk.x = ImageAri.x+200
                ImageAriOk.y = ImageAri.y+50

                stepY = ImageAri.y + 150

                val ide = ImageAri.getId()
                PosledovatelY[ide-1] = view.y
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageAriOk.setOnClickListener() {
            val ide = ImageAri.getId()
            PosledovatelY[ide-1] = ImageAri.y
            PosledovatelText[ide-1] = EtAri.text.toString()
            PosledovatelType[ide-1] = 1
            Toast.makeText(this, "${EtAri.text.toString()} INDEX: $ide", Toast.LENGTH_SHORT).show()
        }
        ImageAri.setImageResource(R.drawable.ic_ari)
        ImageAriOk.setImageResource(R.drawable.ic_ok)
        ImageAri.setOnTouchListener(listener)
        ImageAri.y = stepY
        ImageAriOk.x = ImageAri.x+200
        ImageAriOk.y = ImageAri.y+50
        EtAri.x=ImageAri.x+300
        EtAri.y = ImageAri.y+35
        mainLayout.addView(ImageAriOk,100,100)
        mainLayout.addView(ImageAri,200,200)
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

        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2

                EtIf.x = ImageIf.x+300
                EtIf.y = ImageIf.y+35

                ImageIfOk.x = ImageIf.x+200
                ImageIfOk.y = ImageIf.y+50

                ImageBegin.x = ImageIf.x+150
                ImageBegin.y = ImageIf.y+150

                ImageEnd.x = ImageIf.x+150
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100
                stepY = ImageEnd.y + 150

                val ide = ImageIf.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                if (ImageBegin.y+100 < motionEvent.rawY - view.height*1.5f) view.y = motionEvent.rawY - view.height*1.5f
                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
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
            Toast.makeText(this, "If (${EtIf.text})", Toast.LENGTH_SHORT).show()
        }

        ImageIf.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageIf.y = stepY

        ImageIfOk.x = ImageIf.x+200
        ImageIfOk.y = ImageIf.y+50

        ImageBegin.x = ImageIf.x+150
        ImageBegin.y = ImageIf.y+150

        ImageEnd.x = ImageIf.x+150
        ImageEnd.y = ImageIf.y+250

        EtIf.x = ImageIf.x+300
        EtIf.y = ImageIf.y+35

        mainLayout.addView(ImageIfOk,100,100)
        mainLayout.addView(ImageIf,200,200)
        mainLayout.addView(ImageBegin,150,150)
        mainLayout.addView(ImageEnd,150,150)
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
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2
                ImageElseOk.x = ImageElse.x+200
                ImageElseOk.y = ImageElse.y+50
                ImageBegin.x = ImageElse.x+150
                ImageBegin.y = ImageElse.y+150
                ImageEnd.x = ImageElse.x+150
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100
                stepY = ImageEnd.y + 150
                val ide = ImageElse.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
                stepY = ImageElse.y + 150
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                if (ImageBegin.y+100 < motionEvent.rawY - view.height*1.5f) view.y = motionEvent.rawY - view.height*1.5f
                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
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
            Toast.makeText(this, "Else", Toast.LENGTH_SHORT).show()
        }
        ImageElse.setImageResource(R.drawable.ic_else)
        ImageElseOk.setImageResource(R.drawable.ic_ok)
        ImageElse.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageElse.y = stepY
        ImageElseOk.x = ImageElse.x+200
        ImageElseOk.y = ImageElse.y+50
        ImageBegin.x = ImageElse.x+150
        ImageBegin.y = ImageElse.y+150

        ImageEnd.x = ImageElse.x+150
        ImageEnd.y = ImageElse.y+250

        mainLayout.addView(ImageElseOk,100,100)
        mainLayout.addView(ImageElse,200,200)
        mainLayout.addView(ImageBegin,150,150)
        mainLayout.addView(ImageEnd,150,150)
        stepY = ImageEnd.y + 50
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createFor(view : View) {
        val ImageFor = ImageView(this)
        val ImageForOk = ImageView(this)
        val ImageBegin = ImageView(this)
        val ImageEnd = ImageView(this)
        val EtFor = EditText(this)
        EtFor.hint = "i = 0; i < 10; i++"
        deleters+=5
        ImageFor.setId(i+1)
        i++
        ImageBegin.setId(i+1)
        i++
        ImageEnd.setId(i+1)
        i++
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2

                EtFor.x = ImageFor.x+300
                EtFor.y = ImageFor.y+35

                ImageForOk.x = ImageFor.x+200
                ImageForOk.y = ImageFor.y+50

                ImageBegin.x = ImageFor.x+150
                ImageBegin.y = ImageFor.y+150

                ImageEnd.x = ImageFor.x+150
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100
                stepY = ImageEnd.y + 150

                val ide = ImageFor.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                if (ImageBegin.y+100 < motionEvent.rawY - view.height*1.5f) view.y = motionEvent.rawY - view.height*1.5f
                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageForOk.setOnClickListener() {
            val ide = ImageFor.getId()
            PosledovatelY[ide-1] = ImageFor.y
            PosledovatelText[ide-1] = "For (${EtFor.text.toString()})"
            PosledovatelType[ide-1] = 8
            val ide1 = ImageBegin.getId()
            PosledovatelY[ide1-1] = ImageBegin.y
            PosledovatelText[ide1-1] = "{"
            PosledovatelType[ide1-1] = 9
            val ide2 = ImageEnd.getId()
            PosledovatelY[ide2-1] = ImageEnd.y
            PosledovatelText[ide2-1] = "}"
            PosledovatelType[ide2-1] = 10
            Toast.makeText(this, "For (${EtFor.text.toString()})", Toast.LENGTH_SHORT).show()
        }
        ImageFor.setImageResource(R.drawable.ic_for)
        ImageForOk.setImageResource(R.drawable.ic_ok)
        ImageBegin.setImageResource(R.drawable.begin)
        ImageEnd.setImageResource(R.drawable.end)
        ImageFor.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageFor.y = stepY
        ImageForOk.x = ImageFor.x+200
        ImageForOk.y = ImageFor.y+50
        ImageBegin.x = ImageFor.x+150
        ImageBegin.y = ImageFor.y+150
        ImageEnd.x = ImageFor.x+150
        ImageEnd.y = ImageFor.y+250
        EtFor.x = ImageFor.x+300
        EtFor.y = ImageFor.y+35

        mainLayout.addView(ImageForOk,100,100)
        mainLayout.addView(ImageFor,200,200)
        mainLayout.addView(ImageBegin,150,150)
        mainLayout.addView(ImageEnd,150,150)
        mainLayout.addView(EtFor)
        stepY = ImageEnd.y + 50
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createWhile(view : View) {
        val ImageWhile = ImageView(this)
        val ImageWhileOk = ImageView(this)
        val ImageBegin = ImageView(this)
        val ImageEnd = ImageView(this)
        val EtWhile = EditText(this)
        EtWhile.hint = "i = 0; i < 10; i++"
        deleters+=5
        ImageWhile.setId(i+1)
        i++
        ImageBegin.setId(i+1)
        i++
        ImageEnd.setId(i+1)
        i++
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2

                EtWhile.x = ImageWhile.x+300
                EtWhile.y = ImageWhile.y+35

                ImageWhileOk.x = ImageWhile.x+200
                ImageWhileOk.y = ImageWhile.y+50

                ImageBegin.x = ImageWhile.x+150
                ImageBegin.y = ImageWhile.y+150

                ImageEnd.x = ImageWhile.x+150
                if (ImageBegin.y+100 >= ImageEnd.y)  ImageEnd.y = abs(ImageEnd.y-ImageBegin.y)+ImageBegin.y+100
                stepY = ImageEnd.y + 150

                val ide = ImageWhile.getId()
                PosledovatelY[ide-1] = view.y
                val ide1 = ImageBegin.getId()
                PosledovatelY[ide1-1] = view.y
                val ide2 = ImageEnd.getId()
                PosledovatelY[ide2-1] = view.y
            }
            true
        })
        val listener2 = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                if (ImageBegin.y+100 < motionEvent.rawY - view.height*1.5f) view.y = motionEvent.rawY - view.height*1.5f
                val ide = view.getId()
                PosledovatelY[ide-1] = view.y
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageWhileOk.setOnClickListener() {
            val ide = ImageWhile.getId()
            PosledovatelY[ide-1] = ImageWhile.y
            PosledovatelText[ide-1] = "While (${EtWhile.text.toString()})"
            PosledovatelType[ide-1] = 11
            val ide1 = ImageBegin.getId()
            PosledovatelY[ide1-1] = ImageBegin.y
            PosledovatelText[ide1-1] = "{"
            PosledovatelType[ide1-1] = 12
            val ide2 = ImageEnd.getId()
            PosledovatelY[ide2-1] = ImageEnd.y
            PosledovatelText[ide2-1] = "}"
            PosledovatelType[ide2-1] = 13
            Toast.makeText(this, "While (${EtWhile.text.toString()})", Toast.LENGTH_SHORT).show()
        }
        ImageWhile.setImageResource(R.drawable.ic_while)
        ImageWhileOk.setImageResource(R.drawable.ic_ok)
        ImageBegin.setImageResource(R.drawable.begin)
        ImageEnd.setImageResource(R.drawable.end)
        ImageWhile.setOnTouchListener(listener)
        ImageEnd.setOnTouchListener(listener2)
        ImageWhile.y = stepY
        ImageWhileOk.x = ImageWhile.x+200
        ImageWhileOk.y = ImageWhile.y+50
        ImageBegin.x = ImageWhile.x+150
        ImageBegin.y = ImageWhile.y+150
        ImageEnd.x = ImageWhile.x+150
        ImageEnd.y = ImageWhile.y+250
        EtWhile.x = ImageWhile.x+300
        EtWhile.y = ImageWhile.y+35

        mainLayout.addView(ImageWhileOk,100,100)
        mainLayout.addView(ImageWhile,200,200)
        mainLayout.addView(ImageBegin,150,150)
        mainLayout.addView(ImageEnd,150,150)
        mainLayout.addView(EtWhile)
        stepY = ImageEnd.y + 50
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createArr(view : View) {
        val ImageArr = ImageView(this)
        val ImageArrOk = ImageView(this)
        val EtArr = EditText(this)
        deleters+=3
        EtArr.hint = "Array(100)"
        ImageArr.setId(i+1)
        i++
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2
                EtArr.x=ImageArr.x+300
                EtArr.y = ImageArr.y+35
                ImageArrOk.x = ImageArr.x+200
                ImageArrOk.y = ImageArr.y+50

                stepY = ImageArr.y + 150

                val ide = ImageArr.getId()
                PosledovatelY[ide-1] = view.y
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImageArrOk.setOnClickListener() {
            val ide = ImageArr.getId()
            PosledovatelY[ide-1] = ImageArr.y
            PosledovatelText[ide-1] = EtArr.text.toString()
            PosledovatelType[ide-1] = 14
            Toast.makeText(this, "${EtArr.text.toString()} INDEX: $ide", Toast.LENGTH_SHORT).show()
        }
        ImageArr.setImageResource(R.drawable.ic_mas)
        ImageArrOk.setImageResource(R.drawable.ic_ok)
        ImageArr.setOnTouchListener(listener)
        ImageArr.y = stepY
        ImageArrOk.x = ImageArr.x+200
        ImageArrOk.y = ImageArr.y+50
        EtArr.x=ImageArr.x+300
        EtArr.y = ImageArr.y+35
        mainLayout.addView(ImageArrOk,100,100)
        mainLayout.addView(ImageArr,200,200)
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
        i++
        val listener = View.OnTouchListener(function = {view,motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height*1.5f
                view.x = motionEvent.rawX - view.width/2
                EtPrint.x=ImagePrint.x+300
                EtPrint.y = ImagePrint.y+35
                ImagePrintOk.x = ImagePrint.x+200
                ImagePrintOk.y = ImagePrint.y+50

                stepY = ImagePrint.y + 150

                val ide = ImagePrint.getId()
                PosledovatelY[ide-1] = view.y
            }
            true
        })
        val mainLayout = findViewById<View>(R.id.mainLay) as ConstraintLayout
        ImagePrintOk.setOnClickListener() {
            val ide = ImagePrint.getId()
            PosledovatelY[ide-1] = ImagePrint.y
            PosledovatelText[ide-1] = "${EtPrint.text}"

            PosledovatelType[ide-1] = 15
            Toast.makeText(this, "Print ${EtPrint.text.toString()}", Toast.LENGTH_SHORT).show()
        }
        ImagePrint.setImageResource(R.drawable.ic_print)
        ImagePrintOk.setImageResource(R.drawable.ic_ok)
        ImagePrint.setOnTouchListener(listener)
        ImagePrint.y = stepY
        ImagePrintOk.x = ImagePrint.x+200
        ImagePrintOk.y = ImagePrint.y+50
        EtPrint.x=ImagePrint.x+300
        EtPrint.y = ImagePrint.y+35
        mainLayout.addView(ImagePrintOk,100,100)
        mainLayout.addView(ImagePrint,200,200)
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
    }

}
