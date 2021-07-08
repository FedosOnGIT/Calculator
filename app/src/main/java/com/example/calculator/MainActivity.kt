package com.example.calculator

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var elements: LinkedList<String> = LinkedList()
    private var balance = 0
    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //actionBar?.hide()
        close.setOnClickListener {
            if (balance > 0) {
                write(")")
                balance--
            }
        }
        delete.setOnClickListener {
            if (elements.isNotEmpty()) {
                val last = elements.pop()
                if (last[last.lastIndex] == '(') {
                    balance--
                } else if (last[last.lastIndex] == ')') {
                    balance++
                }
                expression.text = expression.text.substring(0, expression.text.length - last.length)
            }
        }
        clear.setOnClickListener {
            clean()
            result.text = ""
        }
        equals.setOnClickListener {
            for (i in 0 until balance) {
                write(")")
            }
            result.text = calculator.solve(expression.text.toString())
            clean()
        }
        val buttons : Array<Pair<Button, String>> = arrayOf(
                Pair(zero, "0"),
                Pair(one, "1"),
                Pair(two, "2"),
                Pair(three, "3"),
                Pair(four, "4"),
                Pair(five, "5"),
                Pair(six, "6"),
                Pair(seven, "7"),
                Pair(eight, "8"),
                Pair(nine, "9"),
                Pair(dot, "."),
                Pair(e, "e"),
                Pair(pi, "π"),
                Pair(plus, "+"),
                Pair(minus, "-"),
                Pair(multiply, "*"),
                Pair(divide, "/"),
                Pair(degree, "^"),
                Pair(square, "^2")
        )
        buttons.forEach { pair ->
            if (pair.first != null) {
                pair.first.setOnClickListener {
                    write(pair.second)

                }
            }
        }
        val openButtons : Array<Pair<Button, String>> = arrayOf(
                Pair(open, "("),
                Pair(sin, "sin("),
                Pair(cos, "cos("),
                Pair(tg, "tg("),
                Pair(abs, "abs("),
                Pair(sqrt, "√(")
        )
        openButtons.forEach { pair ->
            if (pair.first != null) {
                pair.first.setOnClickListener {
                    balance++
                    write(pair.second)
                }
            }
        }
        if (savedInstanceState != null) {
            val array = savedInstanceState.getStringArrayList("List")
            if (array != null) {
                for (element in array) {
                    elements.push(element)
                }
            }
            expression.text = savedInstanceState.getString("Expression")
            result.text = savedInstanceState.getString("Result")
            balance = savedInstanceState.getInt("Balance")
        }
    }

    private fun write(element: String) {
        elements.push(element)
        expression.append(element)
    }

    private fun clean() {
        balance = 0
        expression.text = ""
        elements.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val arr = ArrayList<String>()
        for (i in elements) {
            arr.add(i)
        }
        outState.putStringArrayList("List", arr)
        outState.putString("Expression", expression.text.toString())
        outState.putString("Result", result.text.toString())
        outState.putInt("Balance", balance)
    }

}