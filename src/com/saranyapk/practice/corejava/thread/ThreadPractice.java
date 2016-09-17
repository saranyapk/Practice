package com.saranyapk.practice.corejava.thread;

public class ThreadPractice
{
	private static volatile Boolean isEvenStarted = false;

	public static void main( String args[] )
	{
		Object lock = new Object();

		Thread even = new Thread( new ThreadPractice().new EvenThread( lock ) );
		Thread odd = new Thread( new ThreadPractice().new OddThread( lock ) );

		even.start();
		odd.start();

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
			synchronized (lock)
			{
				isEvenStarted = true;
				
				lock.notify();
			}

			for ( int i = 0; i < 10; i = i + 2 )
			{
				synchronized (lock)
				{
					try
					{
						lock.notify();

						System.out.println( i );

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
			synchronized (lock)
			{
				while ( !isEvenStarted )
				{
					try
					{
						lock.wait();
					}
					catch ( InterruptedException e )
					{
						e.printStackTrace();
					}
				}
			}
			for ( int i = 1; i < 10; i = i + 2 )
			{
				synchronized (lock)
				{
					try
					{

						lock.notify();

						System.out.println( i );

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
}
