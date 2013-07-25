package edu.pitt.cs1635.ccc35.proj2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;



public class setColor extends Activity {
	private Button red;
	private Button green;
	private Button black;
	private Button blue;
	
	private Button yellow; 
	private Button cyan; 
	private Button white; 
	private Button gray;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_pick);
		
		red = (Button)findViewById(R.id.red);
		green = (Button)findViewById(R.id.green); 
		
		black = (Button)findViewById(R.id.black);
		blue = (Button)findViewById(R.id.blue);
		
		yellow = (Button)findViewById(R.id.yellow);
		cyan = (Button)findViewById(R.id.cyan);
		
		white= (Button)findViewById(R.id.white);
		gray = (Button)findViewById(R.id.gray);
		
		
		red.setBackgroundColor(Color.RED);
		green.setBackgroundColor(Color.GREEN);
		black.setBackgroundColor(Color.BLACK);
		blue.setBackgroundColor(Color.BLUE);
		
		
		yellow.setBackgroundColor(Color.YELLOW);
		cyan.setBackgroundColor(Color.CYAN);
		white.setBackgroundColor(Color.WHITE);
		gray.setBackgroundColor(Color.GRAY);
		
	}

	
	public void setYellow(View v) {
		setResult(Color.YELLOW);
		this.finish();
	}

	public void setCyan(View v) {
		setResult(Color.CYAN);
		this.finish();
	}
	
	public void setWhite(View v) {
		setResult(Color.WHITE);
		this.finish();
	}
	
	public void setGray(View v) {
		setResult(Color.GRAY);
		this.finish();
	}
	
	
	public void setRed(View v) {
		setResult(Color.RED);
		this.finish();
	}

	public void setGreen(View v) {
		setResult(Color.GREEN);
		this.finish();
	}
	
	public void setBlack(View v) {
		setResult(Color.BLACK);
		this.finish();
	}
	
	public void setBlue(View v) {
		setResult(Color.BLUE);
		this.finish();
	}
	
	public void cancelColor(View v) {
		this.finish();
	}

}
