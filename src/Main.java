import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static int w = 0;
	static int h = 0;
	static int[][] map;
	
	public static void main(String[] args) {
		readInputs();
		List<Integer> longestSkiiPath = findBestSkiiPath();
		
		int pathLength = longestSkiiPath.size();
		int largestDrop = longestSkiiPath.get(0) - longestSkiiPath.get(pathLength - 1);
		
		System.out.println("lenght : " + pathLength + " drop : " + largestDrop);
		
		System.out.println("Done!");
	}
	
	public static void readInputs() {
		Scanner scanner = new Scanner(System.in);
		w = scanner.nextInt();
		h = scanner.nextInt();
		
		if (w <= 0 || h <= 0) {
			System.out.println("Incorrect map size!");
			scanner.close();
			return;
		}
		
		map = new int[w][h];
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				map[x][y] = scanner.nextInt();
			}
		}
		scanner.close();
	}
	
	public static List<Integer> findBestSkiiPath() {
		List<List<Integer>> bestPaths = new ArrayList<>();
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				List<List<Integer>> allPathsFromCurrentPoint = new ArrayList<>();
				List<Integer> currentPath = new ArrayList<>();			
				
				currentPath.add(map[x][y]);
				findPathsForPoint(x, y, currentPath, allPathsFromCurrentPoint);
				
				List<Integer> bestPathForCurrentPoint = getBestPath(allPathsFromCurrentPoint);		
				bestPaths.add(bestPathForCurrentPoint);
				//printPath(bestPathForCurrentPoint);
			}		
		}
		
		//getBestofBestPaths
		return getBestPath(bestPaths);
	}
	
	public static List<Integer> getBestPath(List<List<Integer>> currentPaths) {
		List<Integer> bestPath = null;		
		for (int i = 0; i < currentPaths.size(); i++) {
			if (bestPath == null)
				bestPath = currentPaths.get(i);
			else if (bestPath.size() < currentPaths.get(i).size()) 
				bestPath = currentPaths.get(i);
			else {
				int bestPathDepth = bestPath.get(0) - bestPath.get(bestPath.size() - 1);
				int currentPathDepth = currentPaths.get(i).get(0) - currentPaths.get(i).get(currentPaths.get(i).size() - 1);
				if (bestPathDepth < currentPathDepth)
					bestPath = currentPaths.get(i);
			}
		} 
		return bestPath;
	}
	
	public static void findPathsForPoint(int x, int y, List<Integer> currentPath, List<List<Integer>> allPathsFromCurrentPoint) {
		int left = x - 1;
		int right = x + 1;
		int up = y - 1;
		int down = y + 1;
		
		if (!(((left >= 0) && (map[left][y] < map[x][y])) || ((right < w) && (map[right][y] < map[x][y])) ||
			((up >= 0) && (map[x][up] < map[x][y])) || ((down < h) && (map[x][down] < map[x][y])))) {				
			//printPath(currentPath);
			allPathsFromCurrentPoint.add(currentPath);
			return;
		}
		
		if ((left >= 0) && (map[left][y] < map[x][y])) {
			List<Integer> newPath = addToNewList(currentPath, map[left][y]);
			findPathsForPoint(left, y, newPath, allPathsFromCurrentPoint);
		}
		
		if ((right < w) && (map[right][y] < map[x][y])) {
			List<Integer> newPath = addToNewList(currentPath, map[right][y]);
			findPathsForPoint(right, y, newPath, allPathsFromCurrentPoint);
		}
		
		if ((up >= 0) && (map[x][up] < map[x][y])) {
			List<Integer> newPath = addToNewList(currentPath, map[x][up]);
			findPathsForPoint(x, up, newPath, allPathsFromCurrentPoint);
		}
		
		if ((down < h) && (map[x][down] < map[x][y])) {
			List<Integer> newPath = addToNewList(currentPath, map[x][down]);
			findPathsForPoint(x, down, newPath, allPathsFromCurrentPoint);
		}	
		
	}
	
	public static List<Integer> addToNewList(List<Integer> oldList, int value){
		List<Integer> newList = new ArrayList<>();
		oldList.stream().forEach(i -> newList.add(i));
		newList.add(value);
		return newList;
	}
	
	public static void printPath(List<Integer> path) {
		path.stream().forEach(i->System.out.print(i + " "));
		System.out.println("");
	}
	
	public static void printMap() {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				System.out.print(map[x][y] + " ");
			}
			System.out.println("");
		}
	}

}
