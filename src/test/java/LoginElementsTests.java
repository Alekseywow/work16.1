import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.LoginBodyLombokModel;
import pojo.LoginResponseLombokModel;
import pojo.MissingPasswordResponseLombokModel;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class LoginElementsTests {

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://reqres.in"
    }

    @Test
    void successfullLoginTests() {
//        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        LoginBodyModel authData= new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }

    @Test
    void successfullLoginLombokTests() {

        LoginBodyLombokModel authData= new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }

    @Test
    void successfullLoginAllureTests() {

        LoginBodyLombokModel authData= new LoginBodyLombokModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .body(authData)
                .contentType(JSON)


                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());

    }

    @Test
    void successfullLoginStepTests() {

        LoginBodyLombokModel authData= new LoginBodyLombokModel();

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");
        LoginResponseLombokModel response = step("MAke request", ()->
                given()
                    .filter(withCustomTemplates())
                    .log().uri()
                    .log().body()
                    .log().headers()
                    .body(authData)
                    .contentType(JSON)


                    .when()
                    .post("https://reqres.in/api/login")

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(LoginResponseLombokModel.class));

        step("Check response",()->
            assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));

    }

    @Test
    void successfullLoginSpecTests() {

        LoginBodyLombokModel authData= new LoginBodyLombokModel();

        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");
        LoginResponseLombokModel response = step("MAke request", ()->
                given(loginRequestSpec)
                        .body(authData)

                        .when()
                        .post()

                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(LoginResponseLombokModel.class));

        step("Check response",()->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));

    }

    @Test
    void missingPasswordSpecTests() {

        LoginBodyLombokModel authData= new LoginBodyLombokModel();

        authData.setEmail("eve.holt@reqres.in");

        MissingPasswordResponseLombokModel response = step("MAke request", ()->
                given(loginRequestSpec)
                        .body(authData)

                        .when()
                        .post()

                        .then()
                        .spec(missingPasswordResponseSpec)
                        .extract().as(MissingPasswordResponseLombokModel.class));

        step("Check response",()->
                assertEquals("Missing API key", response.getError()));

    }

}
