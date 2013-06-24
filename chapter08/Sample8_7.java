package pkg;
class BreadContainer
{
	public static final int maxNum=300;
	private int num;
	public BreadContainer()
	{	}
	public BreadContainer(int num)
	{
		this.num=num;
	} 
	public synchronized void produceBread(int produceNum,String producerName)
	{
		while(num+produceNum>maxNum)
		{
			System.out.println(producerName+"要生产"+produceNum+"个，当前"
			+num+"个，资源充足，不需要生产，"+producerName+"去等待！");
			try
			{
				wait();
			}
			catch(Exception e)
			{	
			    e.printStackTrace();
			}
		}
		num=num+produceNum;
		System.out.println(producerName+"生产了"
						+produceNum+"个，现在有"+num+"个。");
		notifyAll();
	}
	public synchronized void consumeBread(int consumeNum,String consumerName)
	{
		while(consumeNum>num)
		{
			System.out.println(consumerName+"要消费"+consumeNum+
				"个，由于现在只有"+num+"个，"+consumerName+"于是去等待！");
			try
			{
				wait();
			}
			catch(Exception e)
			{	
			    e.printStackTrace();
			}
		}
		num=num-consumeNum;
		System.out.println(consumerName+"消费了"
		      +consumeNum+"个，现在还剩下"+num+"个");
		this.notifyAll();
	}
}
class Producer extends Thread
{
	private int produceNum;
	private BreadContainer bc;
	public Producer()
	{	}
	public Producer(int produceNum,BreadContainer bc,String producerName)
	{
		this.produceNum=produceNum;
		this.bc=bc;
		this.setName(producerName);
	}
	public void run()
	{
		bc.produceBread(produceNum,this.getName());
	}	
}
class Consumer extends Thread
{
	private int consumeNum;
	private BreadContainer bc;
	public Consumer()
	{	}
	public Consumer(int consumeNum,BreadContainer bc,String consumerName)
	{
		this.consumeNum=consumeNum;
		this.bc=bc;
		this.setName(consumerName);
	}	
	public void run()
	{
		bc.consumeBread(consumeNum,this.getName());
	}
}
public class Sample8_7
{
	public static void main(String args[])
	{
		BreadContainer bc=new BreadContainer(50);
		Producer p1=new Producer(50,bc,"P1");
		Producer p2=new Producer(200,bc,"P2");
		Producer p3=new Producer(290,bc,"P3");
		Consumer c1=new Consumer(70,bc,"c1");
		Consumer c2=new Consumer(80,bc,"c2");
		c1.start();
		c2.start();
		p1.start();
		p3.start();
		p2.start();
	}
}