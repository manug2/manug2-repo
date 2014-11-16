package happy;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class HappyFace {

    public static HappyFace createFromFile(String filePath, int elementsCount) {
        if (filePath==null || "".equals(filePath.trim()))
            throw new AssertionError("'filePath' is either null or blank");
        return new HappyFace(filePath, null, elementsCount);
    }

    public static HappyFace createFromString(String data, int elementsCount) {
        if (data==null || "".equals(data.trim()))
            throw new AssertionError("'data' to load face is either null or blank");
        return new HappyFace(null, data, elementsCount);
    }

    public static HappyFace createFromString(int elementsCount, String data) {
        if (data==null || "".equals(data.trim()))
            throw new AssertionError("'data' to load face is either null or blank");
        return new HappyFace(null, data, elementsCount);
    }
    private HappyFace(String filePath, String dataString, int elementsCount) {
        if (elementsCount < 1)
            throw new AssertionError("'elementsCount' cannot be less than 1");

        path = filePath;
        data = dataString;
        elements = elementsCount;
        id = generateId();
        matrix = null;
        columns = null;
        rotation = 0;
        previousMatrices = new LinkedList<int[][]>();
        if(path==null)
            name = "" + id;
        else {
            final String[] parts = this.path.split("/");
            final String fileName = parts[parts.length-1];
            final String[] nameParts = fileName.split("\\.");
            name =  nameParts[0];
        }
    }

    protected HappyFace(int elements, int id, int[][] matrix, int rotation, String name) {
        path = null;
        data = null;
        this.elements = elements;
        this.id = id;
        this.matrix = matrix;
        this.loaded = true;
        this.rotation = rotation;
        this.columns = new int[elements][elements];
        for (int i=0; i < elements; i++)
            for (int j=0; j< elements; j++)
                this.columns[i][j] = matrix[j][i];

        this.previousMatrices = new LinkedList<int[][]>();
        this.previousMatrices.add(this.matrix);
        this.name = name;
    }

    private boolean loaded = false;
    private final String path;
    private int[][] matrix;
    protected final int elements; //Number of elements per side in the square matrix
    private final String data;
    private final int id;
    private int[][] columns;
    private int rotation;
    protected final String name;

    protected final List<int[][]> previousMatrices;

    public boolean isLoaded() {
        return loaded;
    }

    public int[][] getMatrix() {
        if (!loaded)
            throw new RuntimeException("face data is not loaded. did you call load()?");
        else
            return matrix;
    }

    public void load() throws IOException {
        if(loaded)
            throw new AssertionError(String.format("attempt to load already loaded face [%s]", name));
        String[] lines;
        if (path!=null) {
            List<String> list = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            if (list==null || list.size()==0)
                throw new AssertionError(String.format("no data found in given face file [%s]", path));
            else
                lines = list.toArray(new String[list.size()]);
        } else if (data!=null)
            lines = data.split(";");
        else
            throw new AssertionError(String.format("no data or file provided to load data"));
        if (lines.length != elements)
            throw new AssertionError(String.format("incomplete data found, while loading face [%s]; should have [%d] rows", name, elements));

        loadInternal(lines);
        loaded = true;
    }

    private void loadInternal(String[] validLines) {
        matrix = new int[elements][elements];
        columns = new int[elements][elements];
        for (int j=0; j < elements; j++) {
            String[] row = validLines[j].trim().split(" ");
            if (row.length !=elements)
                throw new AssertionError(String.format("incomplete data found, should have [%d] rows with only have '0' and '1' separated by space", elements));
            for (int i=0; i < elements; i++ ) {
                int rowSum=0, colSum=0;
                try {
                    int item = Integer.parseInt(row[i]);
                    if (item!=0 && item!=1)
                        throw new AssertionError(String.format("incorrect data format foundat row [%d], should only have '0' and '1'", j));
                    else {
                        matrix[j][i] = item;
                        columns[i][j] = item;
                    }
                } catch (NumberFormatException e) {
                    throw new AssertionError(String.format("incorrect data format found at row [%d], should only have '0' and '1'", j));
                }
            }
        }
        for (int j=0; j < elements; j++) {
            int rowSum = 0, colSum = 0;
            for (int i=0; i < elements; i++ ) {
                rowSum += matrix[j][i];
                colSum += columns[j][i];
            }
            if(rowSum==0)
                throw new AssertionError(String.format("face [%s, %d] has all items in row [%d] as '0'", name, rotation, j));
            else if(colSum==0)
                throw new AssertionError(String.format("face [%s, %d] has all items in column [%d] as '0'", name, rotation, j));
        }
        this.previousMatrices.clear();
        this.previousMatrices.add(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        if (!loaded) {
            builder.append('[').append(name).append(',').append(this).append("]");
        } else {
            builder.append('[').append(name).append(',').append(rotation).append(',').append(System.identityHashCode(this)).append("]");
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                for (int j=0; j < elements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    public void print() {
        System.out.println(this.toString());
    }

    public int elementCount() {
        return elements;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        else if (other == this)
            return true;
        else if (! (other instanceof HappyFace))
            return false;

        HappyFace o = (HappyFace) other;
        return this.id == o.id;
    }

    public boolean identical(HappyFace other) {
        if (other == null)
            return false;

        if ( !(other.loaded && this.loaded ))
            throw new RuntimeException("cannot compare two faces before loading. did you call load()?");

        int[][] mat1 = this.getMatrix();
        int[][] mat2 = this.getMatrix();
        return Arrays.equals(mat1, mat2);
    }

    private static int _identifier=0;
    private static int generateId() {
        int id;
        synchronized (HappyFace.class) {
            id = _identifier++;
        }
        return id;
    }

    public int identifier() {
        return id;
    }

    public int[] getRows(int index) {
        if (!loaded)
            throw new RuntimeException("face data is not loaded. did you call load()?");
        if (index >= elements)
            throw new RuntimeException(String.format("face number of elements is limited to [%d], but requested for [%d]", elements, index));
        return matrix[index];
    }

    public int[] getColumns(int index) {
        if (!loaded)
            throw new RuntimeException("face data is not loaded. did you call load()?");
        if (index >= elements)
            throw new RuntimeException(String.format("face number of elements is limited to [%d], but requested for [%d]", elements, index));
       return columns[index];
    }

    public HappyFace rotateCW() {
        //simple rotation clock-wise

        int[][] m = new int[elements][elements];

        for (int i=0; i < elements; i++) {
            for (int j=0; j < elements; j++) {
                m[i][j] = matrix[elements-1-j][i];
            }
        }

        return new HappyFace(elements, id, m, rotation+1, name);
    }

    public HappyFace flip() {
        int[][] m = new int[elements][elements];

        for (int i=0; i < elements; i++) {
            for (int j=0; j < elements; j++) {
                m[i][j] = matrix[i][elements-1-j];
            }
        }

        return new HappyFace(elements, id, m, rotation + 1, name);
    }

    public int getRotation() {
        return rotation;
    }

    public HappyFace rotate() {
        HappyFace newFace = this;
        do {
            newFace = newFace.rotateSimple();
            for (int[][] previous : previousMatrices ) {
                if (Arrays.deepEquals(newFace.matrix, previous))
                    break;
                else
                    newFace.previousMatrices.add(previous);
            }
        } while (newFace.previousMatrices.size() <= this.previousMatrices.size());
        return newFace;
    }
    public HappyFace rotateSimple() {
        if (rotation<0)
            throw new InvalidRotationException(String.format("Invalid state of face, rotation index should have been >= 0 but was [%d]", rotation));
        else if (rotation>=7)
            throw new InvalidRotationException(String.format("Invalid rotation request, already at last permutation"));

        HappyFace newFace;
        if ((rotation%2) == 0) {
            newFace = flip();
        } else {
            //Un-flip the last flip and then rotate
            newFace = flip();
            newFace = newFace.rotateCW();
            newFace.rotation = newFace.rotation - 1;
            //Counter incremented once each in flipping and rotateCW, hence need to reduce
        }
        return newFace;
    }
    /* auto-rotate sequence

                                                  O (Original)
                                                     | (0)
                                          ___________|____________
                                         | (2)                    | (1)
                                   CW(O) (clock-wise)        F(O) (Flip or mirror image)
                              ___________|____________
                             | (4)                    | (3)
                         CW(CW(O))                 F(CW(O))
                  ___________|____________
                 | (6)                    | (5)
             CW(CW((CW(O)))           F(CW(CW(O)))
                 |___________
                            | (7)
                     F(CW(CW((CW(O))))
     */

    public void match(HappyFace other, FaceDirection direction) {
        if (equals(other) || other==null)
            throw new RuntimeException("other face is null, cannot attempt matching");

        int sum = 0;
        int[] side1, side2;

        if (direction == FaceDirection.Left) {
            side1 = getColumns(0);
            side2 = other.getColumns(elements-1);
        }
        else if (direction == FaceDirection.Bottom) {
            side1 = getRows(elements-1);
            side2 = other.getRows(0);
        }
        else if (direction == FaceDirection.Right) {
            side1 = getColumns(elements-1);
            side2 = other.getColumns(0);
        }
        else if (direction == FaceDirection.Top) {
            side1 = getRows(0);
            side2 = other.getRows(elements-1);
        } else
            throw new FaceNotMatchingException(String.format("face [%s, %d] not matching when attached on side [%s], direction not known", other.name, other.rotation, direction));

        if(side1[0] + side2[0] > 1)
            throw new FaceNotMatchingException(String.format("face [%s, %d] not matching when attached on side [%s] because of edge element [%d]", other.name, other.rotation, direction, 0));
        if(side1[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s, %d] not matching when attached on side [%s] because of edge element [%d]", other.name, other.rotation, direction, elements-1));

        for (int i=1; i < elements-1; i++) {
            int ts = side1[i] + side2[i];
            if (ts != 1)
                throw new FaceNotMatchingException(String.format("face [%s, %d] not matching when attached on side [%s] because of element [%d]", other.name, other.rotation, direction, i));
        }
    }

    public HappyFace clone() {
        HappyFace newFace = new HappyFace(elements, id, matrix, rotation, name);
        for (int j=1; j< previousMatrices.size(); j++)
            newFace.previousMatrices.add(previousMatrices.get(j));
        return newFace;
    }

    public static int[] reverseArray(int[] input) {
        if (input==null)
            throw new AssertionError("input for reversing is null");
        int[] output = new int[input.length];
        for (int i=0; i < input.length; i++)
            output[i] = input[input.length-1-i];
        return output;
    }

    public String name() {
        return name;
    }

    public HappyFace cleanClone() {
        return new HappyFace(elements, id, matrix, rotation, name);
    }

    public List<int[][]> getPreviousMatrices() {
        return previousMatrices;
    }

}
