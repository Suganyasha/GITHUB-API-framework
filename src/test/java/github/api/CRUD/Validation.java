package github.api.CRUD;

import java.util.List;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import github.api.base.APIHelper;
import github.api.base.BaseTest;
import github.api.utils.EnvironmentDetails;
import github.api.utils.ExtentReportsUtility;
import github.api.utils.JsonSchemaValidate;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import requestPOJO.CreateRequest;
import requestPOJO.UpdateRequest;
import responsePOJO.CreateResponse;
import responsePOJO.DeleteResponse;
import responsePOJO.GetResponse;
import responsePOJO.NonSingleRepo;
import responsePOJO.UpdateResponse;

public class Validation extends BaseTest {
	ExtentReportsUtility report = ExtentReportsUtility.getInstance();
	APIHelper apiHelper;
	private Faker faker;
	String name, description, id, node_id, fullname, homepage, newName;
	boolean Private;

	@BeforeClass
	public void beforeClass() {
		faker = new Faker();
		apiHelper = new APIHelper();
	}
	@Test
	public void validateSingleRepo() {
		Response data = apiHelper.getSingleRepo();
		GetResponse getResponse = data.getBody().as(new TypeRef<GetResponse>() {
		});

		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_OK,
				"Response code is not matching for get data.");
		// GetResponse getResponse = null;
		report.logTestInfo("successful satatus code 200");
		Assert.assertEquals(getResponse.getName(), "Hello-World",
				"Get data functionality is not working as expected, name is not matching");

		JsonSchemaValidate.validateSchemaInClassPath(data,
				"ExpectedJsonSchema/GetDataResponseSchema.json");
		report.logTestInfo(
				"Singlegetrepo is validated against expected schema successfully");
	}
	@Test
	public void validateNonSingleRepo() {
		Response data = apiHelper.NonSingleRepo();
		NonSingleRepo getResponse = data.getBody()
				.as(new TypeRef<NonSingleRepo>() {
				});
		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NOT_FOUND,
				"Response code is not matching for get data.");
		// report.logTestInfo("successful satatus code 404");
		Assert.assertEquals(getResponse.message, "Not Found",
				"Get data functionality is not working as expected, name is not matching");

	}
	private GetResponse returnthematchinggetdata(String name,
			List<GetResponse> getResponselist) {
		for (GetResponse respo : getResponselist) {
			if (respo.getName().equals(name)) {
				return respo;
			}
		}
		return null;
	}
	@Test
	public void validateGetALLRepos() {
		Response data = apiHelper.getAllRepos();
		List<GetResponse> getResponseList = data.getBody()
				.as(new TypeRef<List<GetResponse>>() {
				});

		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_OK,
				"Response code is not matching for get data.");
		GetResponse getResponse = returnthematchinggetdata(
				"SeleniumAutomationFrameworkPOM", getResponseList);
		// report.logTestInfo("successful satatus code 200");
		Assert.assertEquals(getResponse.name, "SeleniumAutomationFrameworkPOM",
				"Get data functionality is not working as expected, name is not matching");
	}
	@Test
	public void validateCreateRepo() {
		name = "Hello World";
		description = "This is your firstrepo!";
		homepage = "https: //github.com";
		Private = false;

		CreateRequest createreq = CreateRequest.builder().name(name)
				.description(description).homepage(homepage).Private(Private)
				.build();
		Response data = apiHelper.CreateRepo(createreq);
		CreateResponse response = data.getBody()
				.as(new TypeRef<CreateResponse>() {
				});
		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_CREATED,
				"Response code is not matching for create data.");
		// report.logTestInfo("successful satatus code 201");
		Assert.assertEquals(response.getName(), "Hello World",
				"Create data functionality is not working as expected, name is not matching");

	}
	public void validateCreateExistingRepo() {
		name = "HelloWorld50";
		description = "This is your firstrepo!";
		homepage = "https: //github.com";
		Private = false;

		CreateRequest createreq = CreateRequest.builder().name(name)
				.description(description).homepage(homepage).Private(Private)
				.build();
		Response data = apiHelper.CreateRepo(createreq);
		CreateResponse response = data.getBody()
				.as(new TypeRef<CreateResponse>() {
				});
		Assert.assertEquals(data.getStatusCode(),
				HttpStatus.SC_UNPROCESSABLE_ENTITY,
				"Repository creation failed");
		// report.logTestInfo("successful satatus code 422");
		Assert.assertEquals(response.getName(), "Hello World",
				"Create data functionality is not working as expected, name is not matching");

	}
	public void validateupdateRepo() {
		newName = "HelloWorld100";
		name = "HelloWorld50";
		description = "This is your firstrepo!";
		Private = false;

		UpdateRequest updatereq = UpdateRequest.builder().name(newName)
				.description(description).Private(Private).build();
		Response data = apiHelper.UpdateRepo(name, updatereq);

		UpdateResponse response = data.getBody()
				.as(new TypeRef<UpdateResponse>() {
				});
		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_OK,
				"Update failed");
		// report.logTestInfo("successful satatus code 422");
		Assert.assertEquals(response.getName(), newName,
				"Update data functionality is not working as expected, name is not matching");

	}
	public void validateDeleteRepo() {
		Response data = apiHelper.DeleteRepo(newName);
		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NO_CONTENT,
				"Response code is not matching for get data.");
		// report.logTestInfo("successful satatus code 204");

	}
	public void validateDeleteNonExistingRepo() {
		Response data = apiHelper.DeleteRepo(newName);
		DeleteResponse response = data.getBody()
				.as(new TypeRef<DeleteResponse>() {
				});
		Assert.assertEquals(data.getStatusCode(), HttpStatus.SC_NOT_FOUND,
				"Response code is not matching for get data.");
		Assert.assertEquals(response.getMessage(), "Not Found",
				"Delete data functionality is not working as expected, name is not matching");
		// report.logTestInfo("successful satatus code 404");
	}

}
