package unit

import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import happy.MutableFace

@RunWith(Sputnik)
class MutableFaceTests extends Specification {

    def "there is a mutable face"() {
        MutableFace face
        expect:
        true
    }
}
