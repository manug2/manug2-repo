package happy;

import java.nio.file.Files;
import java.util.Arrays;
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
    }

    protected HappyFace(int elements, int id, int[][] matrix) {
        path = null;
        data = null;
        this.elements = elements;
        this.id = id;
        this.matrix = matrix;
        this.loaded = true;
        this.rotation = -1;
        this.columns = new int[elements][elements];
        for (int i=0; i < elements; i++)
            for (int j=0; j< elements; j++)
                this.columns[i][j] = matrix[j][i];
    }

    private boolean loaded = false;
    private final String path;
    private int[][] matrix;
    protected final int elements; //Number of elements per side in the square matrix
    private final String data;
    private final int id;
    private int[][] columns;
    private int rotation;

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
            throw new AssertionError(String.format("attempt to load already loaded face [%d]", id));
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
            throw new AssertionError(String.format("incomplete data found, should have [%d] rows", elements));

        loadInternal(lines);
        loaded = true;
    }

    private void loadInternal(String[] validLines) {
        matrix = new int[elements][elements];
        columns = new int[elements][elements];
        for (int j=0; j < elements; j++) {
            String[] row = validLines[j].split(" ");
            if (row.length !=elements)
                throw new AssertionError(String.format("incomplete data found, should have [%d] rows with only have '0' and '1' separated by space", elements));
            for (int i=0; i < elements; i++ ) {
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
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        if (!loaded) {
            builder.append('[').append(id).append(',').append(this).append("]");
        } else {
            builder.append('[').append(id).append(',').append(System.identityHashCode(this)).append("]");
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
            id = ++_identifier;
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
        return new HappyFace(elements, id, m);
    }

    public HappyFace rotateCC() {
        //simple rotation counter-clock-wise
        int[][] m = new int[elements][elements];

        for (int i=0; i < elements; i++) {
            for (int j=0; j < elements; j++) {
                m[i][j] = matrix[j][elements-1-i];
            }
        }
        return new HappyFace(elements, id, m);
    }

    public HappyFace flip() {
        //following implementation maybe wrong
        //ideally row 1 should become last row and second becomes second last..
        int[][] m = new int[elements][elements];

        for (int i=0; i < elements; i++) {
            for (int j=0; j < elements; j++) {
                m[i][j] = 1 - matrix[i][j];
            }
        }
        return new HappyFace(elements, id, m);
    }

    public int getRotation() {
        return rotation;
    }

    public HappyFace rotate() {
        //May optimise if two or more rotations lead to same matrix
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
        }
        newFace.rotation = rotation + 1;
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
            for (int i=0; i < elements; i++) {
                int ts = side1[i] + side2[i];
                if (ts <= 1)
                    sum += ts;
                else
                    throw new FaceNotMatchingException(String.format("face [%d, %d] not matching when attached on side [%s] because of element [%d]", id, rotation, direction, i));
            }
        }
        else if (direction == FaceDirection.Bottom) {
            side1 = getRows(elements-1);
            side2 = other.getRows(0);
            for (int i=0; i < elements; i++) {
                int ts = side1[i] + side2[i];
                if (ts <= 1)
                    sum += ts;
                else
                    throw new FaceNotMatchingException(String.format("face [%d, %d] not matching when attached on side [%s] because of element [%d]", id, rotation, direction, i));
            }
        }
        else if (direction == FaceDirection.Right) {
            side1 = getColumns(elements-1);
            side2 = other.getColumns(0);
            for (int i=0; i < elements; i++) {
                int ts = side1[i] + side2[i];
                if (ts <= 1)
                    sum += ts;
                else
                    throw new FaceNotMatchingException(String.format("face [%d, %d] not matching when attached on side [%s] because of element [%d]", id, rotation, direction, i));
            }
        }
        else if (direction == FaceDirection.Top) {
            side1 = getRows(0);
            side2 = other.getRows(elements-1);
            for (int i=0; i < elements; i++) {
                int ts = side1[i] + side2[i];
                if (ts <= 1)
                    sum += ts;
                else
                    throw new FaceNotMatchingException(String.format("face [%d, %d] not matching when attached on side [%s] because of element [%d]", id, rotation, direction, i));
            }
        } else
            throw new FaceNotMatchingException(String.format("face [%d, %d] not matching when attached on side [%s], direction not known", id, rotation, direction));

        if (sum>elements)
            throw new FaceNotMatchingException(String.format("face [%d, %d] not matching when attached on side [%s] because of overlapping elements", id, rotation, direction));

    }

    public HappyFace clone() {
        return new HappyFace(elements, id, matrix);
    }

    public HappyFace verticalFlip() {
        int[][] m = new int[elements][elements];

        for (int i=0; i < elements; i++) {
            for (int j=0; j < elements; j++) {
                m[i][j] = matrix[i][elements-1-j];
            }
        }

        return new HappyFace(elements, id, m);
    }

}
