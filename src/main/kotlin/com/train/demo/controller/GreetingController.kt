package com.train.demo.controller

import com.sun.deploy.net.HttpResponse
import com.train.demo.config.TokenService
import com.train.demo.data.Game
import com.train.demo.data.Greeting
import com.train.demo.repository.GameInterface
import org.springframework.beans.factory.annotation.Autowired
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import org.springframework.web.bind.annotation.*
import java.net.HttpURLConnection
import java.net.URL

import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController @Autowired constructor(@Autowired val gameInterface: GameInterface  ,
                                                @Autowired val tokenService: TokenService){



    var test  = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            Greeting(test.incrementAndGet(), "Hello, $name")

    @GetMapping("/test")
    fun test():Any{

        val weekends = arrayOf("Mon" , "Tus" , "Wed" , "Thurs")
        val dayOfWeekEnd = weekends.filter {  it == "Mon" }
       //     return cityRepository.findAll()
            val shalshMap  = HashMap<String, Any>();
        shalshMap.put("shalash" , 1);
       return tokenService.generateToken( shalshMap, "test")
    }


    @PostMapping("/createGame")
    fun addNewGame(@RequestBody game: Game) : Long{
        return  gameInterface.addNewGame(game);
    }


  @GetMapping("/login")
   suspend fun login() : String{
      val client = HttpClient()
      return client.get<String>("https://en.wikipedia.org/wiki/Main_Page");

  }




}