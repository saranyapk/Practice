package com.saranyapk.practice.corejava.thread;

public class ThreadPractice
{
	public static void main( String args[] )
	{
		Object lock = new Object();

		Thread even = new Thread( new EvenThread( lock ) );
		Thread odd = new Thread( new OddThread( lock ) );

		even.start();
		odd.start();
	}
}

class EvenThread implements Runnable
{
	Object lock;

	public EvenThread( Object lock )
	{
		this.lock = lock;
	}

	@Override
	public void run()
	{
		for ( int i = 0; i < 10; i = i + 2 )
		{
			synchronized (lock)
			{
				lock.notify();

				System.out.println( i );

				try
				{
					if ( i < 10 )
					{
						lock.wait();
					}
				}
				catch ( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
		}
	}

}

class OddThread implements Runnable
{
	Object lock;

	public OddThread( Object lock )
	{
		this.lock = lock;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep( 500 );
		}
		catch ( InterruptedException e1 )
		{
			e1.printStackTrace();
		}
		for ( int i = 1; i < 10; i = i + 2 )
		{
			synchronized (lock)
			{
				lock.notify();

				System.out.println( i );

				try
				{
					if ( i < 9 )
					{
						lock.wait();
					}
				}
				catch ( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
		}
	}

}
