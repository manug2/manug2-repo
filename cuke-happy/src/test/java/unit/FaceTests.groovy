package unit

import happy.HappyFace
import org.junit.Assert
import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification

@RunWith(Sputnik)
class FaceTests extends Specification {

    def "there is a face"() {
        HappyFace f
        expect : true
    }

    def "there is a face with source"() {
        def face = HappyFace.createFromFile("face1.txt", 5)
        expect : face!=null
    }

    def "face should load" () {
        def face = HappyFace.createFromFile("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        expect:
        face.isLoaded() == true
    }

    def "face is un-loaded" () {
        def face = HappyFace.createFromFile("face1.txt", 5)
        expect:
        face.isLoaded() == false
    }

    def "bad face cannot be loaded" () {
        when:
        def face = HappyFace.createFromFile("face1---1.xxy", 5)
        face.load()
        then:
        thrown(IOException)
    }

    def "blank face does not load" () {
        when:
        def face = HappyFace.createFromFile("src/test/resources/testFiles/blank.txt", 5)
        face.load()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "no data found in given face file [src/test/resources/testFiles/blank.txt]"

    }

    def "incomplete face does not load" () {
        when:
        def face = HappyFace.createFromFile("src/test/resources/testFiles/incompleteCols.txt", 5)
        face.load()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "incomplete data found, should have [5] rows with only have '0' and '1' separated by space"

    }

    def "loaded face should have 5 rows" () {
        when:
        def face = HappyFace.createFromFile("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        def matrix = face.getMatrix()
        then:
        matrix.length == 5
    }

    def "loaded face should have 5 items in each rows" () {
        when:
        def face = HappyFace.createFromFile("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        def matrix = face.getMatrix()
        def asExpected = true
        for (int i=0; i < 5; i++)
            asExpected &= (5 ==matrix[i].length)
        then:
        true == asExpected
    }

    def "loaded incomplete face does not have 4 rows" () {
        when:
        def face = HappyFace.createFromFile("src/test/resources/testFiles/incompleteRows.txt", 4)
        face.load()
        then:
        def e = thrown(AssertionError)
        expect:
        e.getMessage().startsWith( "incomplete data found,")
        e.getMessage().endsWith( "should have [4] rows")

    }

    def "cannot get matrix for un-loaded face" () {
        when:
        def face = HappyFace.createFromFile("face1.txt", 5)
        face.getMatrix()
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face data is not loaded. did you call load()?"
    }

    def "create a face using input string"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        expect : face!=null
    }

    def "face can load using input string"() {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        expect : true == face.isLoaded()
    }

    def "data loaded face should have 3 rows" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        def matrix = face.getMatrix()
        then:
        matrix.length == 3
    }

    def "data loaded face should have 3 items in each rows" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        def matrix = face.getMatrix()
        def asExpected = true
        for (int i=0; i < 3; i++)
            asExpected &= (3 ==matrix[i].length)
        then:
        true == asExpected
    }

    def "face can print" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        face.load()
        face.print()
        expect: true
    }

    def "face can rotate" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        face.load()
        face.rotateCW()
        expect: true
    }

    def "face has identifier" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        face.load()
        face.identifier()
        expect: true
    }

    def "face is not equal to null" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        face.load()
        expect: false == face.equals(null)
    }

    def "face is equal to self" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        face.load()
        expect: face.equals(face)
    }

    def "face is not equal to an identical face" () {
        when: "we have two faces, with same data"
        def face1 = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        def face2 = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3);
        face1.load()
        face2.load()
        face1.print()
        face2.print()
        then:
        true == face1.identical(face2)
        false == face1.equals(face2)
    }

    def "faces are identical" () {
        def face1 = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        def face2 = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face1.load()
        face2.load()
        expect: true == face1.identical(face2)
    }

    def "face has column" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        face.getColumns(0)
        expect: true
    }
    def "face has row"() {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        face.getRows(0)
        expect: true
    }

    def "unloaded face does not have column" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.getColumns(0)
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face data is not loaded. did you call load()?"
    }
    def "unloaded face does not have row"() {
        when:
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.getRows(0)
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face data is not loaded. did you call load()?"
    }

    def "face does not have invalid column" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        face.getColumns(10)
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face number of elements is limited to [3], but requested for [10]"
    }

    def "face does not have invalid row" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        face.getRows(10)
        then:
        def e = thrown(RuntimeException)
        expect: e.getMessage() == "face number of elements is limited to [3], but requested for [10]"
    }

    def "2x2 face has expected columns" () {
        def face = HappyFace.createFromString("0 1;1 0", 2)
        face.load()
        expect:
        ([0, 1] == face.getColumns(0)) && ([1, 0] == face.getColumns(1))
    }

    def "2x2 face has expected rows" () {
        def face = HappyFace.createFromString("0 1;1 1", 2)
        face.load()
        expect:
        ([0, 1] == face.getRows(0)) && ([1, 1] == face.getRows(1))
    }

    def "3x3 face has expected columns" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        expect:
        ([0, 1, 1] == face.getColumns(0)) && ([1, 1, 1] == face.getColumns(1)) && ([0, 1, 1] == face.getColumns(2))
    }

    def "3x3 face has expected rows" () {
        def face = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        face.load()
        expect:
        ([0, 1, 0] == face.getRows(0)) && ([1, 1, 1] == face.getRows(1)) && ([1, 1, 1] == face.getRows(2))
    }

    def "3x3 face can clone a face"() {
        def face = HappyFace.createFromString("0 1 0;1 1 0;1 1 1", 3)
        face.load()
        def face1 = face.clone()
        expect:
        true == face.equals(face1)
        true == face.identical(face1)
    }

    def "face cannot have top side with all '0'" () {
        when:
        def face = HappyFace.createFromString("0 0 0;1 1 1;1 1 1", 3)
        face.load()
        then:
        def e = thrown(AssertionError)
        e.getMessage().endsWith(" has all items in row [0] as '0'")
    }

    def "face cannot have right side with all '0'" () {
        when:
        def face = HappyFace.createFromString("1 1 0;1 1 0;1 1 0", 3)
        face.load()
        then:
        def e = thrown(AssertionError)
        e.getMessage().endsWith(" has all items in column [2] as '0'")
    }

    def "face1 has a name"() {
        def face = HappyFace.createFromFile("face1.txt", 5)
        expect :
        "face1" == face.name()
    }

    def "conf5 has a name"() {
        def face = HappyFace.createFromFile("src/test/resources/testFiles/conf5.txt", 5)
        expect :
        "conf5" == face.name()
    }

    def "cloned face has one item in previousMatrices"() {
        def origFace = HappyFace.createFromString("1 1 0;1 1 1;1 1 0", 3)
        origFace.load()
        def face = origFace.clone()
        expect:
        face.getRotation() == 0
        face.getPreviousMatrices().size() == 1
    }

    def "face with non-filled center or internal elements can not load"() {
        when:
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        then:
        thrown(AssertionError)
    }

}
