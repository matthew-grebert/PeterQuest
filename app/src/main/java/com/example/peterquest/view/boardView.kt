package com.example.peterquest.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.peterquest.R
import com.example.peterquest.board.boardModel
import com.example.peterquest.gameObjects.Enviroment.spawner
import com.example.peterquest.space
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

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
    var bitmapFire: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.fire
    )
    var bitmapWinstonFire: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.winston_fire
    )
    var bitmapWinstonCooldown: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.winston_cooldown
    )
    var bitmapRock: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.rock
    )
    var bitmapTintedRock: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.tinted_rock
    )
    var bitmapSpikes: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.spikes
    )
    var bitmapBloodSpikes: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.blood_spikes
    )
    var bitmapLock: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.lock
    )
    var bitmapToxicFloor: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.toxic_floor
    )
    var bitmapWarning: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.warning
    )
    var bitmapPeter1: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.peter1
    )
    var bitmapPeter2: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.peter2
    )
    var bitmapSpawner: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.spawner
    )
    var bitmapPeter3TL: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.peter3_tl
    )
    var bitmapPeter3TR: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.peter3_tr
    )
    var bitmapPeter3BL: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.peter3_bl
    )
    var bitmapPeter3BR: Bitmap = BitmapFactory.decodeResource(
        context?.resources, R.drawable.peter3_br
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

    private fun scaleBitmaps() {
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
        bitmapFire = Bitmap.createScaledBitmap(bitmapFire, width / 6, height / 6, true)
        bitmapWinstonFire =
            Bitmap.createScaledBitmap(bitmapWinstonFire, width / 6, height / 6, true)
        bitmapWinstonCooldown =
            Bitmap.createScaledBitmap(bitmapWinstonCooldown, width / 6, height / 6, true)
        bitmapRock = Bitmap.createScaledBitmap(bitmapRock, width / 6, height / 6, true)
        bitmapTintedRock = Bitmap.createScaledBitmap(bitmapTintedRock, width / 6, height / 6, true)
        bitmapSpikes = Bitmap.createScaledBitmap(bitmapSpikes, width / 6, height / 6, true)
        bitmapBloodSpikes =
            Bitmap.createScaledBitmap(bitmapBloodSpikes, width / 6, height / 6, true)
        bitmapLock = Bitmap.createScaledBitmap(bitmapLock, width / 6, height / 6, true)
        bitmapToxicFloor = Bitmap.createScaledBitmap(bitmapToxicFloor, width / 6, height / 6, true)
        bitmapWarning = Bitmap.createScaledBitmap(bitmapWarning, width / 6, height / 6, true)
        bitmapPeter1 = Bitmap.createScaledBitmap(bitmapPeter1, width / 6, height / 6, true)
        bitmapPeter2 = Bitmap.createScaledBitmap(bitmapPeter2, width / 6, height / 6, true)

        bitmapSpawner = Bitmap.createScaledBitmap(bitmapSpawner, width / 6, height / 6, true)
        bitmapPeter3TL = Bitmap.createScaledBitmap(bitmapPeter3TL, width / 6, height / 6, true)
        bitmapPeter3TR = Bitmap.createScaledBitmap(bitmapPeter3TR, width / 6, height / 6, true)
        bitmapPeter3BL = Bitmap.createScaledBitmap(bitmapPeter3BL, width / 6, height / 6, true)
        bitmapPeter3BR = Bitmap.createScaledBitmap(bitmapPeter3BR, width / 6, height / 6, true)
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
        if (boardModel.getTag(row, col) == "Mark") {
            canvas?.drawBitmap(
                bitmapMark,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Floor") {
            canvas?.drawBitmap(
                bitmapFloor,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Avatar") {
            canvas?.drawBitmap(
                bitmapAvatar,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Stairs") {
            canvas?.drawBitmap(
                bitmapStairs,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "BloodFloor") {
            canvas?.drawBitmap(
                bitmapBloodFloor,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Bat") {
            canvas?.drawBitmap(
                bitmapBat,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "BloodBat") {
            canvas?.drawBitmap(
                bitmapBloodBat,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Maxine") {
            canvas?.drawBitmap(
                bitmapMaxine,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Winston") {
            canvas?.drawBitmap(
                bitmapWinston,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Andrie") {
            canvas?.drawBitmap(
                bitmapAndrie,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Fire") {
            canvas?.drawBitmap(
                bitmapFire,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "WinstonFire") {
            canvas?.drawBitmap(
                bitmapWinstonFire,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "WinstonCooldown") {
            canvas?.drawBitmap(
                bitmapWinstonCooldown,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Rock") {
            canvas?.drawBitmap(
                bitmapRock,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "TintedRock") {
            canvas?.drawBitmap(
                bitmapTintedRock,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Spikes") {
            canvas?.drawBitmap(
                bitmapSpikes,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "BloodSpikes") {
            canvas?.drawBitmap(
                bitmapBloodSpikes,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Lock") {
            canvas?.drawBitmap(
                bitmapLock,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "ToxicFloor") {
            canvas?.drawBitmap(
                bitmapToxicFloor,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Warning") {
            canvas?.drawBitmap(
                bitmapWarning,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Peter1") {
            canvas?.drawBitmap(
                bitmapPeter1,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Peter2") {
            canvas?.drawBitmap(
                bitmapPeter2,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Spawner") {
            canvas?.drawBitmap(
                bitmapSpawner,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Peter3TR") {
            canvas?.drawBitmap(
                bitmapPeter3TR,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Peter3TL") {
            canvas?.drawBitmap(
                bitmapPeter3TL,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Peter3BR") {
            canvas?.drawBitmap(
                bitmapPeter3BR,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "Peter3BL") {
            canvas?.drawBitmap(
                bitmapPeter3BL,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        } else if (boardModel.getTag(row, col) == "SpikeWarning") {
            canvas?.drawBitmap(
                bitmapWarning,
                (col * width / 6).toFloat(),
                (row * height / 6).toFloat(),
                paintLine
            )
        }
    }

    fun attack() {
        var hit = boardModel.attackPart1()
        invalidate()
        if (hit) {
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(150)
                boardModel.attackPart2()
                invalidate()
                delay(300)
                invalidate()
                delay(300)
                invalidate()
                delay(300)
                invalidate()
            }
        }

    }


    fun moveAvatar(direction: String) {

        if (boardModel.moveAvatar(direction)) {//If move was possible
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
        } else {
            invalidate()
        }
    }
}






