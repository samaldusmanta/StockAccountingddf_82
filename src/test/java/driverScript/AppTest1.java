package driverScript;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
 
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfun.FunctionLibrary;
import utilities.ExcelFileUtil;

public class AppTest1
{
	 ExtentReports report;
	 ExtentTest test;
	public WebDriver driver;
  @Test
  public void f() throws Exception
  {  
	  ExcelFileUtil excel=new ExcelFileUtil();
		for(int i=1;i<=excel.rowCount("MasterTestCases");i++){		
			String ModuleStatus="";
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				String TCModule=excel.getData("MasterTestCases", i, 1);
				report=new ExtentReports(System.getProperty("user.dir")+"\\Reports\\"+TCModule+FunctionLibrary .curdate()+".html");
				test=report.startTest(TCModule);
				for(int j=1;j<=excel.rowCount(TCModule);j++)
				{	
					String Description=excel.getData(TCModule, j, 0);
					String Function_Name=excel.getData(TCModule, j, 1);
					String Locator_Type=excel.getData(TCModule, j, 2);
					String Locator_Value=excel.getData(TCModule, j, 3);
					String Test_Data=excel.getData(TCModule, j, 4);
					try{
						if(Function_Name.equalsIgnoreCase("startBrowser")){
						      driver=	FunctionLibrary.startBrowser();
						      test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("openApplication")){
							FunctionLibrary.OpenApplication(driver);
							 test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("waitForElement")){
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("typeAction")){
							FunctionLibrary.typeaction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("clickAction")){
							FunctionLibrary.clickaction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}else if(Function_Name.equalsIgnoreCase("closeBrowser")){
							FunctionLibrary.close(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("captureData")){
							FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("tableValidation")){
							FunctionLibrary.tablevalidation(driver, Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						excel.setData(TCModule, j, 5, "PASS");
						ModuleStatus="PASS";
						test.log(LogStatus.PASS, Description);
						
					}catch(Exception e)
					{
						System.out.println("the exception is ");
						e.printStackTrace();
						excel.setData(TCModule, j, 5, "FAIL");
						ModuleStatus="FAIL";
						File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\ScreenShot\\Screenshots\\"+Description+FunctionLibrary .curdate()+".png"));
						test.log(LogStatus.FAIL, Description);
						break;
					}		
				}// second for loop
				
				if(ModuleStatus.equalsIgnoreCase("PASS"))
				{
					excel.setData("MasterTestCases", i, 3, "PASS");
				}else
				{
					excel.setData("MasterTestCases", i, 3, "FAIL");
				}
				
				report.endTest(test);
             report.flush();
				
	    }//ist if 
			else
			       {
				excel.setData("MasterTestCases", i, 3, "Not Executed");
			       }
	}//1st for loop

}//@test 

	  
	  
}//class
