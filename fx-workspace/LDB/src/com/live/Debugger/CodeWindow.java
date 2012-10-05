package com.live.Debugger;

import java.awt.Paint;

import com.sun.javafx.scene.layout.region.Margins.Converter;
import com.sun.org.apache.bcel.internal.classfile.Attribute;
import com.sun.prism.paint.Color;

import sun.io.Converters;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;


public class CodeWindow {
	int currentExecutionLine = 0;
	ScrollPane windowContainer;
	VBox codeStack;
	DraggableNode node;
	int codeWindowWidth;
	int codeWindowHeight;
	int topPadding;
	int leftPadding;
	
	public CodeWindow()
	{
		//default width and height
		codeWindowWidth = 420;
		codeWindowHeight = 200;
		topPadding = 30;
		leftPadding = 10;
		
		windowContainer = new ScrollPane(); 
		setSize(codeWindowWidth - 20, codeWindowHeight -40);
		
		Rectangle rec = new Rectangle(codeWindowWidth, codeWindowHeight);
		javafx.scene.paint.Paint color = javafx.scene.paint.Paint.valueOf("CCCCCC");
		rec.setFill(color);
		rec.setArcHeight(15);
		rec.setArcWidth(15);
		
		node = new DraggableNode();
		node.getChildren().add(rec);
		node.getChildren().add(windowContainer);
		
		windowContainer.relocate(leftPadding, topPadding);

	}
	
	public void setCodeStack( ObservableList<HBox> _codeList)
	{
		if(codeStack == null)
		{
			codeStack = new VBox();
			codeStack.setId("codeStack");
		}
		codeStack.getChildren().addAll( _codeList );
		
		windowContainer.setContent(codeStack);
	}
	
	public VBox getCodeStack()
	{
		return codeStack;
	}
	
	public ScrollPane getCodeWindow()
	{
		return windowContainer;
	}
	
	public void relocate(double _x, double _y)
	{
		node.relocate(_x, _y);
	}
	
	public void setSize(double _width, double _height)
	{
		windowContainer.setPrefSize(_width, _height);
	}
	
	public int getCurrentExecutionLine()
	{
		return currentExecutionLine;
	}
	
	public int incrementCurrentExecutionLine()
	{
		return currentExecutionLine += 1;
	}
	
	public int decrementCurrentExecutionLine()
	{
		return currentExecutionLine -= 1;
	}
	
	public DraggableNode getDraggableNode()
	{
		return node;
	}
	
	
}
