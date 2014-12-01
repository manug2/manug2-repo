package happy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class CombiFace extends HappyFace {

    public CombiFace(HappyFace anchor) {
        super(anchor.getMatrix(), anchor.getRotation(), anchor.name);

        sideFaceMap = new HashMap<FaceDirection, HappyFace>(5);
        effectiveMatrix = new int[anchor.elementCount()][anchor.elementCount()];
        effectiveColumns = new int[anchor.elementCount()][anchor.elementCount()];
        for (int i = 0; i < anchor.elementCount(); i++) {
            for (int j = 0; j < anchor.elementCount(); j++) {
                effectiveMatrix[i][j] = anchor.getMatrix()[i][j];
                effectiveColumns[j][i] = effectiveMatrix[i][j];
            }
        }
        conns = new FaceConnections();
        blockedList = new ArrayList<>(3);

    }

    private int[][] effectiveMatrix;
    private int[][] effectiveColumns;
    private final Map<FaceDirection, HappyFace> sideFaceMap;
    private FaceConnections conns;
    long numOfOps;

    public final HappyFace getLeft() {
        return sideFaceMap.get(FaceDirection.Left);
    }

    public final HappyFace getBottom() {
        return sideFaceMap.get(FaceDirection.Bottom);
    }

    public final HappyFace getRight() {
        return sideFaceMap.get(FaceDirection.Right);
    }

    public final HappyFace getTop() {
        return sideFaceMap.get(FaceDirection.Top);
    }

    public final HappyFace getParallel() {
        return sideFaceMap.get(FaceDirection.Parallel);
    }

    @Override
    public final void match(final HappyFace face, final FaceDirection direction) {

        if (sideFaceMap.containsKey(direction))
            throw new FaceNotMatchingException(String.format("a matching face already attached on [%s]", direction));
        else if (alreadyMatched(face))
            throw new FaceNotMatchingException(String.format("face [%s, %d] has already been matched previously", face.name(), face.getRotation()));

        if (FaceDirection.Parallel.equals(direction)) {

            matchParallel(face);

        } else {

            //Matches faces to this anchor, and all adjacent sides
            super.match(face, direction);

            if (FaceDirection.Left.equals(direction))
                matchLeft(face);
            else if (FaceDirection.Bottom.equals(direction))
                matchBottom(face);
            else if (FaceDirection.Right.equals(direction))
                matchRight(face);
            else if (FaceDirection.Top.equals(direction))
                matchTop(face);

            //Also attaches the matched face to the anchor, which is this object
            sideFaceMap.put(direction, face);
            conns.add(this, face);
            updateEffectiveMatrix(face, direction);
        }

    }

    @Override
    public final int[] getRows(int index) {
        //Presents the relevant side of self or of an attached face depending on orientation
        if (index == 0) {
            if (sideFaceMap.containsKey(FaceDirection.Top))
                return sideFaceMap.get(FaceDirection.Top).getRows(index);
            else
                return effectiveMatrix[index];
        } else if (index == numOfElements - 1) {
            if (sideFaceMap.containsKey(FaceDirection.Bottom))
                return sideFaceMap.get(FaceDirection.Bottom).getRows(index);
            else
                return effectiveMatrix[index];
        } else
            return effectiveMatrix[index];
    }

    @Override
    public final int[] getColumns(int index) {
        //Presents the relevant side of self or of an attached face depending on orientation
        if (index == 0) {
            if (sideFaceMap.containsKey(FaceDirection.Left))
                return sideFaceMap.get(FaceDirection.Left).getColumns(index);
            else
                return effectiveColumns[index];
        } else if (index == numOfElements - 1) {
            if (sideFaceMap.containsKey(FaceDirection.Right))
                return sideFaceMap.get(FaceDirection.Right).getColumns(index);
            else
                return effectiveColumns[index];
        } else
            return effectiveColumns[index];
    }

    @Override
    public final void print() {

        StringBuilder builder = new StringBuilder(this.getClass().getCanonicalName());
        builder.append('[').append(name).append(',').append(getRotation()).append(',').append(System.identityHashCode(this)).append("]");
        builder.append(getMatchedSequence("=> "));
        //PRINT Top matrix
        final HappyFace top = getTop();
        final HappyFace left = getLeft();
        final HappyFace parallel = getParallel();
        if (parallel == null && top != null) {
            int[][] matrix = top.getMatrix();
            for (int i = 0; i < numOfElements; i++) {
                builder.append("\n");
                if (left != null)
                    for (int k = 0; k < numOfElements; k++)
                        builder.append("  ");
                for (int j = 0; j < numOfElements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
        }

        //PRINT Left, Centre and Right matrices
        final HappyFace right = getRight();
        for (int i = 0; i < numOfElements; i++) {
            builder.append("\n");
            if (left != null) {
                final int[] row = left.getRows(i);
                for (int j = 0; j < numOfElements; j++)
                    builder.append(row[j]).append(' ');
            }

            final int[] myRow = super.getRows(i);
            for (int j = 0; j < numOfElements; j++)
                builder.append(myRow[j]).append(' ');

            if (right != null) {
                final int[] row = right.getRows(i);
                for (int j = 0; j < numOfElements; j++)
                    builder.append(row[j]).append(' ');
            }
        }

        //PRINT Bottom matrices
        final HappyFace bottom = getBottom();
        if (bottom != null) {
            int[][] matrix = bottom.getMatrix();
            for (int i = 0; i < numOfElements; i++) {
                builder.append("\n");
                if (left != null)
                    for (int k = 0; k < numOfElements; k++)
                        builder.append("  ");
                for (int j = 0; j < numOfElements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
        }
        //PRINT Bottom matrices
        if (parallel != null) {
            builder.append("\n");
            for (int k = 0; k < numOfElements; k++)
                builder.append("  ");
            for (int k = 0; k < numOfElements; k++)
                builder.append("^ ");
            int[][] matrix = parallel.getMatrix();
            for (int i = 0; i < numOfElements; i++) {
                builder.append("\n");
                for (int k = 0; k < numOfElements; k++)
                    builder.append("  ");
                for (int j = 0; j < numOfElements; j++)
                    builder.append(matrix[i][j]).append(' ');
            }
            if (top != null) {
                //Print inverted top
                builder.append("\n");
                for (int k = 0; k < numOfElements; k++)
                    builder.append("  ");
                for (int k = 0; k < numOfElements; k++)
                    builder.append("v ");
                int[][] t_matrix = top.getMatrix();
                for (int i = 0; i < numOfElements; i++) {
                    builder.append("\n");
                    if (left != null)
                        for (int k = 0; k < numOfElements; k++)
                            builder.append("  ");
                    for (int j = 0; j < numOfElements; j++)//Print flipped
                        builder.append(t_matrix[numOfElements - 1 - i][j]).append(' ');
                }

            }
        }

        builder.append("\n");
        System.out.println(builder.toString());
    }

    @Override
    public final CombiFace clone() {
        CombiFace cloned = new CombiFace(this);
        for (Entry<FaceDirection, HappyFace> entry : sideFaceMap.entrySet()) {
            cloned.sideFaceMap.put(entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < numOfElements; i++) {
            for (int j = 0; j < numOfElements; j++) {
                cloned.effectiveMatrix[i][j] = effectiveMatrix[i][j];
                cloned.effectiveColumns[j][i] = effectiveMatrix[i][j];
            }
        }
        cloned.conns = conns.clone();
        cloned.blockedList = new ArrayList<>(this.blockedList);

        return cloned;
    }

    public final void checkEdgeLeft(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getLeft();
        if (existing == null)
            return;

        int[] side1, side2;
        int parallelEdgeElement, anchorEdgeElement;
        if (FaceDirection.Top.equals(direction)) {
            side1 = existing.getRows(0);
            side2 = face.getColumns(0);
            anchorEdgeElement = effectiveMatrix[0][0] + side2[numOfElements - 1];
            parallelEdgeElement = side1[0] + side2[0];
            side2 = reverseArray(side2); //For comparing elements inside the edges
        } else if (FaceDirection.Bottom.equals(direction)) {
            side1 = existing.getRows(numOfElements - 1);
            side2 = face.getColumns(0);
            anchorEdgeElement = effectiveMatrix[numOfElements - 1][0] + side2[0];
            parallelEdgeElement = side1[0] + side2[numOfElements - 1];
            side2 = reverseArray(side2); //For comparing elements inside the edges
        } else
            return;

        checkAnchorEdgeElement(anchorEdgeElement, FaceDirection.Left, direction);
        checkParllelEdgeElement(parallelEdgeElement, FaceDirection.Left, direction);
        checkInternalElementsOfEdge(side1, side2, FaceDirection.Left, direction);
    }

    public final void checkEdgeBottom(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getBottom();
        if (existing == null)
            return;
        int[] side1, side2;
        int parallelEdgeElement, anchorEdgeElement;
        if (FaceDirection.Left.equals(direction)) {
            side1 = existing.getColumns(0);
            side2 = face.getRows(numOfElements - 1);
            anchorEdgeElement = effectiveMatrix[numOfElements - 1][0] + side2[numOfElements - 1];
            parallelEdgeElement = side1[numOfElements - 1] + side2[0];
            side2 = reverseArray(side2); //For comparing elements inside the edges
        } else if (FaceDirection.Right.equals(direction)) {
            side1 = existing.getColumns(numOfElements - 1);
            side2 = face.getRows(numOfElements - 1);
            anchorEdgeElement = effectiveMatrix[numOfElements - 1][numOfElements - 1] + side2[0];
            parallelEdgeElement = side1[numOfElements - 1] + side2[numOfElements - 1];
        } else
            return;

        checkAnchorEdgeElement(anchorEdgeElement, FaceDirection.Bottom, direction);
        checkParllelEdgeElement(parallelEdgeElement, FaceDirection.Bottom, direction);
        checkInternalElementsOfEdge(side1, side2, FaceDirection.Bottom, direction);
    }

    public final void checkEdgeRight(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getRight();
        if (existing == null)
            return;
        int[] side1, side2;
        int parallelEdgeElement, anchorEdgeElement;
        if (FaceDirection.Top.equals(direction)) {
            side1 = existing.getRows(0);
            side2 = face.getColumns(numOfElements - 1);
            anchorEdgeElement = effectiveMatrix[0][numOfElements - 1] + side2[numOfElements - 1];
            parallelEdgeElement = side1[numOfElements - 1] + side2[0];
            side2 = reverseArray(side2); //For comparing elements inside the edges
        } else if (FaceDirection.Bottom.equals(direction)) {
            side1 = existing.getRows(numOfElements - 1);
            side2 = face.getColumns(numOfElements - 1);
            anchorEdgeElement = effectiveMatrix[numOfElements - 1][numOfElements - 1] + side2[0];
            parallelEdgeElement = side1[numOfElements - 1] + side2[numOfElements - 1];
        } else
            return;

        checkAnchorEdgeElement(anchorEdgeElement, FaceDirection.Right, direction);
        checkParllelEdgeElement(parallelEdgeElement, FaceDirection.Right, direction);
        checkInternalElementsOfEdge(side1, side2, FaceDirection.Right, direction);
    }

    public final void checkEdgeTop(HappyFace face, FaceDirection direction) {
        final HappyFace existing = getTop();
        if (existing == null)
            return;
        int[] side1, side2;
        int parallelEdgeElement, anchorEdgeElement;
        if (FaceDirection.Left.equals(direction)) {
            side1 = existing.getColumns(0);
            side2 = face.getRows(0);
            anchorEdgeElement = effectiveMatrix[0][0] + side2[numOfElements - 1];
            parallelEdgeElement = side1[0] + side2[0];
        } else if (FaceDirection.Right.equals(direction)) {
            side1 = existing.getColumns(numOfElements - 1);
            side2 = face.getRows(0);
            anchorEdgeElement = effectiveMatrix[0][numOfElements - 1] + side2[0];
            parallelEdgeElement = side1[0] + side2[numOfElements - 1];
            side2 = reverseArray(side2); //For comparing elements inside the edges
        } else
            return;

        checkAnchorEdgeElement(anchorEdgeElement, FaceDirection.Top, direction);
        checkParllelEdgeElement(parallelEdgeElement, FaceDirection.Top, direction);
        checkInternalElementsOfEdge(side1, side2, FaceDirection.Top, direction);
    }

    private void checkAnchorEdgeElement(int anchorEdgeElement, FaceDirection existing, FaceDirection direction) {
        if (anchorEdgeElement != 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of elements at anchor edge", existing, direction));

    }

    private void checkParllelEdgeElement(int parallelEdgeElement, FaceDirection existing, FaceDirection direction) {
        if (parallelEdgeElement > 1)
            throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at parallel edge is present in both", existing, direction));

    }

    private void checkInternalElementsOfEdge(int[] side1, int[] side2, FaceDirection existing, FaceDirection direction) {
        for (int i = 1; i < numOfElements - 1; i++) {
            if ((side1[i] + side2[i]) != 1)
                throw new FaceNotMatchingException(String.format("face [%s] and [%s] not matching because of element at edge with index [%d]", existing, direction, i));
        }
    }

    public final void checkEdgeParallel(HappyFace face) {
        if (sideFaceMap.size() < 4)
            throw new FaceNotMatchingException(String.format("a matching face can be attached on [%s] only after all other sides are full", FaceDirection.Parallel));

        checkCornersOfParallelFace(face);
        checkOverlappingEdgesOfParallelFace(face);

    }

    private void checkCornersOfParallelFace(HappyFace face) {
        final int[][] parallelMatrix = face.getMatrix();
        final int[] leftSide = getLeft().getColumns(0);
        final int[] bottomSide = getBottom().getRows(numOfElements - 1);
        final int[] rightSide = getRight().getColumns(numOfElements - 1);
        final int[] topSide = getTop().getRows(0);

        if ((parallelMatrix[0][0] + topSide[0] + leftSide[0]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Left, FaceDirection.Top));

        else if ((parallelMatrix[numOfElements - 1][0] + leftSide[numOfElements - 1] + bottomSide[0]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Left, FaceDirection.Bottom));

        else if ((parallelMatrix[numOfElements - 1][numOfElements - 1] + bottomSide[numOfElements - 1] + rightSide[numOfElements - 1]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Bottom, FaceDirection.Right));

        else if ((parallelMatrix[0][numOfElements - 1] + rightSide[0] + topSide[numOfElements - 1]) != 1)
            throw new FaceNotMatchingException(String.format("parallel face cannot be placed at [%s, %s] corner", FaceDirection.Right, FaceDirection.Top));
    }

    private void checkOverlappingEdgesOfParallelFace(HappyFace face) {
        final int[][] parallelMatrix = face.getMatrix();
        final int[] leftSide = getLeft().getColumns(0);
        final int[] bottomSide = getBottom().getRows(numOfElements - 1);
        final int[] rightSide = getRight().getColumns(numOfElements - 1);
        final int[] topSide = getTop().getRows(0);

        for (int i = 1; i < numOfElements - 1; i++) {
            if ((parallelMatrix[0][i] + topSide[i]) != 1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Top, i));
            if ((parallelMatrix[i][0] + leftSide[i]) != 1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Left, i));
            if ((parallelMatrix[numOfElements - 1][i] + bottomSide[i]) != 1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Bottom, i));
            if ((parallelMatrix[i][numOfElements - 1] + rightSide[i]) != 1)
                throw new FaceNotMatchingException(String.format("parallel face not matching [%s] row, element [%d]", FaceDirection.Right, i));
        }
    }

    public final boolean alreadyMatched(HappyFace face) {
        return sideFaceMap.containsValue(face);
    }

    public final boolean hasFace(FaceDirection direction) {
        return sideFaceMap.containsKey(direction);
    }

    public final boolean isSolved() {
        return 5 == sideFaceMap.size();
    }

    public final int howManyAttached() {
        return sideFaceMap.size();
    }

    public final String getMatchedSequence(String prefix) {
        StringBuilder builder = new StringBuilder();
        if (prefix != null)
            builder.append('[').append(prefix).append(']').append(' ');

        builder.append('[').append(name).append(" ").append(getRotation()).append(']');
        for (FaceDirection direction : FaceDirection.values()) {
            final HappyFace face = sideFaceMap.get(direction);
            if (face != null)
                builder.append(" - [").append(face.name).append(" ").append(face.getRotation()).append(" (").append(direction).append(")]");
        }
        return builder.toString();
    }

    public final String getMatchedSequence() {
        final StringBuilder builder = new StringBuilder();
        builder.append(name).append(',').append(getRotation());
        for (FaceDirection direction : FaceDirection.values()) {
            final HappyFace face = sideFaceMap.get(direction);
            if (face != null)
                builder.append(',').append(face.name).append(',').append(face.getRotation()).append(',').append(direction);
        }
        return builder.toString();
    }

    public FaceConnections getConnections() {
        return this.conns;
    }

    public final List<FaceDirection> getPendingDirections() {
        List<FaceDirection> pending = new ArrayList<FaceDirection>(5 - sideFaceMap.size());
        for (FaceDirection dir : FaceDirection.values())
            if (!sideFaceMap.containsKey(dir))
                pending.add(dir);
        return pending;
    }

    private void updateEffectiveMatrix(HappyFace face, FaceDirection direction) {
        int[][] faceMat = face.getMatrix();
        switch (direction) {
            case Left:
                effectiveMatrix[0][0] += faceMat[0][numOfElements - 1];//change to sum of two incoming sides
                effectiveMatrix[numOfElements - 1][0] += faceMat[numOfElements - 1][numOfElements - 1];
                if (getBottom() != null)
                    conns.add(face, getBottom());
                if (getTop() != null)
                    conns.add(getTop(), face);
                break;
            case Bottom:
                effectiveMatrix[numOfElements - 1][0] += faceMat[0][0];
                effectiveMatrix[numOfElements - 1][numOfElements - 1] += faceMat[0][numOfElements - 1];
                if (getLeft() != null)
                    conns.add(getLeft(), face);
                if (getRight() != null)
                    conns.add(face, getRight());
                break;
            case Right:
                effectiveMatrix[0][numOfElements - 1] += faceMat[0][0];
                effectiveMatrix[numOfElements - 1][numOfElements - 1] += faceMat[numOfElements - 1][0];
                if (getBottom() != null)
                    conns.add(getBottom(), face);
                if (getTop() != null)
                    conns.add(face, getTop());
                break;
            case Top:
                effectiveMatrix[0][0] += faceMat[numOfElements - 1][0];
                effectiveMatrix[0][numOfElements - 1] += faceMat[numOfElements - 1][numOfElements - 1];
                if (getLeft() != null)
                    conns.add(face, getLeft());
                if (getRight() != null)
                    conns.add(getRight(), face);
                break;
            case Parallel:
                //effective matrix does not change as it does not touch anchor
                break;
        }
        for (int i = 0; i < super.numOfElements; i++) {
            for (int j = 0; j < super.numOfElements; j++) {
                effectiveColumns[j][i] = effectiveMatrix[i][j];
            }
        }
    }

    private void matchParallel(HappyFace face) {
        checkEdgeParallel(face);
        for (FaceDirection d : FaceDirection.values())
            if (sideFaceMap.containsKey(d))
                conns.add(sideFaceMap.get(d), face);
        sideFaceMap.put(FaceDirection.Parallel, face);
    }

    private void matchLeft(HappyFace face) {
        checkEdgeTop(face, FaceDirection.Left);
        checkEdgeBottom(face, FaceDirection.Left);
    }

    private void matchBottom(HappyFace face) {
        checkEdgeLeft(face, FaceDirection.Bottom);
        checkEdgeRight(face, FaceDirection.Bottom);
    }

    private void matchRight(HappyFace face) {
        checkEdgeBottom(face, FaceDirection.Right);
        checkEdgeTop(face, FaceDirection.Right);
    }

    private void matchTop(HappyFace face) {
        checkEdgeLeft(face, FaceDirection.Top);
        checkEdgeRight(face, FaceDirection.Top);
    }

    public CombiFace setNumOfOperations(long num) {
        this.numOfOps = num;
        return this;
    }

    private List<String> blockedList;
    public boolean evalAndRegisterRewinding(final HappyFace face, final FaceDirection direction) {
        if (face.getRotation()==0)
            return false;

        final String blockingString = generateBlockedString(face, direction);
        if(blockedList.contains(blockingString))
            return false;
        else {
            blockedList.add(blockingString);
            return true;
        }
    }
    private String generateBlockedString(final HappyFace face, final FaceDirection direction) {
        return face.name + "<-"+ direction;
    }
    public boolean checkBlocked(final HappyFace face, final FaceDirection direction) {
        return blockedList.contains(generateBlockedString(face, direction));
    }
}
