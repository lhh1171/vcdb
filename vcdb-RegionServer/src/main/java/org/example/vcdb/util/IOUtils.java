
package org.example.vcdb.util;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;


public class IOUtils {
  public static void copyBytes(InputStream in, OutputStream out, int buffSize, boolean close) 
    throws IOException {
    try {
      copyBytes(in, out, buffSize);
      if(close) {
        out.close();
        out = null;
        in.close();
        in = null;
      }
    } finally {
      if(close) {
        closeStream(out);
        closeStream(in);
      }
    }
  }
  

  public static void copyBytes(InputStream in, OutputStream out, int buffSize) 
    throws IOException {
    PrintStream ps = out instanceof PrintStream ? (PrintStream)out : null;
    byte buf[] = new byte[buffSize];
    int bytesRead = in.read(buf);
    while (bytesRead >= 0) {
      out.write(buf, 0, bytesRead);
      if ((ps != null) && ps.checkError()) {
        throw new IOException("Unable to write to output stream.");
      }
      bytesRead = in.read(buf);
    }
  }


  /**
   * Copies count bytes from one stream to another.
   *
   * @param in InputStream to read from
   * @param out OutputStream to write to
   * @param count number of bytes to copy
   * @param close whether to close the streams
   * @throws IOException if bytes can not be read or written
   */
  public static void copyBytes(InputStream in, OutputStream out, long count,
      boolean close) throws IOException {
    byte buf[] = new byte[4096];
    long bytesRemaining = count;
    int bytesRead;

    try {
      while (bytesRemaining > 0) {
        int bytesToRead = (int)
          (bytesRemaining < buf.length ? bytesRemaining : buf.length);

        bytesRead = in.read(buf, 0, bytesToRead);
        if (bytesRead == -1)
          break;

        out.write(buf, 0, bytesRead);
        bytesRemaining -= bytesRead;
      }
      if (close) {
        out.close();
        out = null;
        in.close();
        in = null;
      }
    } finally {
      if (close) {
        closeStream(out);
        closeStream(in);
      }
    }
  }
  
  /**
   * Utility wrapper for reading from {@link InputStream}. It catches any errors
   * thrown by the underlying stream (either IO or decompression-related), and
   * re-throws as an IOException.
   * 
   * @param is - InputStream to be read from
   * @param buf - buffer the data is read into
   * @param off - offset within buf
   * @param len - amount of data to be read
   * @return number of bytes read
   */
  public static int wrappedReadForCompressedData(InputStream is, byte[] buf,
      int off, int len) throws IOException {
    try {
      return is.read(buf, off, len);
    } catch (IOException ie) {
      throw ie;
    } catch (Throwable t) {
      throw new IOException("Error while reading compressed data", t);
    }
  }

  /**
   * Reads len bytes in a loop.
   *
   * @param in InputStream to read from
   * @param buf The buffer to fill
   * @param off offset from the buffer
   * @param len the length of bytes to read
   * @throws IOException if it could not read requested number of bytes 
   * for any reason (including EOF)
   */
  public static void readFully(InputStream in, byte buf[],
      int off, int len) throws IOException {
    int toRead = len;
    while (toRead > 0) {
      int ret = in.read(buf, off, toRead);
      if (ret < 0) {
        throw new IOException( "Premature EOF from inputStream");
      }
      toRead -= ret;
      off += ret;
    }
  }
  
  /**
   * Similar to readFully(). Skips bytes in a loop.
   * @param in The InputStream to skip bytes from
   * @param len number of bytes to skip.
   * @throws IOException if it could not skip requested number of bytes 
   * for any reason (including EOF)
   */
  public static void skipFully(InputStream in, long len) throws IOException {
    long amt = len;
    while (amt > 0) {
      long ret = in.skip(amt);
      if (ret == 0) {
        // skip may return 0 even if we're not at EOF.  Luckily, we can 
        // use the read() method to figure out if we're at the end.
        int b = in.read();
        if (b == -1) {
          throw new EOFException( "Premature EOF from inputStream after " +
              "skipping " + (len - amt) + " byte(s).");
        }
        ret = 1;
      }
      amt -= ret;
    }
  }
  

  public static void cleanup(Closeable... closeables) {
    for (Closeable c : closeables) {
      if (c != null) {
        try {
          c.close();
        } catch(IOException ignored) {

        }
      }
    }
  }

  /**
   * Closes the stream ignoring {@link IOException}.
   * Must only be called in cleaning up from exception handlers.
   *
   * @param stream the Stream to close
   */
  public static void closeStream(Closeable stream) {
    cleanup(stream);
  }
  
  /**
   * Closes the socket ignoring {@link IOException}
   *
   * @param sock the Socket to close
   */
  public static void closeSocket(Socket sock) {
    if (sock != null) {
      try {
        sock.close();
      } catch (IOException ignored) {
      }
    }
  }
  
  /**
   * The /dev/null of OutputStreams.
   */
  public static class NullOutputStream extends OutputStream {
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    }

    @Override
    public void write(int b) throws IOException {
    }
  }  
  
  /**
   * Write a ByteBuffer to a WritableByteChannel, handling short writes.
   * 
   * @param bc               The WritableByteChannel to write to
   * @param buf              The input buffer
   * @throws IOException     On I/O error
   */
  public static void writeFully(WritableByteChannel bc, ByteBuffer buf)
      throws IOException {
    do {
      bc.write(buf);
    } while (buf.remaining() > 0);
  }

  /**
   * Write a ByteBuffer to a FileChannel at a given offset, 
   * handling short writes.
   * 
   * @param fc               The FileChannel to write to
   * @param buf              The input buffer
   * @param offset           The offset in the file to start writing at
   * @throws IOException     On I/O error
   */
  public static void writeFully(FileChannel fc, ByteBuffer buf,
      long offset) throws IOException {
    do {
      offset += fc.write(buf, offset);
    } while (buf.remaining() > 0);
  }
}
