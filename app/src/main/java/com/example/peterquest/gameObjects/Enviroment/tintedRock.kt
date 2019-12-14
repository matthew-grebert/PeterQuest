package com.example.peterquest.gameObjects.Enviroment

import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.space

class tintedRock(x: Int, y: Int): gameObject(x, y) {

    override var yPos: Int = -1;
    override var xPos: Int = -1;
    override var tag: String = "TintedRock";


    init {
        xPos = x;
        yPos = y;
    }

}

