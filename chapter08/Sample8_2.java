package pkg;
class MyThread implements Runnable
{
	@Override
	public void run()
	{
		System.out.println("������ͨ��ʵ��Runnable�ӿڴ������߳�");
	}
}
public class Sample8_2
{
	public static void main(String[] args)
	{
		MyThread mt=new MyThread();
		Thread td=new Thread(mt);
		td.start();
	}
}