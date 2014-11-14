package unit

import happy.CombiFace
import happy.CubeSolver
import happy.HappyFace
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class UniqueSolutionTests extends Specification {

    def "solver can find two cubes from given faces" () {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find two cubes from given faces given in another sequence" () {
        when: "we have a solver and faces in another sequence"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 1, 2, 3, 5, 4] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find two cubes from given faces given in arbitrary sequence" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [4, 1, 0, 3, 5, 2] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find one or more cubes from Confusius faces" () {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/conf%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Confusius faces given in arbitrary sequence" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 1, 5, 3, 4, 2] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/conf%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find two cubes from given faces given in sequence per feature file" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [5, 2, 4, 3, 0, 1] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/face%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Confusius faces given in sequence per feature file" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 2, 3, 5, 1, 4] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/conf%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Watt faces" () {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/watt%d.txt", i), 5))
        def anchor = solver.solve()
        then:
        anchor != null
        anchor.print()
    }

    def "solver can find one or more cubes from Watt faces" () {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/watt%d.txt", i), 5))
        def anchor = solver.solve()
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Watt faces given in sequence per feature file" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver(5)
        System.out.println(solver)
        for ( i in [4, 2, 1, 5, 0, 3] )
            solver.loadFace(HappyFace.createFromFile(String.format("src/test/resources/testFiles/watt%d.txt", i), 5))
        def solutions = solver.solveUnique()
        then:
        1 == solutions.size()
    }

}
