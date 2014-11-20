package happy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HappyFace {

    HappyFace(int[][] matrix, int rotation, String name) {
        this.numOfElements = matrix.length;
        this.matrix = matrix;
        this.rotation = rotation;
        this.columns = new int[numOfElements][numOfElements];
        for (int i=0; i < numOfElements; i++)
            for (int j=0; j< numOfElements; j++)
                this.columns[i][j] = matrix[j][i];

        this.previousMatrices = new LinkedList<>();
        this.previousMatrices.add(new MatrixStringConverter().convert(matrix));
        this.name = name;
    }

    final int[][] matrix;
    final int numOfElements;
    final int[][] columns;
    final String name;
    final List<String> previousMatrices;

    int rotation;

    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        builder.append('[').append(name).append(',').append(rotation).append(',').append(System.identityHashCode(this)).append("]");
        for (int i=0; i < numOfElements; i++) {
            builder.append(System.lineSeparator());
            for (int j=0; j < numOfElements; j++)
                builder.append(matrix[i][j]).append(' ');
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }

    public int elementCount() {
        return numOfElements;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        else if (other == this)
            return true;
        else if (! (other instanceof HappyFace))
            return false;

        HappyFace o = (HappyFace) other;
        return this.name.equals(o.name);
    }

    public boolean identical(HappyFace other) {
        if (other == null)
            return false;

        int[][] mat1 = this.getMatrix();
        int[][] mat2 = this.getMatrix();
        return Arrays.equals(mat1, mat2);
    }

    public int[] getRows(int index) {
        if (index >= numOfElements)
            throw new RuntimeException(String.format("face number of elements is limited to [%d], but requested for [%d]", numOfElements, index));
        return matrix[index];
    }

    public int[] getColumns(int index) {
        if (index >= numOfElements)
            throw new RuntimeException(String.format("face number of elements is limited to [%d], but requested for [%d]", numOfElements, index));
       return columns[index];
    }

    public HappyFace rotateCW() {
        //simple rotation clock-wise

        int[][] m = new int[numOfElements][numOfElements];

        for (int i=0; i < numOfElements; i++) {
            for (int j=0; j < numOfElements; j++) {
                m[i][j] = matrix[numOfElements -1-j][i];
            }
        }

        return new HappyFace(m, rotation+1, name);
    }

    public HappyFace flip() {
        int[][] m = new int[numOfElements][numOfElements];

        for (int i=0; i < numOfElements; i++) {
            for (int j=0; j < numOfElements; j++) {
                m[i][j] = matrix[i][numOfElements -1-j];
            }
        }

        return new HappyFace(m, rotation + 1, name);
    }

    public int getRotation() {
        return rotation;
    }

    public HappyFace rotate() {
        HappyFace newFace = this;
        String newFaceMatrixString;
        do {
            newFace = newFace.rotateSimple();
            newFaceMatrixString = new MatrixStringConverter().convert(newFace.matrix);
            for (String previous : previousMatrices ) {
                if (newFaceMatrixString.equals(previous))
                    break;
                else
                    newFace.previousMatrices.add(previous);
            }
        } while (newFace.previousMatrices.size() <= this.previousMatrices.size());
        return newFace;
    }
    public HappyFace rotateSimple() {
        if (rotation<0)
            throw new InvalidRotationException(String.format("Invalid state of face, rotation index should have been >= 0 but was [%d]", rotation));
        else if (rotation>=7)
            throw new InvalidRotationException(String.format("Invalid rotation request, already at last permutation"));

        HappyFace newFace;
        if (isEven()) {
            newFace = flip();
        } else {
            //Un-flip the last flip and then rotate
            newFace = flip();
            newFace = newFace.rotateCW();
            newFace.rotation = newFace.rotation - 1;
            //Counter incremented once each in flipping and rotateCW, hence need to reduce
        }
        return newFace;
    }

    private boolean isEven() {
        return (rotation%2) == 0;
    }

    /* auto-rotate sequence

                                                  O (Original)
                                                     | (0)
                                          ___________|____________
                                         | (2)                    | (1)
                                   CW(O) (clock-wise)        F(O) (Flip or mirror image)
                              ___________|____________
                             | (4)                    | (3)
                         CW(CW(O))                 F(CW(O))
                  ___________|____________
                 | (6)                    | (5)
             CW(CW((CW(O)))           F(CW(CW(O)))
                 |___________
                            | (7)
                     F(CW(CW((CW(O))))
     */

    public void match(HappyFace other, FaceDirection direction) {
        if (equals(other) || other==null)
            throw new RuntimeException("other face is null, cannot attempt matching");

        int[] side1, side2;

        if (isLeft(direction)) {
            side1 = getColumns(0);
            side2 = other.getColumns(numOfElements -1);
        }
        else if (isBottom(direction)) {
            side1 = getRows(numOfElements -1);
            side2 = other.getRows(0);
        }
        else if (isRight(direction)) {
            side1 = getColumns(numOfElements -1);
            side2 = other.getColumns(0);
        }
        else if (isTop(direction)) {
            side1 = getRows(0);
            side2 = other.getRows(numOfElements -1);
        } else
            throw new FaceNotMatchingException(String.format("face [%s, %d] not matching when attached on side [%s], direction not known", other.name, other.rotation, direction));

        checkAnchorEdgeElement(side1[0] + side2[0], other, direction);
        checkParllelEdgeElement(side1[numOfElements -1] + side2[numOfElements -1], other, direction);
        checkInternalElementsOfEdge(side1, side2, other, direction);
    }

    public boolean isTop(FaceDirection direction) {
        return direction == FaceDirection.Top;
    }

    public boolean isRight(FaceDirection direction) {
        return direction == FaceDirection.Right;
    }

    public boolean isBottom(FaceDirection direction) {
        return direction == FaceDirection.Bottom;
    }

    public boolean isLeft(FaceDirection direction) {
        return direction == FaceDirection.Left;
    }


    private void checkAnchorEdgeElement(int anchorEdgeElement, HappyFace other, FaceDirection direction) {
        if (anchorEdgeElement > 1)
            throw new FaceNotMatchingException(String.format("face [%s, %d] not matching face [%s] at side [%s] because of edge element 0", other.name, other.rotation, name, direction));

    }

    private void checkParllelEdgeElement(int parallelEdgeElement, HappyFace other, FaceDirection direction) {
        if (parallelEdgeElement > 1)
            throw new FaceNotMatchingException(String.format("face [%s, %d] not matching face [%s] at side [%s] because of last element", other.name, other.rotation, name, direction));

    }

    private void checkInternalElementsOfEdge(int[] side1, int[] side2, HappyFace other, FaceDirection direction) {
        for (int i = 1; i < numOfElements - 1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s, %d] not matching face [%s] at side [%s] because of element at edge with index [%d]", other.name, other.rotation, name, direction, i));
        }
    }

    public HappyFace clone() {
        HappyFace newFace = new HappyFace(matrix, rotation, name);
        for (int j=1; j< previousMatrices.size(); j++)
            newFace.previousMatrices.add(previousMatrices.get(j));
        return newFace;
    }

    public static int[] reverseArray(int[] input) {
        if (input==null)
            throw new AssertionError("input for reversing is null");
        int[] output = new int[input.length];
        for (int i=0; i < input.length; i++)
            output[i] = input[input.length-1-i];
        return output;
    }

    public final String name() {
        return name;
    }

    public final List<String> getPreviousMatrices() {
        return previousMatrices;
    }

    public HappyFace rewind() {
        if (rotation==0)
            return clone();
        else {
            final int[][] matrix = new MatrixStringConverter().convert(previousMatrices.get(previousMatrices.size()-1));
            return new HappyFace(matrix, 0, this.name);
        }
    }

}
