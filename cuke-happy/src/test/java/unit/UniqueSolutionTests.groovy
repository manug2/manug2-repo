package unit

import happy.CubeSolver
import happy.HappyFace
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class UniqueSolutionTests extends Specification {

    def "solver can find one or more cubes from given faces" () {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def solutions = solver.solveUnique()
        System.out.println("Number of solutions = " + solutions.size())
        then:
        0 < solutions.size()

    }
}
