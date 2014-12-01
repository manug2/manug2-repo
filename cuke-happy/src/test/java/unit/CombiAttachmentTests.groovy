package unit

import happy.CombiFace
import happy.FaceDirection
import happy.FaceNotMatchingException
import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class CombiAttachmentTests extends Specification {

    def "anchor can print when right and top attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 1 0").build()
        expect :
        anchor.match(face1, FaceDirection.Top)
        anchor.match(face2, FaceDirection.Right)
    }

    def "anchor's top and bottom change after attaching a matching face on left"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;0 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 1 1").build()
        expect:
        [0, 1 ,0] == anchor.getRows(0)//original Top
        [0, 1 ,1] == anchor.getRows(2)//original Bottom
        anchor.match(face1, FaceDirection.Left)
        [1, 1 ,0] == anchor.getRows(0)//effective Top after adding Left
        [1, 1 ,1] == anchor.getRows(2)//effective Bottom after adding Left
    }

    def "anchor's top and bottom change after attaching a matching face on right"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 1 0;0 1 1;1 1 0").build()
        expect:
        [0, 1 ,0] == anchor.getRows(0)//original Top
        [1, 1 ,0] == anchor.getRows(2)//original Bottom
        anchor.match(face1, FaceDirection.Right)
        [0, 1 ,1] == anchor.getRows(0)//effective Top after adding Right
        [1, 1 ,1] == anchor.getRows(2)//effective Bottom after adding Right
    }

    def "anchor's left and right change after attaching a matching face on top"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;1 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 1;1 1 0;1 0 1").build()
        expect:
        [0, 1 ,1] == anchor.getColumns(0)//original Left
        [0, 1 ,0] == anchor.getColumns(2)//original Right
        anchor.match(face1, FaceDirection.Top)
        [1, 1 ,1] == anchor.getColumns(0)//effective Left after adding Top
        [1, 1 ,0] == anchor.getColumns(2)//effective Right after adding Top
    }

    def "anchor's left and right change after attaching a matching face at bottom"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 1 0").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 1;1 1 0;1 0 1").build()
        expect:
        [0, 1 ,0] == anchor.getColumns(0)//original Left
        [0, 1 ,0] == anchor.getColumns(2)//original Right
        anchor.match(face1, FaceDirection.Bottom)
        [0, 1 ,1] == anchor.getColumns(0)//effective Left after adding Top
        [0, 1 ,1] == anchor.getColumns(2)//effective Right after adding Top
    }

    def "anchor can print when left and top attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        anchor.match(face1, FaceDirection.Left)
        expect :
        anchor.match(face2, FaceDirection.Top)
    }

    def "anchor can print when left, top and right attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 1 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;0 0 1").build()
        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        expect :
        anchor.match(face3, FaceDirection.Right)
    }

    def "anchor can print when left, top, right and bottom attached face"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 0 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 1;0 0 1").build()
        def face4 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 0 0").build()
        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        anchor.match(face3, FaceDirection.Right)
        expect :
        anchor.match(face4, FaceDirection.Bottom)
    }

    def "anchor can print when parallel is attached after adding all other faces"() {
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 0 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 0 1").build()
        def face4 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 0 0").build()
        def face5 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 0;0 1 1;0 1 0").build()
        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        anchor.match(face3, FaceDirection.Right)
        anchor.match(face4, FaceDirection.Bottom)
        expect :
        anchor.match(face5, FaceDirection.Parallel)
    }

    def "can reverse an array"() {
        expect:
        [3, 2, 1, 0] == CombiFace.reverseArray([0, 1, 2, 3].toArray(new int[0]))
    }

    def "parallel face cannot be placed at [Left, Top] corner"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 0 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 0 1").build()
        def face4 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 0 0").build()
        def face5 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 1 0").build()
        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        anchor.match(face3, FaceDirection.Right)
        anchor.match(face4, FaceDirection.Bottom)
        anchor.checkEdgeParallel(face5)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "parallel face cannot be placed at [Left, Top] corner"
    }

    def "parallel face cannot be placed at [Right, Top] corner"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 0 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 0 1").build()
        def face4 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 0 0").build()
        def face5 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 1;0 1 1;0 1 0").build()
        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        anchor.match(face3, FaceDirection.Right)
        anchor.match(face4, FaceDirection.Bottom)
        anchor.checkEdgeParallel(face5)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "parallel face cannot be placed at [Right, Top] corner"
    }

    def "parallel face cannot be placed at [Bottom, Right] corner"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 0 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 0 1").build()
        def face4 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 0 0").build()
        def face5 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 0;0 1 1;0 1 1").build()

        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        anchor.match(face3, FaceDirection.Right)
        anchor.match(face4, FaceDirection.Bottom)
        anchor.checkEdgeParallel(face5)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "parallel face cannot be placed at [Bottom, Right] corner"
    }

    def "parallel face cannot be placed at [Left, Bottom] corner"() {
        when:
        def face = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 0;1 0 1").build()
        def anchor = new CombiFace(face)
        def face1 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 1 0").build()
        def face2 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;1 1 1;0 0 1").build()
        def face3 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 0 1;1 1 0;0 0 1").build()
        def face4 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("0 1 0;0 1 1;1 0 0").build()
        def face5 = HappyFaceBuilder.createBuilder().numOfElements(3).usingString("1 0 0;0 1 1;1 1 0").build()

        anchor.match(face1, FaceDirection.Left)
        anchor.match(face2, FaceDirection.Top)
        anchor.match(face3, FaceDirection.Right)
        anchor.match(face4, FaceDirection.Bottom)
        anchor.checkEdgeParallel(face5)
        then:
        def e = thrown(FaceNotMatchingException)
        e.getMessage() == "parallel face cannot be placed at [Left, Bottom] corner"
    }
}
