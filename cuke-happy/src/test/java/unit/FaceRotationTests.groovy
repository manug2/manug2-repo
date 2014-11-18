package unit

import happy.HappyFace
import happy.HappyFaceBuilder
import happy.InvalidRotationException
import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification;

@RunWith(Sputnik)
class FaceRotationTests extends Specification {

    def "2x2 face has expected rows after clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "2x2 face has expected rows after clock-wise rotation 2" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 1").build()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([1, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after clock-wise rotation 2" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 1").build()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 1] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "3x3 face has expected rows after clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 1, 0] == rotatedFace.getRows(0)) && ([1, 1, 1] == rotatedFace.getRows(1)) && ([1, 1, 0] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected columns after clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def rotatedFace = face.rotateCW()
        rotatedFace.print()
        expect:
        ([1, 1, 1] == rotatedFace.getColumns(0)) && ([1, 1, 1] == rotatedFace.getColumns(1)) && ([0, 1, 0] == rotatedFace.getColumns(2))
    }

    def "3x3 face has expected columns after flipping" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def flippedFace = face.flip()
        flippedFace.print()
        expect:
        ([0, 1, 1] == flippedFace.getColumns(0)) && ([1, 1, 1] == flippedFace.getColumns(1)) && ([0, 1, 1] == flippedFace.getColumns(2))
    }

    def "3x3 face has expected rows after flipping" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;0 1 1").build()
        face.print()
        def flippedFace = face.flip()
        flippedFace.print()
        expect:
        ([0, 1, 1] == flippedFace.getRows(0)) && ([1, 1, 1] == flippedFace.getRows(1)) && ([1, 1, 0] == flippedFace.getRows(2))
    }

    def "2x2 face has expected rows after 3 clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getRows(0)) && ([0, 1] == rotatedFace.getRows(1))
    }

    def "2x2 face has expected columns after 3 clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([1, 0] == rotatedFace.getColumns(0)) && ([0, 1] == rotatedFace.getColumns(1))
    }

    def "3x3 face has expected rows after 3 clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([0, 1, 1] == rotatedFace.getRows(0)) && ([1, 1, 1] == rotatedFace.getRows(1)) && ([0, 1, 1] == rotatedFace.getRows(2))
    }

    def "3x3 face has expected columns after 3 clock-wise rotation" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def rotatedFace = face.rotateCW().rotateCW().rotateCW()
        rotatedFace.print()
        expect:
        ([0, 1, 0] == rotatedFace.getColumns(0)) && ([1, 1, 1] == rotatedFace.getColumns(1)) && ([1, 1, 1] == rotatedFace.getColumns(2))
    }

    def "3x3 face makes one additional rotation automatically when first rotation leads to identical previous face" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def rotatedFace = face.rotate()
        rotatedFace.print()
        expect:
        2 == rotatedFace.getRotation();
        ([1, 1, 1] == rotatedFace.getColumns(0)) && ([1, 1, 1] == rotatedFace.getColumns(1)) && ([0, 1, 0] == rotatedFace.getColumns(2))
    }
    def "3x3 face makes additional rotations automatically when third rotation leads to identical previous faces recursively" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;1 1 0").build()
        face.print()
        def rotatedFace = face.rotate().rotate().rotate()
        rotatedFace.print()
        expect:
        6 == rotatedFace.getRotation();
        ([0, 1, 0] == rotatedFace.getRows(0)) && ([1, 1, 1] == rotatedFace.getRows(1)) && ([1, 1, 1] == rotatedFace.getRows(2))
    }

    def "original face has correct set of previous matrices without rotation"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;1 1 0").build()
        face.print()
        face.rotate()
        expect:
        face.getRotation() == 0
        face.getPreviousMatrices().size()==1
        [1, 1, 0] == face.getRows(0) && [1, 1, 1] == face.getRows(1) && [1, 1, 0] == face.getRows(2)
        "[[1 1 0][1 1 1][1 1 0]]" == face.getPreviousMatrices().get(0)
    }

    def "new face has correct set of previous matrices after 1 rotation"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;1 1 0").build()
        face.print()
        def rotatedFace = face.rotate()
        rotatedFace.print()
        expect:
        rotatedFace.getRotation() == 1
        rotatedFace.getPreviousMatrices().size()==2
        [0, 1, 1] == rotatedFace.getRows(0) && [1, 1, 1] == rotatedFace.getRows(1) && [0, 1, 1] == rotatedFace.getRows(2)

        //New face is first entry in previousMatrices
        "[[0 1 1][1 1 1][0 1 1]]" == rotatedFace.getPreviousMatrices().get(0)

        //original face is equal to second entry in previousMatrices
        face.getMatrixAsString().equals(rotatedFace.getPreviousMatrices().get(1))
    }

    def "new face has correct set of previous matrices after 2 rotations"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;1 1 0").build()
        face.print()
        def rotatedFace = face.rotate().rotate()
        rotatedFace.print()
        expect:
        rotatedFace.getRotation() == 2
        rotatedFace.getPreviousMatrices().size()==3
        [1, 1, 1] == rotatedFace.getRows(0) && [1, 1, 1] == rotatedFace.getRows(1) && [0, 1, 0] == rotatedFace.getRows(2)

        //original face is equal to third entry in previousMatrices
        face.getMatrixAsString().equals(rotatedFace.getPreviousMatrices().get(2))

        //latest rotated matrix is equal to first entry in previousMatrices
        rotatedFace.getMatrixAsString().equals(rotatedFace.getPreviousMatrices().get(0))
    }

    def "original face retains previous matrices after one rotation" () {
        when: "first rotation leads to identical previous face"
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        face.rotate()
        then:
        face.getRotation() == 0
        face.getPreviousMatrices().size()==1
        ([0, 1, 0] == face.getRows(0)) && ([1, 1, 1] == face.getRows(1)) && ([1, 1, 1] == face.getRows(2))
        face.getMatrixAsString().equals(face.getPreviousMatrices().get(0))
    }

    def "new face has correct set of previous matrices after one additional automatic rotation" () {
        when: "first rotation leads to identical previous face"
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        def rotatedFace = face.rotate()
        rotatedFace.print()
        then:
        rotatedFace.getRotation() == 2
        rotatedFace.getPreviousMatrices().size()==2
        ([1, 1, 1] == rotatedFace.getColumns(0)) && ([1, 1, 1] == rotatedFace.getColumns(1)) && ([0, 1, 0] == rotatedFace.getColumns(2))
    }

    def "rotated face clone has expected previousMatrices"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;1 1 0").build()
        face.print()
        def rotatedFace = face.rotate()
        rotatedFace.print()
        def clonedFace = rotatedFace.clone()
        clonedFace.print()
        expect:
        clonedFace.getRotation() == 1
        clonedFace.getPreviousMatrices().size()==2
        [0, 1, 1] == clonedFace.getRows(0) && [1, 1, 1] == clonedFace.getRows(1) && [0, 1, 1] == clonedFace.getRows(2)

        //New face is first entry in previousMatrices
        "[[0 1 1][1 1 1][0 1 1]]" == rotatedFace.getPreviousMatrices().get(0)
        face.getMatrixAsString().equals(clonedFace.getPreviousMatrices().get(1))
    }

}
