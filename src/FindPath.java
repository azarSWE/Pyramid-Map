import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class contains the code needed to compute a path from the entrance 
 * of the pyramid to all the treasure chambers
 * @author azararya
 */
public class FindPath {
	
	// instance variable
	Map pyramidMap;    // a reference to an object of the provided class Map
	
	/**
	 * This constructor initializes instance variable pyramidMap with a string input parameter 
	 * @param fileName
	 * @throws InvalidMapCharacterException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public FindPath(String fileName) throws InvalidMapCharacterException, FileNotFoundException, IOException {
		try {
			Map mapObject = new Map(fileName);
			pyramidMap = mapObject;
		} 
		catch (InvalidMapCharacterException e) {e.getMessage();}
		catch (FileNotFoundException e) {e.getMessage();}		
		catch (IOException e) {e.getMessage();};
	}
	
	/**
	 * This method finds a path from the entrance to all the treasure chambers
	 * @return a path which is an object of DLStack class
	 */
	public DLStack path() {
		DLStack<Chamber> tempStack = new DLStack<Chamber>();
		Chamber startingChamber = pyramidMap.getEntrance();
		int N = pyramidMap.getNumTreasures();
		
		tempStack.push(startingChamber);
		startingChamber.markPushed();
		
		int treasureCount = 0;
		while (tempStack != null) {
			Chamber currentChamber = tempStack.peek();
			if (currentChamber.isTreasure()) {
				treasureCount ++;
				if (treasureCount == N) 
					break;
			}
			
			Chamber c = this.bestChamber(currentChamber);
			if (c != null) {
				tempStack.push(c);
				c.markPushed();
			} else 
				tempStack.pop().markPopped();
		}	
		return tempStack;
	}
	
	/**
	 * This method returns the value of pyramidMap
	 * @return instance variable pyramidMap which is an object in Map class
	 */
	public Map getMap() {
		return pyramidMap;
	}
	
	/**
	 * This method returns true if currentChamber is dim, returns false otherwise
	 * @param currentChamber
	 * @return boolean
	 */
	public boolean isDim(Chamber currentChamber) {
		if (!(currentChamber == null || currentChamber.isLighted() || currentChamber.isSealed())) {
				for (int i = 0; i <= 5; i++) {
					if (currentChamber.getNeighbour(i) != null) {
						if (currentChamber.getNeighbour(i).isLighted()) 
							return true;
					}
				}	
		}
		return false;
	}
	
	/**
	 * This method selects the best chamber to move to from currentChamber
	 * priority order is: treasure, lighted and dim  
	 * @param currentChamber
	 * @return best chamber
	 */
	public Chamber bestChamber(Chamber currentChamber) {
		Chamber neighborChamber;
		
		// check if there is any treasure chamber in neighbor
		for (int i = 0; i <= 5; i++) {
			neighborChamber = currentChamber.getNeighbour(i);
			if(neighborChamber != null) {
				if (neighborChamber.isTreasure() && !(neighborChamber.isMarked())) 
					return neighborChamber;
			}
		}
		
		// check if there is any lighted chamber in neighbor
		for (int j = 0; j <= 5; j++) {
			neighborChamber = currentChamber.getNeighbour(j);
			if(neighborChamber != null) {
				if (neighborChamber.isLighted() && !(neighborChamber.isMarked())) 
					return neighborChamber;
			}
		}
		
		// check if there is any dim chamber in neighbor
		for (int k = 0; k <= 5; k++) {
			neighborChamber = currentChamber.getNeighbour(k);
			if(neighborChamber != null) {
				if (this.isDim(neighborChamber) && !(neighborChamber.isMarked())) 
				return neighborChamber;
			}
		}
			
		return null;
	}
}
