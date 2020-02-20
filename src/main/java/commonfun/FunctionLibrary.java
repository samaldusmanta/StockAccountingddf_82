package commonfun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary {
	
		 static WebDriver  Driver;
	    public  static WebDriver startBrowser() throws Exception {
		
//			System.out.println(PropertyFileUtil.getValueForKey("browser"));
		if(PropertyFileUtil.getValueForKey("browser").equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
			Driver=new ChromeDriver();
		}  else if(PropertyFileUtil.getValueForKey("browser").equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\driver\\geckodriver.exe");
			Driver=new FirefoxDriver();

		}else{
			System.setProperty("webdriver.ie.driver","D:\\pintu\\StockAccountingHybrid\\driver\\IEDriverServer.exe");
			Driver=new InternetExplorerDriver();
		
		}
		return Driver;
	    }
	    
		public static void OpenApplication (WebDriver driver ) throws Exception{//,
			driver.get(PropertyFileUtil.getValueForKey("url"));
		}
		public static void waitForElement(WebDriver driver, String locatortype,String locatorvalue , String waittime )
		{
			WebDriverWait mywait=new WebDriverWait(driver, Integer.parseInt("10"));
			
			if(locatortype.equalsIgnoreCase("id"))
			{
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
			}
			else if(locatortype.equalsIgnoreCase("name"))
			{
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
			}
			else if(locatortype.equalsIgnoreCase("xpath"))
			{
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
			}
			
			else
			{
				System.out.println("unable to locate for waitforElement method with "+locatorvalue);
			}
		}
		public  static void typeaction( WebDriver driver, String locatortype,String locatorvalue ,String testdata)
		{
			
			if(locatortype.equalsIgnoreCase("id"))
			{
				 driver.findElement(By.id(locatorvalue)).clear();
				 driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
			}
			else if(locatortype.equalsIgnoreCase("name"))
			{
				 driver.findElement(By.name(locatorvalue)).clear();
				 driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
			}
			else if(locatortype.equalsIgnoreCase("xpath"))
			{
				 driver.findElement(By.xpath(locatorvalue)).clear();
				 driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
			}
			else
			{
				System.out.println("unable to locate for typeaction method with "+locatorvalue);
			}
		}
			public static void clickaction ( WebDriver driver, String locatortype,String locatorvalue )
			{
//				System.out.println("came inside clickaction "+locatortype.length()+" "+locatorvalue);
				
				if(locatortype.equalsIgnoreCase("id"))
				{
					driver.findElement(By.id(locatorvalue)).click();
				}
				else if(locatortype.equalsIgnoreCase("name")){
					driver.findElement(By.name(locatorvalue)).click();
				} 
				else if(locatortype.equalsIgnoreCase("xpath")){
					driver.findElement(By.xpath(locatorvalue)).click();
				}
			}

			public static void captureData(WebDriver driver,String locatortytpe,
					String locatorvalue) throws Exception{
				
				String supplierdata="";
				
				if(locatortytpe.equalsIgnoreCase("id")){
					supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
				}
				
				else if(locatortytpe.equalsIgnoreCase("xpath")){
					supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
				}
				
				else if(locatortytpe.equalsIgnoreCase("name")){
					supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");
				}
				
				FileWriter fw=new FileWriter (System.getProperty("user.dir")+"\\CaptureData\\suppnumber.txt");
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(supplierdata);
				bw.flush();
				bw.close();	
			}
	   
			
			
			public static  void tablevalidation ( WebDriver driver,String column) throws Exception{
				FileReader  fr=new FileReader(System.getProperty("user.dir")+"\\CaptureData\\suppnumber.txt");
				BufferedReader Br=new BufferedReader(fr);
				 String Exp_data=Br.readLine();
				 if(driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchtextbox"))).isDisplayed()){
						Thread.sleep(5000);
						driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchtextbox"))).sendKeys(Exp_data);
						driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchbutton"))).click();
					}else{
						driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searchpanelbutton"))).click();
						Thread.sleep(5000);
						driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchtextbox"))).sendKeys(Exp_data);
						driver.findElement(By.id(PropertyFileUtil.getValueForKey("searchbutton"))).click();
					}
					
					WebElement table=driver.findElement(By.id(PropertyFileUtil.getValueForKey("suppliertable")));
					
					List<WebElement>rows=table.findElements(By.tagName("tr"));
					
					for(int i=1;i<rows.size();i++){
					 String act_data= driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span")).getText();	
					 Assert.assertEquals(Exp_data, act_data); 
					 System.out.println(act_data+"   "+Exp_data);
					 break;
					}
			}	
		public static void close( WebDriver driver  )
		{
		driver.close();
		}
		public static String   curdate(){
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_DD_hh_mm_ss");
			String requiredate=sdf.format(d);
			return requiredate;
			
		}

}
