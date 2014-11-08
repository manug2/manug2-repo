package happy;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class CombiFace extends HappyFace {
    public CombiFace(HappyFace anchor) {
        super(anchor.elementCount(), anchor.identifier(), anchor.getMatrix());

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
    public boolean match(final HappyFace face, final FaceDirection direction) {
        //Matches faces to this anchor, and all adjacent sides
        //Also attaches the matched face to the anchor, which is this object
        boolean matched;
        final HappyFace left = getLeft();
        final HappyFace top = getTop();
        final HappyFace right = getRight();
        final HappyFace bottom = getBottom();

        if (FaceDirection.Parallel.equals(direction)){
            if(sideFaceMap.size()<4)
                throw new RuntimeException(String.format("a matching face can be attached on [%s] only after all other sides are full", direction.toString()));

            matched = true;
            matched &= left.match(face, FaceDirection.Left);//Should we horizontal flip?
            if (matched)
                matched &= bottom.match(face, FaceDirection.Bottom);//Should we vertical flip?
            if (matched)
                matched &= right.match(face, FaceDirection.Right);//Should we horizontal flip?
            if (matched)
                matched &= top.match(face, FaceDirection.Top);//Should we vertical flip?
        } else {
            matched = super.match(face, direction);
            if (matched && FaceDirection.Left.equals(direction) && top!=null)
                matched &= top.match(face.rotateCW(), FaceDirection.Left);
            if (matched && FaceDirection.Left.equals(direction) && bottom!=null)
                matched &= bottom.match(face.rotateCC(), FaceDirection.Left);

            if (matched && FaceDirection.Bottom.equals(direction) && left!=null)
                matched &= left.match(face.rotateCW(), FaceDirection.Bottom);
            if (matched && FaceDirection.Bottom.equals(direction) && right!=null)
                matched &= right.match(face.rotateCC(), FaceDirection.Bottom);

            if (matched && FaceDirection.Right.equals(direction) && bottom!=null)
                matched &= bottom.match(face.rotateCW(), FaceDirection.Right);
            if (matched && FaceDirection.Right.equals(direction) && top!=null)
                matched &= top.match(face.rotateCC(), FaceDirection.Right);

            if (matched && FaceDirection.Top.equals(direction) && left!=null)
                matched &= left.match(face.rotateCW(), FaceDirection.Top);
            if (matched && FaceDirection.Top.equals(direction) && right!=null)
                matched &= right.match(face.rotateCC(), FaceDirection.Top);

        }

        if (matched) {
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
        return matched;
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
}
