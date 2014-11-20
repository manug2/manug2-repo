package happy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CubeSolver {

    public CubeSolver(int elementsCount) {
        elements = elementsCount;
        faces = new ArrayList<HappyFace>(6);
    }
    private final List<HappyFace> faces;
    private int elements;
    public void loadFace(HappyFace face) throws IOException {
        if (face.elementCount()!=elements)
            throw new AssertionError(String.format("the cube needs faces with [%d] elements", elements));

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
        combinationsTried.clear();
        CombiFace solved = solve(anchor, faces);
        if (solved!=null)
            return solved;
        else
            throw new RuntimeException(String.format("I could not solve the cube"));
    }

    private CombiFace solve(CombiFace anchor, List<HappyFace> origFaces) {

        final List<HappyFace> faces = new ArrayList<>(origFaces.size());
        faces.addAll(origFaces);
        if(faces.contains(anchor))
            faces.remove(anchor);
        if (faces.size()==0)
            return null;

        int opId = -1;
        final Iterator<HappyFace> faceIterator = faces.iterator();
        while (!anchor.isSolved() && faceIterator.hasNext()) {
            final HappyFace currFace = faceIterator.next();
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();
            HappyFace found = null;

            while(found == null && dirIterator.hasNext() )
                found = matchOne(anchor, currFace, dirIterator.next(), opId);
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

        combinationsTried.clear();

        List<HappyFace> uniqueSolutions = new ArrayList<>(5);

        for (int i=0; i < faces.size(); i++) {
            CombiFace anchor = new CombiFace(faces.get(i).clone());
            solveForAnchor(1000000*i, anchor, getFacesForRecursion(anchor, faces), uniqueSolutions);
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

    public int solveForAnchor(final int sequence, CombiFace anchor, final List<HappyFace> faces
            , final List<HappyFace> validCombinations) {

        if (faces.size()==0)
            return sequence;
        int outSequence = sequence;

        final Iterator<HappyFace> faceIterator = faces.iterator();
        CombiFace preservedAnchor;

        while (faceIterator.hasNext()) {
            if(anchor.isSolved())
                break;

            final HappyFace currFace = faceIterator.next();
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();

            while( dirIterator.hasNext()) {
                if(anchor.isSolved())
                    break;
                final FaceDirection direction = dirIterator.next();

                if(FaceDirection.Parallel.equals(direction)) {
                    //Skip is parallel face without matching others first.
                    if (anchor.howManyAttached()<4)
                        break;
                }

                HappyFace face = currFace.clone();
                HappyFace found;
                preservedAnchor = anchor.clone();
                final StringBuilder combination = new StringBuilder();
                while (true) {
                    outSequence++;
                    if (combination.length()>0)
                        combination.delete(0, combination.length());
                    combination.append(sequence).append(',').append(outSequence).append(',').append(face.name).append(',').append(face.getRotation());
                    combination.append(',').append(direction).append(',').append(anchor.getMatchedSequence());

                    try {
                        anchor.match(face, direction);
                        found = face;
                        combination.append(',').append(true);
                        combinationsTried.add(combination.toString());
                        break;
                    } catch (FaceNotMatchingException fe) {
                        found = null;
                        try {
                            face = face.rotate();
                        } catch (InvalidRotationException re) {
                            combination.append(',').append(false);
                            combinationsTried.add(combination.toString());
                            break;
                        }
                    }
                }
                if (found == null)
                    continue; //to match with next direction

                final List<HappyFace> spawnFaces = getFacesForRecursion(found, faces);
                if (anchor.isSolved()) {
                    anchor.print();
                    combinationsTried.add(anchor.getMatchedSequence("SOLUTION :" + sequence + ":" + outSequence));
                    try {
                        for (HappyFace validCombi : validCombinations)
                            checkConnections(anchor, validCombi);
                        validCombinations.add(anchor);
                    } catch (AssertionError e) {
                        System.out.println(e.getMessage());
                        combinationsTried.add(anchor.getMatchedSequence("SOLUTION NOT UNIQUE :" + sequence + ":" + outSequence));
                    }
                } else {
                    final CombiFace solverFurtherAnchor = anchor.clone();
                    outSequence = solveForAnchor(outSequence, solverFurtherAnchor, spawnFaces, validCombinations);
                }
                anchor = preservedAnchor.clone();
                break;
            }
        }
        return outSequence;
    }


    public void checkConnections(CombiFace anchor, HappyFace other) {
        if (other==null)
            throw new AssertionError("Cannot compare connections when other face is null");
        else if (! (other instanceof CombiFace))
            throw new AssertionError("Cannot compare connections when other face is not a CombiFace");
        CombiFace combi = (CombiFace) other;

        if (anchor.getConnections().equals(combi.getConnections()))
            throw new AssertionError(String.format("combinations around faces [%s] and [%s] are same", anchor.name(), combi.name()));
        else
            System.out.println( anchor.getConnections());
    }

    public List<HappyFace> getFacesForRecursion(HappyFace found, List<HappyFace> faces) {
        final List<HappyFace> spawnFaces = new ArrayList<>(faces.size()-1);
        for (int k=0; k < faces.size(); k++) {
            if( ! found.equals(faces.get(k)))
                spawnFaces.add(faces.get(k));
        }
        return spawnFaces;
    }

    private List<String> combinationsTried = new ArrayList<>(200);
    public List<String> getCombinationsTried() {
        return combinationsTried;
    }

    public void dumpCombinations(String path) throws IOException {
        try {
            Files.delete(Paths.get(path));
        } catch (NoSuchFileException e) {}

        Path file = Files.createFile(Paths.get(path));
        BufferedWriter writer = Files.newBufferedWriter(file, Charset.defaultCharset());
        writer.write("In Seq,Out Seq,Candidate,C Rotation,Direction,Anchor,Rotation,One,O Rotation,Two,T R,Three,Th R,Four,F R,Five,F R");
        writer.newLine();
        for (String line : combinationsTried) {
            writer.write(line);
            writer.newLine();
            writer.flush();
        }
        writer.close();
    }

    public List<HappyFace> solveUnique(String path) throws IOException {
        List<HappyFace> solutions = solveUnique();
        dumpCombinations(path);
        return solutions;
    }

}
