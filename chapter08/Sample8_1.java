package pkg;
class MyThread extends Thread
{
	@Override
	public void run()
	{
		System.out.println("������ͨ���̳�Thread�������߳�");
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