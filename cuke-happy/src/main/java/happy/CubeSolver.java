package happy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CubeSolver {

    public CubeSolver(int elementsCount) {
        elements = elementsCount;
        solved = false;
    }
    List<HappyFace> faces = new ArrayList<HappyFace>(8);
    private int elements;
    private boolean solved;
    public void loadFace(String pathToFace) throws IOException {
        if (faces.size()==6)
            throw new AssertionError("cannot load more than 6 faces");

        HappyFace face = new HappyFace(pathToFace, elements);
        face.load();
        faces.add(face);
    }

    public int elementCount() {
        return elements;
    }
    public void solve() {
        if (solved)
            return ;
        if (faces.size()!=6)
            throw new AssertionError("the cube needs six faces");
        else
            throw new AssertionError("Not yet implemented");
    }

}
