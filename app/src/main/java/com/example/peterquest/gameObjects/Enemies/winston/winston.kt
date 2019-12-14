package com.example.peterquest.gameObjects.Enemies.winston

import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class winston(x: Int, y: Int) : gameObject(x, y) {

    override var health = 1;
    override var damage = 1;
    override var tag = "Winston"
    var newPosY = 0;
    var newPosX = 0;

    override fun move(board: Array<Array<space>>, playerPosX: Int, playerPosY: Int) : Array<Int> {
        newPosX = xPos
        newPosY = yPos
        return arrayOf(newPosX, newPosY)
    }

    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject =
            winstonFire(newPosX, newPosY)
        var floor: gameObject
        if(!board[oldPosX][oldPosY].isBloody) {
            floor = com.example.peterquest.gameObjects.Enviroment.floor(oldPosX, oldPosY)
        }else{
            floor = bloodFloor(oldPosX, oldPosY)
        }
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }
}