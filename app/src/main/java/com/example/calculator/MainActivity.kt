package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var thisActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(thisActivity.root)
    }

    fun btOneOnClick(view: View) {
        addSymbolInfo("1")
    }
    fun btTwoOnClick(view: View) {
        addSymbolInfo("2")
    }
    fun btThreeOnClick(view: View) {
        addSymbolInfo("3")
    }
    fun btFourOnClick(view: View) {
        addSymbolInfo("4")
    }
    fun btFiveOnClick(view: View) {
        addSymbolInfo("5")
    }
    fun btSixOnClick(view: View) {
        addSymbolInfo("6")
    }
    fun btSevenOnClick(view: View) {
        addSymbolInfo("7")
    }
    fun btEightOnClick(view: View) {
        addSymbolInfo("8")
    }
    fun btNineOnClick(view: View) {
        addSymbolInfo("9")
    }
    fun btZeroOnClick(view: View) {
        var str = thisActivity.tvInfo.text.toString()
        if (str == "") {
            str = "0"
        }
        else{
            if (str == "0" || (str[str.length - 1] == '0' && (str[str.length - 2] in listOf ('+','-','*','/') ))){

            }
            else{
                str += "0"
            }
        }

        thisActivity.tvInfo.text = str

    }
    fun btPlusOnClick(view: View){
        addMathSymbolInfo('+')
    }
    fun btMinusOnClick(view: View){
        addMathSymbolInfo('-')
    }
    fun btMultiOnClick(view: View){
        addMathSymbolInfo('*')
    }
    fun btDivisionOnClick(view: View){
        addMathSymbolInfo('/')
    }
    fun btBracketOnClick(view: View){
        //Нужно добавить логику добавления скобок
        //Переделать getResult с учетом скобок
    }
    fun btMinusNumberOnClick(view: View){
        var str = thisActivity.tvInfo.text
        if (str.isEmpty()){
            str = "-"
        }
        thisActivity.tvInfo.text = str
    }
    fun btPointOnClick(view: View){
        var str = thisActivity.tvInfo.text.toString()
        if(str.isEmpty()){
            str = "0."
        }else if(str[str.length - 1] in listOf('+','-','*','/')){
            str += "0."
        }else{
            var isPoint = false
            var i = str.length - 1
            while (str[i] !in listOf('+','-','*','/')){
                if (str[i] == '.')
                    isPoint = true
                i--
                if(i == -1)
                    break
            }
            if (!isPoint) {
                str += '.'
            }
        }
        thisActivity.tvInfo.text = str
    }
    fun btResultOnClick(view: View){
        var str = thisActivity.tvInfo.text.toString()
        var Numbers = mutableListOf<Double>()
        var mathSymbol = mutableListOf<Char>()
        var diopasonMin = 0
        var number: Double
        for (i in str.indices){

            if ((str[i] in listOf('+','-','*','/') ) && (i != 0)){
                number = str.slice(diopasonMin until i).toDouble()
                diopasonMin = i + 1
                mathSymbol += str[i]
                Numbers += number

            }
            else if (i == str.length - 1){
                number = str.slice(diopasonMin..i).toDouble()
                Numbers += number
            }
        }
        Log.d("myLog", "$Numbers")
        Log.d("myLog", "$mathSymbol")
        if (Numbers.size != mathSymbol.size )
            getResult(Numbers, mathSymbol)
    }
    fun btClearOnclick(view: View){
        var str = thisActivity.tvInfo.text.toString()
        if (str.isNotEmpty()) {
            str = str.substring(0, str.length - 1)
        }
        thisActivity.tvInfo.text = str
    }
    fun btClearAllOnclick(view: View){
        thisActivity.tvInfo.text = ""
    }

    private fun addSymbolInfo(Symbol: String){
        var str = thisActivity.tvInfo.text.toString()
        if (str.length > 1) {
            if ((str[str.length - 2] in listOf('+','-','*','/')) && str[str.length - 1] == '0') {
                str = str.substring(0, str.length - 1)
                str += Symbol
            }
            else {
                str += Symbol
            }
        }
        else{
            if(str == "0"){
                str = Symbol
            }
            else{
                str += Symbol
            }
        }
        thisActivity.tvInfo.text = str

    }
    private fun addMathSymbolInfo(mathSymbol: Char){
        var str = thisActivity.tvInfo.text.toString()
        if (str != "") {
            var Symbol = str[str.length - 1]
            if (Symbol in listOf('+','-','*','/')) {
                str = str.substring(0, str.length - 1)
                str += mathSymbol
            }
            else {
                str += mathSymbol
            }
            thisActivity.tvInfo.text = str
        }
    }
    private fun outputResult(result: Double){
        var infinity = 1.0/0.0
        var infinityMinus = -1.0/0.0
        if (result != infinity || result != infinityMinus) {
            var temp: Long
            if (result % 1.0 == 0.0) {
                temp = result.toLong()
                thisActivity.tvInfo.text = "$temp"
            }
            else {
                thisActivity.tvInfo.text = "$result"
            }
        }
    }
    private fun getResult(numbers: MutableList<Double>, mathSymbols: MutableList<Char> ){
        var tempResult: Double
        var multi = true
        var i = 0
        while(multi){
            if('*' in mathSymbols || '/' in mathSymbols) {
                if (mathSymbols[i] in listOf('*','/')) {
                    if (mathSymbols[i] == '*') {
                        tempResult = numbers[i] * numbers[i + 1]
                    }
                    else {
                        tempResult = numbers[i] / numbers[i + 1]
                    }
                    numbers[i] = tempResult
                    numbers.removeAt(i + 1)
                    mathSymbols.removeAt(i)
                    i = 0
                }
                else {
                    i++
                }
                Log.d("myLog", "$numbers")
                Log.d("myLog", "$mathSymbols")
            }
            else {
                multi = false
            }
        }
        var result = numbers[0]
        if (numbers.size > 1) {
                for (i in mathSymbols.indices) {
                    if (mathSymbols[i] == '+'){
                        result += numbers[i+1]
                    }
                    else{
                        result -= numbers[i+1]
                    }
                    Log.d("myLog", "$result")
                }
        }
        outputResult(result)
    }
}