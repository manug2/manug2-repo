package happy;

import java.awt.dnd.InvalidDnDOperationException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: 1301121
 * Date: 11/5/14
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class HappyFace {

    public HappyFace(String filePath, int elementsCount) {
        if (filePath==null || "".equals(filePath.trim()))
            throw new AssertionError("'filePath' is either null or blank");
        if (elementsCount < 1)
            throw new AssertionError("'elementsCount' cannot be less than 1");

        path = filePath;
        matrix = null;
        elements = elementsCount;
        data = null;
    }

    public static HappyFace createFromString(String data, int elementsCount) {
        return new HappyFace(null, data, elementsCount);
    }
    private HappyFace(String filePath, String data, int elementsCount) {
        if (data==null || "".equals(data.trim()))
            throw new AssertionError("'data' to load face is either null or blank");
        if (elementsCount < 1)
            throw new AssertionError("'elementsCount' cannot be less than 1");

        path = null;
        matrix = null;
        elements = elementsCount;
        this.data = data;
    }

    private boolean loaded = false;
    private final String path;
    private int[][] matrix;
    private int elements; //Number of elements per side in the square matrix
    private final String data;

    public boolean isLoaded() {
        return loaded;
    }

    public int[][] getMatrix() {
        if (!loaded)
            throw new InvalidDnDOperationException("matrix requested is invalid before loading. did you call load()?");
        else
            return matrix;
    }

    public void load() throws IOException {
        String[] lines;
        if (path!=null) {
            List<String> list = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            if (list==null || list.size()==0)
                throw new AssertionError(String.format("no data found in given face file [%s]", path));
            lines = list.toArray(new String[]{});
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
        for (int j=0; j < elements; j++) {
            String[] row = validLines[j].split(" ");
            if (row.length !=elements)
                throw new AssertionError(String.format("incomplete data found, should have [%d] rows with only have '0' and '1' separated by space", elements));
            for (int i=0; i < elements; i++ ) {
                try {
                    int item = Integer.parseInt(row[i]);
                    if (item!=0 && item!=1)
                        throw new AssertionError(String.format("incorrect data format foundat row [%d], should only have '0' and '1'", j));
                    else
                        matrix[j][i] = item;
                } catch (NumberFormatException e) {
                    throw new AssertionError(String.format("incorrect data format found at row [%d], should only have '0' and '1'", j));
                }
            }
        }
    }

}
