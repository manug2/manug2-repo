package unit

import happy.HappyFace
import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification;

@RunWith(Sputnik)
class FaceRotationTests extends Specification {

    def "2x2 face has expected rows after clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "2x2 face has expected rows after clock-wise rotation 2" () {
        def face = HappyFace.createFromString("0 1;1 1", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([1, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after clock-wise rotation 2" () {
        def face = HappyFace.createFromString("0 1;1 1", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 1] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "3x3 face has expected rows after clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 1, 0] == rotatedFace.getRows(0)) && ([1, 0, 1] == rotatedFace.getRows(1)) && ([1, 1, 0] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected columns after clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 1, 1] == rotatedFace.getColumns(0)) && ([1, 0, 1] == rotatedFace.getColumns(1)) && ([0, 1, 0] == rotatedFace.getColumns(2))
    }

    def "2x2 face has expected rows after counter-clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCC()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after counter-clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCC()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "3x3 face has expected rows after counter-clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotateCC()
        rotatedFace.print()
        expect:
        ([0, 1, 1] == rotatedFace.getRows(0)) && ([1, 0, 1] == rotatedFace.getRows(1)) && ([0, 1, 1] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected columns after counter-clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotateCC()
        rotatedFace.print()
        expect:
        ([0, 1, 0] == rotatedFace.getColumns(0)) && ([1, 0, 1] == rotatedFace.getColumns(1)) && ([1, 1, 1] == rotatedFace.getColumns(2))
    }

    def "3x3 face has expected columns after flipping" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def flippedFace = face.flip()
        flippedFace.print()
        expect:
        ([1, 0, 0] == flippedFace.getColumns(0)) && ([0, 1, 0] == flippedFace.getColumns(1)) && ([1, 0, 0] == flippedFace.getColumns(2))
    }

    def "3x3 face has expected rows after flipping" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def flippedFace = face.flip()
        flippedFace.print()
        expect:
        ([1, 0, 1] == flippedFace.getRows(0)) && ([0, 1, 0] == flippedFace.getRows(1)) && ([0, 0, 0] == flippedFace.getRows(2))
    }

    def "2x2 face has expected rows after 3 clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after 3 clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "3x3 face has expected rows after 3 clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([0, 1, 1] == rotatedFace.getRows(0)) && ([1, 0, 1] == rotatedFace.getRows(1)) && ([0, 1, 1] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected columns after 3 clock-wise rotation" () {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([0, 1, 0] == rotatedFace.getColumns(0)) && ([1, 0, 1] == rotatedFace.getColumns(1)) && ([1, 1, 1] == rotatedFace.getColumns(2))
    }

    def "3x3 face has expected columns after vertical flipping" () {
        def face = HappyFace.createFromString("1 1 0;1 0 1;0 1 1", 3)
        face.load()
        face.print()
        def flippedFace = face.verticalFlip()
        flippedFace.print()
        expect:
        ([0, 1, 1] == flippedFace.getColumns(0)) && ([1, 0, 1] == flippedFace.getColumns(1)) && ([1, 1, 0] == flippedFace.getColumns(2))
    }

}
