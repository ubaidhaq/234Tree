package trees;

import java.util.*;
import java.io.*;
import java.lang.*;

public class Filing 
{
    
    //For reading from the same file 
    ////////////////////////////////////////////////////////////////////////
    private Scanner y;
    
    public void open_File()
    {
        try
        {
            y=new Scanner(new File("Sample.txt"));
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }
    }
    
    public void readFile()
    {
        while (y.hasNext())
        {
            int a=y.nextInt();
            System.out.println(a);
        }
    }
    
    public void close_File()
    {
        y.close();
    }
    ////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) 
    {
      
        
        //Reading from the file 
        Filing obj2=new Filing();
        obj2.open_File();
        obj2.readFile();
        obj2.close_File();
    }
}