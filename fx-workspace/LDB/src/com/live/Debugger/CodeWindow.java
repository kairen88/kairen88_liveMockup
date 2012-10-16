package com.live.Debugger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

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
		//initialize window dimensions
		//set code window size, min 300 by 300
		if(_windowWidth < 300)
			this.windowWidth = 300;
		else
			this.windowWidth = _windowWidth;
		
		if(_windowHeight < 300)
			this.windowHeight= 300;
		else
			this.windowHeight= _windowHeight;
		
		//construct the code window
		constructCodeWindow(editingCode);	
		
		
		//add change listener to run scripts when webform is loaded		
//		addWebviewLoadedChangeListener();
	}
	
	//overloaded constructor that takes path and loads code from file
	public CodeWindow(Path _editingCodePath, int _windowWidth, int _windowHeight) {
		//initialize window dimensions
		//set code window size, min 300 by 300
		if(_windowWidth < 300)
			this.windowWidth = 300;
		else
			this.windowWidth = _windowWidth;
		
		if(_windowHeight < 300)
			this.windowHeight= 300;
		else
			this.windowHeight= _windowHeight;
		
		//load the code from file
		String editingCode = getCodeFromFile(_editingCodePath);
		
		//construct the code window
		constructCodeWindow(editingCode);	
		
		
		//add change listener to run scripts when webform is loaded		
//		addWebviewLoadedChangeListener();
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
	
	//execute script on editor webview
	public void runScriptOnWebForm(String _script)
	{
		editor.webview.getEngine().executeScript(_script);
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
	
	private void addWebviewLoadedChangeListener()
	{
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
	
	private void constructCodeWindow(String editingCode)
	{
		codeWindowContainer = new DraggableNode();
		
		//checking for code string
		if(editingCode == null)
			editingCode = "";
		
		//initialize codeMirror editor
		//padding for editor is 2*padding, 2*padding + paddingTop
		editor = new CodeEditor(editingCode, windowWidth - (padding * 2), windowHeight - (padding * 2 + paddingTop) );
		
		//creating code window background
		Rectangle codeWindowBackground = createCodeWindowBackground();
		
		//positioning editor with padding
		editor.relocate(padding, padding + paddingTop);

		//add code window to root draggable node
		codeWindowContainer.getChildren().add(codeWindowBackground);
		codeWindowContainer.getChildren().add(editor);

	}
	
	private String getCodeFromFile(Path _editingCodePath)
	{
		//read in code from file
		String editingCode  = "";
		String filePath = _editingCodePath.toString();
		  try{
			  // Open the file that is the first command line parameter
			  FileInputStream fstream = new FileInputStream(filePath);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   
			  {
	    		  //append to string obj template
				  editingCode += strLine + "\n";
			  }
			  //Close the input stream
			  in.close();

		    }catch (Exception e)
		    {
		    	//Catch exception if any
		    	System.err.println("Error: " + e.getMessage());
		    }
		  
		  return editingCode;
	}

}
