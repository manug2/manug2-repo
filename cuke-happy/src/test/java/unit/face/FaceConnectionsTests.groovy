package unit.face

import happy.FaceConnections
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class FaceConnectionsTests extends Specification {

    def "there is a face connection"() {
        FaceConnections conns
        expect:
        true
    }

    def "connections can add a new connection"() {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def connections = new FaceConnections()
        connections.add(face1, face2);
        expect:
        true
    }

    def "connections can add two new connection"() {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face3.txt").build()
        def connections = new FaceConnections()
        connections.add(face1, face2);
        connections.add(face3, face1);
        expect:
        true
    }

    def "connections cannot add a null connection"() {
        when:
        def connections = new FaceConnections()
        connections.add(null, null);
        then:
        thrown(AssertionError)
    }

    def "connections cannot add a connection again"() {
        when:
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def connections = new FaceConnections()
        connections.add(face1, face2);
        connections.add(face1, face2);
        then:
        thrown(AssertionError)
    }

    def "connections are same"() {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def connections1 = new FaceConnections()
        connections1.add(face1, face2);
        def connections2 = new FaceConnections()
        connections2.add(face2, face1);
        expect:
        connections1.equals(connections2);

    }

    def "different connections are not same"() {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face3.txt").build()
        def connections1 = new FaceConnections()
        connections1.add(face1, face2);
        def connections2 = new FaceConnections()
        connections2.add(face3, face1);
        expect:
        ! connections1.equals(connections2);

    }

    def "unequal number of connections with several entries should not match"() {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face3.txt").build()

        def connections1 = new FaceConnections()
        connections1.add(face1, face2);
        connections1.add(face2, face3);
        connections1.add(face3, face1);

        def connections2 = new FaceConnections()
        connections2.add(face3, face1);
        connections2.add(face3, face2);
        expect:
        ! connections1.equals(connections2);

    }

    def "connections with several entries matching are same"() {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face2.txt").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face3.txt").build()

        def connections1 = new FaceConnections()
        connections1.add(face1, face2);
        connections1.add(face2, face3);
        connections1.add(face3, face1);

        def connections2 = new FaceConnections()
        connections2.add(face3, face1);
        connections2.add(face3, face2);
        connections2.add(face2, face1);
        expect:
        connections1.equals(connections2);

    }

    def "8 connections attached in arbitrary sequence do not match"() {
        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
        }
        //FaceConnections[face0<->face4 face4<->face5 face2<->face5 face0<->face2 face2<->face3 face3<->face4 face3<->face5 face0<->face3 ]
        def connections1 = new FaceConnections()
        connections1.add(faces.get(0), faces.get(4));
        connections1.add(faces.get(4), faces.get(5));
        connections1.add(faces.get(2), faces.get(5));
        connections1.add(faces.get(0), faces.get(2));
        connections1.add(faces.get(2), faces.get(3));
        connections1.add(faces.get(3), faces.get(4));
        connections1.add(faces.get(3), faces.get(5));
        connections1.add(faces.get(0), faces.get(3));

        //FaceConnections[face0<->face1 face0<->face3 face1<->face5 face3<->face5 face2<->face5 face0<->face2 face2<->face3 face1<->face2 ]
        def connections2 = new FaceConnections()
        connections2.add(faces.get(0), faces.get(1));
        connections2.add(faces.get(0), faces.get(3));
        connections2.add(faces.get(1), faces.get(5));
        connections2.add(faces.get(3), faces.get(5));
        connections2.add(faces.get(2), faces.get(5));
        connections2.add(faces.get(0), faces.get(2));
        connections2.add(faces.get(2), faces.get(3));
        connections2.add(faces.get(1), faces.get(2));
        expect:
        ! connections1.equals(connections2)

    }

    def "connections from two cubes are same" () {

        def faces = new ArrayList<HappyFace>(6)
        for ( i in [0, 1, 2, 3, 4, 5] ) {
            def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
        }

        //Two cubes are given by solver for above sequence of faces

        //FaceConnections[face0<->face3 face2<->face3 face0<->face2 face3<->face4
        def connections1 = new FaceConnections()
        connections1.add(faces.get(0), faces.get(3))
        connections1.add(faces.get(2), faces.get(3))
        connections1.add(faces.get(0), faces.get(2))
        connections1.add(faces.get(3), faces.get(4))

        // face0<->face4 face3<->face5 face2<->face5 face4<->face5
        connections1.add(faces.get(0), faces.get(4))
        connections1.add(faces.get(3), faces.get(5))
        connections1.add(faces.get(2), faces.get(5))
        connections1.add(faces.get(4), faces.get(5))

        // face0<->face1 face1<->face2 face1<->face5 face1<->face4 ]
        connections1.add(faces.get(0), faces.get(1))
        connections1.add(faces.get(1), faces.get(2))
        connections1.add(faces.get(1), faces.get(5))
        connections1.add(faces.get(1), faces.get(4))

        //FaceConnections[face0<->face1 face0<->face2 face1<->face2 face0<->face3
        def connections2 = new FaceConnections()
        connections2.add(faces.get(0), faces.get(1))
        connections2.add(faces.get(0), faces.get(2))
        connections2.add(faces.get(1), faces.get(2))
        connections2.add(faces.get(0), faces.get(3))

        // face2<->face3 face0<->face4 face1<->face4 face3<->face4
        connections2.add(faces.get(2), faces.get(3))
        connections2.add(faces.get(0), faces.get(4))
        connections2.add(faces.get(1), faces.get(4))
        connections2.add(faces.get(3), faces.get(4))

        // face1<->face5 face2<->face5 face3<->face5 face4<->face5 ]
        connections2.add(faces.get(1), faces.get(5))
        connections2.add(faces.get(2), faces.get(5))
        connections2.add(faces.get(3), faces.get(5))
        connections2.add(faces.get(4), faces.get(5))

        expect:
        connections1.equals(connections2)
    }


    def "connections from two cubes are same from given faces given in arbitrary sequence" () {

        def faces = new ArrayList<HappyFace>(6)
        for ( i in [4, 1, 0, 3, 5, 2] ) {
            def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile(String.format("src/test/resources/testFiles/face%d.txt", i)).build()
            faces.add(face)
        }

        //Two cubes are given by solver for above sequence of faces

        //FaceConnections[face4<->face5 face1<->face5 face1<->face4 face3<->face5
        def connections1 = new FaceConnections()
        connections1.add(faces.get(4), faces.get(5))
        connections1.add(faces.get(1), faces.get(5))
        connections1.add(faces.get(1), faces.get(4))
        connections1.add(faces.get(3), faces.get(5))

        // face3<->face4 face2<->face5 face1<->face2 face2<->face3
        connections1.add(faces.get(3), faces.get(4))
        connections1.add(faces.get(2), faces.get(5))
        connections1.add(faces.get(1), faces.get(2))
        connections1.add(faces.get(2), faces.get(3))

        // face0<->face1 face0<->face2 face0<->face3 face0<->face4 ]
        connections1.add(faces.get(0), faces.get(1))
        connections1.add(faces.get(0), faces.get(2))
        connections1.add(faces.get(0), faces.get(3))
        connections1.add(faces.get(0), faces.get(4))

        //FaceConnections[face1<->face4 face0<->face4 face0<->face1 face3<->face4
        def connections2 = new FaceConnections()
        connections2.add(faces.get(1), faces.get(4))
        connections2.add(faces.get(0), faces.get(4))
        connections2.add(faces.get(0), faces.get(1))
        connections2.add(faces.get(3), faces.get(4))

        // face0<->face3 face4<->face5 face1<->face5 face3<->face5
        connections2.add(faces.get(0), faces.get(3))
        connections2.add(faces.get(4), faces.get(5))
        connections2.add(faces.get(1), faces.get(5))
        connections2.add(faces.get(3), faces.get(5))

        // face1<->face2 face0<->face2 face2<->face3 face2<->face5 ]
        connections2.add(faces.get(1), faces.get(2))
        connections2.add(faces.get(0), faces.get(2))
        connections2.add(faces.get(2), faces.get(3))
        connections2.add(faces.get(2), faces.get(5))

        expect:
        connections1.equals(connections2)
    }

}
