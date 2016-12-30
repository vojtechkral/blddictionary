package hk.kral.blddictionary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var pair: String = "AB"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dict = Dictionary()

        val bSave: Button = findViewById(R.id.bSave) as Button
        val etWord: EditText = findViewById(R.id.etWord) as EditText

        bSave.setOnClickListener({view -> dict.setWord(pair, etWord.text.toString())})
    }
}
