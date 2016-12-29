package hk.kral.blddictionary

import android.os.Environment
import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import java.io.File
import java.util.*

class Dictionary {

    var dictionary: Toml
    val schema: HashMap<String, Object> = HashMap()
    val words: HashMap<String, Object> = HashMap()
    val tomlWriter: TomlWriter = TomlWriter()
    val dFile: File

    init {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        dFile = File(path, "dictionary.toml")
        dFile.createNewFile()

        this.dictionary = Toml().read(dFile)

//        TODO write default scheme
    }

    private fun reader(){
//        TODO read the toml when the app starts
    }

    private fun writeback() {
        val map: HashMap<String, Object> = HashMap()
        map["words"] = words as Object
        map["schema"] = schema as Object
        tomlWriter.write(map, dFile)
    }

    fun setLetter(id: String, letter: String) {
        this.schema[id] = letter as Object
        this.writeback()
    }

    fun setWord(id: String, word: String) {
        this.words[id] = word as Object
        this.writeback()
    }

    fun getLetter(id: String): String = this.schema[id] as String
    fun getWord(id: String): String = this.words[id] as String
}
