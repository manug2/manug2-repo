package unit

import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import spock.lang.Specification
import happy.MatrixStringConverter

@RunWith(Sputnik)
class MatrixStringConversionTests extends Specification{

    def "there is a matrix-string to n fro converter"() {
        MatrixStringConverter converter
        expect: true
    }

    def "converter can convert from string to matrix"() {
        when:
        def converter = new MatrixStringConverter()
        def matrix = converter.convert("0 1;1 0")
        then:
        matrix != null
    }

    def "converter can correctly convert from string to matrix"() {
        when:
        def converter = new MatrixStringConverter()
        def matrix = converter.convert("0 1;1 0")
        then:
        matrix[0][0] == 0
        matrix[0][1] == 1
        matrix[1][0] == 1
        matrix[1][1] == 0
    }

    def "converter can correctly convert from string to 3x3 matrix"() {
        when:
        def converter = new MatrixStringConverter()
        def matrix = converter.convert("0 1 1;1 1 0;0 1 0")
        then:
        matrix[0][0] == 0
        matrix[0][1] == 1
        matrix[0][2] == 1
        matrix[1][0] == 1
        matrix[1][1] == 1
        matrix[1][2] == 0
        matrix[2][0] == 0
        matrix[2][1] == 1
        matrix[2][2] == 0
    }

    def "converter can convert from matrix to string"() {
        when:
        def converter = new MatrixStringConverter()
        int[][] matrix = new int[2][2]
        matrix[0][0] = 0
        matrix[0][1] = 1
        matrix[1][0] = 1
        matrix[1][1] = 0
        def str = converter.convert(matrix)
        then:
        str == "[[0 1][1 0]]"
    }

    def "converter can convert from 3x3 matrix to string"() {
        when:
        def converter = new MatrixStringConverter()
        int[][] matrix = new int[3][3]
        matrix[0][0] = 0
        matrix[0][1] = 1
        matrix[0][2] = 1
        matrix[1][0] = 1
        matrix[1][1] = 1
        matrix[1][2] = 0
        matrix[2][0] = 0
        matrix[2][1] = 1
        matrix[2][2] = 0
        def str = converter.convert(matrix)
        then:
        str == "[[0 1 1][1 1 0][0 1 0]]"
    }

}
