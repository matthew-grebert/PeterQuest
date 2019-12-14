package com.example.peterquest.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.peterquest.R
import com.example.peterquest.board.boardModel
import com.example.peterquest.space
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class boardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackground: Paint = Paint()
    var paintLine: Paint = Paint()
    var gameOver = false

    var bitmapMark: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.mark
    )
    var bitmapFloor: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.floor
    )

    var bitmapAvatar: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.avatar
    )

    var bitmapStairs: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.stairs
    )

    var bitmapBloodFloor: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.bloody_floor
    )

    var bitmapBat: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.bat
    )

    var bitmapBloodBat: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.bloodbat
    )

    var bitmapAndrie: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.andrie
    )

    var bitmapWinston: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.winston
    )

    var bitmapMaxine: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.maxine
    )



    /* //Initialize bitmaps
     var bitmap1: Bitmap = BitmapFactory.decodeResource(
         context?.resources, R.drawable.num1
     )
     var bitmap2: Bitmap = BitmapFactory.decodeResource(
         context?.resources, R.drawable.num2
     )
     var bitmap3: Bitmap = BitmapFactory.decodeResource(
         context?.resources, R.drawable.num3
     )
     var bitmapBomb: Bitmap = BitmapFactory.decodeResource(
         context?.resources, R.drawable.bomb
     )
     var bitmapEmpty: Bitmap = BitmapFactory.decodeResource(
         context?.resources, R.drawable.none
     )*/


    init {
        paintBackground.setColor(Color.GRAY)
        paintBackground.style = Paint.Style.FILL
        paintLine.color = Color.BLACK
        paintLine.style = Paint.Style.STROKE

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // paintText.textSize = height/3f
    }

    private fun scaleBitmaps(){
        bitmapMark = Bitmap.createScaledBitmap(bitmapMark, width / 6, height / 6, true)
        bitmapFloor = Bitmap.createScaledBitmap(bitmapFloor, width / 6, height / 6, true)
        bitmapAvatar = Bitmap.createScaledBitmap(bitmapAvatar, width / 6, height / 6, true)
        bitmapStairs = Bitmap.createScaledBitmap(bitmapStairs, width / 6, height / 6, true)
        bitmapBloodFloor = Bitmap.createScaledBitmap(bitmapBloodFloor, width / 6, height / 6, true)
        bitmapBat = Bitmap.createScaledBitmap(bitmapBat, width / 6, height / 6, true)
        bitmapBloodBat = Bitmap.createScaledBitmap(bitmapBloodBat, width / 6, height / 6, true)
        bitmapMaxine = Bitmap.createScaledBitmap(bitmapMaxine, width / 6, height / 6, true)
        bitmapAndrie = Bitmap.createScaledBitmap(bitmapAndrie, width / 6, height / 6, true)
        bitmapWinston = Bitmap.createScaledBitmap(bitmapWinston, width / 6, height / 6, true)
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        for (i in 1..6) {
            canvas?.drawLine(
                0f, (i * height / 6).toFloat(), width.toFloat(), (i * height / 6).toFloat(),
                paintLine
            )
            canvas?.drawLine(
                (i * width / 6).toFloat(), 0f, (i * width / 6).toFloat(), height.toFloat(),
                paintLine
            )
        }
        drawPlayers(canvas)
    }

    //@RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun drawPlayers(canvas: Canvas?) {
        scaleBitmaps()
        for (i in 0..5) {
            for (j in 0..5) {
                    drawSpace(i, j, canvas);
            }
        }
    }



    private fun drawSpace(col: Int, row: Int, canvas: Canvas?) {
       if (boardModel.getTag(row, col) == "Mark"){
           canvas?.drawBitmap(
               bitmapAndrie,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine)
       }else if (boardModel.getTag(row, col) == "Floor"){
           canvas?.drawBitmap(
               bitmapFloor,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine)
       }else if (boardModel.getTag(row, col) == "Avatar"){
           canvas?.drawBitmap(
               bitmapAvatar,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine)
       }else if (boardModel.getTag(row, col) == "Stairs"){
           canvas?.drawBitmap(
               bitmapStairs,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine)
       }else if (boardModel.getTag(row, col) == "BloodFloor"){
           canvas?.drawBitmap(
               bitmapBloodFloor,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine)
       }else if (boardModel.getTag(row, col) == "Bat") {
           canvas?.drawBitmap(
               bitmapBat,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine
           )
       }else if (boardModel.getTag(row, col) == "BloodBat") {
           canvas?.drawBitmap(
               bitmapBloodBat,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine
           )
       }else if (boardModel.getTag(row, col) == "Maxine") {
           canvas?.drawBitmap(
               bitmapMaxine,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine
           )
       }else if (boardModel.getTag(row, col) == "Winston") {
           canvas?.drawBitmap(
               bitmapWinston,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine
           )
       }else if (boardModel.getTag(row, col) == "Andrie") {
           canvas?.drawBitmap(
               bitmapAndrie,
               (col * width / 6).toFloat(),
               (row * height / 6).toFloat(),
               paintLine
           )
       }
    }

    fun attack(){
        var hit = boardModel.attackPart1()
        invalidate()
        if(hit) {
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(150)
                boardModel.attackPart2()
                invalidate()
            }
        }

    }


    fun moveAvatar(direction: String){

        if(boardModel.moveAvatar(direction)) {//If move was possible
            var death = boardModel.checkHit()
            invalidate()
            if (!death) {
                GlobalScope.launch(context = Dispatchers.Main) {
                    delay(100)
                    boardModel.updateEnemies()
                    boardModel.checkHit()
                    invalidate()
                }
            }
        }else{
            invalidate()
        }
    }
}






