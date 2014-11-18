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
        faces = new ArrayList<>(6);
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

        combinationsTried.clear();

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

    public int solveForAnchor(final int sequence, final CombiFace anchor, final List<HappyFace> origFaces
            , final List<HappyFace> validCombinations) {
        if (sequence%10000 == 0)
            anchor.print();

        final List<HappyFace> faces = new ArrayList<HappyFace>(origFaces.size());
        faces.addAll(origFaces);
        if(faces.contains(anchor))
            faces.remove(anchor);
        if (faces.size()==0)
            return sequence;
        int outSequence = sequence;

        final Iterator<HappyFace> faceIterator = faces.iterator();
        CombiFace clonedAnchor = anchor.clone();
        CombiFace preservedAnchor = null;

        while (faceIterator.hasNext()) {

            preservedAnchor = clonedAnchor.clone();
            final HappyFace currFace = faceIterator.next();
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();

            while(dirIterator.hasNext() ) {

                final FaceDirection direction = dirIterator.next();
                //Skip is parallel face without matching others first.
                if(FaceDirection.Parallel.equals(direction)) {
                    if (clonedAnchor.howManyAttached()<4)
                        break;
                    else {
                        ;//System.out.println(clonedAnchor.getMatchedSequence(null));
                        //clonedAnchor.print();
                    }
                }

                HappyFace face = currFace.clone();
                HappyFace found;
                while (true) {
                    outSequence++;
                    final StringBuilder combination = new StringBuilder();
                    combination.append(sequence).append(',').append(outSequence).append(',');
                    combination.append(face.name).append(',').append(face.getRotation());
                    combination.append(',').append(direction);
                    combination.append(',');
                    combination.append(clonedAnchor.getMatchedSequence());
                    combinationsTried.add(combination.toString());

                    try {
                        clonedAnchor.match(face, direction);
                        found = face;
                    } catch (FaceNotMatchingException fe) {
                        found = null;
                        try {
                            face = face.rotateSimple();
                        } catch (InvalidRotationException re) {
                            break;
                        }
                    }
                    if (found!=null) {
                        //System.out.println(anchor.getMatchedSequence("" + outSequence));
                        final List<HappyFace> spawnFaces = getFacesForRecursion(found, faces);
                        if (clonedAnchor.isSolved()) {
                            try {
                                for (HappyFace validCombi : validCombinations)
                                    checkConnections(clonedAnchor, validCombi);
                                //anchor.print();
                                validCombinations.add(clonedAnchor);
                                clonedAnchor = preservedAnchor.clone();
                            } catch (AssertionError e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            //Attempt matching the next face
                            outSequence = solveForAnchor(outSequence, clonedAnchor, spawnFaces, validCombinations);
                        }

                        //Try other combinations by stepping back by rotating the matched face (in current direction)
                        try { //other combination by stepping back
                            spawnFaces.add(0, found.rotateSimple());
                            outSequence = solveForAnchor(outSequence, preservedAnchor, spawnFaces, validCombinations);
                        } catch (InvalidRotationException re) {
                            break;
                        }

                        //Try other combinations by stepping back by reverting the matched face and try matching in other directions
                        break;
                    }
                }
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
        System.out.println(anchor.getConnections());
        System.out.println( combi.getConnections());
        if (anchor.getConnections().equals(combi.getConnections()))
            throw new AssertionError(String.format("combinations around faces [%s] and [%s] are same", anchor.name(), combi.name()));
    }

    public List<HappyFace> getFacesForRecursion(HappyFace found, List<HappyFace> faces) {
        final List<HappyFace> spawnFaces = new ArrayList<>(faces.size()-1);
        for (int k=0; k < faces.size(); k++) {
            if( ! found.equals(faces.get(k)))
                spawnFaces.add(faces.get(k));
        }
        return spawnFaces;
    }

    private List<String> combinationsTried = new ArrayList<>(10000);
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

}
