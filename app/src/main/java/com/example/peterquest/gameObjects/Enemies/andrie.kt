package com.example.peterquest.gameObjects.Enemies

import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space
import kotlin.random.Random

class andrie(x: Int, y: Int) : gameObject(x, y) {

    override var health = 1;
    override var damage = 1;
    override var tag = "Andrie"
    var newPosY = 0;
    var newPosX = 0;

    override fun move(board: Array<Array<space>>, playerPosX: Int, playerPosY: Int) : Array<Int> {
        newPosX = xPos
        newPosY = yPos
        val direction = Random.nextInt(0, 3)
        if(xPos == 5 && yPos == 5){
            newPosY--
        }else if(direction == 0 && xPos != 0 ){
            newPosX--;
        }else if(direction == 1 && xPos != 5 || xPos == 0){
            newPosX++;
        }else if(direction == 2 && yPos != 0){
            newPosY--;
        }else if(direction == 1 && yPos != 5 || yPos == 0) {
            newPosY++;
        }

        update(board, xPos, yPos, newPosX, newPosY)

        return arrayOf(newPosX, newPosY)
    }

    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject = andrie(newPosX, newPosY)
        var floor = com.example.peterquest.gameObjects.Enviroment.toxicFloor(oldPosX, oldPosY)
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }
}