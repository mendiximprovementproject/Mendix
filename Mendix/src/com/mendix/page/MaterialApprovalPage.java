package com.mendix.page;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mendix.tool.Button;
import com.mendix.tool.Constants;
import com.mendix.tool.Sync;
import com.mendix.tool.Textbox;
import com.mendix.util.ExcelUtil;

public class MaterialApprovalPage {

	/** The driver. */
	WebDriver driver;

	/**
	 * Instantiates a new process Info page.
	 *
	 * @param driver the driver
	 */
	public MaterialApprovalPage(WebDriver driver){
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}



	@FindBy(how=How.CSS, using="div[id^='mxui_widget_NumberInput_'][class^='mx-name-textBox2'] :nth-child(1)")
	WebElement txtboxRequestId;

	@FindBy(how=How.XPATH, using=".//*[@class='glyphicon glyphicon-search']")
	WebElement btnReqIdSearch;

	@FindBy(how=How.XPATH, using=".//*[text()='Search']")
	WebElement btnReqIdMyTaskSearch;

	@FindBy(how=How.XPATH, using="//label[text()='Request ID']/../../div[2]/input")
	WebElement txtboxReqIdSearch;

	@FindBy(how=How.XPATH, using="(.//button[text()='Search'])[1]")
	WebElement btnReqIdEnterSearch;

	@FindBy(how=How.XPATH, using=".//button[text()='Open Task']")
	WebElement btnOpenTask;

	@FindBy(how=How.XPATH, using="//a[text()=' My Tasks']")
	WebElement menuMyTask;

//	@FindBy(how=How.XPATH, using="//div[@class='mx-placeholder']/button")
	@FindBy(how=How.XPATH, using="//span[@class='glyphicon glyphicon-flash']")
	WebElement btnlocalAction;
	
	@FindBy(how=How.XPATH, using="//*[text()='Reject Global Request']")
	WebElement btnRejectAction;

//	@FindBy(how=How.XPATH, using=".//*[@class='btn mx-button mx-name-actionButton11 btn-success']")
	@FindBy(how=How.CSS, using=".glyphicon.glyphicon-ok")
	WebElement btnGDAApproval;
	
	@FindBy(how=How.CSS, using="glyphicon glyphicon-save")
	WebElement btnGlobalRequestSubmit;

	@FindBy(how=How.XPATH, using="//*[text()='Proceed']")
	WebElement btnProceed;

	@FindBy(how=How.XPATH, using=".//button[text()='Submit Global Request']")
	WebElement btnGlobalRequest;

	/*@FindBy(how=How.CSS, using=".btn.btn-primary")
	WebElement btnMsgReqIdOk;*/

	@FindBy(how=How.XPATH, using="//*[text()='OK']")
	WebElement btnMsgReqIdOkdraft;
	@FindBy(how=How.XPATH, using="//*[text()='OK']")
	WebElement btnOK;
	
	@FindBy(how=How.CSS, using="div[class='modal-body mx-dialog-body']")
    WebElement btnMsgClose;
	
	@FindBy(how=How.CSS, using=".btn.btn-primary")
    WebElement btnMsgReqIdOk;
	
	@FindBy(how=How.CSS, using=".glyphicon.glyphicon-remove")
	WebElement btnGDAReject;
	/**
	 * Enter UserName.
	 * Enter Password
	 * 
	 * Click Login
	 *
	 * @param strMenuName the str menu name
	 * @return 
	 * @return true, if successful
	 */


	public boolean reqIdSearchMyTasks(String strValue) 

