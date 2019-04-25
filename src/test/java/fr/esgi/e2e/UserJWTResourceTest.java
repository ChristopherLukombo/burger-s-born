package fr.esgi.e2e;

import fr.esgi.web.Login;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static io.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserJWTResourceTest {

    private static final String PSEUDO = "Ben.lockwood";
    private static final String PASSWORD = "Alberta10!";

    @Autowired
    private Environment env;

    @LocalServerPort
    private int port;

    @Before
    public void setup() throws UnknownHostException {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        RestAssured.baseURI = protocol + "://" + InetAddress.getLocalHost().getHostAddress();
        RestAssured.port = port;
    }

    @Test
    public void shouldAuthenticateWhenIsKo403() {
        Login login = new Login();
        login.setUsername(PSEUDO);
        login.setPassword(PASSWORD);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/api/authenticate")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void shouldAuthenticateWhenIsKo422() {
        Login login = new Login();
        login.setUsername(null);
        login.setPassword(PASSWORD);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/api/authenticate")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

}
