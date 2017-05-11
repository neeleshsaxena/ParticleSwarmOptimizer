import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GraphStream {
	
	
	public void getGraph(List<Customer> customerList){
	
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		MultiGraph graph = new MultiGraph("Particle Swarm Optimizer");
		
		graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setStrict(false);
        graph.setAutoCreate(true);
        graph.display();
        
        for(int i=0;i<customerList.size()-1;i++){
        	StringBuilder st = new StringBuilder();
        	st.append(customerList.get(i));
        	st.append(customerList.get(i+1));
        	graph.addEdge(st.toString(), customerList.get(i).getName(), customerList.get(i+1).getName());
        }

        /*graph.addEdge("NeeleshKunal", "Neelesh", "Kunal");
        graph.addEdge("KunalManasi", "Kunal", "Manasi");
        graph.addEdge("ManasiNeelesh", "Manasi", "Neelesh");
        graph.addEdge("NeeleshYash", "Neelesh", "Yash");
        graph.addEdge("YashKandi", "Yash", "Kandi");
        graph.addEdge("KandiKetki", "Kandi", "Ketki");*/
        //graph.addEdge("EF", "E", "F");
        
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        
        explore(graph.getNode(customerList.get(0).getName()));
        
	}
	
	public  void explore(Node source) {
        Iterator<? extends Node> k = source.getBreadthFirstIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            sleep(); 
        }
    }
 
 protected  void sleep() {
     try { Thread.sleep(1000); } catch (Exception e) {}
 }
 
 protected  String styleSheet =
            "node {	fill-color: black; size: 30px; text-mode:normal; text-background-mode:rounded-box; text-background-color:yellow;}"+
            "edge {arrow-shape:arrow; arrow-size:8px,8px; fill-color: blue; size: 3px;}" +
            "node.marked {	fill-color: red;}";

}
