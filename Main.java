/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 4hm3t
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{   
            Maze mazeFromText = new Maze("./maze.txt");
            
            BFS_Solver b2 = new BFS_Solver(mazeFromText);
            DFS_Solver d2 = new DFS_Solver(mazeFromText);
                
            String b2res=b2.solve();
            String d2res=d2.solve();
                
            Path b2path = Paths.get("./bfs.txt");
            Path d2path2 = Paths.get("./dfs.txt");
            
            try 
            {
                // Now calling Files.writeString() method
                // with path , content & standard charsets
                Files.writeString(b2path, b2res, StandardCharsets.UTF_8);
                Files.writeString(d2path2, d2res, StandardCharsets.UTF_8);
            }            
            // Catch block to handle the exception
            catch (IOException ex) 
            {
                // Print messqage exception occurred as
                // invalid. directory local path is passed
                System.out.print("Invalid Path");
            }
            
            System.out.println("Completed!");
	}
}

