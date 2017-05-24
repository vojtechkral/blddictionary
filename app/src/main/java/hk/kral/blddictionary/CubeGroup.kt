package hk.kral.blddictionary

import java.util.*
import android.util.Log
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


// FIXME: letter -> String
sealed class Btn(val letter: Char, val x: Int, val y: Int)
class BtnLetter(letter: Char, x: Int, y: Int): Btn(letter, x, y)
class BtnFace(val color: Int, letter: Char, x: Int, y: Int): Btn(letter, x, y)

typealias CubeGroupCB = (Btn) -> Unit

class CubeGroup(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int): ViewGroup(ctx, attrs, defStyleAttr) {
    companion object {
        private val TAG = "CubeGroup"
    }

    private var buttons = ArrayList<Button>()
    lateinit private var dict: Dictionary
    private var onClickCB: CubeGroupCB? = null
    private var onLongClickCB: CubeGroupCB? = null

    constructor(ctx: Context, attrs: AttributeSet) : this(ctx, attrs, 0)
    constructor(ctx: Context) : this(ctx, null, 0)

    init {
        addFace('E', 0, 3, 'L', Color.rgb(255, 85, 0))
        addFace('I', 3, 3, 'F', Color.BLUE)
        addFace('A', 3, 0, 'U', Color.YELLOW)
        addFace('M', 6, 3, 'R', Color.RED)
        addFace('Q', 9, 3, 'B', Color.GREEN)
        addFace('U', 3, 6, 'D', Color.WHITE)
    }

    private fun addButton(dt: Btn) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val btn = inflater.inflate(R.layout.flatbutton, null) as Button

        when(dt) {
            is BtnLetter -> {
                btn.text = "?"
            }
            is BtnFace -> {
                btn.text = " "
                btn.setBackgroundColor(dt.color)
            }
        }

        btn.setTag(dt)
        btn.setOnClickListener {
            onClickCB?.invoke(btn.getTag() as Btn)
        }
        btn.setOnLongClickListener {
            onLongClickCB?.invoke(btn.getTag() as Btn)
            true
        }

        this.addView(btn)
        buttons.add(btn)
    }

    private fun addFace(letter: Char, x: Int, y: Int, face: Char, color: Int) {
        addButton(BtnLetter(letter + 0, x + 0, y + 0))
        addButton(BtnLetter(letter + 0, x + 1, y + 0))
        addButton(BtnLetter(letter + 1, x + 2, y + 0))
        addButton(BtnLetter(letter + 1, x + 2, y + 1))
        addButton(BtnLetter(letter + 2, x + 2, y + 2))
        addButton(BtnLetter(letter + 2, x + 1, y + 2))
        addButton(BtnLetter(letter + 3, x + 0, y + 2))
        addButton(BtnLetter(letter + 3, x + 0, y + 1))
        addButton(BtnFace(color, face, x + 1, y + 1))
    }

    fun setDictionary(dictionary: Dictionary) {
        dict = dictionary
        for (btn in buttons) {
            val dt = btn.getTag() as Btn
            when(dt) {
                is BtnLetter -> btn.text = dict.getLetter(dt.letter.toString())
                else -> {}
            }
        }
    }

    fun onClick(cb: CubeGroupCB) { onClickCB = cb }
    fun onLongClick(cb: CubeGroupCB) { onLongClickCB = cb }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (btn in buttons) {
            val dt = btn.getTag() as Btn

            val bWidth = (r - l) / 12
            val bHeight = (b - t) / 9
            val bLeft = dt.x * bWidth
            val bRight = bLeft + bWidth
            val bTop = dt.y * bHeight
            val bBottom = bTop + bHeight

            val childWidth = bRight - bLeft
            val childHeight = bBottom - bTop
            if (childWidth != btn.measuredWidth || childHeight != btn.measuredWidth) {
                btn.measure(
                        View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(childHeight, View.MeasureSpec.EXACTLY))
            }
            btn.layout(bLeft, bTop, bRight, bBottom)
        }
    }
}
