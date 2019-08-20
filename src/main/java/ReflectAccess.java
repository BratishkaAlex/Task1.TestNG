import java.lang.reflect.Field;
import java.util.ArrayList;

public class ReflectAccess {
    public static int getNumberByRelfectForFile(File testFile, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFile.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return ((Double) field.get(testFile)).intValue();
    }

    public static String getStringByRelfectForFile(File testFile, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFile.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (String) field.get(testFile);
    }

    public static int getNumberByRelfectForFileStorage(FileStorage testFS, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFS.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return ((Integer) field.get(testFS)).intValue();
    }

    public static void reflectWriteListForFileStorage(FileStorage testFS, ArrayList files) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFS.getClass().getDeclaredField("files");
        field.setAccessible(true);
        field.set(testFS, files.clone());
    }

    public static ArrayList getFilesForFileStorage(FileStorage testFS) throws NoSuchFieldException, IllegalAccessException {
        Field field = testFS.getClass().getDeclaredField("files");
        field.setAccessible(true);
        return (ArrayList) field.get(testFS);
    }
}
