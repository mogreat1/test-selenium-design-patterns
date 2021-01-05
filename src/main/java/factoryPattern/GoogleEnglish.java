package factoryPattern;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

class GoogleEnglish extends GooglePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//span[text()='I agree']")
    private WebElement agreeBtn;
    @FindBy(xpath = "//*[@role='combobox']")
    private WebElement searchBox;
    @FindBy(name = "btnK")
    private WebElement searchBtn;
    @FindBy(css = "div.rc")
    private List<WebElement> results;

    public GoogleEnglish(final WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void clickBtn() {
        driver.switchTo().frame(0);
        wait.until(ExpectedConditions.elementToBeClickable(this.agreeBtn)).click();
    }

    @Override
    public void launchSite() {
        this.driver.get("https://www.google.com");
        clickBtn();
    }

    @Override
    public void search(String keyword) {
        for (char ch : keyword.toCharArray()) {
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.MILLISECONDS);
            this.searchBox.sendKeys(ch + "");
        }
        this.wait.until((d) -> this.searchBtn.isDisplayed());
        this.searchBtn.click();
    }

    @Override
    public int getResultsCount() {
        this.wait.until((driver1 -> this.results.size() > 1));
        return this.results.size();
    }
}
