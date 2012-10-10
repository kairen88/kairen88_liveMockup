package com.live.Debugger;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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

	int currentWindowIdx = 0;
	
	ArrayList<CodeWindow> codeWindowAry ;
	CodeWindow currentCodeWindow;
	
	NavigationBar currentNaviBar;
	ArrayList<NavigationBar> navibarAry;
	TickNavigator tickNavigator;
	
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

//---------------------------------
//        //testing navibation bar
		
		//adding loops to navi bar
		CodeLoop lop1 = new CodeLoop (2, 25);
    	CodeLoop  lop2 = new CodeLoop (4, 20);
    	CodeLoop  lop3 = new CodeLoop (7, 14);
    	CodeLoop  lop4 = new CodeLoop (16, 19);
    	CodeLoop  lop5 = new CodeLoop (13, 14);
    	CodeLoop  lop6 = new CodeLoop (28, 30);
    	
    	CodeLoop [] loopAry = {lop3, lop2, lop4, lop1, lop5, lop6};
		
		navibarAry = new ArrayList<NavigationBar>();

		NavigationBar naviBarRoot = new NavigationBar(loopAry);
		
		navibarAry.add(naviBarRoot);
		currentNaviBar = naviBarRoot;
		
		NavigationBar naviBarRoot2 = new NavigationBar(null);
		Pane naviBar2 = naviBarRoot2.getNaviBarRoot();
		
		navibarAry.add(naviBarRoot2);
		
		Pane naviBarSection = (Pane) getRootAnchorPane().lookup("#naviBarSection");
		
		navibarAry.get(0).getNaviBarRoot().relocate(10, 20); 
//		addElementToRoot(navibarAry.get(0).getNaviBarRoot());
		naviBarSection.getChildren().add(navibarAry.get(0).getNaviBarRoot());
		
		navibarAry.get(1).getNaviBarRoot().relocate(10, 100); 
//		addElementToRoot(navibarAry.get(1).getNaviBarRoot());
		naviBarSection.getChildren().add(navibarAry.get(1).getNaviBarRoot());
//-----------------------------------      
		
//		//testing out codemirror code editor
		codeWindowAry = new ArrayList<CodeWindow>();
		
		Path path = FileSystems.getDefault().getPath("resource", "sampleJs.txt");
		
		Pane codeWindowSection = (Pane) getRootAnchorPane().lookup("#codeWindowSection");
		
		//adding 1st code window setting it as current
		CodeWindow editor = new CodeWindow(path, 600, 300);
//		addDraggableElementToRoot(editor.getRootNode());
		codeWindowSection.getChildren().add(editor.getRootNode());
		editor.getRootNode().relocate(20, 20);
		codeWindowAry.add(editor);
		
		currentCodeWindow = editor;
		
//		adding 2nd code window to ary, setting code window below min width and height
		editor = new CodeWindow(editingCode, 200, 150);
//		addDraggableElementToRoot(editor.getRootNode());
		codeWindowSection.getChildren().add(editor.getRootNode());
		editor.getRootNode().relocate(20, 330);
		codeWindowAry.add(editor);
		
		String script = "alert('sdf');" +
//				"{" +
				"var newdiv = document.createElement('div');" +
//				"var divIdName = 'div1';" +
//				"newdiv.setAttribute('id',divIdName);" +
//				"newdiv.style.width = \"100px\";" +
//						"newdiv.style.height = \"100px\";" +
//								"newdiv.style.height = \"100px\";" +
//								"newdiv.style.position = \"absolute\";" +
//								"newdiv.style.background = \"#FF0000\";" +
								"newdiv.innerHTML = 'this is 1st DIV';" +
								"document.body.appendChild(newdiv);" ;
//								"}";
		editor.runScriptOnWebForm(script);
		
//--------------------------------------------------------------
		//tick navigator

		TickNavigator t = new TickNavigator();
		t.setTickNavigatorToNaviBar(currentNaviBar);
		
		tickNavigator = t;
		
