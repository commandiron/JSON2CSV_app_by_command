package com.demirli.a17json2csvexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var jsonObject: JSONObject
    private lateinit var jsonArray: JSONArray

    private var keysArray = arrayListOf<String>()
    private var keysString = ""

    private var valuesArray = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        convert_btn.setOnClickListener {

            keysArray.clear()
            valuesArray.clear()

            val jsonString = json_tv.text.toString()

            if(jsonString != ""){
                textToJSONArray(jsonString)

                JSONArrayToKeys()

                JSONArrayToValues()

                valuesToRow()
            }else{
                csv_tv.setText("")
            }
        }
    }

    fun textToJSONArray(jsonString: String){
        try {
            jsonArray = JSONArray(jsonString)
        }catch (e: Exception){
        }
    }

    fun JSONArrayToKeys(){
        jsonObject = JSONObject(jsonArray[0].toString())

        jsonObject.keys().forEach {
            keysArray.add("\"" + it + "\"")
        }
        keysString = keysArray.toString()
    }

    fun JSONArrayToValues(){
        for (i in 0 until jsonArray.length()){
            jsonObject = JSONObject(jsonArray[i].toString())
            val jsonkeys = jsonObject.keys()

            while (jsonkeys.hasNext()){
                val key = jsonkeys.next()
                val values = jsonObject[key].toString()

                if(values.contains("^\\d+$".toRegex())){
                    valuesArray.add(values)
                }else{
                    valuesArray.add("\"" + values + "\"")
                }
            }
        }
    }

    fun valuesToRow(){
        val valuesRow =valuesArray.chunked(keysArray.size)
        var finalString = keysString.substring(1,keysString.toString().length-1) + "\n"

        for(i in valuesRow){
            finalString = finalString + i.toString().substring(1,i.toString().length-1) + "\n"
        }
        csv_tv.setText(finalString)
    }
}
