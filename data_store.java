/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arvin
 */
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.JSONValue;
import org.json.simple.parser.*; 

public class data_store 
{
    File f;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    
    public data_store(String s) throws Exception
    {
        f=new File(s);
        BufferedWriter out = new BufferedWriter(new FileWriter(f)); 
        out.close();
    }
    
    int create(String key,JSONObject val) throws Exception
    {        
        writeLock.lock();
        try{
            BufferedReader in = new BufferedReader(new FileReader(f));
            String s;
            while((s=in.readLine())!=null)
            {
                String ab[]=s.split("#");
                //System.out.println(ab[0]);
                if(ab[0].equals(key))
                {
                    System.out.println("Error:  Key aldready exists");
                    return 0;
                }
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(f,true));
            out.write(key+"#"+val.toJSONString().replace('\n','$')+"\n");
            out.close();
            //System.out.println(2);
        }finally{
            writeLock.unlock();
        }
        
        return 1;

    }
    
    JSONObject read(String key) throws Exception
    {
        readLock.lock();
        try{
            BufferedReader in = new BufferedReader(new FileReader(f)); 
            String s;
            while((s=in.readLine())!=null)
            {
                String ab[]=s.split("#");
                //System.out.println(ab[0]);
                if(ab[0].equals(key))
                {
                    String ans=ab[1].replace('$','\n');
                    JSONObject z=(JSONObject)new JSONParser().parse(ans);
                    System.out.println(z.toJSONString());
                    return z;
                }
            }
            System.out.println("Error: No key found");
        }finally{
            readLock.unlock();
        }
        return null;
    }
    
    int delete(String key) throws Exception
    {
        writeLock.lock();
        try{
            File tempFile = new File("D://myTempFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentLine;
            while((currentLine = reader.readLine()) != null) 
            {
                String trimmedLine = currentLine.split("#")[0];
                if(trimmedLine.equals(key)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close(); 
            reader.close();
            tempFile.renameTo(new File("D://tmp.txt"));
        }finally{
            writeLock.unlock();
        }
        return 1;
    }
    
}

