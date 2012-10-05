package com.live.Debugger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import netscape.javascript.JSObject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class liveDebugging extends Application {
	
	private Parent root = null;
	CodeWindow currentCodeWindow = null;
	//0 represents no line executed, 1st line is 1, current code display list starts from 0 so need to + 1
	int currentExecutionLine = 0; 
	//for testing
	CodeWindow CodeWindow1 = null;
	CodeWindow CodeWindow2 = null;
	int currentWindowIdx = 0;
	
	CodeEditor editor ;
	
	 static final private String editingCode =
			    "import javafx.application.Application;\n" +
			    "import javafx.scene.Scene;\n" +
			    "import javafx.scene.web.WebView;\n" +
			    "import javafx.stage.Stage;\n" +
			    "\n" +
			    "/** Sample code editing application wrapping an editor in a WebView. */\n" +
			    "public class CodeEditorExample extends Application {\n" +
			    "  public static void main(String[] args) { launch(args); }\n" +
			    "  @Override public void start(Stage stage) throws Exception {\n" +
			    "    WebView webView = new WebView();\n" +
			    "    webView.getEngine().load(\"http://codemirror.net/mode/groovy/index.html\");\n" +
			    "    final Scene scene = new Scene(webView);\n" +
			    "    webView.prefWidthProperty().bind(scene.widthProperty());\n" +
			    "    webView.prefHeightProperty().bind(scene.heightProperty());\n" +
			    "    stage.setScene(scene);\n" +
			    "    stage.show();\n" +
			    "  }\n" +
			    "}";

	public static void main(String[] args) {
			launch(args);
		}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader();
		root = FXMLLoader.load(getClass().getClassLoader().getResource("LDB_fxml.fxml"));


		CodeWindow testWin = new CodeWindow();
		testWin.setCodeStack(getArray());
		
		addDraggableElementToRoot(testWin.getDraggableNode());
		testWin.relocate(20, 10);
//		testWin.setSize(400, 180);		
        
        //testing
        CodeWindow1 = testWin;
        
        //setting code window as currentCodeWindow
        setCurrentCodeWindow(testWin);        
//-------------------------------
        CodeWindow testWin2 = new CodeWindow();
		testWin2.setCodeStack(getArray());
		
		addDraggableElementToRoot(testWin2.getDraggableNode());
		testWin2.relocate(20, 230);
//		testWin2.setSize(400, 180);
		
        
        //testing
        CodeWindow2 = testWin2;
//---------------------------------
        int numberOfTicks = 30;
        double naviBarHeight = 30.0;
        double naviBarWidth = 200.0;
        int naviBarDefaultPadding = 5;
        
        Rectangle rec = new Rectangle(naviBarWidth, naviBarHeight);
		javafx.scene.paint.Paint color = javafx.scene.paint.Paint.valueOf("36B541");
		rec.setFill(color);
		rec.setArcHeight(5);
		rec.setArcWidth(5);
		
		HBox timeSegments = new HBox();
		
		double segmentHeight = naviBarHeight;
		double segmentWidth = Math.floor( (naviBarWidth - (2 * naviBarDefaultPadding)) / numberOfTicks );
		double segmentTickHeight = naviBarHeight / 3.0;
		
		int loopStart = 5;
		int loopEnd = 10;
		
		for(int i = 0; i < numberOfTicks; i++)
		{
			Pane segmentContainer = new Pane();			
			
			Rectangle segmentRec = new Rectangle(segmentWidth, segmentHeight);
			javafx.scene.paint.Paint normalColor = color;
			javafx.scene.paint.Paint loopColor = javafx.scene.paint.Paint.valueOf("B023A4");
			
			if(i >= loopStart && i <= loopEnd)
				segmentRec.setFill(loopColor);
			else
				segmentRec.setFill(normalColor);
			
			Line segmentTick = new Line(1.0, segmentHeight, 1.0, segmentHeight - segmentTickHeight);
			javafx.scene.paint.Paint naviBarTickColor = javafx.scene.paint.Paint.valueOf("FFFFFF");
			segmentTick.setStroke(naviBarTickColor);
			
			segmentContainer.getChildren().addAll(segmentRec, segmentTick);
			
//			naviBarTick.relocate(10, 10);
			
			timeSegments.getChildren().add(segmentContainer);
		}
		
		Label naviBarTitle = new Label("MethodName()");
		naviBarTitle.setStyle("-fx-text-fill: #FFFFFF;");

		Pane naviBar = new Pane();
		naviBar.getChildren().addAll(rec, timeSegments, naviBarTitle);
		double naviBarTiltlePadding = (naviBarWidth / 2)  - (naviBarTitle.getWidth() / 2);
		naviBarTitle.relocate( naviBarTiltlePadding, 0.0);
		
		double naviBarCalculatedPadding = (naviBarWidth - (segmentWidth * numberOfTicks)) /2;
		timeSegments.relocate(naviBarCalculatedPadding, 0);

		
		naviBar.relocate(20, 400); 		
		addElementToRoot(naviBar);
//-----------------------------------      
		
		//testing out codemirror code editor
		DraggableNode codeMirror = new DraggableNode();
		editor = new CodeEditor(editingCode);
		
		Rectangle codeMirrorBackground = new Rectangle(700, 400);
		javafx.scene.paint.Paint codeMirrorBackgroundColor = javafx.scene.paint.Paint.valueOf("CCCCCC");
		codeMirrorBackground.setFill(codeMirrorBackgroundColor);
		codeMirrorBackground.setArcHeight(15);
		codeMirrorBackground.setArcWidth(15);
		
		StackPane codeMirrorSP = new StackPane();
		Pane pane = new Pane();
		pane.getChildren().add(editor);
		codeMirrorSP.getChildren().add(codeMirrorBackground);
		codeMirrorSP.getChildren().add(pane);
		codeMirrorSP.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
		
		editor.relocate(20, 20);
//		editor.webview.getEngine().executeScript("editor.setLineClass(5, null, 'test');");
		
		
		codeMirror.getChildren().add(codeMirrorSP);
		addDraggableElementToRoot(codeMirror);
		
        initializeElementControl();  	
        
		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.setWidth(800);
		primaryStage.setHeight(500);
		primaryStage.show();
	}
	
	private AnchorPane getRootAnchorPane()
    {
		AnchorPane pane = (AnchorPane) root.lookup("#AnchorPane");        
        return pane;
    }
	
	private void addDraggableElementToRoot(DraggableNode node)
    {
		AnchorPane root = getRootAnchorPane();
		root.getChildren().add(node);
    }
	
	private void addElementToRoot(Node node)
    {
		AnchorPane root = getRootAnchorPane();
		root.getChildren().add(node);
    }
	
	//need to find a way to get genric type for parent, for now use pane
    public void addElement(ScrollPane Parent, Node node)
    {
        if (Parent!=null) 
        {
            Parent.setContent(node); 
        }
    }
    
    private ObservableList<HBox> getArray()
    {
    	ObservableList<HBox> vertArray  = FXCollections.observableArrayList();
    	ArrayList<String[]> list = getInputFromFile(); 
    	
    	for(String[] strAry : list)
    	{
	    	HBox hBox = new HBox();
	    	hBox.setStyle("-fx-background-color: #ECC3BF");
	    	for(String string : strAry)
	    	{
	    		Label label = new Label(string);    		
	    		hBox.getChildren().add(label);    		
	    	}
	    	vertArray.add(hBox);
    	}

//        hBox2.setStyle("-fx-background-color: #ECC3BF");
    	return vertArray;
    }
    
    private ArrayList<String[]> getInputFromFile()
    {
    	ArrayList<String[]> list = new ArrayList<String[]>();
    	try{
    		  // Open the file that is the first 
    		  // command line parameter
    		  FileInputStream fstream = new FileInputStream("textfile.txt");
    		  // Get the object of DataInputStream
    		  DataInputStream in = new DataInputStream(fstream);
    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		  String strLine;
    		  //Read File Line By Line
    		  while ((strLine = br.readLine()) != null)   
    		  {
	    		  // Print the content on the console
	    		  //System.out.println (strLine);
	    		  String[] strAry = strLine.split(" ");
	    		  
	    		  list.add(strAry); 
    		  }
    		  //Close the input stream
    		  in.close();

		    }catch (Exception e)
		    {
		    	//Catch exception if any
		    	System.err.println("Error: " + e.getMessage());
		    }
		return list;
    }
    
    private void setCurrentCodeWindow(CodeWindow codeWindow)
    {
    	currentCodeWindow = codeWindow;
    }
    
    private void initializeElementControl()
    {
    	Button nextBtn = (Button) getRootAnchorPane().lookup("#NextBtn");
        Button previousBtn = (Button) getRootAnchorPane().lookup("#PrevBtn");
        
    	nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	
	            	currentCodeWindow.incrementCurrentExecutionLine();
	            	//set current line to yellow
	            	VBox codeStack = currentCodeWindow.getCodeStack();
	            	codeStack.getChildren().get(currentCodeWindow.getCurrentExecutionLine() - 1).setStyle("-fx-background-color: #FFFF99");
	
	                if(currentCodeWindow.getCurrentExecutionLine() > 1)
	                	//set previous line to green
	                	codeStack.getChildren().get(currentCodeWindow.getCurrentExecutionLine() - 2).setStyle("-fx-background-color: #98FB98");
	                
	                System.out.println(currentCodeWindow.getCurrentExecutionLine());
	                
	                editor.webview.getEngine().executeScript("editor.setLineClass(15, null, 'test');");
            	
            }
        });
        
        previousBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(currentCodeWindow.getCurrentExecutionLine() > 1)
            	{
            		currentCodeWindow.decrementCurrentExecutionLine();
            		//set previous line to yellow
            		VBox codeStack = currentCodeWindow.getCodeStack();
	            	codeStack.getChildren().get(currentCodeWindow.getCurrentExecutionLine() - 1).setStyle("-fx-background-color: #FFFF99");
                
            		//set current line to red
	            	codeStack.getChildren().get(currentCodeWindow.getCurrentExecutionLine()).setStyle("-fx-background-color: #ECC3BF");
                
                	System.out.println(currentCodeWindow.getCurrentExecutionLine() );
                	
            	}
            }
        });
        
      //setting button to toggle current code window
        Button toggleBtn = (Button)getRootAnchorPane().lookup("#button");
        toggleBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent e) 
        		{
        			if(currentWindowIdx == 0)
        			{
        				setCurrentCodeWindow(CodeWindow2);
        				currentWindowIdx = 1;
        			}else
        				
        			{
        				setCurrentCodeWindow(CodeWindow1);
        				currentWindowIdx = 0;
        			}
        			
        			Label label = (Label) root.lookup("#label");
        			label.setText("Toggle" + currentWindowIdx);
        		}
        	});
        
    }
    
	
}
