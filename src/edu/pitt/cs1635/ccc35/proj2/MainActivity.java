package edu.pitt.cs1635.ccc35.proj2;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;


import edu.pitt.cs1635.ccc35.proj2.DrawActivity;


public class MainActivity extends Activity {
	private AlertDialog.Builder signal;
public static final int COLORCHOICE = 12939;

	private TextView text;
	
	

	private Intent setColorIntent;
	private boolean checkSubmit;
	
	private DrawActivity surface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		signal = new AlertDialog.Builder(this);
		text = (TextView) findViewById(R.id.sText); 
		surface = (DrawActivity)findViewById(R.id.view1);
		checkSubmit = false;
		setColorIntent = new Intent(this.getApplicationContext(), setColor.class);
	     
		surface.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					
					surface.surfaceBoard.add(new Combine(x,y, true, false));
				}
				else if(event.getAction() == MotionEvent.ACTION_UP) {
					surface.surfaceBoard.add(new Combine(x,y, false, true));
				}
				else {
					surface.surfaceBoard.add(new Combine(x,y));
				}
				
				surface.invalidate();
				return true;
			} });
		
		
	}
	public void clearInput(View v) {
		surface.clearBoard();
	}
	

	
	@SuppressLint("NewApi")
	public void submitInput(View v) {
		if(surface.surfaceBoard.isEmpty()) {
			showMessage(getString(R.string.errorWrite));
			return;
		}
		checkSubmit = false;
        final ProgressDialog progress = ProgressDialog.show(this, "", getString(R.string.sendVal), true);
        new Thread() {
			public void run() {
			    try {
			    	while(!checkSubmit) {
			    		sleep(500);
			    	}
			    	sleep(1000);
				}
			    catch (Exception e) { }

			    progress.dismiss();
			}
		}.start();
		
		class PostHandler extends AsyncTask<URL, Void, String> {
			private HttpResponse response = null;
			
			protected void onPostExecute(String result) {
				if(response != null) {
					try {
						showMessage("Retrieved: " + EntityUtils.toString(response.getEntity()));
					} catch (ParseException e) {
						
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				else {
					showMessage("Error Unable to Retrieve!");
				}
			}
			
			
			// This will get in background for server
			protected String doInBackground(URL... params) {
				String apikey = "11773edfd643f813c18d82f56a8104ed";
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost("http://cwritepad.appspot.com/reco/gb2312");
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				
				nameValuePairs.add(new BasicNameValuePair("key", apikey));
				
				nameValuePairs.add(new BasicNameValuePair("q", getEnc()));
				
			    try {
		            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		            response = client.execute(post);
			    }
			    catch(Exception e) {
			    	checkSubmit = true;
			    	return "";
			    }
				
				checkSubmit = true;
				return "";
			}
		}
		
		
		new PostHandler().execute();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == COLORCHOICE && resultCode != 0) {
			surface.setPaintColor(resultCode);
		}
	}
	
	public void setColor(View v) {
		startActivityForResult(setColorIntent, COLORCHOICE);
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadFile(View v) {
		try {
			FileInputStream out = openFileInput(getString(R.string.saveLoc));
			ObjectInputStream obj = new ObjectInputStream(out);
			surface.surfaceBoard= (ArrayList<Combine>)obj.readObject();
			obj.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		surface.invalidate();
		showMessage(getString(R.string.loadMessage));
	}
	
	public void saveFile(View v) {
		try {
			FileOutputStream out = openFileOutput(getString(R.string.saveLoc), Context.MODE_PRIVATE);
			ObjectOutputStream obj = new ObjectOutputStream(out);
			obj.writeObject(surface.surfaceBoard);
			obj.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		showMessage(getString(R.string.saveMessage));
	}

    private void showMessage(String s) {
    	signal.setMessage(s);
    	signal.show();
    }
	


	public String getEnc() {
		String enc = "[";
		boolean bFirst = true;
		for(Combine c : surface.surfaceBoard) {
			if(bFirst) {
				enc += getEncForPair(c, surface.getMeasuredWidth(), surface.getMeasuredHeight());
			}
			else  {
				enc += ", " + getEncForPair(c, surface.getMeasuredWidth(), surface.getMeasuredHeight());
			}
			bFirst = false;
		}
		if(!enc.equals("["))
			enc += ", 255, 255";
		enc += "]";
		return enc;
	}
	
	private static String getEncForPair(Combine c, float max_w, float max_h) {
		return (int)((c.x/max_w)*254.0f) + ", " + (int)((c.y/max_h)*254.0f) + (c.sigEnd ? ", 255, 0" : "");
	}
		
		
		
	}

	


