package com.example.peterquest.gameObjects.Enemies.Peter

import com.example.peterquest.gameObjects.Enemies.winston.winstonFire
import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class peter1(x: Int, y: Int) : gameObject(x, y) {

    override var tag = "Peter1"
    var newPosY = 0;
    var newPosX = 0;

    override fun move(board: Array<Array<space>>, playerPosX: Int, playerPosY: Int) : Array<Int> {
        newPosX = xPos
        newPosY = yPos
        if (yPos != playerPosY) {
            if (yPos < playerPosY && board[xPos][yPos+1].legalEnemyMove()) {
                newPosY = yPos+1;
                newPosX = xPos;
            } else {
                if (yPos > playerPosY && board[xPos][yPos-1].legalEnemyMove()) {
                    newPosY = yPos-1;
                    newPosX = xPos;
                }
            }
        }else if(xPos != playerPosX){
            if (xPos < playerPosX && board[xPos+1][yPos].legalEnemyMove()) {
                newPosY = yPos;
                newPosX = xPos+1;
            } else {
                if (xPos > playerPosX && board[xPos-1][yPos].legalEnemyMove()) {
                    newPosY = yPos;
                    newPosX = xPos-1;
                }
            }
        }

        if(xPos != newPosX || yPos != newPosY) {
            update(board, xPos, yPos, newPosX, newPosY)
        }

        return arrayOf(newPosX, newPosY)
    }

    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject =
            peter1(newPosX, newPosY)
        var floor: gameObject
        if(!board[oldPosX][oldPosY].isBloody) {
            floor = com.example.peterquest.gameObjects.Enviroment.floor(oldPosX, oldPosY)
        }else{
            floor = bloodFloor(oldPosX, oldPosY)
        }
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)


    }
}