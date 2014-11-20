package happy;

public class MatrixBuilder {

    public int[][] build(String[] lines) {
        return usingLines(lines).check().matrix;
    }

    int[][] matrix;
    int numOfElements;
    public MatrixBuilder numOfElements(int numOfElements) {
        this.numOfElements = numOfElements;
        return this;
    }
    public MatrixBuilder usingLines(String[] lines) {
        parseStrings(lines);
        return this;
    }

    public MatrixBuilder check() {
        checkInternalElements();
        checkRowsAndColumns();
        return this;
    }

    private final void checkInternalElements() {
        for (int i = 1; i < numOfElements - 1; i++)
            for (int j = 1; j < numOfElements - 1; j++)
                if (matrix[i][j] != 1)
                    throw new AssertionError(String.format("face not filled, element [%d][%d]", i, j));
    }

    private final void checkRowsAndColumns() {
        for (int j=0; j < numOfElements; j++) {
            int rowSum = 0;
            for (int i=0; i < numOfElements; i++ ) {
                rowSum += matrix[j][i];
            }
            if(rowSum==0)
                throw new AssertionError(String.format("face has all items in row [%d] as '0'", j));
        }
        for (int j=0; j < numOfElements; j++) {
            int colSum = 0;
            for (int i=0; i < numOfElements; i++ ) {
                colSum += matrix[i][j];
            }
            if(colSum==0)
                throw new AssertionError(String.format("face has all items in column [%d] as '0'", j));
        }
    }

    private MatrixBuilder parseStrings(final String[] validLines) {
        if(numOfElements == 0)
            throw new AssertionError("cannot build without knowing numberOfElements");
        else if(numOfElements < 2)
            throw new AssertionError("cannot build with less than 2 elements");
        else if (validLines.length != numOfElements)
            throw new AssertionError(String.format("cannot build without [%d] lines", numOfElements));

        matrix = new int[numOfElements][numOfElements];
        for (int j=0; j < numOfElements; j++) {
            String[] row = validLines[j].trim().split(" ");
            if (row.length != numOfElements)
                throw new AssertionError(String.format("incomplete data found, should have [%d] rows with only have '0' and '1' separated by space", numOfElements));
            for (int i=0; i < numOfElements; i++ ) {
                try {
                    int item = Integer.parseInt(row[i]);
                    if (item!=0 && item!=1)
                        throw new AssertionError(String.format("incorrect data format found at row [%d], should only have '0' and '1'", j));
                    else
                        matrix[j][i] = item;
                } catch (NumberFormatException e) {
                    throw new AssertionError(String.format("incorrect data format found at row [%d], should only have '0' and '1'", j));
                }
            }
        }
        return this;
    }

}
