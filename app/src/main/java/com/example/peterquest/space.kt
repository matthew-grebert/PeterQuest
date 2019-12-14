package com.example.peterquest

import com.example.peterquest.gameObjects.gameObject

class space(x: Int, y: Int) {

    var posX: Int = x;
    var posY: Int = y;
    var gameObject: gameObject = gameObject(x, y);
    var isBloody = false

    constructor(): this(0, 0){

    }

    fun updateSpace(x:Int, y:Int, go:gameObject){
        gameObject = go
        posX = x
        posY = y
    }

    fun legalEnemyMove() : Boolean{
        if(gameObject.tag == "Floor" || gameObject.tag == "BloodFloor" || gameObject.tag == "Avatar"){
            return true;
        }else{
            return false;
        }
    }

    fun legalPlayerMove() : Boolean{
        if(gameObject.tag != "Rock"){
            return true;
        }else{
            return false;
        }
    }

    fun legalBatSwing() : Boolean{
        if(gameObject.tag != "Stairs" && gameObject.tag != "Rock"){
            return true;
        }else{
            return false;
        }
    }

    fun getTag() : String{
        return gameObject.tag
    }
}