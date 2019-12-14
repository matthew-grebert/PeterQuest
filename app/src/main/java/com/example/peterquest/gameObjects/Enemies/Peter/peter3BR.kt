package com.example.peterquest.gameObjects.Enemies.Peter

import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class peter3BR(x: Int, y: Int) : gameObject(x, y) {

    override var tag = "Peter3BR"
    var newPosY = 0;
    var newPosX = 0;

    override fun move(board: Array<Array<space>>, playerPosX: Int, playerPosY: Int) : Array<Int> {
        newPosX = xPos
        newPosY = yPos
        return arrayOf(newPosX, newPosY)
    }

    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject =
            peter3BR(newPosX, newPosY)
        var floor: gameObject
        if(!board[oldPosX][oldPosY].isBloody) {
            floor = com.example.peterquest.gameObjects.Enviroment.floor(oldPosX, oldPosY)
        }else{
            floor = bloodFloor(oldPosX, oldPosY)
        }
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }
}