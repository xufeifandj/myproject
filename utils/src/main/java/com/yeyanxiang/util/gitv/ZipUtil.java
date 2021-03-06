package com.yeyanxiang.util.gitv;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipUtil {

    private static final String TAG = "ZipUtil";
    private static byte[] buffer = new byte[1024 * 10];

    private ZipUtil() {
        // empty
    }

    private static void close(Closeable... closeables) throws IOException {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        }
    }

    /**
     * 关闭一个或多个流对象
     *
     * @param closeables 可关闭的流对象列表
     */
    private static void closeQuietly(Closeable... closeables) {
        try {
            close(closeables);
        } catch (IOException e) {
            e.printStackTrace();
            // do nothing
        }
    }

    /**
     * 压缩文件
     *
     * @param filePath 待压缩的文件路径
     * @return 压缩后的文件
     */
    public static File zip(String filePath) {
        Log.i(TAG, "zip start " + filePath);
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            String soureName = source.getName();
            if (soureName.endsWith(".zip")) {
                Log.e(TAG, "zip " + soureName + " already zip");
                return source;
            }
            int pos = soureName.lastIndexOf(".");
            if (pos > 0) {
                soureName = soureName.substring(0, pos);
            }
            String zipName = soureName + ".zip";
            target = new File(source.getParent(), zipName);
            if (target.exists()) {
                Log.i(TAG, "zip " + zipName + " is exists");
                Log.i(TAG, "zip delete " + target.delete());
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                addEntry("/", source, zos);
                Log.i(TAG, "zip succ " + target.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                closeQuietly(zos, fos);
//                source.delete();
            }
        }
        return target;
    }

    /**
     * 扫描添加文件Entry
     *
     * @param base   基路径
     * @param source 源文件
     * @param zos    Zip文件输出流
     * @throws IOException
     */
    private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
        String entry = base + source.getName();
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                addEntry(entry + "/", file, zos);
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read = 0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                closeQuietly(bis, fis);
            }
        }
    }

    /**
     * 解压文件
     *
     * @param filePath 压缩文件路径
     */
    public static void unzip(String filePath) {
        File source = new File(filePath);
        if (source.exists()) {
            ZipInputStream zis = null;
            BufferedOutputStream bos = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source));
                ZipEntry entry = null;
                while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                    File target = new File(source.getParent(), entry.getName());
                    if (!target.getParentFile().exists()) {
                        target.getParentFile().mkdirs();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(target));
                    int read = 0;
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    bos.flush();
                }
                zis.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                closeQuietly(zis, bos);
            }
        }
    }
}