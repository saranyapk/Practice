package com.saranyapk.practice.corejava.thread;

public class ThreadPractice
{
	private static volatile Boolean isEvenPrinted = false;

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
			for ( int i = 0; i < 10; i = i + 2 )
			{
				synchronized (lock)
				{
					lock.notify();

					if ( !isEvenPrinted )
					{
						System.out.println( i );

						isEvenPrinted = true;
					}

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
			for ( int i = 1; i < 10; i = i + 2 )
			{
				synchronized (lock)
				{
					lock.notify();

					if ( isEvenPrinted )
					{
						System.out.println( i );

						isEvenPrinted = false;
					}

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
}
