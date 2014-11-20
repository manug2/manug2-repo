package unit

import happy.CombiFace
import happy.CubeSolver
import happy.FaceDirection
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith

import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class SolverMatchingTests extends Specification {

    def "solver can try solving with 6 faces"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        solver.solve()
        then:
        true
    }

    def "face1.txt matches face0.txt on Left with 0 rotation"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))
        def face = solver.getFace(1)
        expect :
        null != solver.matchOne(anchor, face, FaceDirection.Left, 0)
    }

    def "face2.txt matches face0.txt on Bottom after Left attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)

        def face = solver.getFace(2)
        def matched = solver.matchOne(anchor, face, FaceDirection.Bottom, 0)
        expect :
        null != matched
    }

    def "face3.txt matches face0.txt on Right after Left, Bottom attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)

        def face = solver.getFace(3)
        def matched = solver.matchOne(anchor, face, FaceDirection.Right, 0)
        expect :
        null != matched
    }

    def "face4.txt matches face0.txt on Top after Left, Bottom, Right attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)
        solver.matchOne(anchor, solver.getFace(3), FaceDirection.Right, 0)

        def face = solver.getFace(4)
        def matched = solver.matchOne(anchor, face, FaceDirection.Top, 0)
        expect :
        null != matched
    }

    def "face5.txt matches face0.txt on Parallel side when all other faces attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)
        solver.matchOne(anchor, solver.getFace(3), FaceDirection.Right, 0)
        solver.matchOne(anchor, solver.getFace(4), FaceDirection.Top, 0)

        def face = solver.getFace(5)
        def matched = solver.matchOne(anchor, face, FaceDirection.Parallel, 0)
        expect :
        null != matched
    }

    def "solver cannot solve try solving with 6 faces when one is incompatible"() {
        when: "we have a solver"
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 6] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        solver.solve()
        then:
        thrown(RuntimeException)
    }

    def "conf5.txt matches conf0.txt on Parallel side when all other faces attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/conf%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)
        solver.matchOne(anchor, solver.getFace(3), FaceDirection.Right, 0)
        solver.matchOne(anchor, solver.getFace(4), FaceDirection.Top, 0)

        def face = solver.getFace(5)
        def matched = solver.matchOne(anchor, face, FaceDirection.Parallel, 0)
        expect :
        null != matched
    }

    def "face4.txt cannot match face0.txt on Parallel side when all other faces attached"() {
        when:
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 5, 4] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)
        solver.matchOne(anchor, solver.getFace(3), FaceDirection.Right, 0)
        solver.matchOne(anchor, solver.getFace(4), FaceDirection.Top, 0)

        def face = solver.getFace(5)
        def matched = solver.matchOne(anchor, face, FaceDirection.Parallel, 0)
        then :
        null == matched

    }

    def "face1.txt connects with face0.txt on Left"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))
        def face = solver.getFace(1)
        solver.matchOne(anchor, face, FaceDirection.Left, 0)
        expect :
        anchor.getConnections().size() == 1
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 ]"
    }

    def "face2.txt connects with face0.txt on Bottom after Left attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)

        def face = solver.getFace(2)
        solver.matchOne(anchor, face, FaceDirection.Bottom, 0)
        anchor.print()
        expect :
        anchor.getConnections().size() == 3
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 ]"
    }

    def "face3.txt connects with face0.txt on Right after Left, Bottom attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)

        def face = solver.getFace(3)
        solver.matchOne(anchor, face, FaceDirection.Right, 0)
        anchor.print()
        expect :
        anchor.getConnections().size() == 5
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3 face2<->face3 ]"
    }

    def "face4.txt connects with face0.txt on Top after Left, Bottom, Right attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)
        solver.matchOne(anchor, solver.getFace(3), FaceDirection.Right, 0)

        def face = solver.getFace(4)
        solver.matchOne(anchor, face, FaceDirection.Top, 0)
        anchor.print()
        expect :
        anchor.getConnections().size() == 8
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3 face2<->face3 face0<->face4 face1<->face4 face3<->face4 ]"
    }

    def "face5.txt connects with face0.txt on Parallel side when all other faces attached"() {
        def solver = new CubeSolver(5)
        for ( i in [0, 1, 2, 3, 4, 5] )
            solver.loadFace(HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build())
        CombiFace anchor = new CombiFace(solver.getFace(0))

        solver.matchOne(anchor, solver.getFace(1), FaceDirection.Left, 0)
        solver.matchOne(anchor, solver.getFace(2), FaceDirection.Bottom, 0)
        solver.matchOne(anchor, solver.getFace(3), FaceDirection.Right, 0)
        solver.matchOne(anchor, solver.getFace(4), FaceDirection.Top, 0)

        def face = solver.getFace(5)
        solver.matchOne(anchor, face, FaceDirection.Parallel, 0)
        expect :
        anchor.getConnections().size() == 12
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3 face2<->face3 face0<->face4 face1<->face4 face3<->face4 face1<->face5 face2<->face5 face3<->face5 face4<->face5 ]"
    }

    def "solver can prepare faces for recursion once a match is found"() {
        def solver = new CubeSolver(5)
        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }
        def anchor = new CombiFace(faces.get(0))
        faces.remove(anchor)
        def found = faces.get(1)
        def facesForRecursion = solver.getFacesForRecursion(found, faces)
        expect:
        facesForRecursion.size() == 4
        ! facesForRecursion.contains(anchor)
        ! facesForRecursion.contains(found)

    }

    def "solver gives list of tried combinations"() {
        def solver = new CubeSolver(5)
        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }
        def anchor = new CombiFace(faces.get(0))
        faces.remove(anchor)
        def validCombinations = new ArrayList<HappyFace>(1)
        solver.solveForAnchor(0, anchor, faces, validCombinations)
        expect:
        solver.getCombinationsTried() != null
        solver.getCombinationsTried().size() == 158
    }

    def "solver gives expected list of first 5 tried combinations for given faces in matching sequence"() {
        def solver = new CubeSolver(5)
        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }
        def anchor = new CombiFace(faces.get(0))
        faces.remove(anchor)
        def validCombinations = new ArrayList<HappyFace>(1)
        solver.solveForAnchor(0, anchor, faces, validCombinations)
        def combos = solver.getCombinationsTried()
        expect:
        combos[0] == "0,1,face1,0,Left,face0,0,true"
        combos[1] == "1,2,face2,0,Bottom,face0,0,face1,0,Left,true"
        combos[2] == "2,3,face3,0,Right,face0,0,face1,0,Left,face2,0,Bottom,true"
        combos[3] == "3,4,face4,0,Top,face0,0,face1,0,Left,face2,0,Bottom,face3,0,Right,true"
        combos[4] == "4,5,face5,0,Parallel,face0,0,face1,0,Left,face2,0,Bottom,face3,0,Right,face4,0,Top,true"
    }

    def "solver for given faces in matching sequence, prints solution in the 6th entry of tried combinations"() {
        def solver = new CubeSolver(5)
        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }
        def anchor = new CombiFace(faces.get(0))
        faces.remove(anchor)
        def validCombinations = new ArrayList<HappyFace>(1)
        solver.solveForAnchor(0, anchor, faces, validCombinations)
        def combos = solver.getCombinationsTried()
        expect:
        combos[5] == "[SOLUTION :4:5] [face0 0] - [face1 0 (Left)] - [face2 0 (Bottom)] - [face3 0 (Right)] - [face4 0 (Top)] - [face5 0 (Parallel)]"
    }

    def "solver for given faces in matching sequence, tries next combination after back-stepping"() {
        def solver = new CubeSolver(5)
        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
            solver.loadFace(face)
        }
        def anchor = new CombiFace(faces.get(0))
        faces.remove(anchor)
        def validCombinations = new ArrayList<HappyFace>(1)
        solver.solveForAnchor(0, anchor, faces, validCombinations)
        def combos = solver.getCombinationsTried()
        expect:
        combos[6] == "3,9,face5,4,Top,face0,0,face1,0,Left,face2,0,Bottom,face3,0,Right,false"
    }

}
