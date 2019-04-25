package fr.esgi.e2e;

import fr.esgi.web.ManagedUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountResourceTest {

    private static final String PSEUDO = "Ben.lockwood";
    private static final String PASSWORD = "Alberta10!";
    private static final String EMAIL = "ben.lockwood@gmail.com";
    private static final long ROLE_ID = 1L;
    private static final String FIRST_NAME = "Ben";
    private static final String LAST_NAME = "Lockwood";
    private static final int YEAR = 1977;
    private static final int MONTH = 12;
    private static final int DAY_OF_MONTH = 6;

    private ManagedUser managedUser;

    private static final String IMAGES_FOLDER = "./images";
    private static final String TOTO_PNG = "toto.png";

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
    public void shouldRegisterWhenIsOk() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(managedUser)
                .when()
                .post("/api/register")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void shouldRegisterWhenIsKo() {
        managedUser.setPassword("ti");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(managedUser)
                .when()
                .post("/api/register")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void shouldRegisterWithFileWhenIsOk() throws IOException {
        final File file = createFile();
        int userId = 1;

        given()
                .multiPart("file",  file, "application/octet-stream")
                .post("/api/register/file/" + userId)
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    private File createFile() throws IOException {
        final File file = new File(TOTO_PNG);
        file.createNewFile();
        return file;
    }

    @Test
    public void shouldCheckIsAuthenticateWhenIsOk() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .get("/api/authenticate")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }


    @Before
    @After
    public void deleteFolder() throws IOException {
        if (Files.isDirectory(Paths.get(IMAGES_FOLDER))) {
            deleteFolderAndContents();
        }
    }

    public void deleteFolderAndContents() throws IOException {
        List<String> fileNames = Arrays.asList(IMAGES_FOLDER, TOTO_PNG);

        for (String fileName: fileNames) {
            FileUtils.forceDelete(new File(fileName));
        }
    }

}
