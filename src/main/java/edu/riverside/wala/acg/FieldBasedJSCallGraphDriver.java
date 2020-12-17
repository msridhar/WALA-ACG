package edu.riverside.wala.acg;

import com.ibm.wala.cast.js.callgraph.fieldbased.FieldBasedCallGraphBuilder;
import com.ibm.wala.cast.js.callgraph.fieldbased.flowgraph.FlowGraph;
import com.ibm.wala.cast.js.callgraph.fieldbased.flowgraph.vertices.ObjectVertex;
import com.ibm.wala.cast.js.html.DefaultSourceExtractor;
import com.ibm.wala.cast.js.ipa.callgraph.JSCallGraph;
import com.ibm.wala.cast.js.translator.CAstRhinoTranslatorFactory;
import com.ibm.wala.cast.js.util.CallGraph2JSON;
import com.ibm.wala.cast.js.util.FieldBasedCGUtil;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphStats;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.NullProgressMonitor;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File; 
import java.io.FileWriter;


public class FieldBasedJSCallGraphDriver {

  /**
   * Usage: FieldBasedJSCallGraphDriver path_to_js_or_html_file
   *
   * @param args
   * @throws WalaException
   * @throws CancelException
   * @throws IOException
   * @throws IllegalArgumentException
   */
  public static void main(String[] args) throws IOException, WalaException, CancelException {
    Path path = Paths.get(args[0]);
    String mode = new String(args[2]);
    File dir = new File(args[1]);
    if (!dir.exists()){
        dir.mkdir();
    }
    File file1 = new File(args[1]+"/SCG_"+mode+".json");
    File file2 = new File(args[1]+"/FG_"+mode+".json");
    //URL url = path.toUri().toURL();
    FieldBasedCGUtil f = new FieldBasedCGUtil(new CAstRhinoTranslatorFactory());
    //FieldBasedCallGraphBuilder.CallGraphResult results =
            //f.buildCG(url, FieldBasedCGUtil.BuilderType.OPTIMISTIC_WORKLIST, false, DefaultSourceExtractor::new);
   //Pair<JSCallGraph, PointerAnalysis<ObjectVertex>> results=null;
   FieldBasedCallGraphBuilder.CallGraphResult results = null;
   //System.out.println("Here "+ args[0].endsWith(".js"));
      if(mode.equalsIgnoreCase("PES")){
              //results=f.buildCG(url, FieldBasedCGUtil.BuilderType.PESSIMISTIC,false, DefaultSourceExtractor::new);
              results=f.buildScriptDirCG(path, FieldBasedCGUtil.BuilderType.PESSIMISTIC, new NullProgressMonitor(),false);

      }
      else if (mode.equalsIgnoreCase("OPT")){
              //results=f.buildCG(url, FieldBasedCGUtil.BuilderType.OPTIMISTIC_WORKLIST,false, DefaultSourceExtractor::new);
              results=f.buildScriptDirCG(path, FieldBasedCGUtil.BuilderType.OPTIMISTIC_WORKLIST,new NullProgressMonitor(),false);

      }

    //CallGraph CG = results.getCallGraph();
    CallGraph CG = results.getCallGraph();
    FlowGraph flowGraph = results.getFlowGraph();

    /*System.out.println(CallGraphStats.getStats(CG));*/
    /*System.out.println("CALL GRAPH:");
    System.out.println((new CallGraph2JSON(false)).serialize(CG));*/
    /*System.out.println("FLOW GRAPH:");
    System.out.println(flowGraph.toJSON());*/
    try {  
      FileWriter myWriter1 = new FileWriter(file1);
      myWriter1.write((new CallGraph2JSON(false,true)).serialize(CG));
      myWriter1.close();
      System.out.println("Successfully wrote to Call Graph "+ file1);
    } catch (IOException e) {
      System.out.println("An error occurred while writing Call Graph.");
      e.printStackTrace();
    }
     try {  
      FileWriter myWriter2 = new FileWriter(file2);
      myWriter2.write(flowGraph.toJSON());
      myWriter2.close();
      System.out.println("Successfully wrote Flow Graph to "+ file2);
    } catch (IOException e) {
      System.out.println("An error occurred while writing Flow Graph.");
      e.printStackTrace();
    }

  }

}