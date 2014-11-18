package unit

import happy.HappyFaceBuilder
import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification

@RunWith(Sputnik)
class FaceTests extends Specification {

    def "blank face does not build" () {
        when:
        HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/blank.txt").build()
        then:
        thrown(AssertionError)
    }

    def "incomplete face does not build" () {
        when:
        HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/incompleteCols.txt").build()
        then:
        thrown(AssertionError)
    }

    def "face does not build without knowing number of elements" () {
        when:
        HappyFaceBuilder.createBuilder().usingFile("src/test/resources/testFiles/face1.txt").build()
        then:
        thrown(AssertionError)
    }

    def "built face should have 5 rows" () {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def matrix = face.getMatrix()
        then:
        matrix.length == 5
    }

    def "loaded face should have 5 items in each rows" () {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def matrix = face.getMatrix()
        def asExpected = true
        for (int i=0; i < 5; i++)
            asExpected &= (5 ==matrix[i].length)
        then:
        asExpected
    }

    def "create a face using input string"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        expect : face!=null
    }

    def "data loaded face should have 3 rows" () {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def matrix = face.getMatrix()
        then:
        matrix.length == 3
    }

    def "data loaded face should have 3 items in each rows" () {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def matrix = face.getMatrix()
        def asExpected = true
        for (int i=0; i < 3; i++)
            asExpected &= (3 ==matrix[i].length)
        then:
        asExpected
    }

    def "face can print" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.print()
        expect: true
    }

    def "face can rotate" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.rotateCW()
        expect: true
    }
    def "face is equal to self" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        expect: face.equals(face)
    }

    def "face is not equal to an identical face" () {
        when: "we have two faces, with same data"
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        then:
        ! face1.equals(face2)
    }

    def "faces are identical" () {
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        expect:
        face1.identical(face2)
    }

    def "face has column" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.getColumns(0)
        expect: true
    }
    def "face has row"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.getRows(0)
        expect: true
    }

    def "face does not have invalid column" () {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.getColumns(10)
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face number of elements is limited to [3], but requested for [10]"
    }

    def "face does not have invalid row" () {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face.getRows(10)
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face number of elements is limited to [3], but requested for [10]"
    }

    def "3x3 face has expected columns" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        expect:
        ([0, 1, 1] == face.getColumns(0)) && ([1, 1, 1] == face.getColumns(1)) && ([0, 1, 1] == face.getColumns(2))
    }

    def "3x3 face has expected rows" () {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        expect:
        ([0, 1, 0] == face.getRows(0)) && ([1, 1, 1] == face.getRows(1)) && ([1, 1, 1] == face.getRows(2))
    }

    def "3x3 face can clone a face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def face1 = face.clone()
        expect:
        face.equals(face1)
        face.identical(face1)
    }

    def "face cannot have top side with all '0'" () {
        when:
        HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 0;1 1 1;1 1 1").build()
        then:
        def e = thrown(AssertionError)
        e.getMessage().endsWith(" has all items in row [0] as '0'")
    }

    def "face cannot have right side with all '0'" () {
        when:
        HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 0;1 1 0").build()
        then:
        def e = thrown(AssertionError)
        e.getMessage().endsWith(" has all items in column [2] as '0'")
    }

    def "face1 has a name"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        expect :
        "face1" == face.name()
    }

    def "conf5 has a name"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/conf5.txt").build()
        expect :
        "conf5" == face.name()
    }

    def "cloned face has one item in previousMatrices"() {
        def origFace = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;1 1 1;1 1 0").build()
        def face = origFace.clone()
        expect:
        face.getRotation() == 0
        face.getPreviousMatrices().size() == 1
    }

    def "face with non-filled center or internal elements can not load"() {
        when:
        HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 0 1;1 1 1").build()
        then:
        thrown(AssertionError)
    }

}
