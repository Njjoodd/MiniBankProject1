package com.coded.Mini.Bank.Project

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals


@SpringBootTest
class MiniBankProjectApplicationTests {

 @Autowired
 lateinit var
	@Test
	fun testUserRegistration() {
		val requestBody = mapOf("username" to "testuser", "password" to "password123")
		val result = restemplate.postForEntity ("/users/v1/register", requestBody, String:: class.java)
		assertEquals(requestBody?.contains("Registration successful")?: false)
	}


//	fun contextLoads() {
//	}

}
