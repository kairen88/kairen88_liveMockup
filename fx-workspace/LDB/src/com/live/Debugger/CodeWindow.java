package com.live.Debugger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class CodeWindow {
	
	private CodeEditor editor;
	private DraggableNode codeWindowContainer;
	private StackPane codeWindowSP;
	
	private int windowWidth;
	private int windowHeight;
	private int padding = 15;
	private int paddingTop = 20;
	
	private int currentExecutionLine = 0;
	
	public CodeWindow(String editingCode, int _windowWidth, int _windowHeight) {
		//set code window size, min 300 by 300
		if(_windowWidth < 300)
			this.windowWidth = 300;
		else
			this.windowWidth = _windowWidth;
		
		if(_windowHeight < 300)
			this.windowHeight= 300;
		else
			this.windowHeight= _windowHeight;
		
		codeWindowContainer = new DraggableNode();
		//initialize codeMirror editor
		//padding for editor is 2*padding, 2*padding + paddingTop
		editor = new CodeEditor(editingCode, windowWidth - (padding * 2), windowHeight - (padding * 2 + paddingTop) );
		
		//creating code window background
		Rectangle codeWindowBackground = createCodeWindowBackground();
		
		//stack pane to stack elements in code window
		codeWindowSP = new StackPane();

		Pane eidtorPane = new Pane();
		eidtorPane.getChildren().add(editor);
		
		//positioning editor with padding
		editor.relocate(padding, padding + paddingTop);

		codeWindowSP.getChildren().add(codeWindowBackground);
		codeWindowSP.getChildren().add(eidtorPane);
		
		//add stack pane to root draggable node
		codeWindowContainer.getChildren().add(codeWindowSP);
		
		
		
		//add change listener to run scripts when webform is loaded		
		this.editor.webview.getEngine().getLoadWorker().stateProperty().addListener(
		        new ChangeListener<State>() {
		            public void changed(ObservableValue ov, State oldState, State newState) {
		                if (newState == State.SUCCEEDED) {
		                	//run scripts when webform loaded
		                	//set all lines to class newLine, red		                	
		                	
		                }
		            }
		        });
	}
	
	//public methods-----------------------------------------------------------
	
	//returns the root/container node for the code window which is a draggable node
	public DraggableNode getRootNode()
	{
		return codeWindowContainer;	
	}
	
	//sets the class for the line number indicated to completedLine which styles it green
	public void setLineColorToCompleted(int lineNum)
	{
		editor.webview.getEngine().executeScript("editor.setLineClass(" + String.valueOf(lineNum) + ", null, 'completedLine');");
	}
	
	//sets the class for the line number indicated to completedLine which styles it yellow
	public void setLineColorToCurrent(int lineNum)
	{
		editor.webview.getEngine().executeScript("editor.setLineClass(" + String.valueOf(lineNum) + ", null, 'currentLine');");
	}
	
	//sets the class for the line number indicated to completedLine which styles it red
	public void setLineColorToNew(int lineNum)
	{
		editor.webview.getEngine().executeScript("editor.setLineClass(" + String.valueOf(lineNum) + ", null, 'newLine');");
	}
	
	//return current execution line
	public int getCurrentExecutionLine()
	{
		return currentExecutionLine;
	}
	
	//sets current execution line
	public void setCurrentExecutionLine(int newLineNum)
	{
		this.currentExecutionLine = newLineNum ;
	}
	
	//increment the current execution line by 1
	public void incrementCurrentExecutionLine()
	{
		this.currentExecutionLine += 1;
	}
	
	//decrement the current execution line by 1
	public void decrementCurrentExecutionLine()
	{
		this.currentExecutionLine -= 1;
	}
	
	public int getLineCount()
	{
		Object codeLineCount = editor.webview.getEngine().executeScript("editor.lineCount();");
		return (int) codeLineCount;
	}
	
	//private helper methods-----------------------------------------------------------

	
	private Rectangle createCodeWindowBackground()
	{
		Rectangle codeWindowBackground = new Rectangle(windowWidth, windowHeight);
		javafx.scene.paint.Paint codeMirrorBackgroundColor = javafx.scene.paint.Paint.valueOf("CCCCCC");
		codeWindowBackground.setFill(codeMirrorBackgroundColor);
		codeWindowBackground.setArcHeight(15);
		codeWindowBackground.setArcWidth(15);
		
		return codeWindowBackground;
	}

}
