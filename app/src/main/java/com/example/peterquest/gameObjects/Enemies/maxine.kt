package com.example.peterquest.gameObjects.Enemies

import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class maxine(x: Int, y: Int) : gameObject(x, y) {

    override var health = 1;
    override var damage = 1;
    override var tag = "Maxine"
    var newPosY = 0;
    var newPosX = 0;

    override fun move(board: Array<Array<space>>, playerPosX: Int, playerPosY: Int) : Array<Int> {
        newPosX = xPos
        newPosY = yPos
        if(board[5-playerPosX][5-playerPosY].legalEnemyMove()){
            newPosX = 5-playerPosX
            newPosY = 5-playerPosY
            update(board, xPos, yPos, newPosX, newPosY)
        }



        return arrayOf(newPosX, newPosY)
    }

    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject = maxine(newPosX, newPosY)
        var floor: gameObject
        if(!board[oldPosX][oldPosY].isBloody) {
            floor = com.example.peterquest.gameObjects.Enviroment.floor(oldPosX, oldPosY)
        }else{
            floor = bloodFloor(oldPosX, oldPosY)
        }
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }
}