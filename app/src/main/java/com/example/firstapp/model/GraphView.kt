package com.example.firstapp.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.firstapp.utils.WeatherPreferences
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class GraphView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val maxTempDataSet = mutableListOf<DataPoint>()

    private var maxTempXMin = 0
    private var maxTempXMax = 0
    private var maxTempYMin = 0
    private var maxTempYMax = 0
    private var maxTempTotalLevels = 0

    private val formatDate = SimpleDateFormat("EEEE", Locale(WeatherPreferences.getLanguage()))

    private val dataPointPaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 7f
        style = Paint.Style.FILL
    }

    private val dataMaxPointLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 7f
        isAntiAlias = true
    }

    private val text = Paint().apply {
        style = Paint.Style.FILL
        textSize = 20f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGraph(canvas)
    }

    private fun drawGraph(canvas: Canvas){
        maxTempDataSet.forEachIndexed { index, currentDataPoint ->
            //x,y position of the current point
            val startX = currentDataPoint.xVal.toRealX()

            /**
             *
            Based on the x,y axis of the screen, calculate the starting y position, where we subtract from height
            the point value, for example if one currentDataPoint value is 10 and the minimum temperature
            value is 5(because we don't start from 0 but from the minimum value of temperature) we get 5, then we
            subtract this value from height(level of temperatures).
             */
            val startY =(height - (currentDataPoint.yVal-maxTempYMin).toRealY())*0.9f

            val day = formatDate.format(currentDataPoint.day)

            if (index < maxTempDataSet.size - 1) {
                val nextDataPoint = maxTempDataSet[index + 1]

                //x,y position of the next point
                val endX = nextDataPoint.xVal.toRealX()
                val endY = (height -(nextDataPoint.yVal-maxTempYMin).toRealY())*0.9f

                //draw line to connect current point with the next one
                canvas.drawLine(startX, startY , endX,  endY, dataMaxPointLinePaint)
            }

            //draw point as circle
            canvas.drawCircle(startX, startY, 7f, dataPointPaint)

            //display temperature value on x axis
            canvas.drawText("${currentDataPoint.yVal} C", 50f,startY,text)

            //display day on y axis
            canvas.drawText("$day",startX,height-10.toFloat(),text)
        }
    }

    fun setData(forecastMaxTempSet: List<DataPoint>) {
        maxTempXMin = forecastMaxTempSet.minByOrNull { it.xVal }?.xVal ?: 0
        maxTempXMax = forecastMaxTempSet.maxByOrNull { it.xVal }?.xVal ?: 0
        maxTempYMin = forecastMaxTempSet.minByOrNull { it.yVal }?.yVal ?: 0
        maxTempYMax = forecastMaxTempSet.maxByOrNull { it.yVal }?.yVal ?: 0

        maxTempTotalLevels =abs(maxTempYMax-maxTempYMin)  + 1

        maxTempDataSet.clear()
        maxTempDataSet.addAll(forecastMaxTempSet)
        invalidate()
    }


    //Calculate x value based on the width of the screen and I multiplied width by 0.9 to not use the whole screen space
    private fun Int.toRealX() = toFloat() / maxTempXMax * (width * 0.9f)

    /**
     *
    Calculate y value based on the height of the screen and it takes as a starting point the minimum value of the temperature,
    so it doesn't start from 0. Also it is multiplied height by 0.9 to not use the whole space
     */
    private fun Int.toRealY() = toFloat() * ((height*0.9f) / maxTempTotalLevels)
}

data class DataPoint(
    val xVal: Int,
    val yVal: Int,
    val day : Date
)