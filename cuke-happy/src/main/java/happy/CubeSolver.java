package happy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //if (faces.size()==6)
        //    throw new AssertionError("cannot load more than 6 faces");

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

        CombiFace origAnchor = new CombiFace(faces.get(0));
        origAnchor.print();
        final int facesToTry = faces.size() - 1;

        CombiFace anchor = origAnchor.clone();
        solveWithFixedAnchor(anchor);
        //anchor.print();
        //origAnchor.print();
        throw new AssertionError("Not yet implemented");
    }

    public void printFaces() {
        for (HappyFace face : faces)
            face.print();
    }

    private void solveWithFixedAnchor(CombiFace anchor) {
        final Map<FaceDirection, Boolean> facesMatched = new HashMap<FaceDirection, Boolean>(5);

        for (int j=1; j <= faces.size(); j++) {
            boolean found = false;
            FaceDirection direction = FaceDirection.Left;
            if( ! facesMatched.containsKey(direction)) {
                found = matchOne(anchor, faces.get(j), direction);
                if (found) {
                    facesMatched.put(direction, true);
                    break;
                }
            }
            direction = FaceDirection.Bottom;
            if( ! facesMatched.containsKey(direction)) {
                found = matchOne(anchor, faces.get(j), direction);
                if (found) {
                    facesMatched.put(direction, true);
                    break;
                }
            }
            direction = FaceDirection.Right;
            if( ! facesMatched.containsKey(direction)) {
                found = matchOne(anchor, faces.get(j), direction);
                if (found) {
                    facesMatched.put(direction, true);
                    break;
                }
            }
            direction = FaceDirection.Top;
            if( ! facesMatched.containsKey(direction)) {
                found = matchOne(anchor, faces.get(j), direction);
                if (found) {
                    facesMatched.put(direction, true);
                    break;
                }
            }
            direction = FaceDirection.Parallel;
            if( ! facesMatched.containsKey(direction)) {
                found = matchOne(anchor, faces.get(j), direction);
                if (found) {
                    facesMatched.put(direction, true);
                    break;
                }
            }
        }
    }

    public boolean matchOne(CombiFace anchor, HappyFace origFace, FaceDirection direction) {
        HappyFace face = origFace.clone();
        boolean found = false;
        while (!found) {
            try {
                try {
                    anchor.match(face, direction);
                    anchor.print();
                    found = true;
                    break;
                } catch (FaceNotMatchingException fe) {
                    System.out.println(fe.getMessage());
                    face = face.rotate();
                } catch (RuntimeException fe) {
                    System.out.println(fe.getMessage());
                    face = face.rotate();
                }
            } catch (InvalidRotationException fe) {
                System.out.println(fe.getMessage());
                break;
            }
        }
        anchor.print();
        return found;
    }

}
