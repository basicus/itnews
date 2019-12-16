package com.news.it.ui.views

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Paint.Align
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.news.it.R
import java.text.SimpleDateFormat
import java.util.*


enum class NewsState {
    new,
    read;
}

class RssStateView : View {

    private var viewStrokeWidth = 20f

    private var strokePaint: Paint = Paint().apply {
        strokeWidth = viewStrokeWidth
        style = Paint.Style.STROKE
        ANTI_ALIAS_FLAG
    }
    private var headerPaint: Paint = Paint().apply {
        style = Paint.Style.FILL
        ANTI_ALIAS_FLAG
    }

    private val datePaint: Paint = Paint().apply {
        color = Color.BLACK
        textAlign = Align.CENTER
        ANTI_ALIAS_FLAG
    }
    private val monthPaint: Paint = Paint().apply {
        color = Color.WHITE
        textAlign = Align.CENTER
        ANTI_ALIAS_FLAG
    }

    private var headerHeight: Float = 0f

    private var cornerRound = 0.8f
        set(value) {
            field = when {
                value >= 1 -> 0.8f
                value <= 0 -> 0.8f
                else -> value
            }

        }

    private var headerRect = RectF()

    private var roundRecLeft = RectF()
    private var roundRecRight = RectF()

    private val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    private val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
    private var month = ""
    private var monthDate = ""

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        if (set == null) return
        val ta = context.obtainStyledAttributes(set, R.styleable.RssStateView)

        viewStrokeWidth = ta.getDimension(R.styleable.RssStateView_stroke_width, 20f)
        strokePaint.strokeWidth = viewStrokeWidth
        cornerRound = ta.getFloat(R.styleable.RssStateView_corner_round, 0.8f)
        ta.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val w = width.toFloat() - viewStrokeWidth
        val h = height.toFloat() - viewStrokeWidth

        headerHeight = h * 0.4f

        headerRect.left = viewStrokeWidth
        headerRect.top = 0f
        headerRect.right = w
        headerRect.bottom = headerHeight

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.run {
            //draw month name rect
            canvas.drawRect(headerRect, headerPaint)
            drawViewStroke(width.toFloat(), height.toFloat(), canvas)
            drawDate(canvas)
            drawMonthName(canvas)
        }
    }

    private fun drawViewStroke(width: Float, height: Float, canvas: Canvas) {
        val path = Path()

        val w = width - viewStrokeWidth
        val h = height - viewStrokeWidth
        val radius = 1 - cornerRound

        roundRecLeft = RectF(
            0f + viewStrokeWidth,
            h * cornerRound,
            h * radius,
            h
        )
        roundRecRight = RectF(
            w - h * radius,
            h * cornerRound,
            w,
            h
        )

        with(path) {
            reset()
            moveTo(viewStrokeWidth, 0f)
            arcTo(roundRecLeft, 180f, -90f, false)
            lineTo(w - h * radius, h)
            arcTo(roundRecRight, 90f, -90f, false)
            lineTo(w, 0f)
            close()
        }

        canvas.drawPath(path, strokePaint)
    }

    private fun drawDate(canvas: Canvas) {
        val midWidth = width / 2f
        datePaint.textSize = height / 2.5f
        datePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        val dateMidY = headerHeight + (height - headerHeight) / 2
        val dateTextYPos = dateMidY - (datePaint.descent() + datePaint.ascent()) / 2
        canvas.drawText(monthDate, midWidth, dateTextYPos, datePaint)
    }

    private fun drawMonthName(canvas: Canvas) {
        val midWidth = width / 2f
        monthPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        monthPaint.textSize = width / 6f
        val monthMidY = headerHeight / 2f
        val monthTextYPos = monthMidY - (monthPaint.descent() + monthPaint.ascent()) / 2
        canvas.drawText(month, midWidth, monthTextYPos, monthPaint)
    }

    fun setDate(date: Date?) {
        val cal = Calendar.getInstance()
        cal.time = date ?: Date()
        monthDate = dayFormat.format(cal.time)
        month = monthFormat.format(cal.time)
        invalidateView()
    }

    fun setState(state: NewsState) {
        val color: Int = when (state) {
            NewsState.new -> ContextCompat.getColor(context, R.color.newsNewState)
            NewsState.read -> ContextCompat.getColor(context, R.color.newsReadState)
        }

        strokePaint.color = color
        headerPaint.color = color
        invalidateView()
    }

    private fun invalidateView() {
        requestLayout()
        invalidate()
    }
}