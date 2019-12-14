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
        if(gameObject.tag == "Floor" || gameObject.tag == "BloodFloor" || gameObject.tag == "Avatar" || gameObject.tag == "Spikes" || gameObject.tag == "BloodSpikes"){
            return true;
        }else{
            return false;
        }
    }

    fun legalFireMove() : Boolean{
        if(gameObject.tag != "Stairs" && gameObject.tag != "Winston" && gameObject.tag != "WinstonCooldown" && gameObject.tag != "WinstonFire" && gameObject.tag != "Peter2"){
            return true;
        }else{
            return false;
        }
    }

    fun legalWarning(): Boolean{
        if(gameObject.tag != "Avatar" && gameObject.tag != "Peter3BR" && gameObject.tag != "Peter3BL" && gameObject.tag != "Peter3TL" && gameObject.tag != "Peter3TR"){
            return true
        }
        return false
    }

    fun legalPlayerMove() : Boolean{
        if(gameObject.tag != "Rock" && gameObject.tag != "TintedRock" && gameObject.tag != "Lock"){
            return true;
        }else{
            return false;
        }
    }

    fun legalBatSwing() : Boolean{
        if(gameObject.tag != "Stairs" && gameObject.tag != "Rock" && gameObject.tag != "TintedRock"){
            return true;
        }else{
            return false;
        }
    }

    fun getTag() : String{
        return gameObject.tag
    }
}