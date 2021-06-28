package by.panasenko.webproject.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FlowerValidatorTest {
    @DataProvider(name = "validName")
    public static Object[][] validName() {
        return new Object[][]{{"Орхидея"}, {"орхидея"}, {"Орхидея цимбидиум"}};
    }

    @Test(dataProvider = "validName")
    public void validateNamePositiveTest(String name) {
        Assert.assertTrue(FlowerValidator.validateName(name));
    }

    @DataProvider(name = "invalidName")
    public static Object[][] invalidName() {
        return new Object[][]{{"щ"}, {""}, {"orhideya"}};
    }

    @Test(dataProvider = "invalidName")
    public void validateNameNegativeTest(String name) {
        Assert.assertFalse(FlowerValidator.validateName(name));
    }

    @DataProvider(name = "validDescription")
    public static Object[][] validDescription() {
        return new Object[][]{{"Это редкое цитрусовое деревце"}, {"rose with a unique color"}, {"роза с уникальной окраской"}};
    }

    @Test(dataProvider = "validDescription")
    public void validateDescriptionPositiveTest(String description) {
        Assert.assertTrue(FlowerValidator.validateDescription(description));
    }

    @DataProvider(name = "invalidDescription")
    public static Object[][] invalidDescription() {
        return new Object[][]{{""}, {"o"}, {"rhfc"}};
    }

    @Test(dataProvider = "invalidDescription")
    public void validateDescriptionNegativeTest(String description) {
        Assert.assertFalse(FlowerValidator.validateDescription(description));
    }

    @DataProvider(name = "validPrice")
    public static Object[][] validPrice() {
        return new Object[][]{{"5.90"}, {"67.00"}, {"599.50"}};
    }

    @Test(dataProvider = "validPrice")
    public void validatePricePositiveTest(String price) {
        Assert.assertTrue(FlowerValidator.validatePrice(price));
    }

    @DataProvider(name = "invalidPrice")
    public static Object[][] invalidPrice() {
        return new Object[][]{{""}, {"5.9"}, {"1234.50"}};
    }

    @Test(dataProvider = "invalidPrice")
    public void validatePriceNegativeTest(String price) {
        Assert.assertFalse(FlowerValidator.validatePrice(price));
    }

    @DataProvider(name = "validWatering")
    public static Object[][] validWatering() {
        return new Object[][]{{"5"}, {"67"}, {"8915"}};
    }

    @Test(dataProvider = "validWatering")
    public void validateWateringPositiveTest(String watering) {
        Assert.assertTrue(FlowerValidator.validateWatering(watering));
    }

    @DataProvider(name = "invalidWatering")
    public static Object[][] invalidWatering() {
        return new Object[][]{{""}, {"12.0"}, {"12345"}};
    }

    @Test(dataProvider = "invalidWatering")
    public void validateWateringNegativeTest(String watering) {
        Assert.assertFalse(FlowerValidator.validatePrice(watering));
    }

    @DataProvider(name = "validSoil")
    public static Object[][] validSoil() {
        return new Object[][]{{"PODZOLIC"}, {"SODPODZOLIC"}, {"UNPAVED"}};
    }

    @Test(dataProvider = "validSoil")
    public void validateSoilPositiveTest(String soil) {
        Assert.assertTrue(FlowerValidator.validateSoil(soil));
    }

    @DataProvider(name = "invalidSoil")
    public static Object[][] invalidSoil() {
        return new Object[][]{{""}, {"12.0"}, {"podzolic"}};
    }

    @Test(dataProvider = "invalidSoil")
    public void validateSoilNegativeTest(String soil) {
        Assert.assertFalse(FlowerValidator.validateSoil(soil));
    }

    @DataProvider(name = "validId")
    public static Object[][] validId() {
        return new Object[][]{{"1"}, {"2"}, {"3"}};
    }

    @Test(dataProvider = "validId")
    public void validateIdPositiveTest(String soil) {
        Assert.assertTrue(FlowerValidator.validateFlowerTypeId(soil));
    }

    @DataProvider(name = "invalidId")
    public static Object[][] invalidId() {
        return new Object[][]{{""}, {"12"}, {"p"}};
    }

    @Test(dataProvider = "invalidId")
    public void validateIdNegativeTest(String id) {
        Assert.assertFalse(FlowerValidator.validateFlowerTypeId(id));
    }
}
