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

        //May optimise if two or more rotations lead to same matrix

        CombiFace anchor = new CombiFace(faces.get(0));
        anchor.print();
        final int facesToTry = faces.size() - 1;

        for (int i=0; i <2; i++) {
            boolean left=false, bottom = false, right=false, top = false;
            for (int j=1; j <= facesToTry; j++) {
                HappyFace face = faces.get(j);
                if (!left) {
                    try {
                        if (anchor.match(face, FaceDirection.Left)) {
                            System.out.println(String.format("Found match face #[%d] on [%s]", j, FaceDirection.Left));
                            face.print();
                            left = true;
                            break;
                        } else
                            face = face.rotate();
                    } catch (InvalidRotationException e) {
                         System.out.println(String.format("Out of rotations for face #[%d], while trying to match on [%s]", j, FaceDirection.Left));
                    }
                }
            }
            anchor.flip();
        }
        anchor.print();
        throw new AssertionError("Not yet implemented");
    }

    public void printFaces() {
        for (HappyFace face : faces)
            face.print();
    }

}
