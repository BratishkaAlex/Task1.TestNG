import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class FileTest {
    private String testFilename = "src/Files/test1.txt";
    private String testContent = "qwerty";


    @Test
    public void testGetExtension() {
        String extension = testFilename.substring(testFilename.trim().lastIndexOf('.') + 1);
        assertEquals(new File(testFilename, testContent).getExtension(), extension, "Bad work in getExtension, didn't return expected extension");
    }


    @Test
    public void testGetSize() {
        assertEquals(new File(testFilename, testContent).getSize(), testContent.length() / 2, "Bad work in getSize, didn't return expected size");
    }

    @Test
    public void testGetContent() {
        assertEquals(new File(testFilename, testContent).getContent(), testContent, "Bad work in getContent, didn't return expected content");
    }

    @Test
    public void testGetFilename() {
        assertEquals(new File(testFilename, testContent).getFilename(), testFilename, "Bad work in getFileName, didn't return expected filename");
    }

    @DataProvider(name = "dataForTestConstructor")
    public static Object[][] testDataForGetSize() {
        return new Object[][]{{"src/Files/test1.txt", "qwerty"}, {"", "zxcvbnmlk"}, {"src/Files/test3", ""}};
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorSize(String path, String content) {
        File testFile = new File(path, content);
        int testSize = 0;
        try {
            testSize = ReflectAccess.getNumberByRelfectForFile(testFile, "size");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field size)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field size)");
        }
        assertEquals(testSize, content.length() / 2, "Bad work in constructor File(field size), size doesn't equal a half of content");
    }

    @Test
    public void testConstructorSize1() {
        File testFile = new File(testFilename, "1");
        int testSize = 0;
        try {
            testSize = ReflectAccess.getNumberByRelfectForFile(testFile, "size");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field size)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field size)");
        }
        assertEquals(testSize, 1, "Bad work in constructor File(field size), when content length equals 1");
    }


    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorExtension(String path, String content) {
        File testFile = new File(path, content);
        String extByReflect = null;
        try {
            extByReflect = ReflectAccess.getStringByRelfectForFile(testFile, "extension");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field extension)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field extension)");
        }
        String testExt = path.substring(path.trim().lastIndexOf('.') + 1);
        assertEquals(extByReflect, testExt, "Bad work in constructor File(field extension), didn't save the expected extension");
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorExtensionEquals(String path, String content) {
        File testFile = new File(path, content);
        String extByReflect = null;
        try {
            extByReflect = ReflectAccess.getStringByRelfectForFile(testFile, "extension");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field extension)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field extension)");
        }
        assertFalse(extByReflect.equals(path), "There is no extension in path");
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorFilename(String path, String content) {
        File testFile = new File(path, content);
        String fileName = null;
        try {
            fileName = ReflectAccess.getStringByRelfectForFile(testFile, "filename");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field filename)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field filename)");
        }
        assertEquals(fileName, path, "Bad work in constructor File(field filename), didn't save the expected filename");
    }

    @Test
    public void testConstructorFilenameEquals() {
        File testFile = new File("", testContent);
        String fileName = null;
        try {
            fileName = ReflectAccess.getStringByRelfectForFile(testFile, "filename");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field filename)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field filename)");
        }
        assertFalse(fileName.equals(""), "Filename cant be empty");
    }

    @Test(dataProvider = "dataForTestConstructor")
    public void testConstructorContent(String path, String content) {
        File testFile = new File(path, content);
        String testContent = null;
        try {
            testContent = ReflectAccess.getStringByRelfectForFile(testFile, "content");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing  constructor File(field content)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing  constructor File(field content)");
        }
        assertEquals(testContent, content, "Bad work in constructor File(field content), didn't save the expected content");
    }
}