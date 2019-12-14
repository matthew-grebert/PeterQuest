package com.example.peterquest.gameObjects.Avatar

import com.example.peterquest.R
import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.Enviroment.floor
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class avatar(x: Int, y: Int) : gameObject(x, y) {

    override var health = 1;
    override var damage = 1;
    override var tag = "Avatar"
    var newPosY = 0;
    var newPosX = 0;


    override fun update(board: Array<Array<space>>, oldPosX: Int, oldPosY: Int, newPosX: Int, newPosY: Int){

        board[newPosX][newPosY].gameObject = avatar(newPosX, newPosY)
        var floor: gameObject
        if(!board[oldPosX][oldPosY].isBloody) {
            floor = floor(oldPosX, oldPosY)
        }else{
            floor = bloodFloor(oldPosX, oldPosY)
        }
        board[oldPosX][oldPosY].updateSpace(oldPosX, oldPosY, floor)



    }
}