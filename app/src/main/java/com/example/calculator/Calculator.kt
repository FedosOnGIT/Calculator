package com.example.calculator
import java.lang.NumberFormatException
import java.lang.StringBuilder
import kotlin.math.*

private class CalculatorException : Exception {
    constructor(message: String, exception: Exception) : super(message + " " + exception.message)
    constructor(message: String?) : super(message)
}

class Calculator {
    private var position: Int = 0
    private lateinit var expression: String
    private val Maximal = 4
    private var Last = ' '
    private val Operators = mapOf(
            '+' to 0,
            '-' to 0,
            '*' to 1,
            '/' to 1,
            '^' to 3
    )

    private val unary = mapOf(
            "abs" to { number: Double -> abs(number) },
            "\u221A" to { number: Double -> sqrt(number) },
            "sin" to {number: Double -> sin(number)},
            "cos" to {number: Double -> cos(number)},
            "tg" to {number: Double -> tan(number)}
    )

    private val constants = mapOf(
            'π' to Math.PI,
            'e' to Math.E
    )

    fun solve(expression: String): String {
        position = 0
        this.expression = expression
        return try {
            val result = process(0)
            if (hasNext()) {
                throw CalculatorException("Extra close brackets")
            }
            result.toString()
        } catch (e : CalculatorException) {
            e.message!!
        }
    }

    private fun get(): Char {
        return expression[position]
    }

    private fun hasNext(): Boolean {
        return position != expression.length
    }

    private fun next(): Char {
        return expression[position++]
    }

    private fun checkUnary(): ((Double) -> Double)? {
        for ((key, value) in unary) {
            val finalPosition = key.length + position
            if (finalPosition < expression.length) {
                if (key == expression.substring(position, finalPosition)) {
                    position = finalPosition
                    return value
                }
            }
        }
        return null
    }

    private fun checkConstant() : Double? {
        val symbol = get()
        if (constants.containsKey(symbol)) {
            next()
            return constants[symbol]
        }
        return null
    }

    private fun skipWhitespaces() {
        while (hasNext() && Character.isWhitespace(get())) {
            next()
        }
    }

    private fun process(priority: Int): Double {
        skipWhitespaces()
        if (priority == 2 && get() == '-') {
            next()
            return process(priority + 1) * -1
        }
        if (priority == Maximal) {
            return processUnary()
        }
        var result = process(priority + 1)
        skipWhitespaces()
        while (hasNext() || Last != ' ') {
            var operator = ' '
            if (Last != ' ') {
                operator = Last
                Last = ' '
            } else {
                if (!Operators.containsKey(get())) {
                    break
                }
                operator = next()
            }
            if (Operators[operator] != priority) {
                Last = operator
                break
            }
            val other = process(priority + 1)
            skipWhitespaces()
            result = when (operator) {
                '+' -> result + other
                '-' -> result - other
                '*' -> result * other
                '/' -> {
                    if (other == 0.0) {
                        throw CalculatorException("Division by zero")
                    }
                    result / other
                }
                '^' -> {
                    val help = result.pow(other)
                    if (help.isNaN()) {
                        throw CalculatorException("Incorrect exponentiation")
                    }
                    help
                }
                else -> throw CalculatorException("Incorrect operator")
            }
        }
        skipWhitespaces()
        return result
    }

    private fun processUnary(): Double {
        val function = checkUnary()
        if (function == null) {
            if (get() == '(') {
                next()
                val result = process(0)
                if (!hasNext() || get() != ')') {
                    throw CalculatorException("Missing close bracket")
                }
                next()
                return result
            }
            return number()
        } else {
            val result = function(process(4))
            if (result.isNaN()) {
                throw CalculatorException("Incorrect input value")
            }
            return result
        }
    }

    private fun number() : Double {
        val constant = checkConstant()
        if (constant != null) {
            return constant
        }
        skipWhitespaces()
        val number = StringBuilder()
        while (hasNext() && (Character.isDigit(get()) || get() == '.')) {
            number.append(next())
        }
        try {
            return number.toString().toDouble()
        } catch (e : NumberFormatException) {
            throw CalculatorException("Incorrect number", e)
        }
    }
}
