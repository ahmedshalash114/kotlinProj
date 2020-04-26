package com.train.demo

import com.sun.deploy.net.HttpResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoApplicationTests {

    @Test
   suspend fun contextLoads() : Any {
        val client = HttpClient()

        val response = client.get<HttpResponse>("https://en.wikipedia.org/wiki/Main_Page")
        client.close();

        assert(response != null)

        return response;
    }

}
