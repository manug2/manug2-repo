package unit

import happy.CombiFace
import happy.FaceDirection
import happy.FaceNotMatchingException
import happy.HappyFace
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class CombiAttachmentTests extends Specification {

    def "anchor can print when right and top attached face"() {
        def face = HappyFace.createFromString("0 0 1;1 0 1;1 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;1 1 0", 3)
        face2.load()

        expect :
        true  == anchor.match(face1, FaceDirection.Top)
        true  == anchor.match(face2, FaceDirection.Right)
        anchor.print()
    }

    def "anchor's top and bottom change after attaching a matching face on left"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;0 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 1 1", 3)
        face1.load()
        expect:
        [0, 1 ,0] == anchor.getRows(0)//original Top
        [0, 1 ,1] == anchor.getRows(2)//original Bottom
        anchor.match(face1, FaceDirection.Left)
        anchor.print()
        [1, 1 ,0] == anchor.getRows(0)//effective Top after adding Left
        [1, 1 ,1] == anchor.getRows(2)//effective Bottom after adding Left
    }

    def "anchor's top and bottom change after attaching a matching face on right"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("1 1 0;0 0 1;1 1 0", 3)
        face1.load()
        expect:
        [0, 1 ,0] == anchor.getRows(0)//original Top
        [1, 1 ,0] == anchor.getRows(2)//original Bottom
        anchor.match(face1, FaceDirection.Right)
        anchor.print()
        [0, 1 ,1] == anchor.getRows(0)//effective Top after adding Right
        [1, 1 ,1] == anchor.getRows(2)//effective Bottom after adding Right
    }

    def "anchor's left and right change after attaching a matching face on top"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;1 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 1;1 0 0;1 0 1", 3)
        face1.load()
        expect:
        [0, 1 ,1] == anchor.getColumns(0)//original Left
        [0, 1 ,0] == anchor.getColumns(2)//original Right
        anchor.match(face1, FaceDirection.Top)
        anchor.print()
        [1, 1 ,1] == anchor.getColumns(0)//effective Left after adding Top
        [1, 1 ,0] == anchor.getColumns(2)//effective Right after adding Top
    }

    def "anchor's left and right change after attaching a matching face at bottom"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("1 0 1;1 0 0;1 0 1", 3)
        face1.load()
        expect:
        [0, 1 ,0] == anchor.getColumns(0)//original Left
        [0, 1 ,0] == anchor.getColumns(2)//original Right
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        [0, 1 ,1] == anchor.getColumns(0)//effective Left after adding Top
        [0, 1 ,1] == anchor.getColumns(2)//effective Right after adding Top
    }

    def "anchor can print when left and top attached face"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        expect :
        true  == anchor.match(face1, FaceDirection.Left)
        true  == anchor.match(face2, FaceDirection.Top)
        anchor.print()
    }

    def "anchor can print when left, top and right attached face"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        def face3 = HappyFace.createFromString("0 0 1;1 0 1;0 0 1", 3)
        face3.load()

        expect :
        true  == anchor.match(face1, FaceDirection.Left)
        true  == anchor.match(face2, FaceDirection.Top)
        anchor.print()
        true  == anchor.match(face3, FaceDirection.Right)
        anchor.print()
    }

    def "anchor can print when left, top, right and bottom attached face"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 0 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        def face3 = HappyFace.createFromString("0 0 1;1 0 1;0 0 1", 3)
        face3.load()
        def face4 = HappyFace.createFromString("0 1 0;0 0 1;1 0 0", 3)
        face4.load()

        expect :
        true  == anchor.match(face1, FaceDirection.Left)
        true  == anchor.match(face2, FaceDirection.Top)
        true  == anchor.match(face3, FaceDirection.Right)
        anchor.print()
        true  == anchor.match(face4, FaceDirection.Bottom)
        anchor.print()
    }

    def "anchor can print when parallel is attached after adding all other faces"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 0 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        def face3 = HappyFace.createFromString("0 0 1;1 0 0;0 0 1", 3)
        face3.load()
        def face4 = HappyFace.createFromString("0 1 0;0 0 1;1 0 0", 3)
        face4.load()
        def face5 = HappyFace.createFromString("1 0 0;0 1 1;0 1 0", 3)
        face5.load()
        face5.print()

        expect :
        true  == anchor.match(face1, FaceDirection.Left)
        true  == anchor.match(face2, FaceDirection.Top)
        true  == anchor.match(face3, FaceDirection.Right)
        true  == anchor.match(face4, FaceDirection.Bottom)
        anchor.print()
        true  == anchor.match(face5, FaceDirection.Parallel)
        anchor.print()
    }

    def "anchor can check edge when attaching top and left already attached"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 1 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;1 0 1;0 0 1", 3)
        face2.load()
        expect :
        true  == anchor.match(face1, FaceDirection.Left)
        anchor.print()
        face2.print()
        anchor.checkEdge(FaceDirection.Left, face2, FaceDirection.Top)
    }

    def "can reverse an array"() {
        expect:
        [3, 2, 1, 0] == CombiFace.reverseArray([0, 1, 2, 3].toArray(new int[0]))
    }

    def "anchor check edge is OK when attaching top and bottom already attached"() {
        def face = HappyFace.createFromString("0 1 0;1 0 1;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("1 0 1;1 0 0;1 0 1", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        expect :
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        anchor.checkEdge(FaceDirection.Bottom, face2, FaceDirection.Top)
    }

    def "anchor check edge is OK when attaching top and right already attached"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 0 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 1 0;0 0 1", 3)
        face2.load()
        expect :
        anchor.match(face1, FaceDirection.Right)
        anchor.print()
        anchor.checkEdge(FaceDirection.Right, face2, FaceDirection.Top)
    }

    def "anchor gives error when attaching top and top already attached"() {
        when:
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 0 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        true  == anchor.match(face1, FaceDirection.Right)
        anchor.checkEdge(FaceDirection.Top, face2, FaceDirection.Top)
        then :
        def e = thrown(RuntimeException)
        e.getMessage() == "cannot check edges when same direction given for existing and new face"
    }

    def "anchor check edge is OK when attaching right and bottom already attached"() {
        def face = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("1 0 0;0 0 1;1 0 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 0 1;1 0 0;1 0 1", 3)
        face2.load()
        expect :
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        face2.print()
        anchor.checkEdge(FaceDirection.Bottom, face2, FaceDirection.Right)
    }

    def "anchor gives error when checking right and bottom already attached"() {
        when:
        def face = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("1 0 0;0 0 1;1 0 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 0 1;1 0 0;0 0 1", 3)
        face2.load()
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        face2.print()
        anchor.checkEdge(FaceDirection.Bottom, face2, FaceDirection.Right)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "face [Bottom] and [Right] not matching because of element at edge with index [0]"
    }

    def "anchor gives error when checking left and bottom already attached at index 1"() {
        when:
        def face = HappyFace.createFromString("0 0 1;1 1 0;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;0 0 1;1 0 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 0 1;1 0 0;0 0 1", 3)
        face2.load()
        face2.print()
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        anchor.checkEdge(FaceDirection.Bottom, face2, FaceDirection.Left)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "face [Bottom] and [Left] not matching because of element at edge with index [1]"
    }

    def "anchor gives error when checking left and bottom already attached at index 2"() {
        when:
        def face = HappyFace.createFromString("0 0 1;1 1 0;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;0 0 1;1 0 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 0 1;1 0 0;1 1 1", 3)
        face2.load()
        face2.print()
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        anchor.checkEdge(FaceDirection.Bottom, face2, FaceDirection.Left)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "face [Bottom] and [Left] not matching because of element at edge with index [2] is present in both"

    }

    def "anchor check edge is OK when attaching left and bottom already attached"() {
        def face = HappyFace.createFromString("0 0 1;1 1 0;0 1 0", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;0 0 1;1 0 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 0 1;1 0 0;0 1 1", 3)
        face2.load()
        expect :
        anchor.match(face1, FaceDirection.Bottom)
        anchor.print()
        face2.print()
        anchor.checkEdge(FaceDirection.Bottom, face2, FaceDirection.Left)
    }

    def "anchor gives error during check edge when parallel is attached after adding all other faces"() {
        def face = HappyFace.createFromString("0 1 0;1 0 0;1 0 1", 3)
        face.load()
        def anchor = new CombiFace(face)
        def face1 = HappyFace.createFromString("0 0 1;1 0 0;0 1 0", 3)
        face1.load()
        def face2 = HappyFace.createFromString("0 1 0;0 0 1;0 0 1", 3)
        face2.load()
        def face3 = HappyFace.createFromString("0 0 1;1 0 0;0 0 1", 3)
        face3.load()
        def face4 = HappyFace.createFromString("0 1 0;0 0 1;1 0 0", 3)
        face4.load()
        def face5 = HappyFace.createFromString("1 0 0;0 1 1;0 1 0", 3)
        face5.load()
        face5.print()

        expect :
        true  == anchor.match(face1, FaceDirection.Left)
        true  == anchor.match(face2, FaceDirection.Top)
        true  == anchor.match(face3, FaceDirection.Right)
        true  == anchor.match(face4, FaceDirection.Bottom)
        anchor.print()
        anchor.checkEdgeParallel(face5, FaceDirection.Parallel)

    }

}
