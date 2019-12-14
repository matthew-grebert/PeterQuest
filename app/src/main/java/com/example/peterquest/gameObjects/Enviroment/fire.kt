package com.example.peterquest.gameObjects.Enviroment

import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class fire(x: Int, y: Int): gameObject(x, y) {

    override var yPos: Int = -1;
    override var xPos: Int = -1;
    override var tag: String = "Fire";


    init {
        xPos = x;
        yPos = y;
    }



    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject = fire(newPosX, newPosY)
        var floor = floor(oldPosX, oldPosY)
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }

}

