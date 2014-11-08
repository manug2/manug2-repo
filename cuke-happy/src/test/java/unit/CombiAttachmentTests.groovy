package unit

import happy.CombiFace
import happy.FaceDirection
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
        true == anchor.checkEdge(FaceDirection.Left, FaceDirection.Top)//Checks if an edge is formed and all values in edge are full/one (1)
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


}
