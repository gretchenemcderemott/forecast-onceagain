package AppResourceTracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DemoProject {

	public static void main(String[] args) {
		List<String> headers1 = new ArrayList<>();
		headers1.add("          ");
		headers1.add("PercNumofWeeks");
			String[] hearders1array = new String[headers1.size()];
			for(int i=0; i<headers1.size(); i++){
				hearders1array[i] = headers1.get(i);
			}
			
		List<String> team = new ArrayList<>();
			team.add("bin");
			team.add("gem");
			team.add("brien");
			team.add("bonnie");
			team.add("lakeisha");
			String[] teamarray = new String[team.size()];
			for(int i=0; i<team.size(); i++){
				teamarray[i] = team.get(i);
			}
			
		List<String> headers = new ArrayList<>();
			headers.add("Pase");
			headers.add("DBA");
			headers.add("Dev Total");
			headers.add("DM Total");
			headers.add("Team Lead Total");
			headers.add("Scrum Master/PM Total");
			headers.add("Pase Total");
			headers.add("DBA Total");
			headers.add("Dev Only Total");
			headers.add("Org Total");
			headers.add("Total");
			
			//New ArrayList
	        ArrayList<String> mainHeaders = new ArrayList<String>();
	        mainHeaders.addAll(headers1);
	        mainHeaders.addAll(team);
	        mainHeaders.addAll(headers);
			String[] mainHeadersArray = new String[mainHeaders.size()];
			for(int i=0; i<mainHeaders.size(); i++){
				mainHeadersArray[i] = mainHeaders.get(i);
			}
	        
/*	        for(String x : mainHeadersArray){
	        	System.out.print(x + " || ");
	        }
	        
		System.out.println();*/

		

		
		
/*		String tempmonths;
		String tempheaders;
		for(int i = 0; i < 6; i++){
			tempmonths = monthsarray[i];
			System.out.print(tempmonths);
			for(int j = 0; j < 18; j++){
				tempheaders = mainHeadersArray[j];
				System.out.print(tempheaders);
				
			}
			
			System.out.println();
		}*/
		
		
			List<String> months = new ArrayList<>();
			months.add("jan");
			months.add("feb");
			months.add("mar");
			months.add("apr");
			months.add("may");
			months.add("jun");
			String[] monthsarray = new String[months.size()];
			for(int i=0; i<months.size(); i++){
				monthsarray[i] = months.get(i);
			}
		
		List<Integer> percnum = new ArrayList<>();
			percnum.add(0);
			percnum.add(0);
			percnum.add(3);
			percnum.add(4);
			percnum.add(1);
			percnum.add(0);
			int[] percnumarray = new int[percnum.size()];
			for(int i=0; i<percnum.size(); i++){
				percnumarray[i] = percnum.get(i);
				//System.out.println(percnumarray[i]);
			}
					
		List<Integer> percent = new ArrayList<>();
			percent.add(1);
			percent.add(1);
			int[] percentarray = new int[percent.size()];
			for(int i=0; i<percent.size(); i++){
				percentarray[i] = percent.get(i);
				//System.out.println(array1[i]);
			}
			
	        int arr[][]={{1,2,3},{1,2,3},{1,2,3}};
	        int sum=0;
	        for(int col=0;col<arr[0].length;col++){
	            for(int row=0;row<arr.length;row++){
	                sum+=arr[row][col];
	            }
	            System.out.println("Sum is "+sum);
	            sum=0;
	        }
	        
	        
/*	        int sumrows;
	        for (int i = 0; i < arr.length; i++)
	        {   
	          sumrows=0;
	          for (int j = 0; j < arr[0].length; j++)
	          {                
	            sumrows += arr[i][j]; 

	          }

	         System.out.println("Print the sum of rows =" + sumrows);
	        } 
	    }*/
			
/*			int temppercnum;
			int temppercent;
			int[][] result = new int[6][2];
			for(int i=0; i<6; i++){
				temppercnum = percnumarray[i];
				for(int j=0; j<2; j++){
					
					temppercent=percentarray[j];
					result[i][j] = (temppercnum * temppercent);
					System.out.print(result[i][j] + " ");
					//System.out.print("test");
				}
				System.out.println();
			}*/
			
			
			
			
/*			int temppercnum;
			int temppercent;
			Double[][] result = new Double[6][2];
			for(int i=0; i<6; i++){
				temppercnum = percnumarray[i];
				result[y] = temp3;
				System.out.print(monthsarray);
					for(int i=0; i<2; i++){
						temp1=percnumarray[i];
						result[i][j] = 
						System.out.print(temp1 + " ");
				}
				System.out.println();
			}*/
			
/*		List<Integer> percent = new ArrayList<>();
		percent.add(1);
		percent.add(2);
			int[] array2 = new int[percent.size()];
			for(int i=0; i<percent.size(); i++){
				array2[i] = percent.get(i);
				//System.out.println(array2[i]);
			}*/
			
		
		
			

		

	
	}
	
}




