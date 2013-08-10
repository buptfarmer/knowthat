package com.Ving.knowthat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Locale;

import com.Ving.knowthat.util.Client;
import com.Ving.knowthat.util.Server;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TalkActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	public static String TAG_NET="net";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_talk);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.talk, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		private Fragment[] fragments=null; 
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			fragments = new DummySectionFragment[3];
			for(int i=0;i<fragments.length;i++){
				fragments[i]= new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragments[i].setArguments(args);
			}
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
//			Fragment fragment = new DummySectionFragment();
//			Bundle args = new Bundle();
//			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//			fragment.setArguments(args);
			return fragments[position];
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = null;
			int key = getArguments().getInt(ARG_SECTION_NUMBER);
			switch (key) {
			case 1:
				//welcome page
				rootView = inflater.inflate(R.layout.fragment_welcome,
						container, false);
				Button server_start =(Button) rootView.findViewById(R.id.server_start);
				Button server_stop =(Button) rootView.findViewById(R.id.server_stop);
				Button client_start =(Button) rootView.findViewById(R.id.client_start);
				Button client_stop =(Button) rootView.findViewById(R.id.client_stop);
				server_start.setOnClickListener(welcomeListener);
				server_stop.setOnClickListener(welcomeListener);
				client_start.setOnClickListener(welcomeListener);
				client_stop.setOnClickListener(welcomeListener);
				break;
			case 2:
				//server page
				rootView = inflater.inflate(R.layout.fragment_server,
						container, false);
				serverText = (TextView) rootView.findViewById(R.id.conversation_text);
				serverEditText = (EditText) rootView.findViewById(R.id.edit_text);
				serverSend = (Button) rootView.findViewById(R.id.send);
				serverSend.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.d(TAG_NET,"server send clicked!");
						DataOutputStream out;
						try {
							out = new DataOutputStream(serverSocket.getOutputStream());
							out.writeUTF(serverEditText.getText().toString());
							
							Bundle b = new Bundle();
							b.putString("message", serverEditText.getText().toString());
							Message sendMessage = Message.obtain(
									serverHandler, 1);
							sendMessage.setData(b);
							sendMessage.sendToTarget();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.d(TAG_NET, "test server");
								while (true) {
									try {
										DataInputStream in = new DataInputStream(
												serverSocket.getInputStream());
										String msg = in.readUTF();
										// serverText.setText("server received :"+msg);
										Log.d(TAG_NET, "server received :"
												+ msg);

										Bundle b = new Bundle();
										b.putString("message", msg);
										Message sendMessage = Message.obtain(
												serverHandler, 1);
										sendMessage.setData(b);
										sendMessage.sendToTarget();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}).start();
					}
				});

				
//				TextView dummyTextView = (TextView) rootView
//						.findViewById(R.id.section_label);
//				dummyTextView.setText(Integer.toString(getArguments().getInt(
//						ARG_SECTION_NUMBER)));
				break;
			case 3:
				//client page
				rootView = inflater.inflate(R.layout.fragment_client,
						container, false);
				clientText = (TextView) rootView.findViewById(R.id.conversation_text);
				clientEditText = (EditText) rootView.findViewById(R.id.edit_text);
				clientSend = (Button) rootView.findViewById(R.id.send);
				clientSend.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.d(TAG_NET,"client send clicked!");
						DataOutputStream out;
						try {
							out = new DataOutputStream(clientSocket.getOutputStream());
							out.writeUTF(clientEditText.getText().toString());
							Bundle b = new Bundle();
							b.putString("message", clientEditText.getText().toString());
							Message sendMessage = Message.obtain(
									clientHandler, 1);
							sendMessage.setData(b);
							sendMessage.sendToTarget();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.d(TAG_NET, "test client");
								while (true) {
									try {
										DataInputStream in = new DataInputStream(
												clientSocket.getInputStream());
										String msg = in.readUTF();
										// clientText.setText("server received :"+msg);
										Log.d(TAG_NET, "server received :"
												+ msg);

										Bundle b = new Bundle();
										b.putString("message", msg);
										Message sendMessage = Message.obtain(
												clientHandler, 1);
										sendMessage.setData(b);
										sendMessage.sendToTarget();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}).start();
					}
				});

				break;
			default:
				break;
			}
			return rootView;
		}
		private View.OnClickListener welcomeListener = new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String host = "localhost";// 默认连接到本机
				final int port = 8189;
				switch (v.getId()) {
				case R.id.server_start:
//					server = new Server();
//					server.service2();
					serverHandler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							super.handleMessage(msg);
							switch (msg.what) {
							case 1:
								
								if(msg.getData() == null){
									Log.d(TAG_NET,"msg.getData() == null" );
								}else if(msg.getData().getString("message") == null){
									Log.d(TAG_NET,"msg.getData().getString(\"message\") == null" );
									if(serverText == null){

										Log.d(TAG_NET,"serverText == null" );
										break;
									}
									serverText.append("msg.getData().getString(\"message\") == null");
								}else {
									
									serverText.append(msg.getData().getString("message"));
								}
								
								break;
							}
						}
					};
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								Log.d(TAG_NET,"server started!");
								server = new ServerSocket(port);
								serverSocket = server.accept();
								DataOutputStream out = new DataOutputStream(
										serverSocket.getOutputStream());
								out.writeUTF("服务器：" + "hello");
								Log.d(TAG_NET,"服务器：" + "hello");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}).start();
					break;


				case R.id.client_start:
					clientHandler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							super.handleMessage(msg);
							switch (msg.what) {
							case 1:
								if(msg.getData() == null){
									Log.d(TAG_NET,"msg.getData() == null" );
								}else if(msg.getData().getString("message") == null){
									Log.d(TAG_NET,"msg.getData().getString(\"message\") == null" );
									if(clientText == null){

										Log.d(TAG_NET,"clientText == null" );
										break;
									}
									clientText.append("msg.getData().getString(\"message\") == null");
								}else {
									clientText.append("\n"+msg.getData().getString("message"));
								}
								break;
							}
						}
					};
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {

								Log.d(TAG_NET,"client started!");
								clientSocket = new Socket(host, port);
								DataOutputStream out = new DataOutputStream(
										clientSocket.getOutputStream());
								
								out.writeUTF("client：" + "world");
								Log.d(TAG_NET,"client：" + "world");
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
						}
					}).start();
//					client = new Client();
//			        client.chat2();  
					break;

				case R.id.server_stop:
				case R.id.client_stop:
//					server.stop();
//					client.stop();
					break;

				default:
					break;
				}
			}
		};
		private static Handler serverHandler ;
		private static Handler clientHandler ;
		private static ServerSocket server;
		private static Socket serverSocket;
		private static Client client;
		private static Socket clientSocket;
		
		private static TextView serverText;
		private static EditText serverEditText;
		private static Button serverSend;

		private static TextView clientText;
		private static EditText clientEditText;
		private static Button clientSend;
	}

}
