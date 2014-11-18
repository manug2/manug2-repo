package unit

import happy.FaceDirection
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class FaceMatchingTests extends Specification {

    def "face can match a suitable face at bottom"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Bottom)
    }
    def "face can match a suitable face at right"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Right)
    }
    def "face can match a suitable face at top"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Top)
    }
    def "face can not match a face at any other direction"() {
        when:
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        def face1  = HappyFaceBuilder.createBuilder().numOfElements(2).usingString("0 1;1 0").build()
        anchor.match(face1, 4)
        then:
        thrown(MissingMethodException)
    }
    def "3x3 face can match a suitable face at left"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def face1  = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 1;0 1 0;0 1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Left)
    }
    def "3x3 face can match a suitable face at bottom"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 0 1").build()
        def face1  = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;0 1 1").build()
        expect:
        anchor.match(face1, FaceDirection.Bottom)
    }
    def "3x3 face can match a suitable face at right"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def face1  = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 1;0 1 0;0 1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Right)
    }
    def "3x3 face can match a suitable face at top"() {
        def anchor = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def face1  = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 1;0 1 0;1 0 1").build()
        expect:
        anchor.match(face1, FaceDirection.Top)
    }

}
