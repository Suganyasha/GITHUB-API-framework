package github.api.base;

import java.util.HashMap;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import github.api.utils.EnvironmentDetails;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import requestPOJO.CreateRequest;
import requestPOJO.DeleteRequest;
import requestPOJO.UpdateRequest;
@Slf4j
public class APIHelper {
	RequestSpecification reqSpec;
	String token = EnvironmentDetails.getProperty("token");

	public APIHelper() {
		System.out.println("APIHelper: BaseURL: "
				+ EnvironmentDetails.getProperty("baseURL"));

		RestAssured.baseURI = EnvironmentDetails.getProperty("baseURL");
		reqSpec = RestAssured.given();
	}
	public HashMap<String, String> getHeaders() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer " +token);
		return headers;
	}

	public Response getSingleRepo() {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		String ownerVal = EnvironmentDetails.getProperty("owner");
		Response response = null;
		try {
			System.out.println(
					"Formattd Value:" + "/repos/" + ownerVal + "/Hello-World");
			response = reqSpec.get("/repos/" + ownerVal + "/Hello-World");
			response.then().log().all();
		} catch (Exception e) {
			Assert.fail("Get data is failing due to :: " + e.getMessage());
		}
		return response;
	}

	public Response NonSingleRepo() {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		String owner = EnvironmentDetails.getProperty("owner");
		Response response = null;
		try {
			response = reqSpec.get("/repos/" + owner + "/Hello");
			response.then().log().all();
		} catch (Exception e) {
			Assert.fail("Get data is failing due to :: " + e.getMessage());
		}
		return response;
	}
	public Response getAllRepos() {
		reqSpec = RestAssured.given();
		reqSpec.headers(getHeaders());
		//String ownerVal = EnvironmentDetails.getProperty("owner");
		Response response = null;
		try {
			response = reqSpec.get("/user/repos");
			response.then().log().all();
		} catch (Exception e) {
			Assert.fail("Get data is failing due to :: " + e.getMessage());
		}
		return response;
	}	
	public Response CreateRepo(CreateRequest createRequest) {
		reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders());
        Response response = null;
        try {
            log.info("Adding below data :: " + new ObjectMapper().writeValueAsString(createRequest));
            reqSpec.body(new ObjectMapper().writeValueAsString(createRequest)); //Serializing addData Request POJO classes to byte stream
            response = reqSpec.get("/user/repos");
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Add data functionality is failing due to :: " + e.getMessage());
        }
        return response;     
    }
	public Response UpdateRepo(String RepoName,UpdateRequest updateRequest) {
		reqSpec = RestAssured.given();
        reqSpec.headers(getHeaders());
        String owner = EnvironmentDetails.getProperty("owner");
        Response response = null;
        try {
            log.info("Adding below data :: " + new ObjectMapper().writeValueAsString(updateRequest));
            reqSpec.body(new ObjectMapper().writeValueAsString(updateRequest)); //Serializing addData Request POJO classes to byte stream
            response = reqSpec.put("/repos/" + owner + "/" + RepoName);
            response.then().log().all();
        } catch (Exception e) {
            Assert.fail("Add data functionality is failing due to :: " + e.getMessage());
        }
        return response;     
    }
	 public Response DeleteRepo(String repoName) {
	        reqSpec = RestAssured.given();
	        reqSpec.headers(getHeaders());
	        String owner = EnvironmentDetails.getProperty("owner");
	        Response response = null;
	        try {
	 
	            response = reqSpec.delete("/repos/" + owner + "/"+repoName);
	            response.then().log().all();
	        } catch (Exception e) {
	            Assert.fail("Delete data functionality is failing due to :: " + e.getMessage());
	        }
	        return response;
	    }
}
