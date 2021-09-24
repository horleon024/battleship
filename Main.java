package battleship;

import java.util.Scanner;

public class Main {
    
	public static char[][] initMap() {
		char[][] map = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = '~';
            }
        }
		return map;
	}
	
	public static void printMap(char[][] map, boolean isStartLine, boolean isEndLine) {
		if (isStartLine) {
			System.out.println();
		}
		System.out.print("  ");
		for (int i = 1; i <= 10; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		char c = 'A';
		for (int i = 0; i < 10; i++) {	
			System.out.print(c + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(map[i][j] + " ");
            }
			System.out.println();
			c++;
        }
		if (isEndLine) {
			System.out.println();
		}
	}

	public static boolean updateMapHorizontal(String[] array, char[][] map, int nbOfCells, String shipName) {
		int start = Integer.parseInt(array[0].substring(1));
		int end = Integer.parseInt(array[1].substring(1));
		int row = array[0].charAt(0) - 'A';
		if (row < 0 || row > 9) {
			System.out.println("\nError! Wrong ship location! Try again:\n");
			return false;
		}
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		int startRow = row == 0 ? 0 : row - 1;
		int endRow = row == 9 ? 9 : row + 1;
		int startColl = start == 1 ? 0 : start - 2;
		int endColl = end == 10 ? 9 : end;
		if (nbOfCells != end - start + 1) {
			System.out.println("\nError! Wrong length of the " + shipName + "! Try again:\n");
			return false;
		}
		if (start > 11 - nbOfCells || start < 0 || end < 0 || end > 10) {
			System.out.println("\nError! Wrong ship location! Try again:\n");
			return false;
		}
		for (int j = startRow; j <= endRow; j++) {
			for (int i = startColl; i <= endColl; i++) {
				if (map[j][i] != '~') {
					System.out.println("\nError! You placed it too close to another one. Try again:\n");
					return false;
				}
			}
		}
		for (int i = start - 1; i < end; i++) {
			map[row][i] = 'O';
		}
		
		return true;
	}

	public static boolean updateMapVertical(String[] array, char[][] map, int nbOfCells, String shipName) {
		int start = array[0].charAt(0) - 'A';
		int end = array[1].charAt(0) - 'A';
		int coll = Integer.parseInt(array[0].substring(1));
		if (coll < 0 || coll > 10) {
			System.out.println("\nError! Wrong ship location! Try again:\n");
			return false;
		}
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		int startRow = start == 0 ? 0 : start - 1;
		int endRow = end == 9 ? 9 : end + 1;
		int startColl = coll == 1 ? 0 : coll - 2;
		int endColl = coll == 10 ? 9 : coll;
		if (nbOfCells != end - start + 1) {
			System.out.println("\nError! Wrong length of the " + shipName + "! Try again:\n");
			return false;
		}
		if (start > 10 - nbOfCells || start < 0 || end < 0 || end > 9) {
			System.out.println("\nError! Wrong ship location! Try again:\n");
			return false;
		}
		for (int j = startColl; j <= endColl; j++) {
			for (int i = startRow; i <= endRow; i++) {
				if (map[i][j] != '~') {
					System.out.println("\nError! You placed it too close to another one. Try again:\n");
					return false;
				}
			}
		}
		for (int i = start; i <= end; i++) {
			map[i][coll - 1] = 'O';
		}
		
		return true;
	}

	public static boolean checkCoordinates(String coordinates, char[][] map, int nbOfCells, String shipName) {
		String[] array = coordinates.split(" ");
		if (array.length != 2) {
			return false;
		}
		if (array[0].charAt(0) == array[1].charAt(0)) {
			return updateMapHorizontal(array, map, nbOfCells, shipName);
		} else if (array[0].substring(1).equals(array[1].substring(1))) {
			return updateMapVertical(array, map, nbOfCells, shipName);
		} else {
			System.out.println("\nError! Wrong ship location! Try again:\n");
			return false;
		}
	}

	public static String putShip(char[][] map, String shipName, int shipLength, Scanner scanner) {	
		System.out.println("Enter the coordinates of the " + shipName + " (" + shipLength + " cells):");
		System.out.println();
		String coordinates = scanner.nextLine();
		while (!checkCoordinates(coordinates, map, shipLength, shipName)) {
			coordinates = scanner.nextLine();
		}
		printMap(map, true, true);
		return coordinates;
	}

	public static String putShips(char[][] map, Scanner scanner) {
		printMap(map, false, true);
		String shipCoordinates = "";
		shipCoordinates += putShip(map, "Aircraft Carrier", 5, scanner) + "  ";
		shipCoordinates += putShip(map, "Battleship", 4, scanner) + "  ";
		shipCoordinates += putShip(map, "Submarine", 3, scanner) + "  ";
		shipCoordinates += putShip(map, "Cruiser", 3, scanner) + "  ";
		shipCoordinates += putShip(map, "Destroyer", 2, scanner);
		return shipCoordinates;	
	}

	public static boolean checkShipHorizontal(String[] coordinate, char[][] map) {
		int start = Integer.parseInt(coordinate[0].substring(1));
		int end = Integer.parseInt(coordinate[1].substring(1));
		int row = coordinate[0].charAt(0) - 'A';
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		for (int i = start - 1; i < end; i++) {
			if (map[row][i] == 'O') {
				return false;
			}
		}
		return true;
	}

	public static boolean checkShipVertical(String[] coordinate, char[][] map) {
		int start = coordinate[0].charAt(0) - 'A';
		int end = coordinate[1].charAt(0) - 'A';
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		int coll = Integer.parseInt(coordinate[0].substring(1));
		for (int i = start; i <= end; i++) {
			if (map[i][coll - 1] == 'O') {
				return false;
			}
			
		}
		return true;
	}

	public static int checkDestoyedShips(String[] coordinates, char[][] map) {
		String[] coordinate;
		boolean destroyed = false;
		for (int i = 0; i < coordinates.length; i++) {
			if (coordinates[i] != null) {
				coordinate = coordinates[i].split(" ");
				if (coordinate[0].charAt(0) == coordinate[1].charAt(0)) {
					destroyed = checkShipHorizontal(coordinate, map);
				} else if (coordinate[0].substring(1).equals(coordinate[1].substring(1))) {
					destroyed = checkShipVertical(coordinate, map);
				}
				if (destroyed) {
					coordinates[i] = null;
					for (int j = 0; j < coordinates.length; j++) {
						if (coordinates[j] != null) {
							return 1;
						}
					}
					return 2;
				}
			}			
		}
		return 0;
	}

	public static boolean tryToShoot(char[][] map1, char[][]map2, char[][] fogMap, String[] shipCoordinates, Scanner scanner, int playerNb) {
		printBothMap(map1, fogMap);
		System.out.println("Player " + playerNb + ", it's your turn:\n");
		String coordinates = scanner.nextLine();
		int x = coordinates.charAt(0) - 'A';
		int y = Integer.parseInt(coordinates.substring(1)) - 1;
		if (x < 0 || x > 9 || y < 0 || y > 9) {
			scanner.close();
			return false;
		}
		if (map2[x][y] == '~') {
			map2[x][y] = 'M';
			System.out.println("\nYou missed!");
		} else {
			map2[x][y] = 'X';
			int destroyed = checkDestoyedShips(shipCoordinates, map2);
			if (destroyed == 0) {
				System.out.println("\nYou hit a ship!");
			} else if (destroyed == 1) {
				System.out.println("\nYou sank a ship!");
			}		
		}
		return true;
	}

	public static void takeShot(char[][] map1, char[][]map2, char[][] fogMap, String[] coordinates, Scanner scanner, int playerNb) {
		while (!tryToShoot(map1, map2, fogMap, coordinates, scanner, playerNb)) {
			System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
		}
	}

	public static boolean didTheGameEnd(char[][] map) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (map[i][j] == 'O') {
					return false;
				}
			}
		}
		return true;
	}

	public static void passTurn(Scanner scanner) {
		System.out.println("Press Enter and pass the move to another player");
		scanner.nextLine();
		for (int i = 0; i < 10; i++) {
			System.out.println();
		}
	}

	public static void printBothMap(char[][] map, char[][] fogmap) {
		printMap(fogmap, false, false);
		System.out.println("---------------------");
		printMap(map, false, true);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		char[][] map1 = initMap();
		char[][] map2 = initMap();
		char[][] fogMap = initMap();
		System.out.println("Player 1, place your ships on the game field\n");
		String[] coordinates1 = putShips(map1, scanner).split("  ");
		passTurn(scanner);
		System.out.println("Player 2, place your ships on the game field\n");
		String[] coordinates2 = putShips(map2, scanner).split("  ");
		passTurn(scanner);
		while (true) {
			takeShot(map1, map2, fogMap, coordinates2, scanner, 1);
			if (didTheGameEnd(map2)) {
				break ;
			}
			passTurn(scanner);
			takeShot(map2, map1, fogMap, coordinates1, scanner, 2);
			if (didTheGameEnd(map1)) {
				break ;
			}
			passTurn(scanner);
		}
		System.out.println("You sank the last ship. You won. Congratulations!");
		scanner.close();
	}
}
