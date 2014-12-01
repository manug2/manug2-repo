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

    List<HappyFace> faces
    CubeSolver solver
    CombiFace anchor
    def setup() {
        faces = new ArrayList<HappyFace>(6)
        solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
        }
        anchor = new CombiFace(faces.get(0))
    }

    def "solver can try solving with 6 faces"() {
        when: "we have a solver"
        solver.solve(faces)
        then:
        true
    }

    def "face1.txt matches face0.txt on Left with 0 rotation"() {
        def face = faces.get(1)
        expect :
        null != solver.matchOne(anchor, face, FaceDirection.Left)
    }

    def "face2.txt matches face0.txt on Bottom after Left attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)

        def face = faces.get(2)
        def matched = solver.matchOne(anchor, face, FaceDirection.Bottom)
        expect :
        null != matched
    }

    def "face3.txt matches face0.txt on Right after Left, Bottom attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)

        def face = faces.get(3)
        def matched = solver.matchOne(anchor, face, FaceDirection.Right)
        expect :
        null != matched
    }

    def "face4.txt matches face0.txt on Top after Left, Bottom, Right attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)
        solver.matchOne(anchor, faces.get(3), FaceDirection.Right)

        def face = faces.get(4)
        def matched = solver.matchOne(anchor, face, FaceDirection.Top)
        expect :
        null != matched
    }

    def "face5.txt matches face0.txt on Parallel side when all other faces attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)
        solver.matchOne(anchor, faces.get(3), FaceDirection.Right)
        solver.matchOne(anchor, faces.get(4), FaceDirection.Top)

        def face = faces.get(5)
        def matched = solver.matchOne(anchor, face, FaceDirection.Parallel)
        expect :
        null != matched
    }

    def "solver cannot solve try solving with 6 faces when one is incompatible"() {
        when: "we have a solver"
        faces = new ArrayList<HappyFace>(6)
        solver = new CubeSolver().usingNumOfFaces(6)
        for ( i in [0, 1, 2, 3, 4, 6] ) {
            HappyFace face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
        }
        solver.solve(faces)
        then:
        thrown(RuntimeException)
    }

    def "conf5.txt matches conf0.txt on Parallel side when all other faces attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)
        solver.matchOne(anchor, faces.get(3), FaceDirection.Right)
        solver.matchOne(anchor, faces.get(4), FaceDirection.Top)

        def face = faces.get(5)
        def matched = solver.matchOne(anchor, face, FaceDirection.Parallel)
        expect :
        null != matched
    }

    def "face4.txt cannot match face0.txt on Parallel side when all other faces attached"() {
        when:
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)
        solver.matchOne(anchor, faces.get(3), FaceDirection.Right)
        solver.matchOne(anchor, faces.get(5), FaceDirection.Top)

        def face = faces.get(4)
        def matched = solver.matchOne(anchor, face, FaceDirection.Parallel)
        then :
        null == matched

    }

    def "face1.txt connects with face0.txt on Left"() {
        def face = faces.get(1)
        solver.matchOne(anchor, face, FaceDirection.Left)
        expect :
        anchor.getConnections().size() == 1
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 ]"
    }

    def "face2.txt connects with face0.txt on Bottom after Left attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)

        def face = faces.get(2)
        solver.matchOne(anchor, face, FaceDirection.Bottom)
        anchor.print()
        expect :
        anchor.getConnections().size() == 3
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 ]"
    }

    def "face3.txt connects with face0.txt on Right after Left, Bottom attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)

        def face = faces.get(3)
        solver.matchOne(anchor, face, FaceDirection.Right)
        anchor.print()
        expect :
        anchor.getConnections().size() == 5
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3 face2<->face3 ]"
    }

    def "face4.txt connects with face0.txt on Top after Left, Bottom, Right attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)
        solver.matchOne(anchor, faces.get(3), FaceDirection.Right)

        def face = faces.get(4)
        solver.matchOne(anchor, face, FaceDirection.Top)
        anchor.print()
        expect :
        anchor.getConnections().size() == 8
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3 face2<->face3 face0<->face4 face1<->face4 face3<->face4 ]"
    }

    def "face5.txt connects with face0.txt on Parallel side when all other faces attached"() {
        solver.matchOne(anchor, faces.get(1), FaceDirection.Left)
        solver.matchOne(anchor, faces.get(2), FaceDirection.Bottom)
        solver.matchOne(anchor, faces.get(3), FaceDirection.Right)
        solver.matchOne(anchor, faces.get(4), FaceDirection.Top)

        def face = faces.get(5)
        solver.matchOne(anchor, face, FaceDirection.Parallel)
        expect :
        anchor.getConnections().size() == 12
        anchor.getConnections().toString() == "FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3 face2<->face3 face0<->face4 face1<->face4 face3<->face4 face1<->face5 face2<->face5 face3<->face5 face4<->face5 ]"
    }

    def "solver can prepare faces for recursion once a match is found"() {
        faces.remove(anchor)
        def found = faces.get(1)
        def facesForRecursion = solver.getFacesForRecursion(found, faces)
        expect:
        facesForRecursion.size() == 4
        ! facesForRecursion.contains(anchor)
        ! facesForRecursion.contains(found)

    }

}
