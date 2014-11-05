package unit

import happy.HappyFace

import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification

import java.awt.dnd.InvalidDnDOperationException;

@RunWith(Sputnik)
class FaceTests extends Specification {

    def "there is a face"() {
        HappyFace f
        expect : true
    }

    def "there is a face with source"() {
        def face = new HappyFace("face1.txt", 5)
        expect : face!=null
    }

    def "face should load" () {
        def face = new HappyFace("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        expect:
        face.isLoaded() == true
    }

    def "face is un-loaded" () {
        def face = new HappyFace("face1.txt", 5)
        expect:
        face.isLoaded() == false
    }

    def "bad face cannot be loaded" () {
        when:
        def face = new HappyFace("face1---1.xxy", 5)
        face.load()
        then:
        thrown(IOException)
    }

    def "blank face does not load" () {
        when:
        def face = new HappyFace("src/test/resources/testFiles/blank.txt", 5)
        face.load()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "no data found in given face file [src/test/resources/testFiles/blank.txt]"

    }

    def "incomplete face does not load" () {
        when:
        def face = new HappyFace("src/test/resources/testFiles/incompleteCols.txt", 5)
        face.load()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "incomplete data found, should have [5] rows with only have '0' and '1' separated by space"

    }

    def "loaded face should have 5 rows" () {
        when:
        def face = new HappyFace("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        def matrix = face.getMatrix()
        then:
        matrix.length == 5
    }

    def "loaded face should have 5 items in each rows" () {
        when:
        def face = new HappyFace("src/test/resources/testFiles/face1.txt", 5)
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
        def face = new HappyFace("src/test/resources/testFiles/incompleteRows.txt", 4)
        face.load()
        then:
        def e = thrown(AssertionError)
        expect: e.getMessage() == "incomplete data found, should have [4] rows"

    }

    def "cannot get matrix for un-loaded face" () {
        when:
        def face = new HappyFace("face1.txt", 5)
        def matrix = face.getMatrix()
        then:
        def e = thrown(InvalidDnDOperationException)
        expect: e.getMessage() == "matrix requested is invalid before loading. did you call load()?"
    }

    def "create a face using input string"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        expect : face!=null
    }

    def "face can load using input string"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        expect : true == face.isLoaded()
    }

    def "data loaded face should have 3 rows" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def matrix = face.getMatrix()
        then:
        matrix.length == 3
    }

    def "data loaded face should have 3 items in each rows" () {
        when:
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def matrix = face.getMatrix()
        def asExpected = true
        for (int i=0; i < 3; i++)
            asExpected &= (3 ==matrix[i].length)
        then:
        true == asExpected
    }

}
