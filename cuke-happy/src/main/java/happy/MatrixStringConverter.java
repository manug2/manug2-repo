package happy;

public class MatrixStringConverter {

    public String convert(int[][] matrix) {
        final int numOfElements = matrix.length;
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i=0; i < numOfElements; i++) {
            builder.append('[');
            for (int j=0; j < numOfElements-1; j++)
                builder.append(matrix[i][j]).append(' ');
            builder.append(matrix[i][numOfElements-1]);
            builder.append(']');
        }
        builder.append(']');
        return builder.toString();
    }

    public int[][] convert(String str) {
        if(str==null)
            throw new AssertionError("cannot convert a null string");
        String newStr = str.replaceAll("\\[", "").replaceAll("]",";").replace(";;","");
        String[] lines = newStr.split(";");
        MatrixBuilder builder = new MatrixBuilder();
        return builder.numOfElements(lines.length).usingLines(lines).matrix;
    }

}
