package com.example.peterquest.gameObjects.Enviroment

import com.example.peterquest.gameObjects.gameObject

class lock(x: Int, y: Int): gameObject(x, y) {

    override var yPos: Int = -1;
    override var xPos: Int = -1;
    override var tag: String = "Lock";


    init {
        xPos = x;
        yPos = y;
    }

}

