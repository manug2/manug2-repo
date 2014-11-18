package unit

import happy.CombiFace
import happy.FaceDirection
import happy.FaceNotMatchingException
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class CombiFace5x5Tests extends Specification {

    def "anchor face is equal to source face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def anchor = new CombiFace(face)
        expect: anchor.equals(face)
    }

    def "anchor face is identical to source face"() {
        def face =HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        def anchor = new CombiFace(face)
        expect: anchor.identical(face)
    }

    def "anchor does not match and attach an un-suitable face"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingString(
                        "0 1 1 1 0;" +
                        "1 1 1 1 1;" +
                        "1 1 1 1 0;" +
                        "0 1 1 1 0;" +
                        "1 0 0 0 0").build()

        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingString(
                        "0 1 1 1 1;" +
                        "1 1 1 1 0;" +
                        "0 1 1 1 0;" +
                        "1 1 1 1 0;" +
                        "0 0 1 0 0").build()

        anchor.match(face1, FaceDirection.Left)
        then:thrown(FaceNotMatchingException)
    }

    def "anchor matches and attaches an un-suitable face"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(5).usingString(
                "0 1 1 1 0;" +
                "1 1 1 1 1;" +
                "1 1 1 1 1;" +
                "0 1 1 1 0;" +
                "1 0 0 0 1").build()

        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(5).usingString(
                "1 1 1 1 0;" +
                "0 1 1 1 0;" +
                "0 1 1 1 1;" +
                "1 1 1 1 0;" +
                "0 0 1 0 0").build()
        anchor.print()
        anchor.match(face1, FaceDirection.Left)
        then:thrown(FaceNotMatchingException)
    }

}
