package edu.pitt.cs1635.ccc35.proj2;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;


public class DrawActivity extends View {
	protected Paint draw;
	protected Paint draw1;
	protected ArrayList<Combine> surfaceBoard;

	public DrawActivity (Context context, AttributeSet a) {
		super(context, a);
		setMinimumWidth(100);
		setMinimumHeight(100);
		surfaceBoard = new ArrayList<Combine>();
		
		draw= new Paint(Paint.ANTI_ALIAS_FLAG);
		draw1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		
		
		draw.setStyle(Paint.Style.STROKE);
		draw.setStrokeWidth(4);
		draw.setARGB(255, 255, 0, 0);
		draw1.setARGB(255, 255, 0, 0);
	}
	
	public void clearBoard() {
		surfaceBoard.clear();
		this.invalidate();
	}
	
	public void setPaintColor(int color) {
		draw.setColor(color);
		draw1.setColor(color);
		this.invalidate();
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		for(int i = 0; i < surfaceBoard.size()-1; ++i) {
			Combine cur = surfaceBoard.get(i);
			Combine next = surfaceBoard.get(i+1);
			
			if(cur.combinePoint && next.sigEnd) {
				canvas.drawCircle(cur.x, cur.y, 4.0f, draw1);
				i++;
			}
		}
		
	    Path p= new Path();
	    ArrayList<Path> paths = new ArrayList<Path>();
	    boolean first = true;
	    
	    for(Combine c : surfaceBoard){
	        if(first || c.combinePoint){
	        	if(!first) {
	        		paths.add(p);
	        		p = new Path();
	        	}
	            first = false;
	            p.moveTo(c.x, c.y);
	        }
	        else{
	            p.lineTo(c.x, c.y);
	        }
	    }
	    paths.add(p);
		
	    for(Path path : paths) {
	    	canvas.drawPath(path, draw);
	    }
	}

}
