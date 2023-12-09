/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class DFS_Solver extends Solver
{
	/*
	 * Constructor
	 * m: The maze to solve
	 */
	public DFS_Solver(Maze m)
	{
		this.maze = m;
		this.result = "";
		this.frontier = new Stack<Node<Maze>>();
		this.closedSquares = new Stack<Square>();
	}
	
	public String solve()
	{            
		Boolean endfound = false;
		this.nodesCounter = 0;
		this.pathLength = 0;
		
		//Init maze 
		this.closedSquares.clear(); //stack for the visited ones aka explored set
		this.maze.initMaze();
		
		//Init frontier
		this.frontier.clear(); //frontier is fringe (stack)
		((Stack<Node<Maze>>) this.frontier).push(new Node<Maze>(this.maze)); //Add first state
		
		//Measure run time
		long startTime = System.currentTimeMillis();
		
		//Search
		while(!endfound)
		{
                        //you should check if there exist a node to expand
			if(this.frontier.isEmpty())
				break;
			
			else
			{
                                //You should first pop it from the stack (you visit it)
				Node<Maze> current = ((Stack<Node<Maze>>) this.frontier).pop(); //Get first node from the frontier
				this.maze = (Maze) current.getContent(); //Get maze from the node
				Square currState = this.maze.getCurrState(); //Get current state from the maze
				
                                //checking if we have found the solution
				if(currState.getLine() == this.maze.getEnd().getLine() && currState.getCol() == this.maze.getEnd().getCol())
				{
					Node<Maze> temp = new Node<Maze>(this.maze);
					temp.setFather(current); //Set current as father (parent) for all next states
					((Stack<Node<Maze>>) this.frontier).push(temp); //push the goal (end) so you can reach starting point by using fathers (parents)
					endfound = true;
				}				
				else
				{
					LinkedList<Node<Maze>> nexts = this.getNextSquares(); //Get next possible states
					
                                        //now the current one was visited (that you want to explore its next states)
                                        //So it should be pushed to other stack (which keeps track the visited ones) 
                                        //do not fotget to check if it is already in
                                        //do not forget to set * for the visited one (current one) (is it gonna be the Node or Square?)
                                        if(!this.closedSquares.contains(currState))
					{
						((Stack<Square>) this.closedSquares).push(currState);
						currState.setAttribute("*");
					
					                                        
                                                //You may want to use an iterator to reach every possible next state one by one 
                                                //or you may want to get size of the next possible states and reach them accordingly
                                                Iterator<Node<Maze>> x = nexts.descendingIterator();

                                                //Add next possible states to stack 
                                                //(think about which stack it could be. Is it gonna be frontier or closedSquares?)
                                                //Do not forget to add all next possible states (at most they are: w s e n)
                                                //-> to be able to have w s e n, you should iterate it from end to start and push it like that
                                                //Do not forget to set father (parent) node of the pushed ones before pushing (as current node)
                                                //Do not forget to increment nodesCounter
                                                while(x.hasNext())
                                                {
                                                        Node<Maze> neighbor = x.next();
                                                        if (!this.closedSquares.contains(neighbor.getContent().getCurrState())) {
                                                                neighbor.setFather(current); //Set current as father for all next states
                                                                ((Stack<Node<Maze>>) this.frontier).push(neighbor);
                                                                this.nodesCounter++;
                                                        }
                                                        
                                                        
                                                }                                       
                                        }
                                        //You may need typecasting for the Stack (before pushing) as can be observed from given codes
				}
			}
		}
		
		long endTime = System.currentTimeMillis();
		
		long time = endTime - startTime;
		
		this.result = "    ____             __  __       _______           __     _____                      __  \r\n" + 
				"   / __ \\___  ____  / /_/ /_     / ____(_)_________/ /_   / ___/___  ____ ___________/ /_ \r\n" + 
				"  / / / / _ \\/ __ \\/ __/ __ \\   / /_  / / ___/ ___/ __/   \\__ \\/ _ \\/ __ `/ ___/ ___/ __ \\\r\n" + 
				" / /_/ /  __/ /_/ / /_/ / / /  / __/ / / /  (__  ) /_    ___/ /  __/ /_/ / /  / /__/ / / /\r\n" + 
				"/_____/\\___/ .___/\\__/_/ /_/  /_/   /_/_/  /____/\\__/   /____/\\___/\\__,_/_/   \\___/_/ /_/ \r\n" + 
				"          /_/                                                                             \n";
                //You should add the result to the String variable "result" 
                //which is going to be printed to a text file
		if(endfound)
		{
                        //give path information first
			this.maze.resetGrid();
			Node<Maze> revertedTree = ((Stack<Node<Maze>>) this.frontier).pop();
			
			revertedTree = revertedTree.getFather().getFather();
			this.result += "Path: " + this.maze.getEnd().toString() + "(End) <- ";
			this.pathLength++;
			
			while(revertedTree.hasFather())
			{
				Maze temp = revertedTree.getContent();
				Square state = temp.getCurrState();
				
				if(!state.equals(this.maze.getEnd()))
				{
					this.result += state.toString() + " <- ";
					this.maze.getGrid()[state.getLine()][state.getCol()].setAttribute("*");
					this.pathLength++;
				}
				revertedTree = revertedTree.getFather();
			}
			
			this.result += this.maze.getStart().toString() + "(Start) \n" + "Path length: " + this.pathLength + "\nNumber of nodes created: " + this.nodesCounter + "\nExecution time: " + time/1000d + " seconds\n";
			this.result += this.maze.printMaze();
		}
		else
		{
			this.result += "Failed : Unable to go further and/or end is unreachable.";
		}
		
		return this.result;
	}
	
	public LinkedList<Node<Maze>> getNextSquares()
	{
		LinkedList<Node<Maze>> res = new LinkedList<Node<Maze>>();
		
		//Get 4 next squares
		LinkedList<Maze> nexts = this.maze.getCurrState().getNexts();
		
		for(int i = 0; i < nexts.size(); i++)
		{
			Square tempSq = nexts.get(i).getCurrState();
			if(!this.closedSquares.contains(tempSq))
			{
				Node<Maze> tempNode = new Node<Maze>(nexts.get(i));
				res.add(tempNode); //Add the state
			}
		}
		
		return res;
	}
	
	public String getResult()
	{
		if(result == "")
			return "No resolution computed, please use DFS_Solver.solve() first";
		else
			return this.result;
	}

	public AbstractCollection<Node<Maze>> getFrontier() 
	{
		return this.frontier;
	}
}

