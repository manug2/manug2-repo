package happy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HappyFaceBuilder {

    public static HappyFaceBuilder createBuilder() {
        return new HappyFaceBuilder();
    }

    String path;
    String data;
    int rotation=0;
    String name;
    MatrixBuilder matrixBuilder = new MatrixBuilder();

    public HappyFaceBuilder name(String name) {
        this.name = name;
        return this;
    }

    private String name() {
        if(this.name==null) {
            if(path==null)
                name = generateId();
            else {
                final String[] parts = this.path.split("/");
                final String fileName = parts[parts.length-1];
                final String[] nameParts = fileName.split("\\.");
                name =  nameParts[0];
            }
        }
        return name;
    }

    public HappyFaceBuilder rotation(int rotation) {
        this.rotation = rotation;
        return this;
    }
    public HappyFaceBuilder numOfElements(int numOfElements) {
        this.matrixBuilder.numOfElements(numOfElements);
        return this;
    }

    public HappyFaceBuilder usingFile(String path) {
        if(this.data!=null)
            throw new AssertionError("trying to use file path after data string has already been set");
        this.path = path;
        return this;
    }

    public HappyFaceBuilder usingString(String data) {
        if(this.path!=null)
            throw new AssertionError("trying to use data string after file path has already been set");
        this.data = data;
        return this;
    }

    public HappyFace build() throws IOException {
        if(path!=null)
            return buildUsingFile();
        else
            return buildFromData();
    }

    private HappyFace buildUsingFile() throws IOException {
        List<String> list = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        String[] lines = list.toArray(new String[list.size()]);
        return new HappyFace(this.matrixBuilder.build(lines), rotation, name());
    }

    public HappyFace buildFromData() {
        String[] lines = data.split(";");
        return new HappyFace(matrixBuilder.build(lines), rotation, name());
    }

    private static int _identifier=0;
    private static String generateId() {
        int id;
        synchronized (HappyFace.class) {
            id = _identifier++;
        }
        return "" + id;
    }

}
