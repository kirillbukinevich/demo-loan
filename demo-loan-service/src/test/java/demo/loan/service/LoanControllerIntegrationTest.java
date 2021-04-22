package demo.loan.service;

import static com.tngtech.keycloakmock.api.ServerConfig.aServerConfig;
import static com.tngtech.keycloakmock.api.TokenConfig.aTokenConfig;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.tngtech.keycloakmock.junit5.KeycloakMockExtension;

import demo.loan.config.SecurityConfig;
import demo.loan.dto.LoanDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class LoanControllerIntegrationTest {

  @RegisterExtension
  static KeycloakMockExtension mock =
      new KeycloakMockExtension(aServerConfig().withRealm("company-services").build());

  @LocalServerPort private int port;

  @Value("${keycloak.resource}")
  private String keycloakResource;

  @BeforeEach
  void setup() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = port;
  }

  @MockBean LoanService loanService;

  @Test
  void whenCallinLoanApi_no_authentication_codeNotFound() {
    given().when().get(EndPoints.API_LOAN).then().statusCode(404);
  }

  @Test
  void whenCallinLoanApi_withUserRole_codeForbidden() {
    given()
        .auth()
        .preemptive()
        .oauth2(
            mock.getAccessToken(aTokenConfig().withResourceRole(keycloakResource, "USER").build()))
        .when()
        .get(EndPoints.API_LOAN)
        .then()
        .statusCode(403);
  }

  @Test
  public void whenCallingLoansEndpoint_thenReturnAllLoans() {
    List<LoanDTO> loanList = new ArrayList<LoanDTO>();
    loanList.add(new LoanDTO(1, "Test", BigDecimal.ONE, 3.2, "12.12.2018", "12.12.2018", 0, 0));
    loanList.add(new LoanDTO(2, "Test2", BigDecimal.ONE, 5.2, "12.12.2018", "12.12.2018", 0, 0));
    when(loanService.getAllLoans()).thenReturn(loanList);

    given()
        .auth()
        .preemptive()
        .oauth2(
            mock.getAccessToken(
                aTokenConfig().withResourceRole(keycloakResource, SecurityConfig.ADMIN).build()))
        .when()
        .get(EndPoints.API_LOAN)
        .then()
        .statusCode(HttpStatus.OK.value())
        .assertThat()
        .body("size()", is(2));

    LoanDTO[] loans =
        given()
            .auth()
            .preemptive()
            .oauth2(
                mock.getAccessToken(
                    aTokenConfig()
                        .withResourceRole(keycloakResource, SecurityConfig.ADMIN)
                        .build()))
            .when()
            .get(EndPoints.API_LOAN)
            .then()
            .statusCode(200)
            .extract()
            .as(LoanDTO[].class);
    assertThat(loans.length).isEqualTo(2);
  }

  @Test
  public void givenLoanId_whenMakingGetRequestToLoanEndpoint_thenReturnLoan() {

    LoanDTO testLoan = new LoanDTO(1, "Test", BigDecimal.ONE, 3.2, "", "", 0, 0);

    when(loanService.getLoanById(1L)).thenReturn(testLoan);

    given()
        .auth()
        .preemptive()
        .oauth2(
            mock.getAccessToken(
                aTokenConfig().withResourceRole(keycloakResource, SecurityConfig.ADMIN).build()))
        .when()
        .pathParams("id", testLoan.getId())
        .get(EndPoints.API_LOAN_BY_ID)
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("id", equalTo((int) testLoan.getId()))
        .body("name", equalTo(testLoan.getName()))
        .body("amount", notNullValue())
        .body("interest", notNullValue())
        .body("startDate", notNullValue())
        .body("endDate", notNullValue())
        .body("days", notNullValue())
        .body("daysLeft", notNullValue());

    LoanDTO result =
        given()
            .auth()
            .preemptive()
            .oauth2(
                mock.getAccessToken(
                    aTokenConfig()
                        .withResourceRole(keycloakResource, SecurityConfig.ADMIN)
                        .build()))
            .when()
            .pathParams("id", testLoan.getId())
            .get(EndPoints.API_LOAN_BY_ID)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(LoanDTO.class);
    assertThat(result).usingRecursiveComparison().isEqualTo(testLoan);

    String responseString =
        given()
            .auth()
            .preemptive()
            .oauth2(
                mock.getAccessToken(
                    aTokenConfig()
                        .withResourceRole(keycloakResource, SecurityConfig.ADMIN)
                        .build()))
            .when()
            .pathParams("id", testLoan.getId())
            .get(EndPoints.API_LOAN_BY_ID)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .asString();
    assertThat(responseString).isNotEmpty();
  }

  @Test
  public void givenLoan_whenMakingPostRequestToLoanEndpoint_thenCorrect() {

    Map<String, String> request = new HashMap<>();
    request.put("id", "0");
    request.put("name", "Test");
    request.put("amount", "32.5");
    request.put("interest", "2.3");
    request.put("startDate", "2021-04-04T21:00:00.000Z");
    request.put("endDate", "2021-04-26T21:00:00.000Z");

    String name =
        given()
            .auth()
            .preemptive()
            .oauth2(
                mock.getAccessToken(
                    aTokenConfig()
                        .withResourceRole(keycloakResource, SecurityConfig.ADMIN)
                        .build()))
            .when()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post(EndPoints.API_LOAN)
            .then()
            .assertThat()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .path("name");
    assertThat(name).isEqualTo("Test");
  }

  @Test
  public void givenLoan_whenMakingPutRequestToLoanEndpoint_thenCorrect() {

    Map<String, String> request = new HashMap<>();
    request.put("id", "0");
    request.put("name", "Test");
    request.put("amount", "32.5");
    request.put("interest", "2.3");
    request.put("startDate", "2021-04-04T21:00:00.000Z");
    request.put("endDate", "2021-04-26T21:00:00.000Z");

    String name =
        given()
            .auth()
            .preemptive()
            .oauth2(
                mock.getAccessToken(
                    aTokenConfig()
                        .withResourceRole(keycloakResource, SecurityConfig.ADMIN)
                        .build()))
            .when()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .put(EndPoints.API_LOAN)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .path("name");
    assertThat(name).isEqualTo("Test");
  }

  @Test
  public void givenLoanId_whenMakingDeleteRequestToLoanEndpoint_thenCorrect() {

    Long loanId = 1L;

    given()
        .auth()
        .preemptive()
        .oauth2(
            mock.getAccessToken(
                aTokenConfig().withResourceRole(keycloakResource, SecurityConfig.ADMIN).build()))
        .when()
        .pathParams("id", loanId)
        .delete(EndPoints.API_LOAN_BY_ID)
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value());
  }
}
