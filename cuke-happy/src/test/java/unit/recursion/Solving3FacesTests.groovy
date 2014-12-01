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
class Solving3FacesTests extends Specification {

    def "solver solves using 3 faces with matching face at left"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 2] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        def cube0 = (CombiFace) allPossibleCombinationsForAnchor.get(0)
        expect:
        cube0.getLeft() == faces.get(0)
    }

    def "solver solves with face0 anchor using faces 1, 2 with matching face at bottom"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 2] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        def cube0 = (CombiFace) allPossibleCombinationsForAnchor.get(0)
        expect:
        cube0.getBottom() == faces.get(1)
    }

    def "solver solves with face0 anchor using faces 1, 2 using 24 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 2] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        numOfOps == 24
    }

    def "solver solves with face0 anchor using faces 4, 1 using 35 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [4, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        def cube0 = (CombiFace) allPossibleCombinationsForAnchor.get(0)
        def cube1 = (CombiFace) allPossibleCombinationsForAnchor.get(1)
        expect:
        cube0.howManyAttached() == 2
        cube1.howManyAttached() == 2
        numOfOps == 35
    }

    def "solver solves with face0 anchor using faces 4, 1 with two solutions"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [4, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        allPossibleCombinationsForAnchor.size() == 2
    }

    def "solver gives 3 (= 1 + 2 + 0) possible solutions using faces 0, 5, 1 in 116 (= 67 + 21 + 52) operations"() {
        when:
        def solver = new CubeSolver().usingNumOfFaces(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 5, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinations = new ArrayList<>(2)
        def numOfOps = solver.solveAllPossibleCombinations(faces, allPossibleCombinations)
        then:
        allPossibleCombinations.size() == 3
        numOfOps == 116
    }

    def "solver solves one possible solution for anchor face5 using faces 0, 1 in 57 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 5)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        allPossibleCombinationsForAnchor.size() == 1
        numOfOps == 57
    }

    def "solver gives two possible solutions for anchor face1 using faces 5, 0 in 15 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [5, 0] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        allPossibleCombinationsForAnchor.size() == 2
        numOfOps == 15
    }
    def "solver cannot solve for anchor face0 using faces 5, 1 in 44 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [5, 1] )
            faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        expect:
        allPossibleCombinationsForAnchor.size() == 0
        numOfOps == 44
    }

    def "solver solves for anchor face5 with pre-attached face1 using face0 in 20 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 5)).build())
        solver.matchOne(anchor,
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build()
                , FaceDirection.Bottom)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        allPossibleCombinationsForAnchor.get(0).print()
        expect:
        allPossibleCombinationsForAnchor.size() == 1
        numOfOps == 20
    }

    def "solver solves for anchor face0 with pre-attached face1 using face2 in 0 operations"() {
        def solver = new CubeSolver().usingNumOfFaces(3)
        CombiFace anchor = new CombiFace(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 0)).build())
        solver.matchOne(anchor,
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 1)).build()
                , FaceDirection.Left)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        faces.add(
                HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", 2)).build())

        List<HappyFace> allPossibleCombinationsForAnchor = new ArrayList<>(2)
        def numOfOps = solver.solveForAnchor(0, anchor, faces, allPossibleCombinationsForAnchor)
        allPossibleCombinationsForAnchor.get(0).print()
        expect:
        allPossibleCombinationsForAnchor.size() == 1
        numOfOps == 0
    }


}

