package pkg;
class MyRunnable implements Runnable
{
	private String flagl;
	private String flagr;
	public MyRunnable(String flagl,String flagr)
	{
		this.flagl=flagl;
		this.flagr=flagr;
	}	
	public void run()
	{
		for(int i=0;i<50;i++)
		{
			System.out.print(flagl+i+flagr);
			Thread.yield();
		}
	}
}
class MyThread extends Thread
{
	private String str1;
	private String str2;
	public MyThread(String str1,String str2)
	{
		this.str1=str1;
		this.str2=str2;
	}
	public void run()
	{
		for(int i=0;i<50;i++)
		{
			System.out.print(str1+i+str2);
			Thread.yield();
		}
	}
}
public class Sample8_5
{
	public static void main(String[] args)
	{
		MyRunnable mr1=new MyRunnable("[","] ");
		MyThread t2=new MyThread("<","> ");
		Thread t1=new Thread(mr1);
		t1.start();	
		t2.start();	
	}
}