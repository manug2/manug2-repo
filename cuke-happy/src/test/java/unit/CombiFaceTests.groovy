package unit

import happy.FaceDirection
import happy.HappyFace
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
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        expect: anchor.equals(face)
    }

    def "anchor face is identical to source face"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        expect: anchor.identical(face)
    }

    def "anchor face has left"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        anchor.getLeft()
        expect: true
    }

    def "anchor face has bottom"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        anchor.getBottom()
        expect: true
    }

    def "anchor face has right"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        anchor.getRight()
        expect: true
    }

    def "anchor face has top"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        anchor.getTop()
        expect: true
    }

    def "anchor does not match and attach an un-suitable face"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        face1.load()
        expect: false == anchor.match(face1, FaceDirection.Left)
    }

    def "anchor matches a suitable face on left"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face1.load()
        expect: true == anchor.match(face1, FaceDirection.Left)
    }

    def "anchor matches a suitable face at bottom"() {
        def face = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 1;1 1 1", 3)
        face1.load()
        expect: true == anchor.match(face1, FaceDirection.Bottom)
    }

    def "anchor matches a suitable face on right"() {
        def face = HappyFace.createFromString("0 0 1;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 0;0 0 1;0 1 0", 3)
        face1.load()
        expect: true == anchor.match(face1, FaceDirection.Right)
    }

    def "anchor matches a suitable face on top"() {
        def face = HappyFace.createFromString("0 0 1;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face1.load()
        expect: true == anchor.match(face1, FaceDirection.Top)
    }

    def "anchor attaches a matching face on left"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face1.load()
        anchor.match(face1, FaceDirection.Left)
        expect: [0, 1, 1] == anchor.getColumns(0)
    }

    def "anchor attaches a matching face at bottom"() {
        def face = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 1;1 1 1", 3)
        face1.load()
        anchor.match(face1, FaceDirection.Bottom)
        expect: [1, 1, 1] == anchor.getRows(2)
    }

    def "anchor attaches a matching face on right"() {
        def face = HappyFace.createFromString("0 0 1;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 0;0 0 1;0 1 0", 3)
        face1.load()
        anchor.match(face1, FaceDirection.Right)
        expect: [0, 1, 0] == anchor.getColumns(2)
    }

    def "anchor attaches a matching face on top"() {
        def face = HappyFace.createFromString("0 0 1;1 0 1;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face1.load()
        anchor.match(face1, FaceDirection.Top)
        expect: [0, 1, 1] == anchor.getRows(0)
    }

    def "anchor can detach a face"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;0 1 0", 3)
        face1.load()
        anchor.match(face1, FaceDirection.Left)
        anchor.detach(FaceDirection.Left)
        expect: [0, 1, 1] == anchor.getColumns(0)
    }

    def "anchor cannot detach a non-attached face"() {
        when:
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        anchor.detach(FaceDirection.Left)
        then:
        def e = thrown(RuntimeException)
        e.getMessage() == "No face was attached to side [Left]"
    }
    def "anchor can print"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        //def face1 = HappyFace.createFromString("0 1 1;1 0 0;0 1 0", 3)
        //face1.load()
        //anchor.match(face1, FaceDirection.Left)

        anchor.print();
        expect : true
    }

    def "anchor cannot be initialized from un-loaded face"() {
        when:
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 1", 3)
        new CombiFace(face)
        then:
        def e = thrown(RuntimeException)
        e.getMessage() == "face data is not loaded. did you call load()?"
    }

}
