package com.android.record.base.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kiddo on 17-4-7.
 */

public class FileUtil {


    private static final int BYTES_PER_MB = 4096;

    /**
     * write file
     *
     * @param outputFullFilename
     * @param byteArray
     */
    public static void writeFile(String outputFullFilename, byte[] byteArray) {
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        FileUtil.createFile(outputFullFilename);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFullFilename);
            copyStream(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            throw new FileUtilException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new FileUtilException(e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new FileUtilException(e);
                }
            }
        }
    }

    /**
     * copy stream , from input to output,it don't close
     *
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        if (inputStream != null && outputStream != null) {
            try {
                int length = -1;
                byte[] buffer = new byte[BYTES_PER_MB];
                while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    outputStream.write(buffer, 0, length);
                    outputStream.flush();
                }
            } catch (Exception e) {
                throw new FileUtilException(e);
            }
        }
    }

    /**
     * create file,full filename,signle empty file.
     *
     * @param fullFilename
     * @return boolean
     */
    public static boolean createFile(final String fullFilename) {
        boolean result = false;
        File file = new File(fullFilename);
        createDirectory(file.getParent());
        try {
            file.setReadable(true, false);
            file.setWritable(true, true);
            result = file.createNewFile();
        } catch (Exception e) {
            throw new FileUtilException(e);
        }
        return result;
    }

    /**
     * create directory
     *
     * @param directoryPath
     */
    public static void createDirectory(final String directoryPath) {
        File file = new File(directoryPath);
        if (!file.exists()) {
            file.setReadable(true, false);
            file.setWritable(true, true);
            file.mkdirs();
        }
    }

    public static class FileUtilException extends RuntimeException {
        private static final long serialVersionUID = 3884649425767533205L;

        public FileUtilException(Throwable cause) {
            super(cause);
        }
    }
}
