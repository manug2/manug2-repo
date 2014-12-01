package unit

import happy.CubeSolver
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class UniqueSolutionTests extends Specification {

    List<HappyFace> faces
    def setup() {
        faces = new ArrayList<>(6)
    }
    def "solver can find one cube from given faces" () {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 4, 5] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from given faces given in another sequence" () {
        when: "we have a solver and faces in another sequence"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 5, 4] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find two cubes from given faces given in arbitrary sequence" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [4, 1, 0, 3, 5, 2] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one or more cubes from Confusius faces" () {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 4, 5] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/conf%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Confusius faces given in arbitrary sequence" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 5, 3, 4, 2] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/conf%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from given faces given in sequence per feature file" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [5, 2, 4, 3, 0, 1] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Confusius faces given in sequence per feature file" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 2, 3, 5, 1, 4] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/conf%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Watt faces" () {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 4, 5] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/watt%d.txt", i)).build())
        def anchor = solver.solve(faces)
        then:
        anchor != null
        anchor.print()
    }

    def "solver can find one or more cubes from Watt faces" () {
        when: "we have a solver"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 4, 5] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/watt%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

    def "solver can find one cube from Watt faces given in sequence per feature file" () {
        when: "we have a solver and faces in incorrect sequence"
        def solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [4, 2, 1, 5, 0, 3] )
            faces.add(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/watt%d.txt", i)).build())
        def solutions = solver.solveUnique(faces)
        then:
        1 == solutions.size()
    }

}
