package pkg;
class runnable1 implements Runnable
{
	@Override
	public void run()
	{
		for(int i=0;i<=10;i++)
		{
			System.out.println(""+i+"[runnable1]");
		}		
	}
}
class runnable2 implements Runnable
{
	@Override
	public void run()
	{
		for(int i=0;i<=10;i++)
		{
			System.out.println(""+i+"[runnable2]");
		}	
	}
}
public class Sample8_4
{
	public static void main(String[] args)
	{
		runnable1 mr1=new runnable1();
		runnable2 mr2=new runnable2();
		Thread t1=new Thread(mr1);
		Thread t2=new Thread(mr2);
		t1.start();
		t2.start();		
	}
}