	{
		Sync.waitForSeconds(Constants.WAIT_6);
		Sync.waitForElementToBeClickable(driver, menuMyTask);

		Button.jsclick("Search My task", menuMyTask, driver);
		Sync.waitForSeconds(Constants.WAIT_5);
		Sync.waitUntilObjectDisappears(driver, "Wait My task to load", By.cssSelector("#mxui_widget_Progress_0"));
		Sync.waitForElementToBeClickable(driver, btnReqIdMyTaskSearch);
		Sync.waitForObject(driver, "Wait for button Request Id search", btnReqIdMyTaskSearch);
 		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(4))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(
		    		  By.xpath("//*[text()='Search']"))
		            );
		        }
				
		
		Sync.waitForSeconds(Constants.WAIT_3);
	
		
		Button.jsclick("Search My task", btnReqIdMyTaskSearch, driver);
		Sync.waitForSeconds(Constants.WAIT_3);
		Textbox.click("Click Enter Request Id", txtboxReqIdSearch);
		Sync.waitForSeconds(Constants.WAIT_3);
		Textbox.enterValue("Enter Request Id textbox", txtboxReqIdSearch, strValue);
		Sync.waitForSeconds(Constants.WAIT_1);
		Button.click("Click Search Button", btnReqIdEnterSearch);
		Sync.waitForSeconds(Constants.WAIT_1);
		return Button.click("Click Open Task", btnOpenTask);

	}

	public boolean approvalBtnClick()
	{
		Sync.waitForSeconds(Constants.WAIT_2);
		Sync.waitUntilObjectDisappears(driver, "Wait My tasks to load", By.xpath(".//*[@id='mxui_widget_Progress_0']/div[2]"));
		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(3))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(
		    		  By.xpath("//span[@class='glyphicon glyphicon-flash']"))
		            );
		        }
		
		
		
		Sync.waitForElementToBeClickable(driver, btnlocalAction);
		Button.click("Click Local Action button", btnlocalAction);
		Sync.waitForSeconds(Constants.WAIT_2);
		return Button.jsclick("Click Approval Button", btnGDAApproval, driver);
	}

        public boolean rejectBtnClick()
	{
		Sync.waitForSeconds(Constants.WAIT_2);
		Sync.waitUntilObjectDisappears(driver, "Wait My tasks to load", By.xpath(".//*[@id='mxui_widget_Progress_0']/div[2]"));
		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(3))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(
		    		  By.xpath("//span[@class='glyphicon glyphicon-flash']"))
		            );
		        }
		
		
		
		Sync.waitForElementToBeClickable(driver, btnlocalAction);
		Button.click("Click Local Action button", btnlocalAction);
		Sync.waitForSeconds(Constants.WAIT_2);
		return Button.jsclick("Click Approval Button", btnGDAReject, driver);
	}


	public void approvalBtnClick_Local()
	{

		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(3))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.invisibilityOfElementLocated(
		    		  By.xpath(".//*[@id='mxui_widget_Progress_0']"))
		            );
		        }
		  
		Button.click("Click Local Action button", btnlocalAction);
		Sync.waitForSeconds(Constants.WAIT_2);
		
//		Button.jsclick("Click Approval Button", btnGDAApproval, driver);
	}
	
	public void approvalBtnClick_Global()
	{

		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(3))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(
		    		  By.cssSelector(".glyphicon.glyphicon-ok"))
		            );
		        }
		  
//		Button.click("Click Local Action button", btnlocalAction);
		Sync.waitForSeconds(Constants.WAIT_2);
		
		Button.jsclick("Click Approval Button", btnGDAApproval, driver);
	}
	
	public void submitBtnClick_Global()
	{

		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(3))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(
		    		  By.xpath(".//button[text()='Submit Global Request']"))
		            );
		        }
		  
//		Button.click("Click Local Action button", btnlocalAction);
		Sync.waitForSeconds(Constants.WAIT_2);
		
		Button.jsclick("Click Approval Button", btnGlobalRequest, driver);
	}
	
	
	public void markViewsBtnClick_Local()
	{

		
		WebElement waitElement = null;
		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
		        .withTimeout(Duration.ofMinutes(3))
		        .pollingEvery(Duration.ofSeconds(600))
		        .ignoring(NoSuchElementException.class)
		        .ignoring(TimeoutException.class);
		 
		//First checking to see if the loading indicator is found
		// we catch and throw no exception here in case they aren't ignored
		try {
		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
		   public WebElement apply(WebDriver driver) {
		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
		   }
		 });
		    } catch (Exception e) {
		   }
		 
		//checking if loading indicator was found and if so we wait for it to
		//disappear
		  if (waitElement != null) {
		      WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.invisibilityOfElementLocated(
		    		  By.xpath(".//*[@id='mxui_widget_Progress_0']"))
		            );
		        }
		  
		Button.click("Click Local Action button", btnlocalAction);
		Sync.waitForSeconds(Constants.WAIT_5);
		
		Button.jsclick("Click Mark All views Completed Button", driver.findElement(By.xpath("//button[text()='Mark all Views Completed']")), driver);
		Sync.waitForSeconds(Constants.WAIT_2);
