package mobile.hw3;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static mobile.hw3.Driver.getDriver;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SimpleNativeTests {

    @BeforeClass
    public void setNative() throws Exception {
        getDriver(PropertyFile.NATIVE_PROPERTY);
        System.out.println("Driver is prepared");
    }

    @AfterClass
    public void tearDown() throws Exception {
        getDriver().close();
        System.out.println("Driver is closed");
    }

    @Test(description = "Click on button 'Add contact', check elements, check virtual keyboard")
    public void simplestTest() throws Exception {
        String app_package_name = "com.example.android.contactmanager:id/";

        getDriver().findElement(By.id(app_package_name + "addContactButton")).click();

        //Check Target Account, Contact Name, Contact phone appears
        assertEquals(getDriver().findElementByAccessibilityId("Target Account").getText(), "Target Account");
        assertTrue(getDriver().findElement(By.id(app_package_name + "accountSpinner")).isDisplayed());
        assertEquals(getDriver().findElementByAccessibilityId("Contact Name").getText(), "Contact Name");
        assertTrue(getDriver().findElement(By.id(app_package_name + "contactNameEditText")).isDisplayed());
        assertEquals(getDriver().findElementByAccessibilityId("Contact Phone").getText(), "Contact Phone");
        assertTrue(getDriver().findElement(By.id(app_package_name + "contactPhoneEditText")).isDisplayed());
        assertEquals(getDriver().findElementByAccessibilityId("Contact Email").getText(), "Contact Email");
        assertTrue(getDriver().findElement(By.id(app_package_name + "contactEmailEditText")).isDisplayed());

        //Check virtual keyboard appears
        Process process = Runtime.getRuntime().exec("adb shell dumpsys input_method | grep mInputShown");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String output = reader.readLine();
            assertTrue(Boolean.parseBoolean(output.substring(output.lastIndexOf("=") + 1)));
        }

        System.out.println("Simplest Native test done");
    }

}

