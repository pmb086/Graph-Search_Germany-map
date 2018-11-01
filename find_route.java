import java.io.*;
import java.util.*;

public class find_route{
	static class node{
           node parent;
           String city;
           int weight;
           int total_cost;
           int level;
           //constructor
           node(String c, node p, int w, int t, int l){
                city = c;
		        parent = p;
		        weight = w;
		        total_cost = t;
		        level = l;
           }
	       public node getparent(){
		        return parent;
	       }
           public String getcity() {
                return city;
           }
	       public int getcost(){
                return weight;
	       }
	       public int gettotal_cost(){
	 	        return total_cost;
	       }
	       public int getlevel(){
	    	   return level;
	       }
        }
	public static void main(String[] args){
                int i,j, l=0, reached =0 ;
		String f= args[0];
                String origin = args[1];
		String destiny = args[2];
                
		try{
		  Scanner s = new Scanner(new FileReader(f));
		  List<String> city1 = new ArrayList<String>();
                  List<String> city2 = new ArrayList<String>();
                  List<Integer> len = new ArrayList<Integer>();
                  List<String> scity1 = city2;
                  List<String> scity2 = city1;
		          while(s.hasNext()){
			              String x = s.next();
			              String y = s.next();
			              String z = s.next(); 
				      if(x != "END" && y != "OF" && z != "INPUT"){
				    	  try{
		        			  city1.add(x);
                              city2.add(y);
                              len.add(Integer.parseInt(z));
		                      }
		                      catch(NumberFormatException e){
		                      }
                                      }
	                  }
                 s.close();
		 if((city1.contains(origin) && city2.contains(destiny)) || (city1.contains(origin) && city1.contains(destiny))){
			ArrayList<String> visited = new ArrayList<String>();
			ArrayList<node> node_info = new ArrayList<node>();
			LinkedList<node> fringe = new LinkedList<node>();
			node root = new node(origin, null, 0, 0, l);
			fringe.add(root);
			l++;
			for( i =0 ; i < city1.size() ; i++){
			  if( city1.get(i).equals(origin) && !visited.contains(origin)){
				String branch = city2.get(i);
				int dist = len.get(i);
				node prevnode = root;
                                int c_cost = dist + (prevnode.getcost());
				node b1 = new node(branch, root, dist, c_cost, l);	
				fringe.addLast(b1);				
				}
			  if( scity1.get(i).equals(origin) && !visited.contains(origin)){
					String branch = scity2.get(i);
					int dist = len.get(i);
					node prevnode = root;
	                                int c_cost = dist + (prevnode.getcost());
					node b1 = new node(branch, root, dist, c_cost, l);	
					fringe.addLast(b1);				
					}
			 }
			for( node n : fringe){
				if(n.getcity().equals(destiny))
				{
					System.out.println("distance: " + n.gettotal_cost() + " km");
					System.out.println("route:");
					System.out.println(origin + " to " + destiny +", " + n.gettotal_cost());
					System.exit(0);
				}
			}
			try{
			while (reached == 0){
			l = l+1;
		    //Dequeue root
			node_info.add(fringe.getFirst());
			/***********Deleted node**********/
			visited.add(fringe.getFirst().getcity());
			/***********Visited node*********/
			if(visited.contains(destiny)){
				reached =1;
				continue;
			}
			fringe.removeFirst();
			//fringe sort
			node temp;
			for (i = 0; i < fringe.size(); i++) {
	                for (j = 0; j < fringe.size() - i - 1; j++) {
	                if (fringe.get(j).gettotal_cost() > fringe.get(j + 1).gettotal_cost()) {
	                    temp = fringe.get(j);
	                    fringe.set(j, fringe.get(j + 1));
	                    fringe.set(j + 1, temp);
	                }
	            }
	        }
			//branching
			
			node par1 = fringe.getFirst();
			String p1 = par1.getcity();
			for( i =0 ; i < city1.size() ; i++){
				  if( city1.get(i).equals(p1) && !(visited.contains(p1))){
					String branch = city2.get(i);
					int dist = len.get(i);
					node prevnode = par1;
	                                int c_cost = dist + (prevnode.gettotal_cost());
	                                if(!visited.contains(branch)){
					node b1 = new node(branch, prevnode, dist, c_cost, l);	
					fringe.addLast(b1);				
					}
				  }
				  if( scity1.get(i).equals(par1.getcity()) && !visited.contains(par1.getcity())){
						String branch = scity2.get(i);
						int dist = len.get(i);
						node prevnode = par1;
		                                int c_cost = dist + (prevnode.gettotal_cost());
						if(!visited.contains(branch)){
		                                node b1 = new node(branch, prevnode, dist, c_cost, l);
						fringe.addLast(b1);				
						}
				  }
			}
			}
				
			if( reached == 1){
				boolean reach_root = false;
				String t = destiny;
				node target;
			    ArrayList<String> trans = new ArrayList<String>();
			    trans.add(destiny);
						    for( node f1 : node_info)
						    {
						    	if(f1.getcity().equals(destiny)){
						    	System.out.println("distance: " +" "+f1.gettotal_cost() + "km");
						    	}
						    }	
						    System.out.println("route:\n");    
						    while(!reach_root){
						    for( node f1 : node_info)
						     {
						    	if(f1.getcity().equals(t)){
						    		target = f1.getparent(); 
						    		if(target != null){
						    			trans.add(target.getcity());
						    			t = target.getcity();
						    		}
						    		else{
						    			reach_root = true;
						    			target=null;}
						       }
					         }
						    }
						    Collections.reverse(trans);
						    for( i = 0; i < trans.size()-1; i++){
						    	for( j =0 ; j < city1.size() ; j++){
									  if( city1.get(j).equals(trans.get(i)) && city2.get(j).equals(trans.get(i+1))){
										String top = city1.get(j);
										String branch = city2.get(j);
										int dist = len.get(j);
										System.out.println(top + " to "+ branch+", " + dist + "km");									
										}
									  if( scity1.get(j).equals(trans.get(i)) && scity2.get(j).equals(trans.get(i+1))){
											String top =scity1.get(j);
										    String branch = scity2.get(j);
											int dist = len.get(j);
											System.out.println(top + " to "+ branch+", " + dist + "km");	
									   }
									 }
						    }
			  }
			else if(reached == 0){
				System.out.println("distance: infinity\nroute:\nnone");
			}
		 }
			catch(NoSuchElementException e){
				System.out.println("distance: infinity\nroute:\nnone");
			}
			}
		 else if((city2.contains(origin) && city1.contains(destiny)) || (city2.contains(origin) && city2.contains(destiny))){
				Set<String> visited = new HashSet<String>();
				ArrayList<node> node_info = new ArrayList<node>();
				LinkedList<node> fringe = new LinkedList<node>();
				node root = new node(origin, null, 0, 0, l);
				fringe.add(root);
				l++;
				for( i =0 ; i < city2.size() ; i++){
				  if( city2.get(i).equals(origin) && !visited.contains(origin)){
					String branch = city1.get(i);
					int dist = len.get(i);
					node prevnode = root;
	                                int c_cost = dist + (prevnode.gettotal_cost());
					node b1 = new node(branch, root, dist, c_cost, l);	
					fringe.addLast(b1);				
					}
				  if( scity1.get(i).equals(origin) && !visited.contains(origin)){
						String branch = scity2.get(i);
						int dist = len.get(i);
						node prevnode = root;
		                                int c_cost = dist + (prevnode.gettotal_cost());
						node b1 = new node(branch, root, dist, c_cost, l);	
		                                fringe.addLast(b1);				
						}
				 }
				for( node n : fringe){
					if(n.getcity().equals(destiny))
					{
						System.out.println("distance: " + n.gettotal_cost() + " km");
						System.out.println("route:");
						System.out.println(origin + " to " + destiny +", " + n.gettotal_cost());
						System.exit(0);
					}
				}
				try{
				while (reached == 0){
				l = l+1;
		                //Dequeue root
				node_info.add(fringe.getFirst());
				/**********Deleted node*********/
				visited.add(fringe.getFirst().getcity());
			        /**********Visited node*********/
				if(visited.contains(destiny)){
					reached =1;
					break;
				}
				fringe.removeFirst();
				/**********Fringe after deletion*********/
				//fringe sort
				node temp;
				for (i = 0; i < fringe.size(); i++) {
                                for (j = 0; j < fringe.size() - i - 1; j++) {
		                if (fringe.get(j).gettotal_cost() > fringe.get(j + 1).gettotal_cost()) {
		                    temp = fringe.get(j);
		                    fringe.set(j, fringe.get(j + 1));
		                    fringe.set(j + 1, temp);
		                }
		            }
		        }
				//branching
				node par1 = fringe.getFirst();
				String p1 = par1.getcity();
				for( i =0 ; i < city1.size() ; i++){
					  if( city1.get(i).equals(p1) && !(visited.contains(p1))){
						String branch = city2.get(i);
						int dist = len.get(i);
						node prevnode = par1;
		                                int c_cost = dist + (prevnode.gettotal_cost()); 
		                                if(!visited.contains(branch)){
		                                node b1 = new node(branch, prevnode, dist, c_cost, l);	
						fringe.addLast(b1);				
						}
					  }
					  if( scity1.get(i).equals(par1.getcity()) && !visited.contains(par1.getcity())){
							String branch = scity2.get(i);
							int dist = len.get(i);
							node prevnode = par1;
			                                int c_cost = dist + (prevnode.gettotal_cost());
			                                if(!visited.contains(branch)){
			                                node b1 = new node(branch, prevnode, dist, c_cost, l);
							fringe.addLast(b1);				
							}
					  }
				}
			}
				if( reached == 1){
					boolean reach_root = false;
					String t = destiny;
					node target;
				    ArrayList<String> trans = new ArrayList<String>();
				    trans.add(destiny);
							    for( node f1 : node_info)
							    {
							    	if(f1.getcity().equals(destiny)){
							    	System.out.println("distance: " +" "+f1.gettotal_cost() + "km");
							    	}
							    }	
							    System.out.println("route:\n");    
							    while(!reach_root){
							    for( node f1 : node_info)
							     {
							    	if(f1.getcity().equals(t)){
							    		target = f1.getparent(); 
							    		if(target != null){
							    			trans.add(target.getcity());
							    			t = target.getcity();
							    		}
							    		else{
							    			reach_root = true;
							    			target=null;}
							       }
						         }
							    }
							    
							    Collections.reverse(trans);
							    for( i = 0; i < trans.size()-1; i++){
							    	for( j =0 ; j < city1.size() ; j++){
										  if( city1.get(j).equals(trans.get(i)) && city2.get(j).equals(trans.get(i+1))){
											String top = city1.get(j);
											String branch = city2.get(j);
											int dist = len.get(j);
											System.out.println(top + " to "+ branch+", " + dist + "km");									
											}
										  if( scity1.get(j).equals(trans.get(i)) && scity2.get(j).equals(trans.get(i+1))){
												String top =scity1.get(j);
											    String branch = scity2.get(j);
												int dist = len.get(j);
												System.out.println(top + " to "+ branch+", " + dist + "km");	
										   }
										 }
							    }
							    
				  }
				else if(reached == 0){
					System.out.println("distance: infinity\nroute:\nnone");
				}
				}
				catch(NoSuchElementException e){
					System.out.println("distance: infinity\nroute:\nnone");
				}
		         }
		         else{
			 System.out.println("distance: infinity\nroute:\nnone");
		 }
		
		}
		catch(IOException e){
		 e.printStackTrace();
		}
	
}
} 

