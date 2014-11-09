package happy;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class CombiFace extends HappyFace {
    public CombiFace(HappyFace anchor) {
        super(anchor.elementCount(), anchor.identifier(), anchor.getMatrix());//, anchor.getRotation());

        sideFaceMap = new HashMap<FaceDirection, HappyFace>(5);
        effectiveMatrix = new int[anchor.elementCount()][anchor.elementCount()];
        effectiveColumns = new int[anchor.elementCount()][anchor.elementCount()];
        for (int i=0; i <anchor.elementCount(); i++) {
            for (int j=0; j<anchor.elementCount(); j++) {
                effectiveMatrix[i][j] = anchor.getMatrix()[i][j];
                effectiveColumns[j][i] = effectiveMatrix[i][j];
            }
        }
    }

    private int[][] effectiveMatrix;
    private int[][] effectiveColumns;
    private final Map<FaceDirection, HappyFace> sideFaceMap;


    public HappyFace getLeft() {
        return sideFaceMap.get(FaceDirection.Left);
    }

    public HappyFace getBottom() {
        return sideFaceMap.get(FaceDirection.Bottom);
    }

    public HappyFace getRight() {
        return sideFaceMap.get(FaceDirection.Right);
    }

    public HappyFace getTop() {
        return sideFaceMap.get(FaceDirection.Top);
    }

    public HappyFace getParallel() {
        return sideFaceMap.get(FaceDirection.Parallel);
    }

    @Override
    public void match(final HappyFace face, final FaceDirection direction) {
        if (equals(face) || face==null)
            throw new RuntimeException("other face is null, cannot attempt matching");

        //Matches faces to this anchor, and all adjacent sides
        //Also attaches the matched face to the anchor, which is this object
        boolean matched;

        if (FaceDirection.Parallel.equals(direction)){
            matched = matchParallel(face);
        } else {
            super.match(face, direction);

            final HappyFace left = getLeft();
            final HappyFace top = getTop();
            final HappyFace right = getRight();
            final HappyFace bottom = getBottom();
            if (FaceDirection.Left.equals(direction) && top!=null)
                top.match(face.rotateCW(), FaceDirection.Left);
            if (FaceDirection.Left.equals(direction) && bottom!=null)
                bottom.match(face.rotateCC(), FaceDirection.Left);

            if (FaceDirection.Bottom.equals(direction) && left!=null)
                left.match(face.rotateCW(), FaceDirection.Bottom);
            if (FaceDirection.Bottom.equals(direction) && right!=null)
                right.match(face.rotateCC(), FaceDirection.Bottom);

            if (FaceDirection.Right.equals(direction) && bottom!=null)
                bottom.match(face.rotateCW(), FaceDirection.Right);
            if (FaceDirection.Right.equals(direction) && top!=null)
                top.match(face.rotateCC(), FaceDirection.Right);

            if (FaceDirection.Top.equals(direction) && left!=null)
                left.match(face.rotateCW(), FaceDirection.Top);
            if (FaceDirection.Top.equals(direction) && right!=null)
                right.match(face.rotateCC(), FaceDirection.Top);

        }

        if (sideFaceMap.containsKey(direction))
            throw new RuntimeException(String.format("a matching face already attached on [%s]", direction.toString()));

        sideFaceMap.put(direction, face);
        switch(direction) {
            case Left  :
                effectiveMatrix[0][0] = 1;//change to sum of two incoming sides
                effectiveMatrix[elements-1][0] = 1;
                break;
            case Bottom:
                effectiveMatrix[elements-1][0] = 1;
                effectiveMatrix[elements-1][elements-1] = 1;
                break;
            case Right  :
                effectiveMatrix[0][elements-1] = 1;
                effectiveMatrix[elements-1][elements-1] = 1;
                break;
            case Top:
                effectiveMatrix[0][0] = 1;
                effectiveMatrix[0][elements-1] = 1;
                break;
            case Parallel:
                //effective matrix does not change as it does not touch anchor
                break;
        }
        for (int i=0; i <super.elements; i++) {
            for (int j=0; j<super.elements; j++) {
                effectiveColumns[j][i] = effectiveMatrix[i][j];
            }
        }

    }

    @Override
    public int[] getRows(int index) {
        //Presents the relevant side of self or of an attached face depending on orientation
        if (index==0) {
            if (sideFaceMap.containsKey(FaceDirection.Top))
                return sideFaceMap.get(FaceDirection.Top).getRows(index);
            else
                return effectiveMatrix[index];
        } else if (index == elements-1) {
            if (sideFaceMap.containsKey(FaceDirection.Bottom))
                return sideFaceMap.get(FaceDirection.Bottom).getRows(index);
            else
                return effectiveMatrix[index];
        } else
            return effectiveMatrix[index];
    }

    @Override
    public int[] getColumns(int index) {
        //Presents the relevant side of self or of an attached face depending on orientation
        if (index==0) {
            if(sideFaceMap.containsKey(FaceDirection.Left))
                return sideFaceMap.get(FaceDirection.Left).getColumns(index);
            else
                return effectiveColumns[index];
        } else if (index == elements-1) {
            if (sideFaceMap.containsKey(FaceDirection.Right))
                return sideFaceMap.get(FaceDirection.Right).getColumns(index);
            else
                return effectiveColumns[index];
        } else
            return effectiveColumns[index];
    }

    @Override
    public void print() {

        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        builder.append('[').append(identifier()).append(',').append(System.identityHashCode(this)).append("]");

        //PRINT Top matrix
        final HappyFace top = getTop();
        if (top!=null) {
            int[][] matrix = top.getMatrix();
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                for (int k=0; k < elements; k++)
                    builder.append("  ");
                for (int j=0; j < elements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
        }

        //PRINT Left, Centre and Right matrices
        final HappyFace left = getLeft();
        final HappyFace right = getRight();
        for (int i=0; i < elements; i++) {
            builder.append("\n");
            if (left!=null) {
                final int[] row = left.getRows(i);
                for (int j=0; j < elements; j++)
                    builder.append(row[j]).append(' ');
            }

            final int[] myRow = super.getRows(i);
            for (int j=0; j < elements; j++)
                builder.append(myRow[j]).append(' ');

            if (right!=null) {
                final int[] row = right.getRows(i);
                for (int j=0; j < elements; j++)
                    builder.append(row[j]).append(' ');
            }
        }

        //PRINT Bottom matrices
        final HappyFace bottom = getBottom();
        if (bottom!=null) {
            int[][] matrix = bottom.getMatrix();
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                if (top!=null)
                    for (int k=0; k < elements; k++)
                        builder.append("  ");
                for (int j=0; j < elements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
        }

        //PRINT Bottom matrices
        final HappyFace parallel = getParallel();
        if (parallel!=null) {
            final HappyFace fp = parallel.flip();
            int[][] matrix = parallel.getMatrix();
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                for (int k=0; k < elements; k++)
                    builder.append("  ");
                for (int j=0; j < elements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
        }

        builder.append("\n");
        System.out.println(builder.toString());
    }

    @Override
    public CombiFace clone() {
        CombiFace cloned = new CombiFace(this);
        for (Entry<FaceDirection, HappyFace> entry : sideFaceMap.entrySet()) {
             cloned.sideFaceMap.put(entry.getKey(), entry.getValue());
        }
        for (int i=0; i <elements; i++) {
            for (int j=0; j<elements; j++) {
                cloned.effectiveMatrix[i][j]  = effectiveMatrix[i][j];
                cloned.effectiveColumns[j][i] = effectiveMatrix[i][j];
            }
        }

        return cloned;
    }


    private boolean matchParallel(final HappyFace other) {
        if(sideFaceMap.size()<4)
            throw new RuntimeException(String.format("a matching face can be attached on [%s] only after all other sides are full", FaceDirection.Parallel.toString()));

        int sum = 0;
        int[] side1, side2;

        side1 = getLeft().getColumns(0);
        side2 = other.getColumns(0);
        for (int i=0; i < elements; i++) {
            int ts = side1[i] + side2[i];
            if (ts <= 1)
                sum += ts;
            else
                return false;
        }
        if (sum > elements)
            return false;

        sum = 0;
        side1 = getBottom().getRows(elements-1);
        side2 = other.getRows(elements-1);
        for (int i=0; i < elements; i++) {
            int ts = side1[i] + side2[i];
            if (ts <= 1)
                sum += ts;
            else
                return false;
        }
        if (sum > elements)
            return false;

        sum = 0;
        side1 = getRight().getColumns(elements-1);
        side2 = other.getColumns(elements-1);
        for (int i=0; i < elements; i++) {
            int ts = side1[i] + side2[i];
            if (ts <= 1)
                sum += ts;
            else
                return false;
        }
        if (sum > elements)
            return false;

        sum = 0;
        side1 = getTop().getRows(0);
        side2 = other.getRows(0);
        for (int i=0; i < elements; i++) {
            int ts = side1[i] + side2[i];
            if (ts <= 1)
                sum += ts;
            else
                return false;
        }
        if (sum > elements)
            return false;
        else
            return true;
    }

    public boolean checkEdge(FaceDirection one, FaceDirection two) {
        if (!sideFaceMap.containsKey(one))
            throw new AssertionError(String.format("face not yet attached from direction ", one.toString()));
        if (!sideFaceMap.containsKey(two))
            throw new AssertionError(String.format("face not yet attached from direction [%s]", one.toString()));

        HappyFace faceOne = sideFaceMap.get(one);
        HappyFace faceTwo = sideFaceMap.get(two);

        int sum = 0;
        int[] side1=null, side2=null;
        switch(one) {
            case Left  :
                if (FaceDirection.Top.equals(two)) {
                    side1 = reverseArray(faceOne.getRows(0));
                    side2 = faceTwo.getColumns(0);
                } else if (FaceDirection.Bottom.equals(two)) {
                    side1 = faceOne.getRows(elements-1);
                    side2 = reverseArray(faceTwo.getColumns(0));
                } else
                    //throw new RuntimeException(String.format("useless to test edge between faces [%s] and [%s]", one.toString(), two.toString()));
                    return true;
                break;
            case Bottom:
                if (FaceDirection.Left.equals(two)) {
                    side1 = reverseArray(faceOne.getColumns(0));
                    side2 = faceTwo.getRows(elements-1);
                } else if (FaceDirection.Right.equals(two)) {
                    side1 = reverseArray(faceOne.getColumns(elements-1));
                    side2 = faceTwo.getRows(elements-1);
                } else
                    //throw new RuntimeException(String.format("useless to test edge between faces [%s] and [%s]", one.toString(), two.toString()));
                    return true;
                break;
            case Right  :
                if (FaceDirection.Top.equals(two)) {
                    side1 = faceOne.getRows(0);
                    side2 = reverseArray(faceTwo.getColumns(elements-1));
                } else if (FaceDirection.Bottom.equals(two)) {
                    side1 = faceOne.getRows(elements-1);
                    side2 = reverseArray(faceTwo.getColumns(elements-1));
                } else
                    //throw new RuntimeException(String.format("useless to test edge between faces [%s] and [%s]", one.toString(), two.toString()));
                    return true;
                break;
            case Top:
                if (FaceDirection.Left.equals(two)) {
                    side1 = reverseArray(faceOne.getColumns(0));
                    side2 = faceTwo.getRows(0);
                } else if (FaceDirection.Right.equals(two)) {
                    side1 = reverseArray(faceOne.getColumns(elements-1));
                    side2 = faceTwo.getRows(0);
                } else
                    //throw new RuntimeException(String.format("useless to test edge between faces [%s] and [%s]", one.toString(), two.toString()));
                    return true;
                break;
            case Parallel:
                //effective matrix does not change as it does not touch anchor
                break;
        }
        if (side1==null || side2==null)
            throw new RuntimeException(String.format("cannot evaluate which sides to test edge between faces [%s] and [%s]", one.toString(), two.toString()));

        for (int i=0; i < elements-1; i++) {
            int ts = side1[i] + side2[i];
            if (ts == 1)
                sum += ts;
            else
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of edge at index [%d]", one, two, i));
        }
        if (side1[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of edge at index [%d] is present in both", one, two, elements-1));
        else
            return true;

    }

    public static int[] reverseArray(int[] input) {
        if (input==null)
            throw new AssertionError("input for reversing is null");
        int[] output = new int[input.length];
        for (int i=0; i < input.length; i++)
            output[i] = input[input.length-1-i];
        return output;
    }

    public void checkEdge(FaceDirection existingFace, HappyFace face, FaceDirection direction) {
        if(existingFace.equals(direction))
            throw new RuntimeException("cannot check edges when same direction given for existing and new face");
        if (FaceDirection.Left.equals(existingFace))
            checkEdgeLeft(face, direction);
        else if (FaceDirection.Bottom.equals(existingFace))
            checkEdgeBottom(face, direction);
        else if (FaceDirection.Right.equals(existingFace))
            checkEdgeRight(face, direction);
        else if (FaceDirection.Top.equals(existingFace))
            checkEdgeTop(face, direction);
        else if (FaceDirection.Parallel.equals(existingFace))
            checkEdgeParallel(face, direction);
    }

    public void checkEdgeLeft(HappyFace face, FaceDirection direction) {
        int[] side1, side2;
        if (FaceDirection.Top.equals(direction)) {
            side1 = getLeft().getRows(0);
            side2 = face.getColumns(0);
        } else if (FaceDirection.Bottom.equals(direction)) {
            side1 = getLeft().getRows(elements-1);
            side2 = reverseArray(face.getColumns(0));
        } else
            return ;

        for (int i=1; i < elements; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Left, direction, i));
        }
        if (side1[0] + side2[0] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Left, direction, elements-1));
    }

    public void checkEdgeBottom(HappyFace face, FaceDirection direction) {
        int[] side1, side2;
        if (FaceDirection.Left.equals(direction)) {
            side1 = getBottom().getColumns(0);
            side2 = reverseArray(face.getRows(elements-1));
        } else if (FaceDirection.Right.equals(direction)) {
            side1 = getBottom().getColumns(elements-1);
            side2 = face.getRows(elements-1);
        } else
            return ;

        for (int i=0; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Bottom, direction, i));
        }
        if (side1[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Bottom, direction, elements-1));
    }

    public void checkEdgeRight(HappyFace face, FaceDirection direction) {
        int[] side1, side2;
        if (FaceDirection.Top.equals(direction)) {
            side1 = getRight().getRows(0);
            side2 = reverseArray(face.getColumns(elements-1));
        } else if (FaceDirection.Bottom.equals(direction)) {
            side1 = getRight().getRows(elements-1);
            side2 = face.getColumns(elements-1);
        } else
            return ;

        for (int i=0; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Right, direction, i));
        }
        if (side1[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Right, direction, elements-1));
    }

    public void checkEdgeTop(HappyFace face, FaceDirection direction) {
        int[] side1, side2;
        if (FaceDirection.Left.equals(direction)) {
            side1 = reverseArray(getTop().getColumns(0));
            side2 = face.getRows(0);
        } else if (FaceDirection.Right.equals(direction)) {
            side1 = reverseArray(getTop().getColumns(elements-1));
            side2 = face.getRows(0);
        } else
            return ;

        for (int i=0; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
               throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Top, direction, i));
        }
        if (side1[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Top, direction, elements-1));
    }

    public void checkEdgeParallel(HappyFace face, FaceDirection direction) {
        if ( ! FaceDirection.Parallel.equals(direction))
            throw new RuntimeException(String.format("checkEdgeParallel() called for wrong direction [%s]", direction));

        final int[][] parallelMatrix = face.getMatrix() ;

        //Check items except border sides
        for (int i=2; i < elements-1; i++)
            for (int j=2; j < elements-1; j++)
                if(parallelMatrix[i][j]!=1)
                    throw new FaceNotMatchingException(String.format("parallel face not filled, element [%d][%d]", i, j));

        final int[] leftSide = getLeft().getColumns(0);
        final int[] bottomSide = getBottom().getRows(elements-1);
        final int[] rightSide = getRight().getColumns(elements-1);
        final int[] topSide = getTop().getRows(0);

        //Check corners
        if ((parallelMatrix[0][0] + topSide[0] + leftSide[0]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Left, FaceDirection.Top));

        else if ((parallelMatrix[elements-1][0] + leftSide[elements-1] + bottomSide[0]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Left, FaceDirection.Bottom));

        else if ((parallelMatrix[elements-1][elements-1] + bottomSide[elements-1] + rightSide[elements-1]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Bottom, FaceDirection.Right));

        else if ((parallelMatrix[0][elements-1] + rightSide[0] + topSide[elements-1]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot fix at [%s, %s] corner", FaceDirection.Right, FaceDirection.Top));

        //Check the row/col elements except corners
        for (int i=1; i < elements-1; i++) {
            if ((parallelMatrix[0][i] + topSide[i])!=1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Top, i));
            if ((parallelMatrix[i][0] + leftSide[i])!=1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Left, i));
            if ((parallelMatrix[elements-1][i] + bottomSide[i])!=1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Bottom, i));
            if ((parallelMatrix[i][elements-1] + rightSide[i])!=1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Right, i));
        }
    }

}
