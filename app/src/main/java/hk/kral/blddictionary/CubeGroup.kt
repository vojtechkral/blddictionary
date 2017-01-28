package hk.kral.blddictionary

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import java.util.*


class CubeGroup(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ViewGroup(context, attrs, defStyleAttr) {
    var buttons = HashMap<Button, Pair<Int, Int>>()

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        addFace('E', 0, 3, Color.rgb(255, 85, 0))
        addFace('I', 3, 3, Color.BLUE)
        addFace('A', 3, 0, Color.YELLOW)
        addFace('M', 6, 3, Color.RED)
        addFace('Q', 9, 3, Color.GREEN)
        addFace('U', 3, 6, Color.WHITE)
    }

    private fun addButton(letter: Char, x: Int, y: Int): Button {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val btn = inflater.inflate(R.layout.flatbutton, null) as Button
        btn.text = letter.toString()
        this.addView(btn)
        buttons[btn] = Pair(x, y)
        return btn
    }

    private fun addFace(letter: Char, x: Int, y: Int, color: Int) {
        addButton(letter + 0, x + 0, y + 0)
        addButton(letter + 0, x + 1, y + 0)
        addButton(letter + 1, x + 2, y + 0)
        addButton(letter + 1, x + 2, y + 1)
        addButton(letter + 2, x + 2, y + 2)
        addButton(letter + 2, x + 1, y + 2)
        addButton(letter + 3, x + 0, y + 2)
        addButton(letter + 3, x + 0, y + 1)
        addButton(' ', x + 1, y + 1).setBackgroundColor(color)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((btn, pos) in buttons) {
            val (x, y) = pos

            val bWidth = (r - l) / 12
            val bHeight = (b - t) / 9
            val bLeft = x * bWidth
            val bRight = bLeft + bWidth
            val bTop = y * bHeight
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
