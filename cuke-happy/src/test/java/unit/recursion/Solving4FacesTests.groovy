package unit.recursion

import happy.CombiFace
import happy.CubeSolver
import happy.FaceDirection
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class Solving4FacesTests extends Specification {

    def "solver solves with face0 anchor using faces 1, 2, 3 with matching face at Left, Bottom and Right"() {
        def solver = new CubeSolver().usingNumOfFaces(4)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 2, 3] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        def cube0 = (CombiFace) allPossibleCombinationsForAnchor.get(0)
        expect:
        cube0.getLeft() == faces.get(0)
        cube0.getBottom() == faces.get(1)
        cube0.getRight() == faces.get(2)
    }

    def "solver solves with face0 anchor using faces 1, 2, 3 in 94 operations "() {
        def solver = new CubeSolver().usingNumOfFaces(4)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 2, 3] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 94
    }

    def "solver solves for anchor face0 with pre-attached face1, face2 using face3 in 0 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(4)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        solver.matchOne(anchor,
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build()
                , FaceDirection.Left)
        solver.matchOne(anchor,
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 2)).build()
                , FaceDirection.Bottom)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 3)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        allPossibleCombinationsForAnchor.get(0).print()
        expect:
        allPossibleCombinationsForAnchor.size() == 1
        numOfOps == 0
    }

}
