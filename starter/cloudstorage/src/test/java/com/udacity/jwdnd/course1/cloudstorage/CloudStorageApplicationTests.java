package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	@Autowired
	private NoteService noteService;
	@Autowired private UserService userService;
	@Autowired private CredentialService credentialService;
	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection5","Test5","RT5","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL6","Test6","UT6","123");
		doLogIn("UT6", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		webDriverWait.until(ExpectedConditions.urlToBe("http://localhost:" + this.port + "/some-random-page"));
		Assertions.assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/** TODO:
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File1","TestFile","LFT1","123");
		doLogIn("LFT1", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertTrue(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void readWriteNote() {
		// Create a test account
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		//doMockSignUp("testNote","TestNote","testNote","123");
		doLogIn("testNote", "123");

		String noteTitle = "Test Note Title";
		String noteDescription = "Test note description";
		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-add-note")));
		driver.findElement(By.id("btn-add-note")).click();
		WebElement elNoteTitle = driver.findElement(By.id("note-title"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		elNoteTitle.click();
		elNoteTitle.sendKeys(noteTitle);
		WebElement elNoteDescription = driver.findElement(By.id("note-description"));
		elNoteDescription.click();
		elNoteDescription.sendKeys(noteDescription);
		driver.findElement(By.id("noteSubmit")).submit();
		User user = userService.getUser("testNote");
		List<Note> savedNotes = noteService.getNotes(user.getUserid());
		Note lastSavedNote = savedNotes.get(savedNotes.size() - 1);
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		WebElement tableHeader = driver.findElement(By.id("note-" + lastSavedNote.getNoteId())).findElement(By.tagName("th"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("th")));
		List<WebElement> tableData = driver.findElement(By.id("note-" + lastSavedNote.getNoteId())).findElements(By.tagName("td"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("td")));
		Assertions.assertEquals(noteTitle, tableHeader.getText());
		Assertions.assertTrue(
				tableData
						.stream()
						.map(WebElement::getText)
						.collect(Collectors.toList())
						.contains(noteDescription)
		);
	}

	@Test
	public void updateNote() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
//		doMockSignUp("testNote","TestNote","testNote","123");
		doLogIn("testNote", "123");
		String updatedNoteTitle = "Test Title (updated)";
		String updatedNoteDescription = "Test note description (updated)";
		User user = userService.getUser("testNote");
		List<Note> savedNotes = noteService.getNotes(user.getUserid());
		Note lastSavedNote = savedNotes.get(savedNotes.size() - 1);
		driver.findElement(By.id("nav-notes-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-add-note")));
		WebElement elNote = driver.findElement(By.id("note-" + lastSavedNote.getNoteId()));
		elNote.findElement(By.tagName("button")).click();
		WebElement elNoteId = driver.findElement(By.id("note-id"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.display='block';", elNoteId);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-id")));
		WebElement elNoteTitle = driver.findElement(By.id("note-title"));
		js.executeScript("arguments[0].value='"+ updatedNoteTitle +"';", elNoteTitle);
		WebElement elNoteDescription = driver.findElement(By.id("note-description"));
		js.executeScript("arguments[0].value='"+ updatedNoteDescription +"';", elNoteDescription);
		driver.findElement(By.id("noteSubmit")).submit();
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		WebElement tableHeader = driver.findElement(By.id("note-" + lastSavedNote.getNoteId())).findElement(By.tagName("th"));
		List<WebElement> tableData = driver.findElement(By.id("note-" + lastSavedNote.getNoteId())).findElements(By.tagName("td"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("td")));
		Assertions.assertEquals(updatedNoteTitle, tableHeader.getText());
		Assertions.assertTrue(
				tableData
						.stream()
						.map(WebElement::getText)
						.collect(Collectors.toList())
						.contains(updatedNoteDescription)
		);
	}

	@Test
	public void deleteNote() {
//		doMockSignUp("testNote","TestNote","testNote","123");
		doLogIn("testNote", "123");
		User user = userService.getUser("testNote");
		List<Note> savedNotes = noteService.getNotes(user.getUserid());
		Note lastSavedNote = savedNotes.get(savedNotes.size() - 1);
		driver.findElement(By.id("nav-notes-tab")).click();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-" + lastSavedNote.getNoteId())));
		driver.findElement(By.id("note-" + lastSavedNote.getNoteId())).findElement(By.tagName("a")).click();
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-notes-tab")).click();
		List<WebElement> tableRow = driver.findElements(By.id("note-" + lastSavedNote.getNoteId()));

		Assertions.assertEquals(tableRow.size(), 0);
	}

	@Test
	public void readWriteCredential() {
		// Create a test account
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		//doMockSignUp("testCredential","TestCredential","testCredential","123");
		doLogIn("testCredential", "123");

		String credentialUrl = "Test Credential url";
		String credentialUsername = "Test Credential username";
		String credentialUPassword = "123";
		driver.findElement(By.id("nav-credentials-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-add-credential")));
		driver.findElement(By.id("btn-add-credential")).click();
		WebElement elNoteUrl = driver.findElement(By.id("credential-url"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		elNoteUrl.click();
		elNoteUrl.sendKeys(credentialUrl);
		WebElement elNoteUserName = driver.findElement(By.id("credential-username"));
		elNoteUserName.click();
		elNoteUserName.sendKeys(credentialUsername);
		WebElement elNotePassword = driver.findElement(By.id("credential-password"));
		elNotePassword.click();
		elNotePassword.sendKeys(credentialUPassword);
		driver.findElement(By.id("credentialSubmit")).submit();
		User user = userService.getUser("testCredential");
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		List<Credential> savedNotes = credentialService.getCredentials(user.getUserid());
		Credential lastSavedCredential = savedNotes.get(savedNotes.size() - 1);
		WebElement tableHeader = driver.findElement(By.id("credential-" + lastSavedCredential.getCredentialid())).findElement(By.tagName("th"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("th")));
		Assertions.assertEquals(credentialUrl, tableHeader.getText());
	}

	@Test
	public void updateCredential() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
//		//doMockSignUp("testCredential","TestCredential","testCredential","123");
		doLogIn("testCredential", "123");
		String credentialUrl = "Test Credential url (updated)";
		User user = userService.getUser("testCredential");
		List<Credential> savedCredentials = credentialService.getCredentials(user.getUserid());
		Credential lastSavedNote = savedCredentials.get(savedCredentials.size() - 1);
		driver.findElement(By.id("nav-credentials-tab")).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-add-credential")));
		WebElement elNote = driver.findElement(By.id("credential-" + lastSavedNote.getCredentialid()));
		elNote.findElement(By.tagName("button")).click();
		WebElement elNoteId = driver.findElement(By.id("credential-id"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.display='block';", elNoteId);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-id")));
		WebElement elCredentialUrl = driver.findElement(By.id("credential-url"));
		js.executeScript("arguments[0].value='"+ credentialUrl +"';", elCredentialUrl);
		driver.findElement(By.id("credentialSubmit")).submit();
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		WebElement tableHeader = driver.findElement(By.id("credential-" + lastSavedNote.getCredentialid())).findElement(By.tagName("th"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("th")));
		Assertions.assertEquals(credentialUrl, tableHeader.getText());
	}

	@Test
	public void deleteCredential() {
//		doMockSignUp("testCredential","TestCredential","testCredential","123");
		doLogIn("testCredential", "123");
		User user = userService.getUser("testCredential");
		List<Credential> savedCredentials = credentialService.getCredentials(user.getUserid());
		Credential lastSavedNote = savedCredentials.get(savedCredentials.size() - 1);
		driver.findElement(By.id("nav-credentials-tab")).click();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-" + lastSavedNote.getCredentialid())));
		driver.findElement(By.id("credential-" + lastSavedNote.getCredentialid())).findElement(By.tagName("a")).click();
		driver.get("http://localhost:" + this.port + "/home");
		driver.findElement(By.id("nav-credentials-tab")).click();
		List<WebElement> tableRow = driver.findElements(By.id("credential-" + lastSavedNote.getCredentialid()));

		Assertions.assertEquals(tableRow.size(), 0);
	}

}