//		Button.jsclick("Click Approval Button", btnGDAApproval, driver);
	}
	
	public boolean approveLocalRequest()
	{
		Sync.waitForSeconds(Constants.WAIT_5);
		Sync.waitForElementToBeClickable(driver, btnlocalAction);
		Button.click("Click Local Action button", btnlocalAction);
		return Button.jsclick("Click Approval Button", btnGDAApproval, driver);
	}
	
	

	public void submitRequestOkBtnClick()
	{
		Sync.waitForSeconds(Constants.WAIT_3);
		/*String buttonColor = btnMsgReqIdOk.getCssValue("background-color");
        String buttonTextColor = btnMsgReqIdOk.getCssValue("color");
        System.out.println("Button color: " + buttonColor);
        System.out.println("Text color " + buttonTextColor);
*/		      /*WebDriverWait wait = new WebDriverWait(driver, 60);
		      wait.until(ExpectedConditions.visibilityOfElementLocated(
		    		  By.xpath(".//*[@id='mxui_widget_Progress_0']"))
		            );*/
		      By loadingImage = By.xpath(".//*[@id='mxui_widget_Progress_0']");
		      WebDriverWait waittime = new WebDriverWait(driver, 60);
		      waittime.until(ExpectedConditions.invisibilityOfElementLocated(loadingImage));
		      Actions actions= new Actions(driver);
		      actions.moveToElement(btnMsgReqIdOk);
		      actions.perform();
			Button.click("Click Ok Button", btnMsgReqIdOk);
			
//		}
		
	}
	public void submitRequestOkButtonClick() {
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
	      wait.until(ExpectedConditions.visibilityOfElementLocated(
	    		  By.xpath("//*[text()='OK']")));
		Button.click("Click On OK button", btnOK);
	}
	

	public boolean submitGlobalRequest()
	{

		Sync.waitForElementToBeClickable(driver, btnlocalAction);
		Button.click("Click Local Action button", btnlocalAction);
		return Button.click("Click Approval button", btnGlobalRequest);
	}
	

	public boolean submitGlobalRequest_draft()
	{

		Sync.waitForElementToBeClickable(driver, btnlocalAction);
		Button.click("Click Local Action button", btnlocalAction);
		Button.click("Click Approval button", btnGlobalRequest);
		Actions btnselect = new Actions(driver);
		btnselect.moveToElement(btnMsgReqIdOk);
		btnselect.build();
		btnselect.perform();
		return Button.click("Click Ok Button", btnMsgReqIdOk);
	}
	public boolean RejectBtnClick()
	{

		Sync.waitForElementToBeClickable(driver, btnlocalAction);
		Button.click("Click Local Action button", btnlocalAction);
		Button.click("Click Approval button", btnGlobalRequest);
		Actions btnselect = new Actions(driver);
		btnselect.moveToElement(btnMsgReqIdOk);
		btnselect.build();
		btnselect.perform();
		return Button.click("Click Ok Button", btnMsgReqIdOk);
	}

	public  void launchUFT() throws IOException {
		Runtime.getRuntime().exec("C:\\Users\\IBM_ADMIN\\git\\MDM_TEST\\MDM_POC\\AutoIt UFT Launch\\UFT.exe");
		

	}
