
public class MineTable {
	private static final int MINE = -1;
	private int[][] numbers;
	private int numMines;
	public MineTable(int row, int column, int numMines) {
		this.numMines = numMines;
		numbers = new int[row][column];
		prepare();
	}
	private void prepare() {
		for(int i = 0; i < numMines; i++) {
			int row = (int) (Math.random()*numbers.length);
			int column = (int) (Math.random()*numbers[0].length);
			if(!isMine(row,column)) {
				numbers[row][column] = MINE;
				increaseNumber(row-1,column-1);
				increaseNumber(row-1,column);
				increaseNumber(row-1,column+1);
				increaseNumber(row+1,column-1);
				increaseNumber(row+1,column);
				increaseNumber(row+1,column+1);
				increaseNumber(row,column-1);
				increaseNumber(row,column+1);
			}else {
				i--;
			}
		}
	}
	public int getNumber(int row, int column) {
		return numbers[row][column];
	}
	public int getNumMines() {
		return numMines;
	}
	private void increaseNumber(int row, int column) {
		if(isInTable(row,column) && !isMine(row,column))
			numbers[row][column]++;
	}
	private boolean isInTable(int row, int column) {
		if(row>=0 && column>=0 && row<numbers.length && column<numbers[0].length)
			return true;
		else
			return false;
	}
	private boolean isMine(int row, int column) {
		if(numbers[row][column]==MINE)
			return true;
		else
			return false;
	}
}