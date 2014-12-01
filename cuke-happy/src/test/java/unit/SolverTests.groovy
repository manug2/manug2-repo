package unit

import happy.CubeSolver
import happy.HappyFaceBuilder;
import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification

import happy.HappyFace;

@RunWith(Sputnik)
class SolverTests extends Specification {
    def "there is a solver"() {
        CubeSolver s
        expect: true
    }


    def "cannot solve without a face"() {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        solver.solve(null)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs 6 faces, received null"
    }

    def "cannot solve with 2 faces when six are expected"() {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        def faces = new ArrayList<>(2)
        for ( i in [0, 1] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build())
        solver.solve(faces)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs 6 faces"
    }

    def "cannot solve with 3 faces when six are expected"() {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        def faces = new ArrayList<>(3)
        for ( i in [0, 1, 2] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build())
        solver.solve(faces)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs 6 faces"
    }

    def "cannot solve with 4 faces when six are expected"() {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        def faces = new ArrayList<>(4)
        for ( i in [0, 1, 2, 3] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build())
        solver.solve(faces)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs 6 faces"
    }

    def "cannot solve with 5 faces when six are expected"() {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        def faces = new ArrayList<>(5)
        for ( i in [0, 1, 2, 3, 4] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build())
        solver.solve(faces)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs 6 faces"
    }

}
