package pkg;
class Resource
{
	synchronized void function(Thread currThread)
	{
		System.out.println(currThread.getName()+
		"�߳�ִ��synchronized����ķ�����");
		try
		{
			Thread.sleep(2000);
			System.out.println(currThread.getName()
			+"�߳�˯���ˡ�");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
class MyThread extends Thread
{
	Resource res;
	String tName;
	public MyThread(String tName,Resource res)
	{
		this.setName(tName);
		this.tName=tName;
		this.res=res;
	}
	public void run()
	{
		if(tName.equals("Thread1"))
		{
		     res.function(this);			
		     System.out.println("Thread2����������ͬ��������");
		}
	}
}
public class Sample8_6
{
	public static void main(String args[])
	{
         Resource rs=new Resource();
         MyThread t1=new MyThread("Thread1",rs);
         MyThread t2=new MyThread("Thread2",rs);
         t1.start();
		 try
		 {
			Thread.sleep(10);
	 	 }
		 catch(Exception e)
		 {
			e.printStackTrace();
		 }         
         t2.start();
	}
}