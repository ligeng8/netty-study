package com.it.netty.filetransfer;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest
{
    public static void main(String[] args) throws IOException
    {
        RandomAccessFile raf = new RandomAccessFile("d:/data.txt", "rw");
        Person p = new Person(1001, "xiaoming", 1.80d);
        p.write(raf);// 写入文件后，任意访问文件的指针在文件的结尾

        raf.seek(0);// 读取时，将指针重置到文件的开始位置。
        Person p2 = new Person();
        p2.read(raf);
        System.out.println("id=" + p2.getId() + ";name=" + p2.getName()
                + ";height=" + p2.getHeight());

    }
}
class Person
{
    int id;
    String name;
    double height;
    public Person()
    {
    }
    public Person(int id, String name, double height)
    {
        this.id = id;
        this.name = name;
        this.height = height;
    }
    
    public void write(RandomAccessFile raf) throws IOException
    {
        raf.writeInt(id);
        raf.writeUTF(name);
        raf.writeDouble(height);
    }
    
    public void read(RandomAccessFile raf) throws IOException
    {
        this.id = raf.readInt();
        this.name = raf.readUTF();
        this.height = raf.readDouble();
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public double getHeight()
    {
        return height;
    }
    public void setHeight(double height)
    {
        this.height = height;
    }
    
}