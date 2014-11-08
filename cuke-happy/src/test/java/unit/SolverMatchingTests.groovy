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

    def "solver can try solving with 6 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        solver.solve()
        then: true
        //Doesn't expect any exception here
    }



}
