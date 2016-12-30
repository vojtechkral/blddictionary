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
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        dFile = File(path, "dictionary.toml")

        if (dFile.createNewFile()) {
            setDefaultScheme(scheme)
            writeback()
        } else {
            val dictionary = Toml().read(dFile)
            readFile(dictionary)
        }
    }

    private fun readFile(dictionary: Toml) {
        if (dictionary.getTable("scheme") != null){
            val schemeMap: Map<String, Any> = dictionary.getTable("scheme").toMap()
            this.scheme.putAll(schemeMap)
        }

        if (dictionary.getTable("words") != null){
            val wordMap: Map<String, Any> = dictionary.getTable("words").toMap()
            this.words.putAll(wordMap)
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
        if (word.trim() == ""){
            this.words.remove(id)
        } else {
            this.words[id] = word as Any
            println(word.trim())
        }
        this.writeback()
    }

    fun getLetter(id: String): String = this.scheme[id] as String
    fun getWord(id: String): String = this.words[id] as String

    private fun setDefaultScheme(map: HashMap<String, Any>) {
        for (c in 'A'..'X') {
            map.put(c.toString(), c.toString())
        }
    }
}
