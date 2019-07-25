import java.data_store;
public class test
{
	public static void main(String args[]) throws Exception
	{
			Scanner sc=new Scanner(System.in);
			data_store t=new data_store("D://tmp.txt");
			for(int i=0;i<4;i++)
			{
				String a=sc.nextLine();
				t.create(a,(JSONObject)new JSONParser().parse(new FileReader("D://qw.json")));
			}
			t.read("a");
			t.read("c");
			t.delete("a");
			t.read("a");
			t.delete("q");
		}
}