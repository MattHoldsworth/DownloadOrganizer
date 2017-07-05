package file_Organizer;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;

public class Main {
	
	public static File downloads = new File("C:\\Users\\Matt\\Downloads\\");
	public static File tvShows = new File("D:\\Tv Shows\\");
	public static File[] content;
	public static String fileName;
	public static String[] joinedFileName;
	public static int endTitleIndex;
	public static String seasonPattern = "[sS]\\d{2}";
	public static String episodePattern = "[eE]\\d{2}";
	public static String bothPatterns = seasonPattern + episodePattern;
	public static String title;
	public static String season;
	public static String episode;
	public static String seasonAndEpisode;
	public static File destination;
	
	public static void main(String[] args) {
		
		//If downloads directory exists... continue
		if(downloads.isDirectory()) {
			//Expand to include sub directories of downloads
			//Adds files to content array
			content = downloads.listFiles();
			//For each file in downloads... continue
			for(File file : content) {
		    	
				//** Test code ** - visual output
				//Print out file names as strings, visual indication of successful move
				fileName = (file.getName().toString());
				System.out.print(fileName);
		    	
		    	if (fileName.contains(".")) {
			    	//Split file names at "."
				    joinedFileName = fileName.split("\\.");
		    	} else if (fileName.contains(" ")) {
		    		joinedFileName = fileName.split("\\s+");
		    	}//end if-else
		    	
				//Pattern match to get occurrence of season and episode and output as full words
				for (int i = 0; i < joinedFileName.length; i++){
					if (joinedFileName[i].matches(seasonPattern)) {
						season = joinedFileName[i].substring(0,1).toUpperCase() + "eason " + joinedFileName[i].substring(1);
						episode = joinedFileName[i + 1].substring(0,1).toUpperCase() + "pisode " + joinedFileName[i + 1].substring(1);
						endTitleIndex = i;
					} else if (joinedFileName[i].matches(episodePattern)) {
						season = joinedFileName[i - 1].substring(0,1).toUpperCase() + "eason " + joinedFileName[i - 1].substring(1);
						episode = joinedFileName[i].substring(0,1).toUpperCase() + "pisode " + joinedFileName[i].substring(1);
						endTitleIndex = i - 1;
					} else if (joinedFileName[i].matches(bothPatterns)){
						season = joinedFileName[i].substring(0,1).toUpperCase() + "eason " + joinedFileName[i].substring(1,3);
						episode = joinedFileName[i].substring(3,4).toUpperCase() + "pisode " + joinedFileName[i].substring(4);
						seasonAndEpisode = season + " " + episode;
						endTitleIndex = i;
					}//end if-else
				 }//end for loop
				    
				 //Clear title variable
				 title = "";
				    
				 //Adds each word of the title together with capitalization and trims the whitespace
				 for (int i = 0; i < endTitleIndex; i++){
					 title += joinedFileName[i].substring(0, 1).toUpperCase() 
				    		+ joinedFileName[i].substring(1).toLowerCase() + " ";
				 }//end for loop
				 title = title.trim();    
		    	
//				 //** Test code ** - visual output
//				 //Prints out file name elements as strings, visual indication of successful move
//				 System.out.println("");
//				 System.out.println(title);
//				 System.out.println(seasonAndEpisode);
//				 System.out.println(season);
//				 System.out.println(episode);
			    
        		//Make a new destination file for each file in content array
        		destination = new File(tvShows.toString() + "\\" + title + "\\" + season + "\\" 
        		+ title + ", " + seasonAndEpisode + ".mp4");
        		
			    //If both directories exist... move files
		        if (tvShows.isDirectory()) {
		        	try {
						//Move file from initial file path to destination file path
		        		Files.move(file.toPath(), destination.toPath());
		        	} catch (IOException e) {
						e.printStackTrace();
					}//end try-catch block
		        }//end if statement
		    }//end for-each loop
		}//end if statement
	}//end Main
	
}//end class
