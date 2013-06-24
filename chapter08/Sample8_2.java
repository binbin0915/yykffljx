package pkg;
class MyThread implements Runnable
{
	@Override
	public void run()
	{
		System.out.println("本类是通过实现Runnable接口创建的线程");
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