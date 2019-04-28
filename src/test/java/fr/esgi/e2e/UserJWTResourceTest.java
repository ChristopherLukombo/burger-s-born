package fr.esgi.e2e;

import fr.esgi.web.Login;
import fr.esgi.web.ManagedUser;
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
import java.time.LocalDate;

import static io.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserJWTResourceTest {

    private static final String PSEUDO = "Denis.zili";
    private static final String PASSWORD = "Alberta10!";
    private static final String EMAIL = "zili.denis@gmail.com";
    private static final long ROLE_ID = 1L;
    private static final String FIRST_NAME = "Denis";
    private static final String LAST_NAME = "Zili";
    private static final int YEAR = 1977;
    private static final int MONTH = 12;
    private static final int DAY_OF_MONTH = 6;

    private ManagedUser managedUser;

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

    @Before
    public void createManagedUser() {
        managedUser = getManagedUser();
    }

    public ManagedUser getManagedUser() {
        final ManagedUser managedUser = new ManagedUser();

        managedUser.setPseudo(PSEUDO);
        managedUser.setPassword(PASSWORD);
        managedUser.setFirstName(FIRST_NAME);
        managedUser.setLastName(LAST_NAME);
        managedUser.setEmail(EMAIL);
        managedUser.setRoleId(ROLE_ID);
        managedUser.setActivated(true);
        managedUser.setCreateDate(LocalDate.now());
        managedUser.setBirthDay(LocalDate.of(YEAR, MONTH, DAY_OF_MONTH));
        return managedUser;
    }

    @Test
    public void shouldAuthenticateWhenIsOk() {
        Login login = new Login();
        login.setUsername(PSEUDO);
        login.setPassword(PASSWORD);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(managedUser)
                .when()
                .post("/api/register")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(login)
                .when()
                .post("/api/authenticate")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldAuthenticateWhenIsKo403() {
        Login login = new Login();
        login.setUsername(PSEUDO + "o");
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
