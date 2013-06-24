package pkg;
class MyThread extends Thread
{
	@Override
	public void run()
	{
		System.out.println("本类是通过继承Thread创建的线程");
	}
}
public class Sample8_1
{
	public static void main(String[] args)
	{
		MyThread mt=new MyThread();
		mt.start();
	}
}