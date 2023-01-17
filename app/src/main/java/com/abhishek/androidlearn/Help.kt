package com.abhishek.androidlearn

import android.util.Log
import java.io.File
import java.io.FileReader

class Help {
    companion object {
        fun log_(msg: Any?) {
            Log.d("---------------", msg.toString())
        }
    }
}

class XmlParser(private val xmlString: String) {
    private var start = 0
    private var current = 0
    private val tokens = arrayListOf<Pair<Int, Int>>()
    fun parse() {
        while (true) {
            skipWhiteSpace()
            if (isEnd()) break
            start = current
            val c = advance()
            if (c == '<') {
                var isEnd = false
                if (top() == '/') {
                    advance()
                    isEnd = true
                }
                //added '</' or '<'
                tokens.add(Pair(start, current))
                start = current
                //also parse tag name
                while (!isEnd() && top().isLetter()) {
                    advance()
                }
                //add tag name
                tokens.add(Pair(start, current))
                //if end consume >
            } else if (c.isLetter()) {
                //must be key
                while (!isEnd() && top().isLetterOrDigit()) {
                    advance()
                }
                //add key
                tokens.add(Pair(start, current))
            } else if (c == '=') {
                tokens.add(Pair(start, current))
            } else if (c == '"') {
                //parse value
                start = current
                while (!isEnd()) {
                    advance()
                    if (top() == '"') {
                        tokens.add(Pair(start, current))
                        //consume "
                        advance()
                        break
                    }
                }

            } else if (c == '>') {
                //added >
                tokens.add(Pair(start, current))
            }
        }
        //print tokens
        tokens.forEach {
            println(xmlString.substring(it.first, it.second))
        }
    }

    private fun skipWhiteSpace() {

        while (!isEnd() && top().isWhitespace()) {
            advance()
        }
    }

    private fun isEnd(): Boolean = current + 1 > xmlString.length
    private fun top(): Char = xmlString[current]
    private fun advance(): Char = xmlString[current++]
    private fun next(): Char = xmlString[current + 1]

}

fun main() {
    val file = File("app/src/main/res/xml/test.xml")
    val xmlString = FileReader(file).readText()
    XmlParser(xmlString).parse()

}