package converter

import kotlin.math.pow

fun main() {
    try {
        val sourceRadix = readLine()!!.toInt()
        val sourceNumber = readLine()!!
        val targetRadix = readLine()!!.toInt()

        if (sourceRadix == 0 || targetRadix == 0) return println("Error! Radix cannot be 0!")
        if (sourceRadix !in 1..36 || targetRadix !in 1..36) return println("Error! Radix should be in borders 0<r<37!")

        if (sourceNumber.toIntOrNull() != null) {
            println(sourceNumber.toInt(sourceRadix).toString(targetRadix))
        } else {
            println(sourceNumber.toDecimal(sourceRadix).toTargetRadix(targetRadix))
        }
    }
    catch (e: Exception) {
        println("Error! The input was wrong!")
    }
}

fun String.toInt(radix: Int): Int = if (radix == 1) this.length else Integer.parseInt(this, radix)

fun Int.toString(radix: Int): String = if (radix == 1) "1".repeat(this) else Integer.toString(this, radix)

fun Int.toSymbol(): String = if (this in 10..35) (this + 87).toChar().toString() else this.toString()

fun Char.toInteger(): Int {
    return when (this) {
        in 'A'..'Z' -> this.toInt() - 55
        in 'a'..'z' -> this.toInt() - 87
        in '0'..'9' -> this.toInt() - 48
        else -> this.toInt()
    }
}

fun String.toDecimal(radix: Int): String {
    val intPart = if (!this.contains('.')) this.reversed() else this.substringBefore('.').reversed()
    var decimalIntPart = 0.0

    for (i in intPart.indices) decimalIntPart += intPart[i].toInteger() * radix.toDouble().pow(i)

    return if (!this.contains('.')) decimalIntPart.toInt().toString() else {
        val frPart = this.substringAfter('.')
        var decimalFrPart = 0.0

        for (i in frPart.indices) decimalFrPart += frPart[i].toInteger() / radix.toDouble().pow(i + 1)

        (decimalIntPart.toInt() + decimalFrPart).toString()
    }
}

fun String.toTargetRadix(radix: Int): String {
    val intPart = if (!this.contains('.')) this else this.substringBefore('.')
    val targetIntPart = intPart.toInt().toString(radix)

    return if (!this.contains('.')) targetIntPart else {
        var frPart = ("0." + this.substringAfter('.')).toDouble()
        var decimalFrPart = "."

        repeat(5) {
            decimalFrPart += (frPart * radix).toInt().toSymbol()
            frPart = frPart * radix - (frPart * radix).toInt()
        }

        targetIntPart + decimalFrPart
    }
}