package github.api.base;

import github.api.utils.EnvironmentDetails;
import github.api.utils.TestDataUtils;

import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite
    public void beforeSuite() {
        EnvironmentDetails.loadProperties();
        TestDataUtils.loadProperties();
    }
}
