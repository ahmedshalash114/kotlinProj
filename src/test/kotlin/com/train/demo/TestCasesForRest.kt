package com.train.demo

import com.train.demo.data.Game
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(DemoApplication::class),
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestCasesForRest{

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate


/*
    @Test
    fun addGame(){

        val addTestGame = testRestTemplate.postForEntity("/createGame" , Game(null,"test" , "test2" , "test3" , "test4") , Long::class.java)

        assertNotNull(addTestGame)
        assertEquals(addTestGame?.statusCode, HttpStatus.OK)
        assertEquals(addTestGame?.body.toString(), "5")


    } */
    @Test
    suspend fun parallelRequests() = coroutineScope<Unit> {
        val client = HttpClient()

        // Start two requests asynchronously.
        val firstRequest = async { client.get<ByteArray>("https://en.wikipedia.org/wiki/Main_Page") }
        val secondRequest = async { client.get<ByteArray>("https://en.wikipedia.org/wiki/Main_Page") }

        // Get the request contents without blocking threads, but suspending the function until both
        // requests are done.
        val bytes1 = firstRequest.await() // Suspension point.
        val bytes2 = secondRequest.await() // Suspension point.

        println(firstRequest)

        client.close()
    }
}