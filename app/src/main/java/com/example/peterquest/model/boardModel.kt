package com.example.peterquest.board

import com.example.peterquest.gameObjects.Avatar.avatar
import com.example.peterquest.gameObjects.Avatar.bat
import com.example.peterquest.gameObjects.Avatar.bloodBat
import com.example.peterquest.gameObjects.Enemies.Peter.*
import com.example.peterquest.gameObjects.Enemies.andrie
import com.example.peterquest.gameObjects.gameObject
import com.example.peterquest.gameObjects.Enemies.mark
import com.example.peterquest.gameObjects.Enemies.maxine
import com.example.peterquest.gameObjects.Enemies.winston.winston
import com.example.peterquest.gameObjects.Enemies.winston.winstonCooldown
import com.example.peterquest.gameObjects.Enemies.winston.winstonFire
import com.example.peterquest.gameObjects.Enviroment.*
import com.example.peterquest.space
import com.example.peterquest.view.boardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

object boardModel {



    var currRoom = 1
    var numEnemies = 0
    var newRoom = true
    var peterCycle = 1;
    var numSpikes = 0
    var timing = false;

    var enemies = arrayOf(
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1)
    )

    var spikes = arrayOf(
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1),
        gameObject(-1, -1)
    )
    var playerPosx = 0
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


    fun drawRoom() {
        when (currRoom) {
            1 -> drawRoom1()
            2 -> drawRoom2()
            3 -> drawRoom3()
            4 -> drawRoom4()
            5 -> drawRoom5()
            6 -> drawRoom6()
            7 -> drawRoom7()
            8 -> drawRoom8()
            9 -> drawRoom9()
            10 -> drawRoom10()
            11 -> drawRoom11()
            12 -> drawRoom12()
            13 -> drawRoom13()
        }
        newRoom = false
    }


    fun killPlayer() {
        board[playerPosx][playerPosy].isBloody = true

        drawRoom()
    }

    fun moveAvatar(direction: String): Boolean {
        var oldPlayerX = playerPosx
        var oldPlayerY = playerPosy
        var toReturn = true
        if (direction == "up") {
            if (playerPosx - 1 > -1 && board[playerPosx - 1][playerPosy].legalPlayerMove()) {
                playerPosx = playerPosx - 1
            } else {
                toReturn = false
            }
        }
        if (direction == "down") {
            if (playerPosx + 1 < 6 && board[playerPosx + 1][playerPosy].legalPlayerMove()) {
                playerPosx = playerPosx + 1
            } else {
                toReturn = false
            }
        }
        if (direction == "right") {
            if (playerPosy + 1 < 6 && board[playerPosx][playerPosy + 1].legalPlayerMove()) {
                playerPosy = playerPosy + 1
            } else {
                toReturn = false
            }
        }
        if (direction == "left") {
            if (playerPosy - 1 > -1 && board[playerPosx][playerPosy - 1].legalPlayerMove()) {
                playerPosy = playerPosy - 1
            } else {
                toReturn = false
            }
        }
        var tag = getTag(playerPosx, playerPosy)
        if (tag == "Stairs") {
            currRoom++
            newRoom = true
            drawRoom()
            return false
        } else if (tag == "Fire" || tag == "Spikes" || tag == "BloodSpikes" || tag == "ToxicFloor") {
            killPlayer()
            return false
        }

        if (toReturn) {
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

    fun checkHit(): Boolean {
        var toReturn = false
        for (i in 0..numEnemies - 1) {

            if ((playerPosx == enemies[i].xPos && playerPosy == enemies[i].yPos) || (board[playerPosx][playerPosy].gameObject.tag == "Fire")) {
                killPlayer()
                toReturn = true
            }
        }
        return toReturn
    }

    fun getTag(x: Int, y: Int) = board[x][y].getTag()

    fun killEnemy(index: Int) {

        enemies[index].kill()
        if (enemies[index].tag == "Maxine") {
            for (i in 0..5) {
                for (j in 0..5) {
                    if (board[i][j].getTag() == "TintedRock") {
                        if (board[i][j].isBloody) {
                            board[i][j].gameObject = bloodFloor(i, j)
                        } else {
                            board[i][j].gameObject = floor(i, j)
                        }
                    }
                }
            }
        }

        if (enemies[index].tag == "Andrie") {
            for (i in 0..5) {
                for (j in 0..5) {
                    if (board[i][j].getTag() == "Lock") {
                        board[i][j].gameObject = (stairs(i, j))
                    }
                }
            }
        }
        if (enemies[index].tag == "Peter1" || enemies[index].tag == "Peter2" || enemies[index].tag == "Peter3BR" || enemies[index].tag == "Peter3BL") {
            currRoom++
            drawRoom()
        }
    }

    fun attackPart1(): Boolean {
        var hit = false
        if (canAttack()) {
            for (i in 0..numEnemies - 1) {
                if (playerPosx - 1 == enemies[i].xPos && playerPosy == enemies[i].yPos) {
                    board[enemies[i].xPos][enemies[i].yPos].isBloody = true
                    board[playerPosx - 1][playerPosy].gameObject =
                        bloodBat(enemies[i].xPos, enemies[i].yPos)
                    killEnemy(i)
                    hit = true
                }
            }
            if (hit == false) {
                board[playerPosx - 1][playerPosy].gameObject = bat(playerPosx - 1, playerPosy)
            }
            return true
        }
        return false
    }

    fun attackPart2() {
        if (board[playerPosx - 1][playerPosy].isBloody) {
            board[playerPosx - 1][playerPosy].gameObject = bloodFloor(playerPosx - 1, playerPosy)
        } else {
            board[playerPosx - 1][playerPosy].gameObject = floor(playerPosx - 1, playerPosy)
        }
    }

    fun canAttack(): Boolean {
        if (playerPosx != 0 && board[playerPosx - 1][playerPosy].legalBatSwing()) {
            return true
        }
        return false
    }

    fun updateEnemies() {

        for (i in 0..5) {
            for (j in 0..5) {
                if (board[i][j].getTag() == "Fire") {
                    if (board[i][j].isBloody) {
                        board[i][j].gameObject = bloodFloor(i, j)
                    } else {
                        board[i][j].gameObject = floor(i, j)
                    }
                }
                if (board[i][j].getTag() == "Warning") {
                    board[i][j].gameObject = fire(i, j)
                }

                if (board[i][j].getTag() == "SpikeWarning") {
                    if(numSpikes < 9) {
                        val newSpikes = bloodSpikes(i, j)
                        board[i][j].gameObject = newSpikes
                        numSpikes++
                    }
                }

                if(board[i][j].getTag() == "Spawner"){
                    val newEnemy = Random.nextInt(0, 3)

                    if(numEnemies < 9) {

                        if (newEnemy == 0) {
                            val mark1 = mark(i, j)
                            board[i][j].gameObject = mark1
                            enemies[numEnemies] = mark1

                        } else if (newEnemy == 1){
                            val andrie1 = andrie(i, j)
                            board[i][j].gameObject = andrie1
                            enemies[numEnemies] = andrie1
                        }else if (newEnemy == 2){
                            val win1 = winston(i, j)
                            board[i][j].gameObject = win1
                            enemies[numEnemies] = win1
                        }
                        numEnemies++
                    }

                }
            }
        }
        for (x in 0..numEnemies - 1) {
            var updateArray: Array<Int>
            if (enemies[x].isAlive) {

                if(enemies[x].tag == "Peter2"){
                    val y = Random.nextInt(0, 5)
                    val x = Random.nextInt(0,5)
                    if(board[x][y].getTag() == "BloodFloor"){
                        board[x][y].gameObject = spawner(x, y)
                    }
                }

                if(enemies[x].tag == "Peter1"){
                    val y = Random.nextInt(0, 5)
                    val x = Random.nextInt(0,5)
                    if(board[x][y].getTag() == "Floor"){
                        board[x][y].gameObject = spikeWarning(x, y)
                    }
                }

                if (enemies[x].tag == "Peter3BR") {
                    if (peterCycle == 1) {
                        for (i in 0..2) {
                            for (j in 0..5) {
                                if (board[i * 2][j].legalWarning()) {
                                    board[i * 2][j].gameObject = warning(i * 2, j)
                                }
                            }
                        }
                        peterCycle++;
                    } else if (peterCycle == 2) {
                        for (i in 0..2) {
                            for (j in 0..5) {
                                if (playerPosx == i * 2 && playerPosy == j) {
                                    killPlayer()
                                }
                            }
                        }

                        peterCycle++;
                    } else if (peterCycle == 3) {
                        for (i in 0..2) {
                            for (j in 0..5) {
                                if (board[j][i * 2].legalWarning()) {
                                    board[j][i * 2].gameObject = warning(j, i * 2)
                                }
                            }
                        }
                        peterCycle++
                    } else if (peterCycle == 4) {
                        for (i in 0..2) {
                            for (j in 0..5) {
                                if (playerPosx == j && playerPosy == i * 2) {
                                    killPlayer()
                                }
                            }
                        }

                        peterCycle = 1
                    }
                }



                if (enemies[x].tag == "Winston") {
                    board[enemies[x].xPos][enemies[x].yPos].gameObject =
                        winstonFire(
                            enemies[x].xPos,
                            enemies[x].yPos
                        )
                    enemies[x] = board[enemies[x].xPos][enemies[x].yPos].gameObject
                } else if (enemies[x].tag == "WinstonCooldown") {
                    board[enemies[x].xPos][enemies[x].yPos].gameObject =
                        winston(
                            enemies[x].xPos,
                            enemies[x].yPos
                        )
                    enemies[x] = board[enemies[x].xPos][enemies[x].yPos].gameObject
                } else if (enemies[x].tag == "WinstonFire") {
                    var xPos = enemies[x].xPos
                    var yPos = enemies[x].yPos
                    for (i in 0..5) {
                        if (xPos + i < 6 && board[xPos + i][yPos].legalFireMove()) {
                            board[xPos + i][yPos].gameObject = fire(xPos + i, yPos)
                        }
                        if (xPos - i > -1 && board[xPos - i][yPos].legalFireMove()) {
                            board[xPos - i][yPos].gameObject = fire(xPos - i, yPos)
                        }
                        if (yPos + i < 6 && board[xPos][yPos + i].legalFireMove()) {
                            board[xPos][yPos + i].gameObject = fire(xPos, yPos + i)
                        }
                        if (yPos - i > -1 && board[xPos][yPos - i].legalFireMove()) {
                            board[xPos][yPos - i].gameObject = fire(xPos, yPos - i)
                        }
                    }
                    board[enemies[x].xPos][enemies[x].yPos].gameObject =
                        winstonCooldown(
                            enemies[x].xPos,
                            enemies[x].yPos
                        )
                    enemies[x] = board[enemies[x].xPos][enemies[x].yPos].gameObject
                } else {
                    updateArray = enemies[x].move(board, playerPosx, playerPosy)
                    enemies[x] = board[updateArray[0]][updateArray[1]].gameObject
                    if (checkSpikes(enemies[x])) {
                        killEnemy(x)
                    }
                }
            }
        }
    }

    fun checkSpikes(enemy: gameObject): Boolean {
        var dead = false
        for (j in 0..boardModel.numSpikes - 1) {
            if (enemy.xPos == spikes[j].xPos && enemy.yPos == spikes[j].yPos) {
                board[spikes[j].xPos][spikes[j].yPos].gameObject =
                    bloodSpikes(spikes[j].xPos, spikes[j].yPos)
                board[spikes[j].xPos][spikes[j].yPos].isBloody = true
                return true
            }
        }
        return false
    }



    fun drawRoom1() {

        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }

        var mark1 = mark(0, 4)
        board[0][4].gameObject = mark1
        enemies[0] = mark1;


        playerPosx = 5
        playerPosy = 2
        var avatar = avatar(5, 2)
        board[5][2].gameObject = avatar

        var stairs = stairs(0, 5)
        board[0][5].gameObject = stairs

        numEnemies = 1
        numSpikes = 0
    }

    fun drawRoom2() {
        for (i in 0..5) for (j in 0..5) {
            if (!board[i][j].isBloody || newRoom == true) {
                var toCreate = space(i, j)
                toCreate.gameObject = floor(i, j)
                board[i][j] = toCreate
            } else {
                var toCreate = space(i, j)
                toCreate.gameObject = bloodFloor(i, j)
                board[i][j] = toCreate
                board[i][j].isBloody = true
            }

        }
        val mark1 = mark(3, 1)
        board[3][1].gameObject = mark1
        enemies[0] = mark1;


        var mark2 = mark(1, 3)
        board[1][3].gameObject = mark2
        enemies[1] = mark2;


        playerPosx = 5
        playerPosy = 2
        var avatar = avatar(5, 2)
        board[5][2].gameObject = avatar

        var stairs = stairs(0, 0)
        board[0][0].gameObject = stairs

        for (i in 1..5) {
            board[i][0].gameObject = rock(i, 0)
        }

        board[3][4].gameObject = rock(3, 4)
        board[4][4].gameObject = rock(4, 4)
        board[5][4].gameObject = rock(5, 4)
        board[2][5].gameObject = rock(2, 5)
        board[3][5].gameObject = rock(3, 5)
        board[4][5].gameObject = rock(4, 5)
        board[5][5].gameObject = rock(5, 5)

        numEnemies = 2
        numSpikes = 0

    }

    fun drawRoom3() {
        for (i in 0..5) {
            for (j in 0..5) {
                var toCreate = space(i, j)
                toCreate.gameObject = rock(i, j)
                board[i][j] = toCreate
            }
        }




        playerPosx = 5
        playerPosy = 5
        var avatar = avatar(5, 5)
        board[5][5].gameObject = avatar

        var stairs = stairs(0, 5)
        board[0][5].gameObject = stairs

        board[4][5].gameObject = floor(4, 5)
        board[4][4].gameObject = floor(4, 4)
        board[3][4].gameObject = floor(3, 4)
        board[3][3].gameObject = floor(3, 3)
        board[3][2].gameObject = floor(3, 2)
        board[3][1].gameObject = floor(3, 1)
        board[2][1].gameObject = floor(2, 1)
        board[1][1].gameObject = floor(1, 1)
        board[1][2].gameObject = floor(1, 2)
        board[0][2].gameObject = floor(0, 2)
        board[0][3].gameObject = floor(0, 3)
        board[0][4].gameObject = floor(0, 4)


        numEnemies = 0
        numSpikes = 0
    }

    fun drawRoom4() {

        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }

        var max1 = maxine(0, 0)
        board[0][0].gameObject = max1
        enemies[0] = max1;

        /*var mark2 = mark(2, 5)
        board[2][5].gameObject = mark2
        enemies[1] = mark2*/

        playerPosx = 5
        playerPosy = 5
        var avatar = avatar(5, 5)
        board[5][5].gameObject = avatar

        var stairs = stairs(0, 5)
        board[0][5].gameObject = stairs

        var spike1 = gameObject(1, 5)
        if (board[1][5].isBloody) {
            spike1 = bloodSpikes(1, 5)
        } else {
            spike1 = spikes(1, 5)
        }
        board[1][5].gameObject = spike1
        spikes[0] = spike1

        for (i in 0..5) {
            var rock = rock(3, i)
            board[3][i].gameObject = rock
        }
        var tintedRock = tintedRock(3, 3)
        board[3][3].gameObject = tintedRock

        numEnemies = 1
        numSpikes = 1
    }

    fun drawRoom5() {
        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }

        var max1 = maxine(0, 5)
        board[0][5].gameObject = max1
        enemies[0] = max1;

        var mark = mark(5, 5)
        board[5][5].gameObject = mark
        enemies[1] = mark


        var spike1 = gameObject(1, 5)
        if (board[1][5].isBloody) {
            spike1 = bloodSpikes(1, 5)
        } else {
            spike1 = spikes(1, 5)
        }
        board[1][5].gameObject = spike1
        spikes[0] = spike1

        var spike2 = gameObject(1, 4)
        if (board[1][4].isBloody) {
            spike2 = bloodSpikes(1, 4)
        } else {
            spike2 = spikes(1, 4)
        }
        board[1][4].gameObject = spike2
        spikes[1] = spike2

        board[5][3].gameObject = tintedRock(5, 3)
        board[4][3].gameObject = tintedRock(4, 3)
        board[3][3].gameObject = tintedRock(3, 3)
        board[3][4].gameObject = tintedRock(3, 4)
        board[3][5].gameObject = tintedRock(3, 5)


        playerPosx = 5
        playerPosy = 0
        var avatar = avatar(5, 0)
        board[5][0].gameObject = avatar

        var stairs = stairs(0, 0)
        board[0][0].gameObject = stairs

        numEnemies = 2
        numSpikes = 2
    }

    fun drawRoom6() {
        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }

        var and1 = andrie(0, 4)
        board[0][4].gameObject = and1
        enemies[0] = and1;


        playerPosx = 5
        playerPosy = 2
        var avatar = avatar(5, 2)
        board[5][2].gameObject = avatar

        var lock = lock(0, 5)
        board[0][5].gameObject = lock

        numEnemies = 1
        numSpikes = 0
    }

    fun drawRoom7() {
        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }

        var win1 = winston(2, 2)
        board[2][2].gameObject = win1
        enemies[0] = win1;


        playerPosx = 5
        playerPosy = 5
        var avatar = avatar(5, 5)
        board[5][5].gameObject = avatar

        var stairs = stairs(0, 0)
        board[0][0].gameObject = stairs

        board[1][1].gameObject = rock(1, 1)
        board[1][2].gameObject = rock(1, 2)
        board[1][3].gameObject = rock(1, 3)
        board[2][1].gameObject = rock(2, 1)
        board[2][3].gameObject = rock(2, 3)
        board[3][1].gameObject = rock(3, 1)
        board[3][2].gameObject = rock(3, 2)
        board[3][3].gameObject = rock(3, 3)

        numEnemies = 1
        numSpikes = 0
    }

    fun drawRoom8() {
        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }
        var win1 = winston(4, 0)
        board[4][0].gameObject = win1
        enemies[0] = win1;


        var win2 = winston(2, 0)
        board[2][0].gameObject = win2
        enemies[1] = win2;

        var win3 = winston(0, 0)
        board[0][0].gameObject = win3
        enemies[2] = win3;


        playerPosx = 5
        playerPosy = 5
        var avatar = avatar(5, 5)
        board[5][5].gameObject = avatar

        var stairs = stairs(0, 5)
        board[0][5].gameObject = stairs

        for (i in 0..5) {
            board[i][2].gameObject = rock(i, 2)
            board[i][3].gameObject = rock(i, 3)
        }



        numEnemies = 3
        numSpikes = 0
    }

    fun drawRoom9() {
        for (i in 0..5) {
            for (j in 0..5) {
                if (!board[i][j].isBloody || newRoom == true) {
                    var toCreate = space(i, j)
                    toCreate.gameObject = floor(i, j)
                    board[i][j] = toCreate
                } else {
                    var toCreate = space(i, j)
                    toCreate.gameObject = bloodFloor(i, j)
                    board[i][j] = toCreate
                    board[i][j].isBloody = true
                }

            }
        }
        var win1 = winstonFire(5, 0)
        board[5][0].gameObject = win1
        enemies[0] = win1;


        var win2 = winston(4, 5)
        board[4][5].gameObject = win2
        enemies[1] = win2;

        var win3 = winstonCooldown(3, 0)
        board[3][0].gameObject = win3
        enemies[2] = win3;

        var win4 = winstonFire(2, 5)
        board[2][5].gameObject = win4
        enemies[3] = win4;


        var win5 = winston(1, 0)
        board[1][0].gameObject = win5
        enemies[4] = win5;

        var win6 = winstonCooldown(0, 5)
        board[0][5].gameObject = win6
        enemies[5] = win6;


        playerPosx = 5
        playerPosy = 2
        var avatar = avatar(5, 2)
        board[5][2].gameObject = avatar

        var stairs1 = stairs(0, 2)
        board[0][2].gameObject = stairs1
        var stairs2 = stairs(0, 3)
        board[0][3].gameObject = stairs2

        for (i in 0..5) {
            board[i][1].gameObject = rock(i, 2)
            board[i][4].gameObject = rock(i, 3)
        }



        numEnemies = 6
        numSpikes = 0
    }

    fun drawRoom10() {
        for (i in 0..5) {
            for (j in 0..5) {
                var toCreate = space(i, j)
                toCreate.gameObject = floor(i, j)
                board[i][j] = toCreate

            }
        }

        var peter1 = peter1(1, 3)
        board[1][3].gameObject = peter1
        enemies[0] = peter1;


        playerPosx = 5
        playerPosy = 2
        var avatar = avatar(5, 2)
        board[5][2].gameObject = avatar

        peterCycle = 1
        numEnemies = 1
        numSpikes = 0
    }

    fun drawRoom11() {
        for (i in 0..5) {
            for (j in 0..5) {
                var toCreate = space(i, j)
                toCreate.gameObject = bloodFloor(i, j)
                board[i][j] = toCreate
                board[i][j].isBloody = true
            }
        }
        var peter2 = peter2(0, 2)
        board[0][2].gameObject = peter2
        enemies[0] = peter2;




        playerPosx = 5
        playerPosy = 5
        var avatar = avatar(5, 5)
        board[5][5].gameObject = avatar




        numEnemies = 1
        numSpikes = 0
    }

    fun drawRoom12() {
        for (i in 0..5) {
            for (j in 0..5) {
                val toCreate = space(i, j)
                toCreate.gameObject = bloodFloor(i, j)
                board[i][j] = toCreate
                board[i][j].isBloody = true
            }
        }
        val peterTL = peter3TL(0, 2)
        board[0][2].gameObject = peterTL
        enemies[0] = peterTL;
        val peterTR = peter3TR(0, 3)
        board[0][3].gameObject = peterTR
        enemies[1] = peterTR;

        val peterBL = peter3BL(1, 2)
        board[1][2].gameObject = peterBL
        enemies[2] = peterBL;

        val peterBR = peter3BR(1, 3)
        board[1][3].gameObject = peterBR
        enemies[3] = peterBR;





        playerPosx = 5
        playerPosy = 3
        var avatar = avatar(5, 3)
        board[5][3].gameObject = avatar




        numEnemies = 4
        numSpikes = 0
    }

    fun drawRoom13(){

        for (i in 0..5) {
            for (j in 0..5) {
                val toCreate = space(i, j)
                toCreate.gameObject = bloodFloor(i, j)
                board[i][j] = toCreate
                board[i][j].isBloody = true
            }
        }

        board[0][2].gameObject = fire(0,2)
        board[0][3].gameObject = fire(0,2)
        board[1][2].gameObject = fire(1,2)
        board[1][3].gameObject = fire(1,3)

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(300)
            board[1][2].gameObject = bloodFloor(1,2)
            board[1][3].gameObject = bloodFloor(1,3)

            delay(300)
            board[0][2].gameObject = bloodFloor(0,2)
            board[0][3].gameObject = bloodFloor(0,2)
            delay(300)
            currRoom = 1
            drawRoom()

        }

    }


}



