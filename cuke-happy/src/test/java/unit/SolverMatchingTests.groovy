package unit

import happy.CubeSolver
import happy.HappyFace
import org.junit.runner.RunWith

/**
 * Created with IntelliJ IDEA.
 * User: 1301121
 * Date: 11/5/14
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class SolverMatchingTests extends Specification {

   def "solve with 6 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(new HappyFace(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        solver.solve()
        then: true
        //Doesn't expect any exception here
    }

    def "solver can match 2 faces that are aligned"() {
        when: "we have a solver and 2 faces"
        def solver = new CubeSolver(3)
        solver.loadFace(HappyFace.createFromString("0 1 0;1 0 1;1 0 1", 3));
        solver.loadFace(HappyFace.createFromString("1 0 1;0 1 0;0 1 0", 3));
        def matched = solver.match(0, 1)
        then: true == matched
    }

    def "solver does not attempt to match invalid first face index"() {
        when: "we have a solver and 2 faces"
        def solver = new CubeSolver(5)
        for ( i in [0, 1] )
            solver.loadFace(new HappyFace(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def matched = solver.match(10, 1)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube does not have face with index [10]"
    }

    def "solver does not attempt to match invalid second face index"() {
        when: "we have a solver and 2 faces"
        def solver = new CubeSolver(5)
        for ( i in [0, 1] )
            solver.loadFace(new HappyFace(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def matched = solver.match(0, 11)
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "the cube does not have face with index [11]"
    }

    def "solver can match 2 faces that are aligned after rotating one"() {
        when: "we have a solver and 2 faces"
        def solver = new CubeSolver(5)
        //solver.loadFace(HappyFace.createFromString("0 1 0;1 0 1;1 0 1", 3));
        solver.loadFace(HappyFace.createFromString("1 0 1;0 1 0;0 1 0", 3));
        def matched = solver.match(0, 1)
        then: true == matched
    }

}
