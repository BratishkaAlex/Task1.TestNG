import exception.FileAlreadyExistsException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;


public class FileStorageTest {

    /*@DataProvider(name = "testWrite")
    public static Object[][] testDataForWrite() {
        return new Object[][]{{100, "src/Files/test1.txt", "qwerty"}, {2, "src/Files/test2.txt", "ytredfgdfgwq"}, {-4, "src/Files/test3.txt", "hello"}};
    }*/


    @DataProvider(name = "testFileStorage")
    public static Object[][] testFileStorage() {
        return new Object[][]{{100, "src/Files/test1.txt", "qwerty"}};
    }

    @Test(dataProvider = "testFileStorage")
    //@Parameters(value = {"size", "path", "content"})
    public void testWrite(int size, String path, String content) {
        FileStorage testFS = new FileStorage(size);
        boolean status = false;
        try {
            status = testFS.write(new File(path, content));
        } catch (FileAlreadyExistsException e) {
            System.out.println("FileAlreadyExistsException");
        }
        assertEquals(status, true, "Bad work in write");
    }

    @Test(dataProvider = "testFileStorage")
    public void testWriteNG(int size, String path, String content) {
        FileStorage testFS = new FileStorage(size);
        File testFile = new File(path, content);
        try {
            testFS.write(testFile);
            testFS.write(testFile);
            fail("File already exists, but added");
        } catch (FileAlreadyExistsException e) {
        }
    }

    @Test(dataProvider = "testFileStorage")
    public void testIsExists(int size, String path, String content) {
        FileStorage testFS = new FileStorage(size);
        ArrayList<File> files = new ArrayList<>();
        File testFile = new File(path, content);
        files.add(testFile);
        try {
            reflectAccess(testFS, files);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in isExists");
        }
        assertEquals(testFS.isExists(path), files.contains(testFile), "Bad work in isExists");
    }

    @Test(dataProvider = "testFileStorage")
    public void testDelete(int size, String path, String content) {
        FileStorage testFS = new FileStorage(size);
        ArrayList<File> files = new ArrayList<>();
        File testFile = new File(path, content);
        files.add(testFile);
        files.add(testFile);
        try {
            reflectAccess(testFS, files);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in delete");
        }
        assertEquals(testFS.delete(path), files.remove(testFile), "Bad work in delete");

    }

    @Test(dataProvider = "testFileStorage")
    public void testGetFiles(int size, String path, String content) {
        FileStorage testFS = new FileStorage(size);
        ArrayList<File> files = new ArrayList<>();
        files.add(new File(path, content));
        try {
            reflectAccess(testFS, files);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in getFiles");
        }
        assertEquals(testFS.getFiles(), files, "Bad work in getFiles");
    }

    @Test(dataProvider = "testFileStorage")
    public void testGetFile(int size, String path, String content) {
        FileStorage testFS = new FileStorage(size);
        ArrayList<File> files = new ArrayList<>();
        File testFile = new File(path, content);
        files.add(testFile);
        try {
            reflectAccess(testFS, files);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in getFile");
        }
        assertEquals(testFS.getFile(path), testFile, "Bad work in getFile");
    }


    public void reflectAccess(FileStorage testFS, ArrayList files) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFS.getClass().getDeclaredField("files");
        field.setAccessible(true);
        field.set(testFS, files.clone());
    }

    @DataProvider(name = "forTestingSize")
    public static Object[][] testDataForSize() {
        return new Object[][]{{10}};
    }

    @Test(dataProvider = "forTestingSize")
    public void testGetAvailableSize(int size) {
        assertEquals(new FileStorage(size).getAvailableSize(), size, "Bad work on getAvailableSize");
    }

    @Test(dataProvider = "forTestingSize")
    public void testGetMaxSize(int size) {
        assertEquals(new FileStorage(size).getMaxSize(), size, "Bad work in getMaxSize");
    }


    @DataProvider(name = "forTestingConstructor")
    public static Object[][] testDataForConstructor() {
        return new Object[][]{{Integer.MAX_VALUE + 1}, {Integer.MAX_VALUE}, {10}, {0}, {-9}};
    }

    public int getNumberByRelfect(FileStorage testFS, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFS.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return ((Integer) field.get(testFS)).intValue();
    }

    @Test()
    public void testDefaultConstructor() {
        FileStorage testFS = new FileStorage();
        int maxSize = 0, availableSize = 0;
        try {
            maxSize = getNumberByRelfect(testFS, "maxSize");
            availableSize = getNumberByRelfect(testFS, "availableSize");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing FileStorage constructor");
        }
        assertEquals(maxSize, 100, "Bad work in constructor FileStorage(field maxSize)");
        assertEquals(availableSize, 100, "Bad work in constructor FileStorage(field availableSize)");
    }

    @Test(dataProvider = "forTestingConstructor")
    public void testConstructorWithSizeSetMaxSize(int size) {
        FileStorage testFS = new FileStorage(size);
        int maxSize = 0;
        try {
            maxSize = getNumberByRelfect(testFS, "maxSize");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing FileStorage constructor");
        }
        if (size > 0) {
            assertEquals(maxSize, size, "Bad work in constructor FileStorage(field maxSize)");
        } else {
            fail("Size cant be less than 1");
        }
    }

    @Test(dataProvider = "forTestingConstructor")
    public void testConstructorWithSizeSetAvailableSize(int size) {
        FileStorage testFS = new FileStorage(size);
        int availableSize = 0;
        try {
            availableSize = getNumberByRelfect(testFS, "availableSize");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Something went wrong in testing FileStorage constructor");
        }
        if (size > 0) {
            assertEquals(availableSize, size, "Bad work in constructor FileStorage(field availableSize)");
        } else {
            fail("Size cant be less than 1");
        }
    }
}