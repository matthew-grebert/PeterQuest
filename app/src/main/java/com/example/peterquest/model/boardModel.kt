package com.example.peterquest.board

import com.example.peterquest.gameObjects.Avatar.avatar
import com.example.peterquest.gameObjects.Avatar.bat
import com.example.peterquest.gameObjects.Avatar.bloodBat
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.gameObjects.Enemies.mark
import com.example.peterquest.gameObjects.Enviroment.bloodFloor
import com.example.peterquest.gameObjects.Enviroment.floor
import com.example.peterquest.gameObjects.Enviroment.stairs
import com.example.peterquest.space

object boardModel {

    var currRoom = 1
    var numEnemies = 0
    var newRoom = true
    var enemies = arrayOf(gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1))
    var playerPosx =0
    var playerPosy = 0
    private val board = arrayOf(
        arrayOf(
            space(),
            space(),
            space(),
            space(),
            space(),
            space()
        ),
        arrayOf(
            space(),
            space(),
            space(),
            space(),
            space(),
            space()
        ),
        arrayOf(
            space(),
            space(),
            space(),
            space(),
            space(),
            space()
        ),
        arrayOf(
            space(),
            space(),
            space(),
            space(),
            space(),
            space()
        ),
        arrayOf(
            space(),
            space(),
            space(),
            space(),
            space(),
            space()
        ),
        arrayOf(
            space(),
            space(),
            space(),
            space(),
            space(),
            space()
        )
    )

    init {
        drawRoom()
    }

    fun drawRoom(){
        when(currRoom){
            1 -> drawRoom1()
            2 -> drawRoom2()
        }
        newRoom = false
    }

    fun drawRoom1(){

        for (i in 0..5) {
            for (j in 0..5) {
                if(!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                }else{
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }

        var mark1 = mark(4, 2)
        board[4][2].gameObject = mark1
        enemies[0] = mark1;

        var mark2 = mark(2, 5)
        board[2][5].gameObject = mark2
        enemies[1] = mark2

        playerPosx = 5
        playerPosy = 5
        var avatar = avatar(5, 5)
        board[5][5].gameObject = avatar

        var stairs = stairs(0, 5)
        board[0][5].gameObject = stairs

        numEnemies = 2
    }

    fun drawRoom2(){

        for (i in 0..5) {
            for (j in 0..5) {
                if(!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                }else{
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }
            }
        }

        var mark1 = mark(3, 5)
        board[3][5].gameObject = mark1
        enemies[0] = mark1;

        var mark2 = mark(2, 4)
        board[2][4].gameObject = mark2
        enemies[1] = mark2

        playerPosx = 5
        playerPosy = 3
        var avatar = avatar(5, 3)
        board[5][3].gameObject = avatar

        var stairs = stairs(0, 5)
        board[0][0].gameObject = stairs

        numEnemies = 2
    }




    fun moveAvatar(direction: String): Boolean{
        var oldPlayerX = playerPosx
        var oldPlayerY = playerPosy
        var toReturn = true
        if(direction == "up"){
            if(playerPosx - 1 > -1 && board[playerPosx -1][playerPosy].legalPlayerMove() ){
                playerPosx = playerPosx-1
            }else{
                toReturn = false
            }
        }
        if(direction == "down"){
            if(playerPosx + 1 < 6 && board[playerPosx+1][playerPosy].legalPlayerMove()){
                playerPosx = playerPosx+1
            }else{
                toReturn = false
            }
        }
        if(direction == "right"){
            if(playerPosy + 1 < 6 && board[playerPosx][playerPosy+1].legalPlayerMove()){
                playerPosy = playerPosy+1
            }else{
                toReturn = false
            }
        }
        if(direction == "left"){
            if(playerPosy -1 > -1 && board[playerPosx ][playerPosy-1].legalPlayerMove()){
                playerPosy = playerPosy-1
            }else{
                toReturn = false
            }
        }
        var tag = getTag(playerPosx, playerPosy)
        if(tag == "Stairs"){
            currRoom++
            newRoom = true
            drawRoom()
            return false
        }

        if(toReturn) {
            board[oldPlayerX][oldPlayerY].gameObject.update(
                board,
                oldPlayerX,
                oldPlayerY,
                playerPosx,
                playerPosy
            )
        }
        return toReturn
       // avatar = board[playerPosx][playerPosy].gameObject
    }

    fun checkHit(): Boolean{
        var toReturn = false
        for (i in 0..numEnemies-1){

            if(playerPosx == enemies[i].xPos && playerPosy == enemies[i].yPos){
                board[playerPosx][playerPosy].isBloody = true
                toReturn = true
                drawRoom()
            }
        }
        return toReturn
    }

    fun getTag(x: Int, y: Int) = board[x][y].getTag()


    fun attackPart1(): Boolean{
        var hit = false
        if(canAttack()){
            for (i in 0..numEnemies - 1) {
                if (playerPosx - 1 == enemies[i].xPos && playerPosy == enemies[i].yPos) {
                    board[enemies[i].xPos][enemies[i].yPos].isBloody = true
                    board[playerPosx-1][playerPosy].gameObject = bloodBat(enemies[i].xPos, enemies[i].yPos)
                    enemies[i].kill()
                    hit = true
                }
            }
            if(hit == false){
                board[playerPosx-1][playerPosy].gameObject = bat(playerPosx-1, playerPosy)
            }
            return true
        }
        return false
    }

    fun attackPart2(){
        if(board[playerPosx-1][playerPosy].isBloody){
            board[playerPosx-1][playerPosy].gameObject = bloodFloor(playerPosx-1, playerPosy)
        }else{
            board[playerPosx-1][playerPosy].gameObject = floor(playerPosx-1, playerPosy)
        }
    }

    fun canAttack(): Boolean{
        if(playerPosx != 0 && board[playerPosx-1][playerPosy].legalBatSwing()) {
            return true
        }
        return false
    }

    fun updateEnemies() {
        for (x in 0..numEnemies-1){
            var updateArray: Array<Int>
            if(enemies[x].isAlive) {
                updateArray = enemies[x].move(board, playerPosx, playerPosy)
                enemies[x] = board[updateArray[0]][updateArray[1]].gameObject
            }
        }

    }

}
    /*
    fun setFieldContent(x: Int, y: Int) {
        board[x][y].bomb = true
    }

    fun isVisible(x :Int, y :Int) = board[x][y].isRevealed

    fun makeVisable(x: Int, y :Int){
        board[x][y].isRevealed = true;
        currentRevealed++

    }

    fun setBombCount(x :Int, y :Int, bombs :Int){

        board[x][y].bombsNear = bombs
    }

    fun getBombCount(x: Int, y: Int) :Int{
        return board[x][y].bombsNear
    }

    fun resetboard() {
        for (i in 0..4) {
            for (j in 0..4) {
                board[i][j] = space()
            }
        }
    }

    fun checkWin(col :Int, row :Int) = (currentRevealed == maxReveal && checkBomb(col, row) == false)
*/

