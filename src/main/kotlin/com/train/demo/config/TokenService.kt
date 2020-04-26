package com.train.demo.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function

@Component
class TokenService @Autowired constructor() {
    companion object {
        private const val secret = "asdsadasdas"
    }

    @Value("\${jwt.validity}")
    private val TOKEN_VALIDITY_TIME_IN_MILLISECONDS: Long = 0;

    fun generateToken(claims: HashMap<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + TOKEN_VALIDITY_TIME_IN_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).body
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    fun getSubjectFromToken(token: String): String {
        return getClaimFromToken(token, Function { obj: Claims -> obj.subject })
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Function { obj: Claims -> obj.expiration })
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            println(e)
            return false
        }
    }
}