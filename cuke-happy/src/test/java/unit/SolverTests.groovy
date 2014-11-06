package unit

import happy.CubeSolver

/**
 * Created with IntelliJ IDEA.
 * User: 1301121
 * Date: 11/5/14
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */

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

    def "there is a solver for 3 element face"() {
        def solver = new CubeSolver(3)
        expect : 3 == solver.elementCount();
    }

    def "there is a solver for 5 element face"() {
        def solver = new CubeSolver(5)
        expect : 5 == solver.elementCount();
    }

    def "solver can load a face"() {
        def solver = new CubeSolver(5)
        solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        expect : true
    }

    def "solver cannot load a bad face"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        solver.loadFace(new HappyFace("bad_bad.txt", 5))
        then:
        thrown(IOException)
    }
    def "7th face cannot be loaded" () {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5, 6] )
            solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        then:
        def e = thrown(AssertionError)
        expect:e.getMessage() == "cannot load more than 6 faces"
    }

    def "cannot solve without a face"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        solver.solve()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs six faces"
    }

    def "cannot solve with 2 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1] )
            solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        solver.solve()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs six faces"
    }

    def "cannot solve with 3 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2] )
            solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        solver.solve()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs six faces"
    }

    def "cannot solve with 4 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3] )
            solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        solver.solve()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs six faces"
    }

    def "cannot solve with 5 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4] )
            solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        solver.solve()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs six faces"
    }

    def "solver cannot load face with diff number of elements"() {
        when: "we have a solver"
        def solver = new CubeSolver(3)
        solver.loadFace(new HappyFace("src/test/resources/testFiles/face1.txt", 5))
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube needs faces with [3] elements"
    }

}
