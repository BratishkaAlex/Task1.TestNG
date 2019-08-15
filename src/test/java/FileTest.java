import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class FileTest {

    @DataProvider(name = "dataForFileTest")
    public static Object[][] testDataForFiles() {
        return new Object[][]{{"src/Files/test1.txt", "qwerty"}};
    }


    @Test(dataProvider = "dataForFileTest")
    // @Parameters(value = {"path", "content"})
    public void testGetExtension(String path, String content) {
        String extension = path.substring(path.trim().lastIndexOf('.') + 1);
        assertEquals(new File(path, content).getExtension(), extension, "Bad work in getExtension");
    }


    @Test(dataProvider = "dataForFileTest")
    public void testGetSize(String path, String content) {
        assertEquals(new File(path, content).getSize(), content.length() / 2, "Bad work in getSize");
    }

    @Test(dataProvider = "dataForFileTest")
    public void testGetContent(String path, String content) {
        assertEquals(new File(path, content).getContent(), content, "Bad work in getContent");
    }

    @Test(dataProvider = "dataForFileTest")
    public void testGetFilename(String path, String content) {
        assertEquals(new File(path, content).getFilename(), path, "Bad work in getFileName");
    }

    public int getNumberByRelfect(File testFile, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFile.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return ((Double) field.get(testFile)).intValue();
    }

    public String getStringByRelfect(File testFile, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFile.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(testFile);
    }

    @DataProvider(name = "dataForTestConstructor")
    public static Object[][] testDataForGetSize() {
        return new Object[][]{{"src/Files/test1.txt", "qwerty"}, {"", "zxcvbnmlk"}, {"src/Files/test3", "h"}};
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorSetSize(String path, String content) {
        File testFile = new File(path, content);
        int testSize = 0;
        try {
            testSize = getNumberByRelfect(testFile, "size");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing File constructor");
        }
        if (content.length() > 1) {
            assertEquals(testSize, content.length() / 2, "Bad work in constructor File(field size)");
        } else {
            assertEquals(testSize, 1, "Bad work in constructor File(field size)");
        }
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorSetExtension(String path, String content) {
        File testFile = new File(path, content);
        String extByReflect = null;
        try {
            extByReflect = getStringByRelfect(testFile, "extension");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing File constructor");
        }
        String testExt = path.substring(path.trim().lastIndexOf('.') + 1);
        if (testExt.equals(path)) {
            fail("There is no extension in path");
        } else {
            assertEquals(extByReflect, testExt, "Bad work in constructor File(field extension)");
        }
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorSetFilename(String path, String content) {
        File testFile = new File(path, content);
        String fileName = null;
        try {
            fileName = getStringByRelfect(testFile, "filename");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing File constructor");
        }
        if (fileName.equals("")) {
            fail("Filename cant be empty");
        } else {
            assertEquals(fileName, path, "Bad work in constructor File(field filename)");
        }
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorSetContent(String path, String content) {
        File testFile = new File(path, content);
        String testContent = null;
        try {
            testContent = getStringByRelfect(testFile, "content");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing File constructor");
        }
        assertEquals(testContent, content, "Bad work in constructor File(field content)");
    }
}