//-------------------------------------------------------------		
		
		
        initializeElementControl();  	
        
		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.setWidth(920);
		primaryStage.setHeight(740);
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
    

    
    private void initializeElementControl()
    {
    	Button nextBtn = (Button) getRootAnchorPane().lookup("#NextBtn");
        Button previousBtn = (Button) getRootAnchorPane().lookup("#PrevBtn");
        Button tagBtn = (Button) getRootAnchorPane().lookup("#varTagSwitch");
        
    	nextBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
        		if(currentCodeWindow.getCurrentExecutionLine() < currentCodeWindow.getLineCount())
        		{            	
        			//increment current exe line
        			currentCodeWindow.incrementCurrentExecutionLine();
	            	//set current line to yellow
	            	currentCodeWindow.setLineColorToCurrent(currentCodeWindow.getCurrentExecutionLine() - 1);
	            	
	            	if(currentCodeWindow.getCurrentExecutionLine() > 1)
	                	//set previous line to green
	            		currentCodeWindow.setLineColorToCompleted(currentCodeWindow.getCurrentExecutionLine() - 2);
	            	
	            	//set tick navigator position
	            	tickNavigator.moveTickNavigatorToCurrTick(currentNaviBar, currentCodeWindow);
	            	
	                System.out.println(currentCodeWindow.getCurrentExecutionLine());	                
        		}
            }
        });
        
        previousBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(currentCodeWindow.getCurrentExecutionLine() > 1)
            	{
            		//decrement current exe line
            		currentCodeWindow.decrementCurrentExecutionLine();
	            	
            		//set previous line to yellow
            		currentCodeWindow.setLineColorToCurrent(currentCodeWindow.getCurrentExecutionLine() - 1);
                
            		//set current line to red
            		currentCodeWindow.setLineColorToNew(currentCodeWindow.getCurrentExecutionLine());
            		
            		//set tick navigator position
            		tickNavigator.moveTickNavigatorToCurrTick(currentNaviBar, currentCodeWindow);
            		
                	System.out.println(currentCodeWindow.getCurrentExecutionLine() );
                	
            	}
            }
        });
        
      //setting button to toggle current code window
        Button toggleBtn = (Button)getRootAnchorPane().lookup("#button");
        toggleBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent e) 
        		{
        			//cycle through code windows in ary
        			//if index is pointing to last element in ary, reset to 1st code window else set code window as current
        			if(codeWindowAry.lastIndexOf(currentCodeWindow) == codeWindowAry.size() - 1)
        				currentCodeWindow = codeWindowAry.get(0);
        			else
        				currentCodeWindow = codeWindowAry.get( codeWindowAry.lastIndexOf(currentCodeWindow) + 1 );
        			
        			if(navibarAry.lastIndexOf(currentNaviBar) == navibarAry.size() - 1)
        				currentNaviBar = navibarAry.get(0);
        			else
        				currentNaviBar = navibarAry.get( navibarAry.lastIndexOf(currentNaviBar) + 1 );
        			
        			//set current line to yellow
	            	//set line in previous code window to green
        			
        			tickNavigator.setTickNavigatorToNaviBar(currentNaviBar);
        			tickNavigator.moveTickNavigatorToCurrTick(currentNaviBar, currentCodeWindow);
     			
        		}
        	});
        
        tagBtn.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent e) 
        		{
//                	String scriptStr = readFileReturnString("resource\\js\\create&InsertDivElement.txt");
//                	String scriptStr = readFileReturnString("resource\\js\\testingHoverFunction.txt");
//					String scriptStr = readFileReturnString("resource\\js\\attachTagToVariable.txt");
        			String scriptStr = readFileReturnString("resource\\js\\testScript.js");
					currentCodeWindow.runScriptOnWebForm(scriptStr);
        		}
        	});
        
    }
    
    private String readFileReturnString(String _path)
    {
    	String fileStr = "";
    	
    	try{
  		  // Open the file that is the first command line parameter
  		  FileInputStream fstream = new FileInputStream(_path);
  		  // Get the object of DataInputStream
  		  DataInputStream in = new DataInputStream(fstream);
  		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
  		  String strLine;
  		  //Read File Line By Line
  		  while ((strLine = br.readLine()) != null)   
  		  {
  			  //check to ignor comments
  			  if(!strLine.contains("//"))
      		  //append to string obj template
  				  fileStr += strLine;
  		  }
  		  //Close the input stream
  		  in.close();

  	    }catch (Exception e)
  	    {
  	    	//Catch exception if any
  	    	System.err.println("Error: " + e.getMessage());
  	    }
    	return fileStr;
    }
    
	
}
