package com.example.demo.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class AutoTest {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(3000);
        System.setProperty("webdriver.chrome.driver", "c://chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "c://geckodriver.exe");
        //WebDriver webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().deleteAllCookies();
        // 与浏览器同步非常重要，必须等待浏览器加载完毕
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //打开目标地址
        webDriver.get("http://192.168.1.107:9090/Login/to_login");
        add(webDriver);
        //暂停五秒钟后关闭
        Thread.sleep(2000);
        webDriver.quit();
    }
    static void add(WebDriver webDriver)throws InterruptedException{
        webDriver.findElement(By.id("username")).sendKeys("123");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.id("submit")).click();
        WebElement obj=webDriver.findElement(By.cssSelector(".menu-icon>span"));
        obj.click();
        Thread.sleep(1000);
        webDriver.findElement(By.partialLinkText("管理员拓展功能")).click();
        webDriver.findElement(By.partialLinkText("添加借阅")).click();
        WebElement uid,bid,b1,b2;
        uid=webDriver.findElement(By.id("uid"));
        bid=webDriver.findElement(By.id("bookId"));
        b1=webDriver.findElement(By.id("add"));
        b2=webDriver.findElement(By.id("move"));
        uid.sendKeys("123");
        bid.sendKeys("12");
        b2.click();

        Thread.sleep(10000);
    }
    static void search(WebDriver webDriver)throws InterruptedException{
        String[] strings=new String[3];
        strings[0]="数学";strings[1]="计算机";strings[2]="电路";
        webDriver.findElement(By.id("username")).sendKeys("123");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.id("submit")).click();
        Object object=webDriver.findElement(By.id("book-keyword"));
        WebElement button=webDriver.findElement(By.id("search"));
        for (int i=0;i<3;i++){
            ((WebElement) object).clear();
            ((WebElement) object).sendKeys(strings[i]);
            button.click();
            Thread.sleep(10000);
        }
    }
    static void move(WebDriver webDriver) throws InterruptedException{
        //移动到底部
        //((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        //移动到指定的坐标(相对当前的坐标移动)
        ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 700)");
        //移动到窗口绝对位置坐标，如下移动到纵坐标1600像素位置
        ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 1600)");
        Thread.sleep(1000);
    }
    static void login(WebDriver webDriver) throws InterruptedException{
        //输入账号 密码并登陆系统
        webDriver.findElement(By.id("username")).sendKeys("12334");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(10000);
        webDriver.findElement(By.id("username")).clear();
        webDriver.findElement(By.id("password")).clear();
        webDriver.findElement(By.id("username")).sendKeys("123");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(10000);
    }
    static void load(WebDriver webDriver) throws InterruptedException{
        //移动到指定元素，且元素底部和窗口底部对齐 参考 https://www.cnblogs.com/testway/p/6693140.html
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(false);", webDriver.findElement(By.cssSelector(".load-more-button")));
        webDriver.findElement(By.cssSelector(".load-more-button>a")).click();
        Thread.sleep(1000);
    }
    static void register(WebDriver webDriver) throws InterruptedException{
        webDriver.findElement(By.id("username")).sendKeys("123");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.cssSelector(".reg")).click();
        Alert alert = webDriver.switchTo().alert();
        Thread.sleep(10000);
        alert.accept();
        webDriver.findElement(By.id("username")).clear();
        webDriver.findElement(By.id("password")).clear();
        webDriver.findElement(By.id("username")).sendKeys("liva");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.cssSelector(".reg")).click();
        Thread.sleep(10000);
        webDriver.findElement(By.id("username")).clear();
        webDriver.findElement(By.id("password")).clear();
        webDriver.findElement(By.id("username")).sendKeys("12345678");
        webDriver.findElement(By.id("password")).sendKeys("123");
        webDriver.findElement(By.cssSelector(".reg")).click();
        Thread.sleep(10000);
    }
}
