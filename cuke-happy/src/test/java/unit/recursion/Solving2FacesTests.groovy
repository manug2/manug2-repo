package unit.recursion

import happy.CombiFace
import happy.CubeSolver
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class Solving2FacesTests extends Specification {

    List<HappyFace> faces
    def setup() {
        faces = new ArrayList<>(6)
    }

    def "solver solves with matching anchor using 2 faces"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        HappyFace anchor = solver.solve(faces)
        expect:
        anchor.equals(faces.get(0))
    }

    def "solver solves with matching face on left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 1] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getLeft() == faces.get(1)
    }

    def "solver solves using 2 faces with matching rotation of face on left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 1] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getLeft().getRotation() == faces.get(1).getRotation()
    }

    def "solver solves in 0 operation using 2 faces with matching rotation of face on left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 0
    }

    def "solver solves using 2 faces without face at bottom"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        anchor.getBottom() == null
    }

    def "solver solves using 3 faces with matching face at bottom"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 2)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        def cube0 = (CombiFace) allPossibleCombinationsForAnchor.get(0)
        expect:
        cube0.getBottom() == faces.get(1)
    }

    def "solver solves in 0 operations using 1 face"() {
        def solver = new CubeSolver().usingNumOfFaces(1)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 0
    }

    def "solver cannot solve using 1 face"() {
        when:
        def solver = new CubeSolver().usingNumOfFaces(1)
        for ( i in [0] ) {
            HappyFace face =
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
        }
        solver.solve(faces)
        then:
        thrown (RuntimeException)
    }

    def "solver solves using 2 face in a different sequence"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 2] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())


        HappyFace anchor = solver.solve(faces)
        anchor.print()
        expect:
        anchor == faces.get(0)
    }

    def "solver solves using 2 face skipping face1, with matching face at Left with rotation"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 2)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        anchor.getLeft() == faces.get(0)
        anchor.getLeft().getRotation() == 3
    }

    def "solver solves using 2 face skipping face1, with matching face at Left with rotation using 3 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 2)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 3
    }

    def "solver solves using 2 face in a skipping face1, face2, with matching face at Left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 3)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        anchor.getLeft() == faces.get(0)
        anchor.getLeft().getRotation() == 0
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, has not matching face at Left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 4] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getLeft() == null
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, has not matching face at Bottom"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 4] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getBottom() == null
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, with matching face at Top"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 4] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getTop() == faces.get(1)
        anchor.getTop().getRotation() == 0
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, with matching face at Top, using 21 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 4)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 21
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, face4, has not matching face at Left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 5] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getLeft() == faces.get(1)
        anchor.getLeft().getRotation() == 2
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, face4, has not matching face at Left,u using 2 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 5)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 2
    }

    def "solver solves with face1 anchor using 0, 1 faces"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [1, 0] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        HappyFace anchor = solver.solve(faces)
        expect:
        anchor.equals(faces.get(0))
    }

    def "solver solves with face1 anchor face0 matching on left"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [1, 0] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        CombiFace anchor = (CombiFace) solver.solve(faces)
        expect:
        anchor.getLeft() == faces.get(1)
        anchor.getLeft().name() == "face0"
    }

    def "solver solves with face1 anchor using 0, 1 faces in 1 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 1
    }

    def "solver solveForAnchor with face1 anchor using 0, 1 faces in 1 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 1
    }


    def "solver solveForAnchor using 2 face in a skipping face1, face2, face3, face4, has not matching face at Left, using 2 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 5)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 2
    }

    def "solver gives all possible combinations solutions with faces 0, 1"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 1] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinations = new ArrayList<>(2)
        solver.solveAllPossibleCombinations(faces, allPossibleCombinations)
        expect:
        allPossibleCombinations.size() == 2
    }

    def "solver gives all possible combinations solutions with faces 0, 1, in 1 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 1] )
            faces.add(
                    HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinations = new ArrayList<>(2)
        def numOfOps = solver.solveAllPossibleCombinations(faces, allPossibleCombinations)
        expect:
        numOfOps == 1
    }

    def "solver gives unique solutions with faces 0, 1"() {
        def solver = new CubeSolver().usingNumOfFaces(2)
        for ( i in [0, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> uniqueSolutions = solver.solveUnique(faces)
        expect:
        uniqueSolutions.size() == 1
    }


    def "solver solves with 4 solutions in 28 operation using 2 faces when usesStepsBack"() {
        def solver = new CubeSolver().usingNumOfFaces(2).usesStepBack()
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        for (HappyFace sol in allPossibleCombinationsForAnchor)
            sol.print()
        expect:
        numOfOps == 28
        allPossibleCombinationsForAnchor.size() == 4
    }

    def "solver solves with 4 solutions in 28 operation using 2 faces when usesStepsBack, skipping face1"() {
        def solver = new CubeSolver().usingNumOfFaces(2).usesStepBack()
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 2)).build())
        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        for (HappyFace sol in allPossibleCombinationsForAnchor)
            sol.print()
        expect:
        numOfOps == 28
        allPossibleCombinationsForAnchor.size() == 4
    }

}

