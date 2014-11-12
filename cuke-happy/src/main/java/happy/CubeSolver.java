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

    public HappyFace matchOne(final CombiFace anchor, final HappyFace origFace, final FaceDirection direction, final int opId) {
        HappyFace face = origFace.clone();
        while (true) {
            try {
                anchor.match(face, direction);
                //System.out.println(String.format("[%d] matched face [%d, %d] to anchor in direction [%s]:",opId, face.identifier(), face.getRotation(), direction));
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
        /*
        CombiFace anchor = new CombiFace(faces.get(0).clone());
        List<CombiFace> solutions = solveForAnchor(0, anchor, faces);
        if (solutions!=null && solutions.size()>0)
            uniqueSolutions.addAll(solutions);
        */
        for (int i=0; i < faces.size(); i++) {
            CombiFace anchor = new CombiFace(faces.get(i).clone());
            System.out.println("Solving using following face as anchor:");
            anchor.print();
            List<CombiFace> solutions = solveForAnchor(0, anchor, faces);
            if (solutions!=null && solutions.size()>0)
                uniqueSolutions.addAll(solutions);
        }

        System.out.println("=============PRINTING SOLUTIONS=============");
        System.out.println("============================================");
        for (int i=0; i < uniqueSolutions.size(); i++) {
            System.out.println(String.format("============= SOLUTION # %d ============", i));
            uniqueSolutions.get(i).print();
        }
        return uniqueSolutions;
    }


    private List<CombiFace> solveForAnchor(int level, CombiFace anchor, List<HappyFace> origFaces) {
        List<HappyFace> faces = new ArrayList<HappyFace>(origFaces.size());
        faces.addAll(origFaces);
        List<CombiFace> validCombinations = new ArrayList<CombiFace>(2);
        CombiFace preservedAnchor;

        int opId = -1;
        for (int j=0; j < faces.size(); j++) {
            opId++;
            if (anchor.equals(faces.get(j)))
                continue;

            preservedAnchor = anchor.clone();
            for (FaceDirection direction : FaceDirection.values()) {
                if (anchor.hasFace(direction))
                    continue;
                HappyFace found = matchOne(anchor, faces.get(j), direction, opId);
                if (found!=null) {
                    System.out.println(anchor.getMatchedSequence("" + level));
                    if (! anchor.isSolved()) {
                        //Try new solutions by rotating the list
                        try {
                            List<HappyFace> spawnFaces = new ArrayList<HappyFace>(origFaces.size());
                            for (int k=0; k < faces.size(); k++) {
                                if( !(k==j || anchor.equals(faces.get(k))))
                                    spawnFaces.add(faces.get(k));
                            }
                            spawnFaces.add(faces.get(j).rotate());
                            List<CombiFace> spawnedSolutions = solveForAnchor(level+1, preservedAnchor, spawnFaces);
                            if (spawnedSolutions!=null)
                                validCombinations.addAll(spawnedSolutions);
                        } catch (InvalidRotationException re) {
                            //ignore error
                        }
                    }
                }
            }

            if (anchor.isSolved())
                validCombinations.add(anchor);
        }

        return validCombinations;

    }


}
