package happy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CubeSolver {

    public CubeSolver(int elementsCount) {
        elements = elementsCount;
        solved = false;
        faces = new ArrayList<HappyFace>(6);
    }
    private final List<HappyFace> faces;
    private int elements;
    private boolean solved;
    public void loadFace(HappyFace face) throws IOException {
        if (faces.size()==6)
            throw new AssertionError("cannot load more than 6 faces");

        if (face.elementCount()!=elements)
            throw new AssertionError(String.format("the cube needs faces with [%d] elements", elements));

        if (! face.isLoaded())
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

    public boolean match(int index1, int index2) {
        if (index1>= faces.size())
            throw new AssertionError(String.format("the cube does not have face with index [%s]", index1));
        else if (index2>= faces.size())
            throw new AssertionError(String.format("the cube does not have face with index [%s]", index2));

        faces.get(index1).print();
        faces.get(index2).print();

        int[][] face1 = faces.get(index1).getMatrix();
        int[][] face2 = faces.get(index1).getMatrix();
        for (int i=0; i < elements; i++) {
            if ( (face1[0][i] + face2[elements-1][i]) != 1 )
                return false;
        }

        return true;
    }

}
