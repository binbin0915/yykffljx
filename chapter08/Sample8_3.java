package pkg;
class runnable1 implements Runnable
{
	public void run()
	{
		for(int i=0;i<5;i++)
		{
			System.out.println(""+i+" 我是实现Runnable接口的线程");
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e) 
			{
				e.printStackTrace();
			}			
		}
	}
}
class runnable2 implements Runnable
{
	public void run()
	{
		for(int i=0;i<5;i++)
		{
			System.out.println(""+i+" 我是实现Runnable接口的另一个线程");
			try
			{
				Thread.sleep(100);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}			
		}
	}
}
public class Sample8_3
{
	public static void main(String[] args)
	{
		runnable1 mr1=new runnable1();
		runnable2 mr2=new runnable2();
		Thread t1=new Thread(mr1);
		Thread t2=new Thread(mr2);
		t1.start();
		try
		{
			Thread.sleep(5);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		t2.start();
	}
}