package com.example.peterquest.gameObjects

import com.example.peterquest.R
import com.example.peterquest.space

open class gameObject(x: Int, y: Int) {

    open var yPos: Int = -1;
    open var xPos: Int = -1;
    open var tag: String = "";
    open var health = 0;
    open var damage = 0;
    open var isAlive = true


    init {
        xPos = x;
        yPos = y;
    }

    open fun move(board: Array<Array<space>>, playerPosX: Int, playerPosY: Int) : Array<Int>{
        var toReturn = arrayOf(xPos,yPos)

        return toReturn
    }

    open fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject = gameObject(newPosX, newPosY)
        var floor = gameObject(oldPosX, oldPosY)
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }

    fun kill(){
        isAlive = false
        xPos = -1
        yPos = -1
    }


}

