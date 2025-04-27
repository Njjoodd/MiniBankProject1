package com.coded.Mini.Bank.Project.Part2

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
@Service
class JwtService{
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date()).
            setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //for 10 hours
            .signWith(secretKey)
            .compact()
    }
}