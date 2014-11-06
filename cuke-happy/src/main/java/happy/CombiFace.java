package happy;

import java.util.Map;
import java.util.HashMap;

public class CombiFace extends HappyFace {
    public CombiFace(HappyFace anchor) {
        super(anchor.elementCount(), anchor.identifier(), anchor.getMatrix());
    }

    private Map<FaceDirection, HappyFace> sideFaceMap = new HashMap<FaceDirection, HappyFace>(4);


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

    @Override
    public boolean match(HappyFace face, FaceDirection direction) {
        boolean matched = super.match(face, direction);
        if (matched)
            sideFaceMap.put(direction, face);
        return matched;
    }

    @Override
    public int[] getRows(int index) {
        if (index==0) {
            if (sideFaceMap.containsKey(FaceDirection.Top))
                return sideFaceMap.get(FaceDirection.Top).getRows(index);
            else
                return super.getRows(index);
        } else if (index == elementCount()-1) {
            if (sideFaceMap.containsKey(FaceDirection.Bottom))
                return sideFaceMap.get(FaceDirection.Bottom).getRows(index);
            else
                return super.getRows(index);
        } else
            return super.getRows(index);
    }

    @Override
    public int[] getColumns(int index) {
        if (index==0) {
            if(sideFaceMap.containsKey(FaceDirection.Left))
                return sideFaceMap.get(FaceDirection.Left).getColumns(index);
            else
                return super.getColumns(index);
        } else if (index == elementCount()-1) {
            if (sideFaceMap.containsKey(FaceDirection.Right))
                return sideFaceMap.get(FaceDirection.Right).getColumns(index);
            else
                return super.getColumns(index);
        } else
            return super.getColumns(index);
    }

    public void detach(FaceDirection direction) {
        if (sideFaceMap.containsKey(direction))
            sideFaceMap.remove(direction);
        else
            throw new RuntimeException(String.format("No face was attached to side [%s]", direction.toString()));
    }

    @Override
    public void print() {
        int topCount = 0;
        int leftCount = 0;
        {
            HappyFace top = getTop();
            while (top!=null) {
                topCount++;
                if (top instanceof CombiFace)
                    top = ((CombiFace) top).getTop();
            }
            HappyFace left = getLeft();
            while (left!=null) {
                leftCount++;
                if (left instanceof CombiFace)
                    left = ((CombiFace) left).getLeft();
            }
        }

        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        builder.append('[').append(identifier()).append(',').append(this).append("]");
        final int elements = elementCount();

        //PRINT Top matrices
        if (topCount>0) {
            HappyFace top = getTop();
            while(top!=null) {
                int[][] matrix = top.getMatrix();
                for (int i=0; i < elements; i++) {
                    builder.append("\n");
                    if (leftCount>0)
                        for (int k=0; k < elements*leftCount; k++)
                            builder.append("  ");
                    for (int j=0; j < elements; j++)
                        builder.append(matrix[i][j]).append(' ');
                }
                if (top instanceof CombiFace)
                    top = ((CombiFace) top).getTop();
            }
        }

        //PRINT Left, Centre and Right matrices
        //if (leftCount>0) {
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                HappyFace left = getLeft();
                int leftPendingIndex =  leftCount-1;
                for (int p=0; p < leftPendingIndex; p++)
                    left = ((CombiFace) left).getTop();

                if (left!=null) {
                    final int[] row = left.getRows(i);
                    for (int j=0; j < elements; j++)
                        builder.append(row[j]).append(' ');
                }

                final int[] myRow = super.getRows(i);
                for (int j=0; j < elements; j++)
                    builder.append(myRow[j]).append(' ');

                HappyFace right = getRight();
                while (right!=null) {
                    final int[] row = right.getRows(i);
                    for (int j=0; j < elements; j++)
                        builder.append(row[j]).append(' ');
                    if (right instanceof CombiFace)
                        right = ((CombiFace) right).getRight();
                }
            }
        //}

        //PRINT Bottom matrices
        HappyFace bottom = getBottom();
        while(bottom!=null) {
            int[][] matrix = bottom.getMatrix();
            for (int i=0; i < elements; i++) {
                builder.append("\n");
                if (leftCount>0)
                    for (int k=0; k < elements*leftCount; k++)
                        builder.append(" ");
                for (int j=0; j < elements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
            if (bottom instanceof CombiFace)
                bottom = ((CombiFace) bottom).getTop();
        }

        builder.append("\n");
        System.out.println(builder.toString());

    }

}
