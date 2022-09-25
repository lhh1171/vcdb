package org.example.vcdb.store.file;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName VCFIleWritter
 * @Description TODO
 * @Author lqc
 * @Date 2022/9/24 下午9:06
 * @Version 1.0
 */

public class VCFIleWriter {
    public static void setRegionServerMeta(byte[] content,String fileName){
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("/x2/vcdb/regionServerMeta", "rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocateDirect(1024);
            //读操作
            int bytesRead = fileChannel.read(buf);
            //读写切换
            buf.flip();
            System.out.println(bytesRead);
            System.out.println("-------------------");
//            while (bytesRead != -1) {
////                while (buf.position() < 3) {
////                    System.out.print((char) buf.get());
////                }
////                System.out.println();
////                buf.compact();
////                bytesRead = fileChannel.read(buf);
////                System.out.println(bytesRead);
//
//            }
            while (buf.position()!=bytesRead){
                byte b = buf.get();
                System.out.println(b);
                System.out.println((char) b);
            }
            System.out.println("-------------------");
//            System.out.print((char) buf.get());
            for (int i = 0; i < 9; i++) {
                System.out.print(buf.get(11));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setRegionMeta(byte[] content,String fileName){

    }
    public static void setFileStoreMeta(byte[] content,String fileName){

    }
    public static void setFileStore(byte[] content,String fileName){

    }
    public static void setFileStorePage(byte[] content,int pageIndex,String fileName){

    }
    public static void appendDataSetToFileStorePage(byte[] DataSet,int pageIndex,String fileName){

    }
    public static void setPageLength(int Length,int pageIndex,String fileName){

    }
}
