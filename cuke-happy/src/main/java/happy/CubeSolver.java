package happy;

import java.util.*;

public class CubeSolver {

    public CubeSolver() {
        this.numOfFacesInSolution = 6;
    }
    public CubeSolver usingNumOfFaces(int numOfFaces) {
        this.numOfFacesInSolution = numOfFaces;
        return this;
    }
    public CubeSolver usesStepBack() {
        this.trySteppingBack = true;
        return this;
    }
    public CubeSolver noSteppingBack() {
        this.trySteppingBack = false;
        return this;
    }
    private int numOfFacesInSolution;
    private boolean trySteppingBack = false;

    public HappyFace solve(final List<HappyFace> faces) {
        if (faces==null)
            throw new AssertionError(String.format("the cube needs %d faces, received null", numOfFacesInSolution));
        if (faces.size()<this.numOfFacesInSolution)
            throw new AssertionError(String.format("the cube needs %d faces", numOfFacesInSolution));

        CombiFace anchor = new CombiFace(faces.get(0));
        anchor.print();
        List<HappyFace> uniqueSolutions = new ArrayList<>(faces.size()*2);
        long numOfOps = solveForAnchor(0, anchor, getFacesForRecursion(anchor, faces), uniqueSolutions);

        if(uniqueSolutions.size()==0)
            throw new RuntimeException(String.format("I could not solve the cube"));


        anchor = (CombiFace) uniqueSolutions.get(0);
        if (anchor.howManyAttached()==this.numOfFacesInSolution-1)
            return anchor;
        else
            throw new RuntimeException(String.format("I could not solve the cube"));
    }

    public int getNumOfOpsDuringMatchOne(final HappyFace origFace, final HappyFace found) {
        if (found == null)
            return origFace.maxRotations() - origFace.getRotation();
        else
            return found.getRotation() - origFace.getRotation();
    }

    public HappyFace matchOne(final CombiFace anchor, final HappyFace origFace, final FaceDirection direction) {
        if (anchor.checkBlocked(origFace, direction))
            return null;
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

    public List<HappyFace> solveUnique(final List<HappyFace> faces) {
        if (faces.size() < numOfFacesInSolution)
            throw new AssertionError(String.format("the cube needs %d faces", numOfFacesInSolution));

        List<HappyFace> uniqueSolutions = new ArrayList<>(faces.size()*2);
        long numOfOps = solveAllPossibleCombinations(faces, uniqueSolutions);
        List<HappyFace> filteredSolutions = filterUnique(uniqueSolutions);

        System.out.println("============================================");
        System.out.println("=============PRINTING SOLUTIONS=============");
        System.out.println("============================================");
        System.out.println("");
        for (int i=0; i < filteredSolutions.size(); i++) {
            System.out.println(String.format("============= SOLUTION # %d ============", i));
            System.out.println(((CombiFace)filteredSolutions.get(i)).getMatchedSequence(null));
            filteredSolutions.get(i).print();
        }
        return filteredSolutions;
    }

    public long solveAllPossibleCombinations(final List<HappyFace> faces, List<HappyFace> uniqueSolutions) {
        if (faces.size() < numOfFacesInSolution)
            throw new AssertionError(String.format("the cube needs %d faces", numOfFacesInSolution));

        long numOfOps = 0;
        for (int i=0; i < faces.size(); i++) {
            CombiFace anchor = new CombiFace(faces.get(i).clone());
            numOfOps += solveForAnchor(0, anchor, getFacesForRecursion(anchor, faces), uniqueSolutions);
        }
        System.out.println(String.format("[solveAllPossibleCombinations] using %d operations \t\t", numOfOps));

        return numOfOps;
    }

    public long solveForAnchor(final long lastNumOfOps, CombiFace anchor, final List<HappyFace> faces
            , final List<HappyFace> validCombinations) {
        long numOfOps = lastNumOfOps;
        final Iterator<HappyFace> faceIterator = faces.iterator();
        CombiFace preservedAnchor;

        while (faceIterator.hasNext()) {
            if(anchor.howManyAttached()==this.numOfFacesInSolution-1)
                break;

            HappyFace currFace = faceIterator.next();
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();

            FaceDirection direction = null;
            while( dirIterator.hasNext()) {
                if(anchor.howManyAttached()==this.numOfFacesInSolution-1)
                    break;

                if(direction != null
                        && anchor.evalAndRegisterRewinding(currFace, direction))
                    currFace = currFace.rewind();

                preservedAnchor = anchor.clone();
                direction = dirIterator.next();
                if(FaceDirection.Parallel.equals(direction) && anchor.howManyAttached() < 4)
                    break;

                HappyFace found = matchOne(anchor, currFace, direction);
                numOfOps += getNumOfOpsDuringMatchOne(currFace, found);

                if (found == null)
                    continue; //to match with next direction

                final List<HappyFace> spawnFaces = getFacesForRecursion(found, faces);
                if (anchor.howManyAttached()==this.numOfFacesInSolution-1)
                    validCombinations.add(anchor.setNumOfOperations(numOfOps));
                else
                    numOfOps = solveForAnchor(numOfOps, anchor.clone(), spawnFaces, validCombinations);

                if (trySteppingBack) {
                    HappyFace rotatedFound = null;
                    try {
                        rotatedFound = found.rotate();
                    } catch (InvalidRotationException re) {
                    }

                    numOfOps += getNumOfOpsDuringMatchOne(found, rotatedFound);
                    if (rotatedFound!=null) {
                        spawnFaces.add(rotatedFound);
                        final CombiFace stepBack = preservedAnchor.clone();
                        numOfOps = solveForAnchor(numOfOps, stepBack, spawnFaces, validCombinations);
                    }
                }

                anchor = preservedAnchor.clone();
                break;
            }
        }
        return numOfOps;
    }

    public List<HappyFace> getFacesForRecursion(HappyFace found, List<HappyFace> faces) {
        final List<HappyFace> spawnFaces = new ArrayList<>(faces.size()-1);
        for (int k=0; k < faces.size(); k++) {
            if( ! found.equals(faces.get(k)))
                spawnFaces.add(faces.get(k));
        }
        return spawnFaces;
    }

    public final List<HappyFace> filterUnique(final List<HappyFace> solvedAnchors) {
        if (solvedAnchors.size()<=1)
            return solvedAnchors;

        List<HappyFace> uniqueSolutions = new ArrayList<>(2);
        uniqueSolutions.add(solvedAnchors.get(0));
        for (int i=1; i < solvedAnchors.size(); i++) {
            final CombiFace other = (CombiFace) solvedAnchors.get(i);
            boolean foundDuplicate = false;
            for (int j=0; j < uniqueSolutions.size(); j++) {
                final CombiFace unique = (CombiFace) uniqueSolutions.get(j);
                if (unique.getConnections().equals(other.getConnections())) {
                    System.out.println(  String.format("combinations around faces [%s] and [%s] are same", unique.name(), other.name()));
                    foundDuplicate = true;
                    break;
                }
            }
            if (!foundDuplicate)
                uniqueSolutions.add(other);
        }
        return uniqueSolutions;
    }

}
