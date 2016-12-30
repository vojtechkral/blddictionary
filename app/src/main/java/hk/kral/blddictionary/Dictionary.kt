package hk.kral.blddictionary

import android.os.Environment
import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import java.io.File
import java.util.*

class Dictionary {

    var scheme: HashMap<String, Any> = HashMap()
    var words: HashMap<String, Any> = HashMap()
    val tomlWriter: TomlWriter = TomlWriter()
    val dFile: File

    init {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        dFile = File(path, "dictionary.toml")

        if (dFile.createNewFile()){
            setDefaultScheme(scheme)
            writeback()
        } else {
            val dictionary = Toml().read(dFile)
            readFile(dictionary)
        }
    }

    private fun readFile(dictionary: Toml) {
        val schemeMap: Map<String, Any> = dictionary.getTable("scheme").toMap()
        this.scheme.putAll(schemeMap)

        if (dictionary.getTable("words") != null){
            val wordMap: Map<String, Any> = dictionary.getTable("words").toMap()
            this.words.putAll(wordMap)
            println(wordMap)
        }
    }

    private fun writeback() {
        val map: HashMap<String, Any> = HashMap()
        map["words"] = words as Any
        map["scheme"] = scheme as Any
        tomlWriter.write(map, dFile)
    }

    fun setLetter(id: String, letter: String) {
        this.scheme[id] = letter as Any
        this.writeback()
    }

    fun setWord(id: String, word: String) {
        this.words[id] = word as Any
        this.writeback()
    }

    fun getLetter(id: String): String = this.scheme[id] as String
    fun getWord(id: String): String = this.words[id] as String

    private fun setDefaultScheme(map: HashMap<String, Any>){
//        TODO is there a better way to do this ?
        map.put("a", "A")
        map.put("b", "B")
        map.put("c", "C")
        map.put("d", "D")
        map.put("e", "E")
        map.put("f", "F")
        map.put("g", "G")
        map.put("h", "H")
        map.put("i", "I")
        map.put("j", "J")
        map.put("k", "K")
        map.put("l", "L")
        map.put("m", "M")
        map.put("n", "N")
        map.put("o", "O")
        map.put("p", "P")
        map.put("q", "Q")
        map.put("r", "R")
        map.put("s", "S")
        map.put("t", "T")
        map.put("u", "U")
        map.put("v", "V")
        map.put("w", "W")
        map.put("x", "X")
    }
}
