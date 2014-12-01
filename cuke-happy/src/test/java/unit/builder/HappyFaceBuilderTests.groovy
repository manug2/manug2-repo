package unit.builder

import happy.HappyFaceBuilder
import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(Sputnik)
class HappyFaceBuilderTests extends Specification {

    def "builder can build a face"() {
        HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("src/test/resources/testFiles/face1.txt").build()
        expect : true
    }

    def "builder cannot build a bad face"() {
        when: "we have a solver"
        HappyFaceBuilder.createBuilder().numOfElements(5).usingFile("bad_bad.txt").build()
        then:
        thrown(IOException)
    }

}
