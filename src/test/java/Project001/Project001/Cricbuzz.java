package Project001.Project001;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Cricbuzz {
	
	WebDriver driver;
	List<String> data = new ArrayList<>();
	List<String[]> tableData = new ArrayList<>(); // For storing table data
	
	@BeforeTest
	public void setUp(){
		//Setting Chrome Driver
	    driver=new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    driver.get("https://www.cricbuzz.com");
	    String output = "Cricbuzz Website Launched Successfully";
	    data.add(output);
	    System.out.println(output);
	}
	 
	@Test
	public void navigateToRankings() {
		
		//Performing Mouse Over actions on Rankings
		Actions actions = new Actions(driver);
		WebElement rankingsMenu = driver.findElement(By.xpath("//a[text()='Rankings']"));
		actions.moveToElement(rankingsMenu).perform();
		
		String mouseOver = "Mouse hover performed on Rankings menu";
		data.add(mouseOver);
		System.out.println(mouseOver);
		
		//Clicking on ICC Rankings - Men
		WebElement iccMen = driver.findElement(By.xpath("//a[text()='ICC Rankings - Men']"));
		iccMen.click();
		Set<String> handles = driver.getWindowHandles();
		for(String handle : handles) {
			driver.switchTo().window(handle);
		}
		
		String nav = "Successfully navigated to ICC Mens Rankings Tab";
		data.add(nav);
		System.out.println(nav);
	}
	
	@Test (dependsOnMethods = {"navigateToRankings"})
	public void goToTeamsTab() {
		
		//Going to Teams Tab
		WebElement teamsTab = driver.findElement(By.xpath("//*[@id=\"teams-tab\"]"));
		teamsTab.click();
		String team = "Successfully moved to Teams tab";
		data.add(team);
		System.out.println(team);
	}
	
	@Test(dependsOnMethods = {"goToTeamsTab"})
	public void scroll() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 200);");
		Thread.sleep(1000);
	}
	
	
	@Test(dependsOnMethods = {"scroll"})
	public void fetchTopTeams() throws InterruptedException {
	    Map<String, String> topTeams = new HashMap<>();

	    try {
	    	String topTeam = "Starting to fetch Top Teams from each Format";
			data.add(topTeam);
			System.out.println(topTeam);
			
			// Add table headers
			tableData.add(new String[]{"Format", "Top Team"});
	        
	    	//Test Team
	        driver.findElement(By.xpath("//*[@id=\"teams-tests-tab\"]")).click();
	        String testTeam = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[2]/div/div/div[1]/div[3]/div[2]")).getText();
	        String testLog = "Fetched TEST format top team: " + testTeam;
	        data.add(testLog);
	        System.out.println(testLog);
	        Thread.sleep(1000);
	        captureScreenshot("TestTable.png");
	        
	        //ODI Team
	        driver.findElement(By.xpath("//*[@id=\"teams-odis-tab\"]")).click();
	        String odiTeam = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[2]/div/div/div[2]/div[2]/div[2]")).getText();
	        String odiLog = "Fetched ODI format top team: " + odiTeam;
	        data.add(odiLog);
	        System.out.println(odiLog);
	        Thread.sleep(2000);
	        captureScreenshot("ODITable.png");
	        
	        //T20 Team
	        driver.findElement(By.xpath("//*[@id=\"teams-t20s-tab\"]")).click();
	        String t20Team = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[2]/div/div/div[3]/div[2]/div[2]")).getText();
	        String t20Log = "Fetched T20 format top team: " + t20Team;
	        data.add(t20Log);
	        System.out.println(t20Log);
	        Thread.sleep(1000);
	        captureScreenshot("T20Table.png");
	        
	        //Adding teams to map for finding team is ranked 1st in more than 1 format
	        topTeams.put("Test", testTeam);
	        topTeams.put("ODI", odiTeam);
	        topTeams.put("T20", t20Team);
	        
	        // Add data to table
	        tableData.add(new String[]{"TEST", testTeam});
	        tableData.add(new String[]{"ODI", odiTeam});
	        tableData.add(new String[]{"T20", t20Team});

	        String summary = "Top Teams Summary - TEST: " + testTeam + ", ODI: " + odiTeam + ", T20: " + t20Team;
	        data.add(summary);
	        System.out.println("Top Teams by Format:");
	        topTeams.forEach((format, team) -> System.out.println(format + ": " + team));
	        
	        // Analysis of teams ranked first in multiple formats
	        Map<String, Integer> teamCount = new HashMap<>();
	        for (String team : topTeams.values()) {
	            teamCount.put(team, teamCount.getOrDefault(team, 0) + 1);
	        }

	        // Check and print any team ranked first in two or more formats
	        boolean found = false;
	        for (Map.Entry<String, Integer> entry : teamCount.entrySet()) {
	            if (entry.getValue() >= 2) {
	                String multiFormatTeam = entry.getKey() + " is ranked first in " + entry.getValue() + " formats";
	                data.add(multiFormatTeam);
	                System.out.println(multiFormatTeam);
	                found = true;
	            }
	        }

	        if (!found) {
	            String noMultiFormat = "No team is ranked first in more than one format";
	            data.add(noMultiFormat);
	            System.out.println(noMultiFormat);
	        }
	        
	        String fetchComplete = "Top teams data fetching completed successfully";
	        data.add(fetchComplete);
	        System.out.println(fetchComplete);

	    } catch (NoSuchElementException e) {
	        String error = "Failed to locate ranking tables: " + e.getMessage();
	        data.add(error);
	        System.out.println(error);
	        throw e;
	    }
	}

//	@Test (dependsOnMethods = {"fetchTopTeams"})
	public void captureScreenshot(String fileName) {
		
		String screenshotStart = "Starting screenshot capture";
		data.add(screenshotStart);
		System.out.println(screenshotStart);
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(fileName);
		try {
			org.openqa.selenium.io.FileHandler.copy(src, dest);
			String screenshotSuccess = "Screenshot captured successfully as CricbuzzRankings.png";
			data.add(screenshotSuccess);
			System.out.println(screenshotSuccess);
			
		} catch (Exception e) {
			String screenshotError = "Screenshot failed: " + e.getMessage();
			data.add(screenshotError);
			System.out.println(screenshotError);
		}	
	}
	
	@AfterTest
	public void tearDown() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		String excelStart = "Starting Excel file generation";
		data.add(excelStart);
		System.out.println(excelStart);
		
		// Write both regular data and table data to Excel
		Utilities.writeExcelWithTable(data, tableData);
		
		String excelComplete = "Excel file generated successfully";
		data.add(excelComplete);
		System.out.println(excelComplete);
		
		driver.quit();
		
		String testComplete = "Test Completed and Browser Closed Successfully";
		data.add(testComplete);
		System.out.println(testComplete);
	}
} 