package com.example.lk_epk.util

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.lk_epk.MyApplication
import com.example.lk_epk.R
import com.example.lk_epk.entity.Calibration
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.json.JSONArray
import java.io.*

object FileUtil {
    //创建文件夹
    fun creatFile(path: String, fileName: String): File? {
        var newFile: File? = null
        Log.i("TAG:", "文件创建状态 1")
        if (path != "") {
            try {
                newFile = File(path + File.separator + fileName)
                if (!newFile.exists()) {
                    val isSuccess = newFile.createNewFile()
                    Log.i("TAG:", "文件创建状态--->$isSuccess")
                    Log.i("TAG:", "文件所在路径：$newFile")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return newFile
    }

    //写入文件
    open fun writeData(path: String?, fileData: String) : String{
        try {
            if (Environment.getExternalStorageState() == "mounted") {
                val file = File(path)
                val out = FileOutputStream(file, false)
                out.write(fileData.toByteArray(charset("UTF-8")))
                Log.i("XXXXXX:", "将数据写入到文件中：$fileData")
                out.close()
                return MyApplication.context.resources.getString(R.string.save_success)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return e.toString()
        }
        return ""
    }

    //判断文件是否存在
    fun isFileExists(fileName: String?): Boolean {
        val file = File(fileName)
        return file.exists()
    }
    //判断文件内容是否为空
    fun hasFileExists(folderPath: String?): Boolean {
        val file = File(folderPath)
        if (!file.exists()) {
            return false
        }
        val files = file.listFiles()
        if (files != null) {
            return files.isNotEmpty()
        }
        return false
    }

    //获取文件名字
    fun getFilesAllName(dataFiles: ArrayList<File>, path: String?) {
        val file = File(path)
        val files = file.listFiles()
        if (files == null) {
            Log.e("error", "空目录")
        } else if (dataFiles != null) {
            for (i in dataFiles.indices.reversed()) {
                dataFiles.removeAt(i)
            }
            for (file in files) {
                dataFiles.add(file)
            }
        }
    }

    //读取文件内容
    fun readFileContent(path: String?): String? {
        try {
            if (Environment.getExternalStorageState() != "mounted") {
                return null
            }
            val file = File(path)
            val buffer = ByteArray(32768)
            val fis = FileInputStream(file)
            val sb = StringBuffer("")
            while (true) {
                val len = fis.read(buffer)
                if (len > 0) {
                    sb.append(String(buffer, 0, len))
                } else {
                    fis.close()
                    return sb.toString()
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
    }

    //读取assets文件
    fun getLocalData(context: Context, fileName: String): String? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    //Gsob解析
    fun getGsonData(context: Context, localData: String?): MutableList<Calibration> {
        val dataList = mutableListOf<Calibration>()
        try {
            val data = JSONArray(localData)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson(data.optJSONObject(i).toString(), Calibration::class.java)
                dataList.add(entity)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return dataList
    }

    fun selectMaterialType():MutableList<String>{
        //获取材料数据
        var dataList = mutableListOf<String>()
        val loadData = getLocalData(MyApplication.context,"calibrationlist.json")
        getGsonData(MyApplication.context,loadData).forEach {
            dataList.add("${it.workpiece} ${it.temp}")
        }
        var jsonCaliration = readFileContent(MyApplication.context.getDir(Constant.ADATA_CALITRATION, 0).absolutePath+"/LKCalitration.json")
        val parser = JsonParser()
        if (jsonCaliration!=null&& jsonCaliration?.isNotEmpty()&&jsonCaliration!="[]") {
            val jsonArray = parser.parse(jsonCaliration).asJsonArray
            val gson = Gson()
            val it: Iterator<JsonElement> = jsonArray.iterator()
            while (it.hasNext()) {
                val bean = it.next()
                val calibration =
                    gson.fromJson<Any>(bean, Calibration::class.java as Class<Any?>) as Calibration
                dataList.add("${calibration.workpiece} ${calibration.temp}")
            }
        }
        return dataList;
    }
}