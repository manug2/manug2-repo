package unit

import happy.FaceDirection
import happy.HappyFace
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class FaceMatchingTests extends Specification {

    def "face can match a suitable face at bottom"() {
        def anchor = HappyFace.createFromString("0 1;1 0", 2)
        def face1 = HappyFace.createFromString("0 1;1 0", 2)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Bottom)
    }
    def "face can match a suitable face at right"() {
        def anchor = HappyFace.createFromString("0 1;1 0", 2)
        def face1 = HappyFace.createFromString("0 1;1 0", 2)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Right)
    }
    def "face can match a suitable face at top"() {
        def anchor = HappyFace.createFromString("0 1;1 0", 2)
        def face1 = HappyFace.createFromString("0 1;1 0", 2)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Top)
    }
    def "face can not match a face at any other direction"() {
        when:
        def anchor = HappyFace.createFromString("0 1;1 0", 2)
        def face1  = HappyFace.createFromString("0 1;1 0", 2)
        anchor.load()
        face1.load()
        anchor.match(face1, 4)
        then:
        thrown(MissingMethodException)
    }
    def "3x3 face can match a suitable face at left"() {
        def anchor = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        def face1  = HappyFace.createFromString("1 0 1;0 1 0;0 1 0", 3)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Left)
    }
    def "3x3 face can match a suitable face at bottom"() {
        def anchor = HappyFace.createFromString("0 1 0;1 1 1;1 0 1", 3)
        def face1  = HappyFace.createFromString("0 1 0;1 1 0;0 1 1", 3)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Bottom)
    }
    def "3x3 face can match a suitable face at right"() {
        def anchor = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        def face1  = HappyFace.createFromString("1 0 1;0 1 0;0 1 0", 3)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Right)
    }
    def "3x3 face can match a suitable face at top"() {
        def anchor = HappyFace.createFromString("0 1 0;1 1 1;1 1 1", 3)
        def face1  = HappyFace.createFromString("1 0 1;0 1 0;1 0 1", 3)
        anchor.load()
        face1.load()
        expect:
        anchor.match(face1, FaceDirection.Top)
    }

}
