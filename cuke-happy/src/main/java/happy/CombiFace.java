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

        if (sideFaceMap.containsKey(direction))
            throw new RuntimeException(String.format("a matching face already attached on [%s]", direction.toString()));

        if (FaceDirection.Parallel.equals(direction)) {
            checkEdgeParallel(face, direction);
            return;
        }

        //Matches faces to this anchor, and all adjacent sides
        super.match(face, direction);

       if (FaceDirection.Left.equals(direction)) {
            checkEdgeTop(face, direction);
            checkEdgeBottom(face, direction);
        }

        if (FaceDirection.Bottom.equals(direction)) {
            checkEdgeLeft(face, direction);
            checkEdgeRight(face, direction);
        }

        if (FaceDirection.Right.equals(direction)) {
            checkEdgeBottom(face, direction);
            checkEdgeTop(face, direction);
        }

        if (FaceDirection.Top.equals(direction)) {
            checkEdgeLeft(face, direction);
            checkEdgeRight(face, direction);
        }

        //Also attaches the matched face to the anchor, which is this object
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
        final HappyFace left = getLeft();
        if (top!=null) {
            int[][] matrix = top.getMatrix();
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                if(left!=null)
                    for (int k=0; k < elements; k++)
                        builder.append("  ");
                for (int j=0; j < elements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
        }

        //PRINT Left, Centre and Right matrices
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

    public void checkEdges(FaceDirection existingFace, HappyFace face, FaceDirection direction) {
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
    }

    public void checkEdgeLeft(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getLeft();
        if (existing==null)
            return;

        int[] side1, side2;
        int edgeElement;
        if (FaceDirection.Top.equals(direction)) {
            side1 = existing.getRows(0);
            side2 = face.getColumns(0);
            edgeElement = effectiveMatrix[0][0];
        } else if (FaceDirection.Bottom.equals(direction)) {
            side1 = reverseArray(existing.getRows(elements - 1));
            side2 = face.getColumns(0);
            edgeElement = effectiveMatrix[elements-1][0];
        } else
            return ;

        for (int i=1; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Left, direction, i));
        }
        if ( edgeElement + side2[0] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Left, direction, 0));
        else if ( side1[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Left, direction, elements-1));
    }

    public void checkEdgeBottom(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getBottom();
        if (existing==null)
            return;
        int[] side1, side2;
        int edgeElement;
        if (FaceDirection.Left.equals(direction)) {
            side1 = existing.getColumns(0);
            side2 = reverseArray(face.getRows(elements - 1));
            edgeElement = effectiveMatrix[elements-1][0];
        } else if (FaceDirection.Right.equals(direction)) {
            side1 = existing.getColumns(elements - 1);
            side2 = face.getRows(elements - 1);
            edgeElement = effectiveMatrix[elements-1][elements - 1];
        } else
            return ;

        for (int i=1; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Bottom, direction, i));
        }
        if (edgeElement + side2[0] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Bottom, direction, 0));
        else if (side2[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Bottom, direction, elements-1));
    }

    public void checkEdgeRight(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getRight();
        if (existing==null)
            return;
        int[] side1, side2;
        int edgeElement;
        if (FaceDirection.Top.equals(direction)) {
            side1 = existing.getRows(0);
            side2 = reverseArray(face.getColumns(elements - 1));
            edgeElement = effectiveMatrix[0][elements - 1];
        } else if (FaceDirection.Bottom.equals(direction)) {
            side1 = existing.getRows(elements - 1);
            side2 = face.getColumns(elements - 1);
            edgeElement = effectiveMatrix[elements-1][elements - 1];
        } else
            return ;

        for (int i=1; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Right, direction, i));
        }
        if (edgeElement + side2[0] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Right, direction, 0));
        else if (side2[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Right, direction, elements-1));
    }

    public void checkEdgeTop(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getTop();
        if (existing==null)
            return;
        int[] side1, side2;
        int edgeElement;
        if (FaceDirection.Left.equals(direction)) {
            side1 = reverseArray(existing.getColumns(0));
            side2 = face.getRows(0);
            edgeElement = effectiveMatrix[0][0];
        } else if (FaceDirection.Right.equals(direction)) {
            side1 = reverseArray(existing.getColumns(elements - 1));
            side2 = face.getRows(0);
            edgeElement = effectiveMatrix[0][elements - 1];
        } else
            return ;

        for (int i=1; i < elements-1; i++) {
            if ((side1[i] + side2[i]) != 1)
               throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", FaceDirection.Top, direction, i));
        }
        if (edgeElement + side2[0] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Top, direction, 0));
        if (side2[elements-1] + side2[elements-1] > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d] is present in both", FaceDirection.Top, direction, elements-1));
    }

    public void checkEdgeParallel(HappyFace face, FaceDirection direction) {
        if(sideFaceMap.size()<4)
            throw new RuntimeException(String.format("a matching face can be attached on [%s] only after all other sides are full", FaceDirection.Parallel.toString()));

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
        final int[] rightSide = getRight().getColumns(elements - 1);
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
