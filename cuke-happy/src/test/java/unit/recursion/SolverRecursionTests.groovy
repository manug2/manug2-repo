package unit.recursion

import happy.CombiFace
import happy.CubeSolver
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class SolverRecursionTests extends Specification {

    def "solver solves with matching anchor using 2 faces"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        HappyFace anchor = solver.solve()
        expect:
        anchor.equals(faces.get(0))
    }

    def "solver solves with matching face on left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == faces.get(1)
    }

    def "solver solves using 2 faces with matching rotation of face on left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft().getRotation() == faces.get(1).getRotation()
    }

    def "solver solves in 1 operation using 2 faces with matching rotation of face on left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        int numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 1
    }

    def "solver solves using 2 faces without face at bottom"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getBottom() == null
    }

    def "solver solves using 3 faces with matching face at left"() {
        def solver = new CubeSolver(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1, 2] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == faces.get(1)
    }

    def "solver solves using 3 faces with matching face at bottom"() {
        def solver = new CubeSolver(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1, 2] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getBottom() == faces.get(2)
    }

    def "solver solves in 0 operations using 1 face"() {
        def solver = new CubeSolver(1)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        int numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 0
    }

    def "solver solves using 1 face"() {
        def solver = new CubeSolver(1)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        HappyFace anchor = solver.solve()
        expect:
        anchor == faces.get(0)
    }

    def "solver solves using 2 face in a different sequence"() {
        def solver = new CubeSolver(1)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 2] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        HappyFace anchor = solver.solve()
        anchor.print()
        expect:
        anchor == faces.get(0)
    }

    def "solver solves using 2 face skipping face1, with matching face at Left with rotation"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 2] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == faces.get(1)
        anchor.getLeft().getRotation() == 3
    }

    def "solver solves using 2 face skipping face1, with matching face at Left with rotation using 4 operations"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 2] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }
        CombiFace anchor = new CombiFace(faces.get(0))
        def numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 4
    }

    def "solver solves using 2 face in a skipping face1, face2, with matching face at Left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 3] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == faces.get(1)
        anchor.getLeft().getRotation() == 0
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, has not matching face at Left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 4] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == null
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, has not matching face at Bottom"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 4] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getBottom() == null
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, with matching face at Top"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 4] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getTop() == faces.get(1)
        anchor.getTop().getRotation() == 0
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, with matching face at Top, using 25 operations"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 4] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        int numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 25
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, face4, has not matching face at Left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 5] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == faces.get(1)
        anchor.getLeft().getRotation() == 2
    }

    def "solver solves using 2 face in a skipping face1, face2, face3, face4, has not matching face at Left,u using 3 operations"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 5] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        def numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 3
    }

    def "solver solves using 3 faces using 2 operations"() {
        def solver = new CubeSolver(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 1, 2] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        def numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 2
    }

    def "solver cannot solve using faces 0, 5, 1"() {
        when:
        def solver = new CubeSolver(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 5, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        then:
        thrown (RuntimeException)
    }

    def "solver cannot solve using faces 0, 5, 1 takes 35 operations"() {
        def solver = new CubeSolver(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 5, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        def numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 35
    }

    def "solver solves using faces 0, 4, 1 using 26 operations"() {
        def solver = new CubeSolver(3)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [0, 4, 1] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        def numOfOps = solver.solve(anchor, faces)
        expect:
        anchor.howManyAttached() == 2
        numOfOps == 26
    }

    def "solver solves with face1 anchor using 0, 1 faces"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 0] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        HappyFace anchor = solver.solve()
        expect:
        anchor.equals(faces.get(0))
    }

    def "solver solves with face1 anchor face0 matching on left"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 0] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = (CombiFace) solver.solve()
        expect:
        anchor.getLeft() == faces.get(1)
        anchor.getLeft().name() == "face0"
    }

    def "solver solves with face1 anchor using 0, 1 faces in 2 operations"() {
        def solver = new CubeSolver(2)
        def faces = new ArrayList<HappyFace>(solver.numOfFacesInSolution)
        for ( i in [1, 0] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }

        CombiFace anchor = new CombiFace(faces.get(0))
        int numOfOps = solver.solve(anchor, faces)
        expect:
        numOfOps == 2
    }

}
