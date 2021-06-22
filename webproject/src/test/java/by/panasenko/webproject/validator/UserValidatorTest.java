package by.panasenko.webproject.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorTest {
    @DataProvider(name = "validEmail")
    public static Object[][] validEmail() {
        return new Object[][]{{"panatva@gmail.com"}, {"mbedulya@tut.by"}, {"panasenko@mail.ru"}};
    }

    @Test(dataProvider = "validEmail")
    public void validateEmailPositiveTest(String email) {
        Assert.assertTrue(UserValidator.validateEmail(email));
    }

    @DataProvider(name = "invalidEmail")
    public static Object[][] invalidEmail() {
        return new Object[][]{{"panasenko"}, {""}, {"panasenko@gmailcom"}};
    }

    @Test(dataProvider = "invalidEmail")
    public void validateEmailNegativeTest(String email) {
        Assert.assertFalse(UserValidator.validateEmail(email));
    }

    @DataProvider(name = "validUsername")
    public static Object[][] validUsername() {
        return new Object[][]{{"123456"}, {"Natasha"}, {"natasha2001"}};
    }

    @Test(dataProvider = "validUsername")
    public void validateUsernamePositiveTest(String username) {
        Assert.assertTrue(UserValidator.validateUsername(username));
    }

    @DataProvider(name = "invalidUsername")
    public static Object[][] invalidUsername() {
        return new Object[][]{{"1"}, {"наташа"}, {""}};
    }

    @Test(dataProvider = "invalidUsername")
    public void validateUsernameNegativeTest(String username) {
        Assert.assertFalse(UserValidator.validateUsername(username));
    }

    @DataProvider(name = "validFIO")
    public static Object[][] validFIO() {
        return new Object[][]{{"Natasha"}, {"Панасенко"}, {"Шатюк-Мачуленко"}};
    }

    @Test(dataProvider = "validFIO")
    public void validateFIOPositiveTest(String name) {
        Assert.assertTrue(UserValidator.validateFIO(name));
    }

    @DataProvider(name = "invalidFIO")
    public static Object[][] invalidFIO() {
        return new Object[][]{{"Natasha2001"}, {""}, {"Рита/Катя"}};
    }

    @Test(dataProvider = "invalidFIO")
    public void validateFIONegativeTest(String name) {
        Assert.assertFalse(UserValidator.validateFIO(name));
    }

    @DataProvider(name = "validPhone")
    public static Object[][] validPhone() {
        return new Object[][]{{"+375336109879"}, {"+375441234567"}, {"+375259876543"}};
    }

    @Test(dataProvider = "validPhone")
    public void validatePhonePositiveTest(String phone) {
        Assert.assertTrue(UserValidator.validatePhoneNumber(phone));
    }

    @DataProvider(name = "invalidPhone")
    public static Object[][] invalidPhone() {
        return new Object[][]{{"375336109879"}, {""}, {"+37598745"}};
    }

    @Test(dataProvider = "invalidPhone")
    public void validatePhoneNegativeTest(String phone) {
        Assert.assertFalse(UserValidator.validatePhoneNumber(phone));
    }
}
