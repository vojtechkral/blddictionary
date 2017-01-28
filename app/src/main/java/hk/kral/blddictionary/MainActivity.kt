package hk.kral.blddictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var pair: String = "AB"

    lateinit var etWord: EditText
    lateinit var tvPair: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bSave: Button = findViewById(R.id.bSave) as Button

        etWord = findViewById(R.id.etWord) as EditText
        updateWord()

        tvPair = findViewById(R.id.tvPair) as TextView
        tvPair.text = pair

        bSave.setOnClickListener({ view ->
            Dictionary.setWord(pair, etWord.text.toString())
            Toast.makeText(applicationContext, getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateWord() {
        etWord.setText(Dictionary.getWord(pair))
    }
}
