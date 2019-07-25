
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

public class data_store 
{
    File f;
    public data_store(String s) throws Exception
    {
        f=new File(s);
        BufferedWriter out = new BufferedWriter(new FileWriter(f)); 
        out.close();
    }
    
    int create(String key,JSONObject val) throws Exception
    {        
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
        
        return 1;

    }
    
    JSONObject read(String key) throws Exception
    {
        BufferedReader in = new BufferedReader(new FileReader(f)); 
        String s;
        while((s=in.readLine())!=null)
        {
            String ab[]=s.split("#");
            //System.out.println(ab[0]);
            if(ab[0].equals(key))
            {
                String ans=ab[1].replace('$','\n');
                JSONObject jo=(JSONObject) new JSONParser().parse(ans);
                return jo;
            }
        }
        System.out.println("Error: No key found");
        return null;
    }
    
    int delete(String key) throws Exception
    {
        File tempFile = new File("myTempFile.txt");

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
        boolean successful = tempFile.renameTo(f);
        return 1;
    }
    
}
