package com.hbyd.parks.guava;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Using the Files class to help with common tasks such as moving or copying
 * files, or reading the lines of a file into a list of strings
 *
 * 测试： com.google.common.io.Files
 * JDK 7 也提供了 Files, Guava 的 Files 标注有 @Beta, 表示未来的 Guava 可能会有变动
 */
public class FilesTest {

    private File originalFile = new File("D:/test/original.txt");
    private File copyFile = new File(originalFile.getParent(), "copy.txt");

    @Before
    public void init(){
        if(!originalFile.exists()){
            System.out.println("测试前请先准备测试文件" + originalFile.getAbsolutePath());
        }
    }

    /**
     * 注意：如果将测试源文件置于 classpath 下， maven 在编译时可能会忽略
     * 因此，这里使用不在 classpath 下的绝对路径的文件进行测试
     */
    @Test
    public void testCopyAndMove(){
        File newFile = new File(originalFile.getParent(), "newFile");

        try {
            Files.copy(originalFile, copyFile);
            Assert.assertTrue(copyFile.exists());

            Files.move(copyFile, newFile);
            Assert.assertTrue(newFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 重命名操作：本质就是文件的移动
     */
    @Test
    public void testFilesReadLines() throws IOException {
//      readLines():
//          Reads all of the lines from a file. The lines do not include line-termination
//          characters, but do include other leading and trailing whitespace.
//          每行的换行符被去掉，但保留其他的空白字符

//        List<String> strings = Files.readLines(originalFile, Charset.defaultCharset());
        List<String> strings = Files.readLines(originalFile, Charsets.UTF_8);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    /**
         There is another version of Files.readLines method that takes the LineProcessor
         instance as an additional argument. Each line is fed to the LineProcessor.processLine
         method, which returns a boolean. Lines from the file will continue to be streamed
         to the LineProcessor instance until the file is exhausted or the LineProcessor.
         processLine method returns false.
     */
    @Test
    public void testFilesReadLinesWithLineProcessor() throws IOException {
        File csvFile = new File("D:/test/test.csv");

//      readLines():
//        Streams lines from a File, stopping when our callback returns
//        false, or we have read all of the lines.
        List<String> titles = Files.readLines(csvFile, Charsets.UTF_8, new LineProcessor<List<String>>() {

            /**
             * 书名列表
             */
            private List<String> titles = new ArrayList<String>();

            /**
             * 书名在 csv 中的列索引，从 0 开始
             */
            private int titleIndex = 1;

            /**
             * 逗号分割的 Splitter
             */
            private Splitter commaSplitter = Splitter.on(',');

            @Override
            public boolean processLine(String line) throws IOException {
                String title = Iterables.get(commaSplitter.split(line), titleIndex);
                titles.add(title);

                return true;//为了获取所有的书名，LineProcessor 不能停止，因此这里始终返回 true
            }

            @Override
            public List<String> getResult() {
                return titles;
            }
        });

        for (String title : titles) {
            System.out.println(title);
        }
    }

    @Test
    public void testFilesHash() throws IOException {
//        hash(file, hashFunction)

        HashCode md5 = Files.hash(originalFile, Hashing.md5());
        System.out.println(md5);

        HashCode sha1 = Files.hash(originalFile, Hashing.sha1());
        System.out.println(sha1);

        HashCode crc = Files.hash(originalFile, Hashing.crc32());
        System.out.println(crc);
    }

    /**
     The Files class offers convenience methods for writing/appending to a file or
     reading the contents of a file into a byte array. Most of these become one-liners
     with the opening and closing of resources being taken care of for us.
     */
    @Test
    public void testFilesWrite() throws IOException {
        String strContent = "to be or not to be";

        File file = new File("D:/test/writeDemo.txt");
        file.deleteOnExit();// ensuring the file is deleted when the JVM exits

        Files.write(strContent, file, Charsets.UTF_8);
        Assert.assertEquals(strContent, Files.toString(file, Charsets.UTF_8));

        String strToAppend = ", that's a question";
        Files.append(strToAppend, file, Charsets.UTF_8);

        Assert.assertEquals(strContent + strToAppend, Files.toString(file, Charsets.UTF_8));

        String overwrite = "content to be overwritten!";
        Files.write(overwrite, file, Charsets.UTF_8);
        Assert.assertEquals(overwrite, Files.toString(file, Charsets.UTF_8));
    }



}
