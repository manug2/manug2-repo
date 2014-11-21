package unit.recursion

import happy.CubeSolver
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class UniqueSolutionRecursionTests extends Specification {

    def "solver gives all possible combinations solutions with faces 0, 1"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        List<HappyFace> allPossibleCombinations = new ArrayList<>(2)
        solver.solveAllPossibleCombinations(allPossibleCombinations)
        expect:
        allPossibleCombinations.size() == 2
    }

    def "solver gives all possible combinations solutions with faces 0, 1, in 2 operations"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        List<HappyFace> allPossibleCombinations = new ArrayList<>(2)
        def numOfOps = solver.solveAllPossibleCombinations(allPossibleCombinations)
        expect:
        numOfOps == 3
    }

    def "solver gives unique solutions with faces 0, 1"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        List<HappyFace> uniqueSolutions = solver.solveUnique()
        expect:
        uniqueSolutions.size() == 1
    }
}
