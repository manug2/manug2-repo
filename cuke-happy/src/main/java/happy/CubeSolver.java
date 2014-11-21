package happy;

import java.util.*;

public class CubeSolver {

    public CubeSolver(int numOfFacesInSolution) {
        faces = new ArrayList<>(6);
        this.numOfFacesInSolution = numOfFacesInSolution;
    }

    private final List<HappyFace> faces;
    public final int numOfFacesInSolution;

    public void loadFace(HappyFace face) {
        faces.add(face);
    }

    public HappyFace solve() {
        if (faces.size()<this.numOfFacesInSolution)
            throw new AssertionError(String.format("the cube needs %d faces", numOfFacesInSolution));

        CombiFace anchor = new CombiFace(faces.get(0));
        anchor.print();

        solve(anchor, faces);
        if (anchor.howManyAttached()==this.numOfFacesInSolution-1)
            return anchor;
        else
            throw new RuntimeException(String.format("I could not solve the cube"));
    }

    public long solve(CombiFace anchor, final List<HappyFace> faces) {
        int numOfOps = 0;
        final Iterator<HappyFace> faceIterator = faces.iterator();
        while ( anchor.howManyAttached()!=this.numOfFacesInSolution-1 && faceIterator.hasNext()) {
            final HappyFace currFace = faceIterator.next();
            if(anchor.equals(currFace))
                continue;
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();
            HappyFace found = null;

            while(found == null && dirIterator.hasNext() ) {
                found = matchOne(anchor, currFace, dirIterator.next());
                numOfOps += getNumOfOpsDuringMatchOne(currFace, found);
            }
        }
        return numOfOps;
    }

    public int getNumOfOpsDuringMatchOne(final HappyFace origFace, final HappyFace found) {
        if (found == null)
            return 8 - origFace.getRotation();
        else
            return found.getRotation() - origFace.getRotation() + 1;
    }

    public HappyFace matchOne(final CombiFace anchor, final HappyFace origFace, final FaceDirection direction) {
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

    public List<HappyFace> solveUnique() {
        if (faces.size() < numOfFacesInSolution)
            throw new AssertionError(String.format("the cube needs %d faces", numOfFacesInSolution));

        List<HappyFace> uniqueSolutions = new ArrayList<>(faces.size()*2);
        solveAllPossibleCombinations(uniqueSolutions);
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

    public long solveAllPossibleCombinations(List<HappyFace> uniqueSolutions) {
        if (faces.size() < numOfFacesInSolution)
            throw new AssertionError(String.format("the cube needs %d faces", numOfFacesInSolution));

        long numOfOps = 0;
        for (int i=0; i < faces.size(); i++) {
            CombiFace anchor = new CombiFace(faces.get(i).clone());
            numOfOps += solveForAnchor(anchor, getFacesForRecursion(anchor, faces), uniqueSolutions);
        }
        System.out.println(String.format("[solveAllPossibleCombinations] using %d operations \t\t", numOfOps));

        return numOfOps;
    }

    public long solveForAnchor(CombiFace anchor, final List<HappyFace> faces
            , final List<HappyFace> validCombinations) {
        int numOfOps = 0;
        final Iterator<HappyFace> faceIterator = faces.iterator();
        CombiFace preservedAnchor;

        while (faceIterator.hasNext()) {
            if(anchor.howManyAttached()==this.numOfFacesInSolution-1)
                break;

            final HappyFace currFace = faceIterator.next();
            final Iterator<FaceDirection> dirIterator = anchor.getPendingDirections().iterator();

            while( dirIterator.hasNext()) {
                if(anchor.howManyAttached()==this.numOfFacesInSolution-1)
                    break;
                final FaceDirection direction = dirIterator.next();

                if(FaceDirection.Parallel.equals(direction)) {
                    //Skip is parallel face without matching others first.
                    if (anchor.howManyAttached()<4)
                        break;
                }

                preservedAnchor = anchor.clone();
                HappyFace found = matchOne(anchor, currFace, direction);
                numOfOps += getNumOfOpsDuringMatchOne(currFace, found);

                if (found == null)
                    continue; //to match with next direction

                final List<HappyFace> spawnFaces = getFacesForRecursion(found, faces);
                if (anchor.howManyAttached()==this.numOfFacesInSolution-1)
                    validCombinations.add(anchor);
                else
                    numOfOps += solveForAnchor(anchor.clone(), spawnFaces, validCombinations);

                /*
                try {
                    final CombiFace stepBack = preservedAnchor.clone();
                    spawnFaces.add(found.rotate());
                    outSequence = solveForAnchor(outSequence, stepBack, spawnFaces, validCombinations);
                } catch (InvalidRotationException re) {
                }
                */
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
