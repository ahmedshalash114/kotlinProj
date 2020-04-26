package com.train.demo.repository

import com.train.demo.data.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.Statement


@Repository
class GameRepoImpl (@Autowired val jdbcTemplate : JdbcTemplate) : GameInterface {

    override fun addNewGame(game: Game): Long {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(
                    "INSERT INTO game(game_name_ar, game_name_en , game_desc_ar , game_desc_en ) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            )
            ps.setString(1, game.gameNameAr)
            ps.setString(2, game.gameNameEng)
            ps.setString(3, game.gameDescAr)
            ps.setString(4 , game.gameDescEng)
            ps
        }, keyHolder)

        return keyHolder.key!!.toLong()
    }

    override fun updateGame(int: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}