package com.example.province

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.province.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var date = listOf<ProvinceBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()
    }

    private fun initView() {
        readTextFromSDcard()
        binding?.rv?.layoutManager = LinearLayoutManager(this)
        val adapter = ReadTextAdapter(date)
        binding?.rv?.adapter = adapter

        adapter.onItemClick = { _, pos ->
            val province = date[pos].name
            binding?.rv?.layoutManager = LinearLayoutManager(this)
            val adapter2 = ReadTextAdapter2(date[pos].city)
            binding?.rv?.adapter = adapter2

            adapter2.onItemClick = { _, pos2 ->
                val city = date[pos].city?.get(pos2)?.name
                binding?.rv?.layoutManager = LinearLayoutManager(this)
                val adapter3 = ReadTextAdapter3(date[pos].city?.get(pos2)?.area)
                binding?.rv?.adapter = adapter3

                adapter3.onItemClick = { _, po3 ->
                    val area = date[pos].city?.get(pos2)?.area?.get(po3)
                    Toast.makeText(
                        this,
                        "$province-$city-$area",
                        Toast.LENGTH_SHORT
                    ).show()

                    initView()
                }
            }
        }
    }

    private fun readTextFromSDcard() {
        val inputStreamReader: InputStreamReader
        try {
            inputStreamReader =
                InputStreamReader(assets.open("province.json"), "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            val stringBuilder = StringBuilder()
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            inputStreamReader.close()
            bufferedReader.close()
            val resultString = stringBuilder.toString()
            date = Gson().fromJson(
                resultString,
                object : TypeToken<List<ProvinceBean?>?>() {}.type
            ) //字符串数组转换为集合
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}