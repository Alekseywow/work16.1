package blocked;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqTests {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://reqres.in";

        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Проверить успешное создание пользователя")
    void successfulCreateNewUserTest() {
        String loginData = "{\"name\": \"Aleksey\", \"job\": \"Aqa\"}";
        given()
                .body(loginData)
                .contentType(JSON)
                .log().uri()

                .when()

                .post("/users")

                .then()

                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Aleksey"))
                .body("job", is("Aqa"));
    }

    @Test
    @DisplayName("Проверить успешное получение информацию о пользователе")
    void successfulRetrievalUserInformTest() {
        given()
                .contentType(JSON)
                .log().uri()

                .when()

                .get("/users/2")

                .then()

                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    @DisplayName("Проверить обновление данных о пользователе")
    void successUpdateInfoUserTest() {
        String updateUser = "{\n\"name\": \"Patric\",\n\"job\": \"Support\"\n}";
        given()
                .body(updateUser)
                .contentType(JSON)
                .log().uri()

                .when()

                .put("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Patric"))
                .body("job", is("Support"));
    }
    @Test
    @DisplayName("Проверить, что пользователь успешно удален")
    void successDeleteUserTest() {
        given()
                .contentType(JSON)
                .log().uri()

                .when()
                .delete("/users/2")

                .then()

                .log().status()
                .statusCode(204);

    }

    @Test
    @DisplayName("Проверить, что для незарегистрированного пользователя приходит статус код 404")
    void unsuccessfulUserNotFoundTest() {
        given()
                .contentType(JSON)
                .log().uri()

                .when()

                .get("/users/23")

                .then()

                .log().status()
                .statusCode(404);
    }


}