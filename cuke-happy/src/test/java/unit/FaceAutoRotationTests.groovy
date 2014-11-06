package unit

import happy.HappyFace
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class FaceAutoRotationTests extends Specification {

    def "face has rotation index" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        expect: 0 ==  face.getRotation()
    }

    def "2x2 face has expected rotation index after 1 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 1 && ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected rotation index after 2 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 2 && ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected rotation index after 3 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 3 && ([0, 1] == rotatedFace.getRows(0)) && ([1, 0] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected rotation index after 4 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 4 && ([0, 1] == rotatedFace.getRows(0)) && ([1, 0] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected rotation index after 5 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 5 && ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected rotation index after 6 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 6 && ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected rotation index after 7 auto-rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 7 && ([0, 1] == rotatedFace.getRows(0)) && ([1, 0] == rotatedFace.getRows(1))
    }

    def "3x3 face has expected rotation index after 1 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate()
        rotatedFace.print()
        expect:
        rotatedFace.getRotation() == 1 && ([1, 0, 1] == rotatedFace.getRows(0)) && ([0, 1, 0] == rotatedFace.getRows(1)) && ([0, 0, 0] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected rotation index after 2 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate()
        rotatedFace.print()
        expect:
        rotatedFace.getRotation() == 2 && ([1, 1, 0] == rotatedFace.getRows(0)) && ([1, 0, 1] == rotatedFace.getRows(1)) && ([1, 1, 0] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected rotation index after 3 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 3 && ([0, 0, 1] == rotatedFace.getRows(0)) && ([0, 1, 0] == rotatedFace.getRows(1)) && ([0, 0, 1] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected rotation index after 4 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 4 && ([1, 1, 1] == rotatedFace.getRows(0)) && ([1, 0, 1] == rotatedFace.getRows(1)) && ([0, 1, 0] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected rotation index after 5 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 5 && ([0, 0, 0] == rotatedFace.getRows(0)) && ([0, 1, 0] == rotatedFace.getRows(1)) && ([1, 0, 1] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected rotation index after 6 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 6 && ([0, 1, 1] == rotatedFace.getRows(0)) && ([1, 0, 1] == rotatedFace.getRows(1)) && ([0, 1, 1] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected rotation index after 7 auto-rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate().rotate().rotate().rotate().rotate()
        rotatedFace.print()
        expect:  rotatedFace.getRotation() == 7 && ([1, 0, 0] == rotatedFace.getRows(0)) && ([0, 1, 0] == rotatedFace.getRows(1)) && ([1, 0, 0] == rotatedFace.getRows(2))
    }

}
