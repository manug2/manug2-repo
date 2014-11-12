package unit

import happy.CombiFace
import happy.FaceDirection
import happy.FaceNotMatchingException
import happy.HappyFace
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class CombiFace5x5Tests extends Specification {

    def "anchor face exists"() {
        CombiFace anchor
        expect: true
    }

    def "anchor face is equal to source face"() {
        def face = HappyFace.createFromFile("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        def anchor = new CombiFace(face)
        expect: anchor.equals(face)
    }

    def "anchor face is identical to source face"() {
        def face = HappyFace.createFromFile("src/test/resources/testFiles/face1.txt", 5)
        face.load()
        def anchor = new CombiFace(face)
        expect: anchor.identical(face)
    }

    def "anchor does not match and attach an un-suitable face"() {
        when:
        def face = HappyFace.createFromString(5,
                        "0 1 1 1 0;" +
                        "1 0 0 0 1;" +
                        "1 0 1 0 0;" +
                        "0 0 0 1 0;" +
                        "1 0 0 0 0")
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString(5,
                        "0 1 1 1 1;" +
                        "1 0 0 0 0;" +
                        "0 1 0 0 0;" +
                        "1 0 0 0 0;" +
                        "0 0 1 0 0")
        face1.load()
        face1.print()
        anchor.print()
        anchor.match(face1, FaceDirection.Left)
        anchor.print()
        then:thrown(FaceNotMatchingException)
    }

    def "anchor matches and attaches an un-suitable face"() {
        when:
        def face = HappyFace.createFromString(5,
                "0 1 1 1 0;" +
                "1 0 0 0 1;" +
                "1 0 0 0 1;" +
                "0 1 0 0 0;" +
                "1 0 0 0 1")
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString(5,
                "1 1 1 1 0;" +
                "0 0 0 1 0;" +
                "0 1 0 0 1;" +
                "1 0 0 0 0;" +
                "0 0 1 0 0")
        face1.load()
        face1.print()
        anchor.print()
        anchor.match(face1, FaceDirection.Left)
        then:thrown(FaceNotMatchingException)
    }

}
