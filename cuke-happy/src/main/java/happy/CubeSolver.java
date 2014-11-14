package happy;

import java.io.IOException;
import java.util.*;

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
        if (face.elementCount()!=elements)
            throw new AssertionError(String.format("the cube needs faces with [%d] elements", elements));

        if (! face.isLoaded())
            face.load();
        faces.add(face);
    }

    public int elementCount() {
        return elements;
    }
    public HappyFace solve() {
        if (faces.size()<6)
            throw new AssertionError("the cube needs six faces");

        CombiFace anchor = new CombiFace(faces.get(0));
        anchor.print();
        CombiFace solved = solve(anchor, faces);
        if (solved!=null)
            return solved;
        else
            throw new RuntimeException(String.format("I could not solve the cube"));
    }

    private CombiFace solve(CombiFace anchor, List<HappyFace> faces) {
        anchor.print();
        int opId = -1;
        for (int j=0; j < faces.size(); j++) {
            opId++;
            if (anchor.equals(faces.get(j)))
                continue;

            for (FaceDirection direction : FaceDirection.values()) {
                if (anchor.hasFace(direction))
                    continue;
                matchOne(anchor, faces.get(j), direction, opId);
            }
        }

        if (anchor.isSolved())
            return anchor;
        else
            return null;
    }

    public static HappyFace matchOne(final CombiFace anchor, final HappyFace origFace, final FaceDirection direction, final int opId) {
        HappyFace face = origFace.clone();
        while (true) {
            try {
                anchor.match(face, direction);
                //System.out.println(String.format("[%d] matched face [%d, %d] to anchor in direction [%s]:",opId, face.name(), face.getRotation(), direction));
                return face;
            } catch (FaceNotMatchingException fe) {
                try {
                    face = face.rotate();
                } catch (InvalidRotationException re) {
                    return null;
                }
            }
        }
    }

    public HappyFace getFace(int i) {
        return faces.get(i);
    }

    public List<HappyFace> solveUnique() {
        if (faces.size()!=6)
            throw new AssertionError("the cube needs six faces");


        List<HappyFace> uniqueSolutions = new ArrayList<HappyFace>(5);

        for (int i=0; i < faces.size(); i++) {
            CombiFace anchor = new CombiFace(faces.get(i).clone());
            //System.out.println("Solving using following face as anchor:");
            anchor.print();
            solveForAnchor(0, anchor, faces, uniqueSolutions);
        }

        System.out.println("=============PRINTING SOLUTIONS=============");
        System.out.println("============================================");
        for (int i=0; i < uniqueSolutions.size(); i++) {
            System.out.println(String.format("============= SOLUTION # %d ============", i));
            System.out.println(((CombiFace)uniqueSolutions.get(i)).getMatchedSequence(null));
            uniqueSolutions.get(i).print();
        }
        return uniqueSolutions;
    }


    public static void solveForAnchor(final int level, final CombiFace anchor, final List<HappyFace> origFaces, final List<HappyFace> validCombinations) {
        final List<HappyFace> faces = new ArrayList<HappyFace>(origFaces.size());
        faces.addAll(origFaces);
        if(faces.contains(anchor))
            faces.remove(anchor);
        if (faces.size()==0)
            return;

        int opId = 1000000 + anchor.howManyAttached()*1000 + level;
        final Iterator<HappyFace> faceIterator = faces.iterator();

        while (!anchor.isSolved() && faceIterator.hasNext()) {
            final CombiFace preservedAnchor= anchor.clone();
            final HappyFace currFace = faceIterator.next();
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();
            HappyFace found = null;

            while(found == null && dirIterator.hasNext() ) {
                FaceDirection direction = dirIterator.next();
                System.out.println(anchor.getMatchedSequence("" + opId));

                found = matchOne(anchor, currFace, direction, opId);
                if (found!=null) {
                    //System.out.println(anchor.getMatchedSequence("" + opId));
                    if (anchor.isSolved()) {
                        try {
                            for (HappyFace validCombi : validCombinations)
                                checkConnections(anchor, validCombi);
                            //anchor.print();
                            validCombinations.add(anchor);
                        } catch (AssertionError e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        //Try new solutions by rotating the matched face.
                        final List<HappyFace> spawnFaces = new ArrayList<HappyFace>(origFaces.size());
                        for (int k=0; k < faces.size(); k++) {
                            if( ! currFace.equals(faces.get(k)))
                                spawnFaces.add(faces.get(k));
                        }

                        //Attempt matching the next face
                        solveForAnchor(level+1, anchor, spawnFaces, validCombinations);

                        try { //other combination by stepping back
                            spawnFaces.add(0, found.rotate());
                            solveForAnchor(level, preservedAnchor, spawnFaces, validCombinations);
                        } catch (InvalidRotationException re) {
                        }
                    }
                }
            }
        }
    }


    public static void checkConnections(CombiFace anchor, HappyFace other) {
        if (other==null)
            throw new AssertionError("Cannot compare connections when other face is null");
        else if (! (other instanceof CombiFace))
            throw new AssertionError("Cannot compare connections when other face is not a CombiFace");
        CombiFace combi = (CombiFace) other;
        System.out.println(anchor.getConnections());
        System.out.println( combi.getConnections());
        if (anchor.getConnections().equals(combi.getConnections()))
            throw new AssertionError(String.format("combinations around faces [%s] and [%s] are same", anchor.name(), combi.name()));
    }

}
