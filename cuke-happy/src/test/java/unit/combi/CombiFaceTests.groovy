package unit.combi

import happy.FaceDirection
import happy.FaceNotMatchingException
import happy.HappyFace
import happy.HappyFaceBuilder
import org.junit.runner.RunWith;
import org.spockframework.runtime.Sputnik;
import spock.lang.Specification;
import happy.CombiFace;

@RunWith(Sputnik)
class CombiFaceTests extends Specification {

    def "anchor face exists"() {
        CombiFace anchor
        expect: true
    }

    def "anchor face is equal to source face"() {
        def face =HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        expect: anchor.equals(face)
    }

    def "anchor face is identical to source face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        expect: anchor.identical(face)
    }

    def "anchor face has left"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        anchor.getLeft()
        expect: true
    }

    def "anchor face has bottom"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        anchor.getBottom()
        expect: true
    }

    def "anchor face has right"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        anchor.getRight()
        expect: true
    }

    def "anchor face has top"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        anchor.getTop()
        expect: true
    }

    def "anchor does not match and attach an un-suitable face"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 1").build()
        face1.print()
        anchor.print()
        anchor.match(face1, FaceDirection.Left)
        then:thrown(FaceNotMatchingException)
    }

    def "anchor matches a suitable face on left"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Left)
    }

    def "anchor matches a suitable face at bottom"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 1").build()
        expect:
        anchor.match(face1, FaceDirection.Bottom)
    }

    def "anchor matches a suitable face on right"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Right)
    }

    def "anchor matches a suitable face on top"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        expect:
        anchor.match(face1, FaceDirection.Top)
    }

    def "anchor attaches a matching face on left"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        anchor.match(face1, FaceDirection.Left)
        expect: [0, 1, 1] == anchor.getColumns(0)
    }

    def "anchor attaches a matching face at bottom"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 1").build()
        anchor.match(face1, FaceDirection.Bottom)
        expect: [1, 1, 1] == anchor.getRows(2)
    }

    def "anchor attaches a matching face on right"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 1 0").build()
        anchor.match(face1, FaceDirection.Right)
        expect: [0, 1, 0] == anchor.getColumns(2)
    }

    def "anchor attaches a matching face on top"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        anchor.match(face1, FaceDirection.Top)
        expect: [0, 1, 1] == anchor.getRows(0)
    }

    def "anchor can print"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def anchor = new CombiFace(face)
        anchor.print();
        expect : true
    }

    def "anchor can print when left attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;0 1 0").build()
        expect :
        anchor.match(face1, FaceDirection.Left)
    }

    def "anchor can print when bottom attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 1").build()
        expect :
        anchor.match(face1, FaceDirection.Bottom)
    }
    def "anchor can print when right attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 1 0").build()
        expect :
        anchor.match(face1, FaceDirection.Right)

    }

    def "anchor can print when top attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        expect :
        anchor.match(face1, FaceDirection.Top)
    }

}
