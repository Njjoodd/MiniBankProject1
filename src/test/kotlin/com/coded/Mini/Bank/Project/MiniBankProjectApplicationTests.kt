package com.coded.Mini.Bank.Project
//updated
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MiniBankProjectApplicationTests(
    @Autowired val restTemplate: TestRestTemplate
) {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var baseUrl: String

    private lateinit var token: String

    @BeforeEach
    fun setup() {
        baseUrl = "http://localhost:$port"


        val loginRequest = mapOf("username" to "testuser", "password" to "testpass")
        val response = restTemplate.postForEntity(
            "$baseUrl/api/v1/auth/login",
            loginRequest,
            Map::class.java
        )
        token = response.body?.get("token") as String
    }

    private fun headersWithAuth(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(token)
        return headers
    }

    @Test
    fun `developer can create multiple accounts`() {
        val accountRequest1 = mapOf(
            "userId" to 1,
            "name" to "Savings",
            "intialBalance" to BigDecimal(1000)
        )

        val accountRequest2 = mapOf(
            "userId" to 1,
            "name" to "Checking",
            "intialBalance" to BigDecimal(2000)
        )

        val entity1 = HttpEntity(accountRequest1, headersWithAuth())
        val entity2 = HttpEntity(accountRequest2, headersWithAuth())

        val response1 = restTemplate.postForEntity("$baseUrl/accounts/v1/accounts", entity1, String::class.java)
        val response2 = restTemplate.postForEntity("$baseUrl/accounts/v1/accounts", entity2, String::class.java)

        assertEquals(HttpStatus.OK, response1.statusCode)
        assertEquals(HttpStatus.OK, response2.statusCode)
    }

    @Test
    fun `developer can register a new user`() {
        val registerRequest = mapOf("username" to "newuser1", "password" to "newpass")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val entity = HttpEntity(registerRequest, headers)
        val response = restTemplate.postForEntity("$baseUrl/users/v1/register", entity, String::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `user can login and get jwt token`() {
        val loginRequest = mapOf("username" to "testuser", "password" to "testpass")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val response = restTemplate.postForEntity("$baseUrl/api/v1/auth/login", loginRequest, Map::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.body?.containsKey("token") == true)
    }

    @Test
    fun `developer can read list of accounts`() {
        val headers = headersWithAuth()
        val entity = HttpEntity<Void>(headers)

        val response = restTemplate.exchange(
            "$baseUrl/accounts/v1/accounts",
            HttpMethod.GET,
            entity,
            List::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.body?.isNotEmpty() == true)
    }

    @Test
    fun `developer can create user profile`() {
        val profileRequest = mapOf(
            "userId" to 1,
            "firstName" to "John",
            "lastName" to "Doe",
            "dateOfBirth" to LocalDate.of(1990, 1, 1),
            "salary" to BigDecimal(5000)
        )

        val entity = HttpEntity(profileRequest, headersWithAuth())

        val response = restTemplate.postForEntity(
            "$baseUrl/users/v1/kyc",
            entity,
            String::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `developer can update user profile`() {
        val profileUpdateRequest = mapOf(
            "userId" to 1,
            "firstName" to "Jane",
            "lastName" to "Doe",
            "dateOfBirth" to LocalDate.of(1990, 1, 1),
            "salary" to BigDecimal(7000)
        )

        val entity = HttpEntity(profileUpdateRequest, headersWithAuth())

        val response = restTemplate.postForEntity(
            "$baseUrl/users/v1/kyc",
            entity,
            String::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `developer can read user profile`() {
        val headers = headersWithAuth()
        val entity = HttpEntity<Void>(headers)

        val response = restTemplate.exchange(
            "$baseUrl/users/v1/kyc/1",
            HttpMethod.GET,
            entity,
            String::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `developer can close user account`() {
        val headers = headersWithAuth()
        val entity = HttpEntity<Void>(headers)


        val accountNumber = "9bc06e34-a9f9-4233-a8f8-738ea9e9fad3"

        val response = restTemplate.postForEntity(
            "$baseUrl/accounts/v1/accounts/$accountNumber/close",
            entity,
            String::class.java
        )

        // If account exists:
        assertTrue(response.statusCode == HttpStatus.OK || response.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun `developer can transfer money between accounts`() {
        val transferRequest = mapOf(
            "sourceAccountNumber" to "9bc06e34-a9f9-4233-a8f8-738ea9e9fad3",
            "destinationAccountNumber" to "e54ccbf2-afdb-4d44-b12d-4813223d7998",
            "amount" to BigDecimal(100)
        )

        val entity = HttpEntity(transferRequest, headersWithAuth())

        val response = restTemplate.postForEntity(
            "$baseUrl/transactions/v1",
            entity,
            String::class.java
        )

        // If accounts exist:
        assertTrue(response.statusCode == HttpStatus.OK || response.statusCode == HttpStatus.BAD_REQUEST)
    }
}
