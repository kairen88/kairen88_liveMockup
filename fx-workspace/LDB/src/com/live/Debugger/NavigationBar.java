package com.live.Debugger;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class NavigationBar {
	
	Pane naviBar;
	
	double tickNavigatorPosition;
	int numberOfTicks = 30;
    double naviBarHeight = 30.0;
    double naviBarWidth = 200.0;
    int naviBarDefaultPadding = 5;
    
    double segmentHeight;
	double segmentWidth;
	double segmentTickHeight;
    
    //highlighted loop area, should change to array of pair values
    int loopStart = 5;
	int loopEnd = 10;
	
    public NavigationBar() 
    {
    	//creating navi bar background
    	Rectangle barBackground = new Rectangle(naviBarWidth, naviBarHeight);
		javafx.scene.paint.Paint color = javafx.scene.paint.Paint.valueOf("36B541");
		barBackground.setFill(color);
		barBackground.setArcHeight(5);
		barBackground.setArcWidth(5);
		
		HBox timeSegments = constructTimeSegment();
		
		Label naviBarTitle = new Label("MethodName()");
		naviBarTitle.setStyle("-fx-text-fill: #FFFFFF;");

		naviBar = new Pane();
		naviBar.getChildren().addAll(barBackground, timeSegments, naviBarTitle);
		double naviBarTiltlePadding = (naviBarWidth / 2)  - (naviBarTitle.getWidth() / 2);
		naviBarTitle.relocate( naviBarTiltlePadding, 0.0);
		
		double naviBarCalculatedPadding = (naviBarWidth - (segmentWidth * numberOfTicks)) /2;
		timeSegments.relocate(naviBarCalculatedPadding, 0);		
		
	}
    
    public Pane getNaviBarRoot()
    {
    	return naviBar;
    }
    
    public double getSegmentWidth()
    {
    	return segmentWidth;
    }
    
    public double getNaviBarHeight()
    {
    	return naviBarHeight;
    }
	
    
    private HBox constructTimeSegment()
    {
    	//container to hold time segments
		HBox timeSegments = new HBox();
		//initialize segment dimensions
		segmentHeight = naviBarHeight;
		segmentWidth = Math.floor( (naviBarWidth - (2 * naviBarDefaultPadding)) / numberOfTicks );
		segmentTickHeight = naviBarHeight / 3.0;
		
		javafx.scene.paint.Paint color = javafx.scene.paint.Paint.valueOf("36B541");
		
		//create a rectangle for each time segment and a line
		//if the current time segment falls within the range which is in a loop, set color to the highlight color (red)
		//for each time segment (numberOfTicks) add a set to a segment container
		//stack the segments in a HBox container
    	for(int i = 0; i < numberOfTicks; i++)
		{
			Pane segmentContainer = new Pane();			
			
			Rectangle segmentRec = new Rectangle(segmentWidth, segmentHeight);
			javafx.scene.paint.Paint normalColor = color;
			javafx.scene.paint.Paint loopColor = javafx.scene.paint.Paint.valueOf("B023A4");
			
			//check if current time segment is in loop range
			if(i >= loopStart && i <= loopEnd)
				segmentRec.setFill(loopColor);
			else
				segmentRec.setFill(normalColor);
			
			Line segmentTick = new Line(1.0, segmentHeight, 1.0, segmentHeight - segmentTickHeight);
			javafx.scene.paint.Paint naviBarTickColor = javafx.scene.paint.Paint.valueOf("FFFFFF");
			segmentTick.setStroke(naviBarTickColor);
			
			segmentContainer.getChildren().addAll(segmentRec, segmentTick);
			
			timeSegments.getChildren().add(segmentContainer);
		}
    	
    	return timeSegments;
    }

}
