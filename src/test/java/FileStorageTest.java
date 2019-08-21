import exception.FileAlreadyExistsException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.*;


public class FileStorageTest {
    private FileStorage testFS;
    private File testFile;
    private ArrayList<File> testFiles;
    private int defaultSize = 10;
    private String defaultFilename = "src/Files/test1.txt";
    private String defaultContent = "qwerty";

    @BeforeMethod
    public void initTestData() {
        testFS = new FileStorage(defaultSize);
        testFile = new File(defaultFilename, defaultContent);
        testFiles = new ArrayList<>();
        testFiles.add(testFile);
        try {
            ReflectAccess.reflectWriteListForFileStorage(testFS, testFiles);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in initializing test data");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in initializing test data");
        }
    }

    @Test
    public void testWrite() {
        try {
            assertTrue(testFS.write(new File(defaultFilename, defaultContent)), "Bad work in write, file was not added");
        } catch (FileAlreadyExistsException e) {
            System.out.println("FileAlreadyExistsException in write");
        }
    }

    @Test
    public void testWriteBigContent() {
        try {
            assertFalse(testFS.write(new File(defaultFilename, "12345678900987654321qwertyuioppoiuytrewq")),
                    "Bad work in write, file added when there is not enough availableSize");
        } catch (FileAlreadyExistsException e) {
            System.out.println("FileAlreadyExistsException in write big content");
        }
    }

    @Test
    public void testWriteNG() {
        try {
            assertFalse(testFS.write(testFile), "File already exists, but added");
        } catch (FileAlreadyExistsException e) {
            System.out.println("FileAlreadyExistsException in writeNG");
        }
    }

    @Test
    public void testIsExists() {
        assertTrue(testFS.isExists(defaultFilename), "Bad work in isExists, fexists, but wasn't found");
    }

    @Test
    public void testIsExistsNG() {
        assertFalse(testFS.isExists(defaultFilename + "new"), "Bad work in isExistsNG, file doesn't exist, but found");
    }

    @Test
    public void testDelete() {
        testFS.delete(defaultFilename);
        ArrayList files = null;
        try {
            files = ReflectAccess.getFilesForFileStorage(testFS);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testDelete");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testDelete");
        }
        assertTrue(files.isEmpty(), "Bad work in delete, file didn't delete");
    }

    @Test
    public void testDeleteNG() {
        assertFalse(testFS.delete(defaultFilename + "new"), "Bad work in delete, file doesn't exist, but returned true like it was deleted");
    }

    @Test
    public void testGetFiles() {
        assertEquals(testFS.getFiles(), testFiles, "Bad work in getFiles, didn't return list of files");
    }

    @Test
    public void testGetFile() {
        assertEquals(testFS.getFile(defaultFilename), testFile, "Bad work in getFile, didn't return expected file");
    }

    @Test
    public void testGetFileNG() {
        assertNull(testFS.getFile(defaultFilename + "new"), "Bad work in getFile, file doesn't exist, but was returned");
    }

    @Test
    public void testGetAvailableSize() {
        assertEquals(testFS.getAvailableSize(), defaultSize, "Bad work on getAvailableSize, availableSize doesn't equal defaultSize");
    }

    @Test
    public void testGetMaxSize() {
        assertEquals(testFS.getMaxSize(), defaultSize, "Bad work in getMaxSize, maxSize doesn't equal defaultSize");
    }

    @Test
    public void testDefaultConstructorOnMaxSize() {
        FileStorage testFS = new FileStorage();
        int maxSize = 0;
        try {
            maxSize = ReflectAccess.getNumberByRelfectForFileStorage(testFS, "maxSize");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing default constructor FileStorage(field maxSize");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing default constructor FileStorage(field maxSize");
        }
        assertEquals(maxSize, 100, "Bad work in default constructor FileStorage(field maxSize), maxSize doesn't equal 100");
    }

    @Test
    public void testDefaultConstructorOnAvailableSize() {
        FileStorage testFS = new FileStorage();
        int availableSize = 0;
        try {
            availableSize = ReflectAccess.getNumberByRelfectForFileStorage(testFS, "availableSize");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing default constructor FileStorage(field availableSize)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing default constructor FileStorage(field availableSize)");
        }
        assertEquals(availableSize, 100, "Bad work in default constructor FileStorage(field availableSize), availableSize doesn't equal 100");
    }

    @DataProvider(name = "forTestingConstructor")
    public static Object[][] testDataForConstructor() {
        return new Object[][]{{Integer.MAX_VALUE + 1}, {Integer.MAX_VALUE}, {10}, {0}, {-9}};
    }

    @Test
    public void testConstructorWithSizeSetMaxSize() {
        int maxSize = 0;
        try {
            maxSize = ReflectAccess.getNumberByRelfectForFileStorage(testFS, "maxSize");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing constructor FileStorage with sizes(field maxSize)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing constructor FileStorage with sizes(field maxSize)");
        }
        assertEquals(maxSize, defaultSize, "Bad work in constructor FileStorage(field maxSize), maxSize doesn't equals defaultSize");
    }

    @Test(dataProvider = "forTestingConstructor")
    public void testConstructorWithBadSize(int size) {
        FileStorage testFS = new FileStorage(size);
        int maxSize = 0;
        try {
            maxSize = ReflectAccess.getNumberByRelfectForFileStorage(testFS, "maxSize");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing constructor FileStorage with sizes(field maxSize)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing constructor FileStorage with sizes(field maxSize)");
        }
        assertTrue(maxSize > 0, "Bad work in constructor FileStorage with sizes(field maxSize), size cant be less than 1");
    }

    @Test
    public void testConstructorWithSizeSetAvailableSize() {
        int availableSize = 0;
        try {
            availableSize = ReflectAccess.getNumberByRelfectForFileStorage(testFS, "availableSize");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing constructor FileStorage(field availableSize)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing constructor FileStorage(field availableSize)");
        }
        assertEquals(availableSize, defaultSize, "Bad work in constructor FileStorage(field availableSize), availableSize doesn't equals defaultSize");
    }

    @Test(dataProvider = "forTestingConstructor")
    public void testConstructorWithBadSizeForAVS(int size) {
        FileStorage testFS = new FileStorage(size);
        int availableSize = 0;
        try {
            availableSize = ReflectAccess.getNumberByRelfectForFileStorage(testFS, "availableSize");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException in testing constructor FileStorage(field availableSize)");
        } catch (NoSuchFieldException e) {
            System.out.println("NoSuchFieldException in testing constructor FileStorage(field availableSize)");
        }
        assertTrue(availableSize > 0, "Bad work in constructor FileStorage(field availableSize), size cant be less than 1");
    }
}