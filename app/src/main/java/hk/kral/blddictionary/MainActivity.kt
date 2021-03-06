package hk.kral.blddictionary

import android.util.Log
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.InputType
import android.text.InputFilter
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = "MainActivity"
    }

    var pairID: String = ""
    var pair: String = ""

    lateinit private var dict: Dictionary
    lateinit private var etWord: EditText
    lateinit private var tvPair: TextView
    lateinit private var cubegroup: CubeGroup

    private fun onLetterClick(dt: BtnLetter) {
        val letter = dict.getLetter(dt.letter.toString())
        when(pairID.length) {
            1 -> {
                if (pair[0] == dt.letter) return
                pairID += dt.letter
                pair += letter
                val word = dict.getWord(pairID)
                etWord.setText(word)
                etWord.setSelection(word.length)
            }
            else -> {
                pairID = dt.letter.toString()
                pair = letter
            }
        }
        tvPair.text = pair
    }

    private fun onLetterLongClick(dt: BtnLetter) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(getString(R.string.scheme_edit_title))

        val input = EditText(this)
        input.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
        input.setText(dict.getLetter(dt.letter.toString()))
        input.setSelection(0, 1)
        input.setFilters(arrayOf(
            InputFilter.AllCaps(),
            InputFilter.LengthFilter(1)
        ));
        dialog.setView(input)

        dialog.setPositiveButton(android.R.string.ok, { _, _ ->
                val newletter = input.getText().toString()
                dict.setLetter(dt.letter.toString(), newletter)
                cubegroup.setDictionary(dict)
        })

        dialog.setNegativeButton("Cancel", { dialog, _ -> dialog.cancel() })
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dict = Dictionary(applicationContext)

        val bSave: Button = findViewById(R.id.bSave) as Button

        etWord = findViewById(R.id.etWord) as EditText

        tvPair = findViewById(R.id.tvPair) as TextView
        tvPair.text = pair

        cubegroup = findViewById(R.id.cubegroup) as CubeGroup
        cubegroup.setDictionary(dict)
        cubegroup.onClick({ dt ->
            when (dt) {
                is BtnLetter -> onLetterClick(dt)
                else -> {}
            }
        })
        cubegroup.onLongClick({ dt ->
            when (dt) {
                is BtnLetter -> onLetterLongClick(dt)
                else -> {} // TODO: BtnFace
            }
        })

        bSave.setOnClickListener({
            dict.setWord(pairID, etWord.text.toString())
            Toast.makeText(applicationContext, getString(R.string.toast_saved), Toast.LENGTH_SHORT).show()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0)
        })
    }
}
