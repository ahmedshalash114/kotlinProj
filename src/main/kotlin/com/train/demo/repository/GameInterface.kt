package com.train.demo.repository

import com.train.demo.data.Game

open interface GameInterface {

  open  fun addNewGame(game : Game) : Long

   open fun updateGame(int: Int)

  open  fun deleteGame()
}