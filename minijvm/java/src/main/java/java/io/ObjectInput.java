/* Copyright (c) 2008-2015, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.io;

public interface ObjectInput {
  public int available();
  public void close();
  public void read();
  public void read(byte[] b);
  public void read(byte[] b, int off, int len);
  public Object readObject();
  public int readInt();
  public long readLong();
  public byte readByte();
  public int readUnsignedByte();
  public char readChar();
  public short readShort();
  public int readUnsignedShort();
  public float readFloat();
  public double readDouble();
  public String readLine();
  public String readUTF();
  public long skip(long n);
}