public  void launch_UFT_JDE() throws IOException {
		Runtime.getRuntime().exec("C:\\Users\\SatishKumarSundaramo\\git\\Mendix_New\\Mendix\\AutoIt_UFT_Launch\\JDE.exe");
        }



	public void duplicateCheck() {
		try {
//			Sync.waitUntilObjectDisappears(driver, "Wait for Duplicate check", By.xpath(".//*[@id='mxui_widget_Progress_0']/div[2]"));
						
			WebElement waitElement = null;
			FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
			        .withTimeout(Duration.ofMinutes(3))
			        .pollingEvery(Duration.ofSeconds(600))
			        .ignoring(NoSuchElementException.class)
			        .ignoring(TimeoutException.class);
			 
			//First checking to see if the loading indicator is found
			// we catch and throw no exception here in case they aren't ignored
			try {
			  waitElement = fwait.until(new Function<WebDriver, WebElement>() {
			   public WebElement apply(WebDriver driver) {
			      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));
			   }
			 });
			    } catch (Exception e) {
			   }
			 
			//checking if loading indicator was found and if so we wait for it to
			//disappear
			  if (waitElement != null) {
			      WebDriverWait wait = new WebDriverWait(driver, 120);
			      wait.until(ExpectedConditions.visibilityOfElementLocated(
			    		  By.xpath(".//*[text()='Open Record']"))
			            );
			        }

			driver.manage().window().setPosition(new Point(-2000, 0)) ;
			driver.findElement(By.xpath(".//*[text()='Open Record']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath(".//*[text()='Export to Excel']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath("//*[text()='Confirm and Approve']")).sendKeys(Keys.TAB);
			driver.findElement(By.xpath("//*[text()='Confirm and Approve']")).sendKeys(Keys.RETURN);
			driver.findElement(By.xpath("//*[text()='Proceed']")).click();
			Sync.waitForSeconds(Constants.WAIT_3);


			driver.manage().window().maximize();
			Actions actions = new Actions(driver);
			actions.moveToElement(btnMsgReqIdOk);
			actions.perform();

			Button.click("Click Ok Button", btnMsgReqIdOk);

			/*try
			{
				if(btnMsgReqIdOkdraft.isEnabled())
				{
					Button.click("Click Ok Button", btnMsgReqIdOkdraft);
					System.out.println("Button is Clicked");
				}

			}
			catch(Exception e) {
				System.err.println(e.getMessage());


			}*/

		}
		catch(Exception e) {
			System.err.println(e.getMessage());


		}
	}
//}

	
	public void okbuttonClick() {

		

	      try {

	    	  WebElement waitElement = null;

	  		FluentWait<WebDriver> fwait = new FluentWait<WebDriver>(driver)

	  		        .withTimeout(Duration.ofMinutes(3))

	  		        .pollingEvery(Duration.ofSeconds(600))

	  		        .ignoring(NoSuchElementException.class)

	  		        .ignoring(TimeoutException.class);

	  		 

	  		//First checking to see if the loading indicator is found

	  		// we catch and throw no exception here in case they aren't ignored

	  		try {

	  		  waitElement = fwait.until(new Function<WebDriver, WebElement>() {

	  		   public WebElement apply(WebDriver driver) {

	  		      return driver.findElement(By.xpath(".//*[@id='mxui_widget_Progress_0']"));

	  		   }

	  		 });

	  		    } catch (Exception e) {

	  		   }

	  		 

	  		//checking if loading indicator was found and if so we wait for it to

	  		//disappear

	  		  if (waitElement != null) {

	  		      WebDriverWait wait = new WebDriverWait(driver, 60);

	  		      wait.until(ExpectedConditions.visibilityOfElementLocated(

	  		    		  By.cssSelector("div[class='modal-body mx-dialog-body']"))

	  		            );

	  		        }

			if(btnMsgClose.isEnabled())

			{

				Button.click("Click close Button", btnMsgClose);

				System.out.println("Button is Clicked");

				Button.click("Click Ok Button", btnMsgReqIdOk);

			}



		}

		catch(Exception e) {

			System.err.println(e.getMessage());

			

		}

		}

	}

