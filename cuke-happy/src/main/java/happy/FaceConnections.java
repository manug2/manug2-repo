package happy;


import java.util.ArrayList;

public class FaceConnections {

    private ArrayList<FaceConnection> connections = new ArrayList<FaceConnection>(12);
    public void add(final HappyFace face1, final HappyFace face2) {
        if (face1==null)
            throw new AssertionError("first item is null");
        else if (face2==null)
            throw new AssertionError("second item is null");
        FaceConnection newConn = new FaceConnection(face1.name(), face2.name());
        for (FaceConnection conn : connections) {
             if (newConn.equals(conn))
                 throw new AssertionError(String.format("Connection between faces [%s] and [%s] already exists", newConn.one, newConn.two));
        }
        connections.add(newConn);
    }

    @Override
    public boolean equals(Object other) {
        if (other==null)
            return false;
        FaceConnections obj = (FaceConnections) other;
        if (connections.size()!= obj.connections.size())
            return false;

        for (FaceConnection conn : obj.connections) {
            if (! connections.contains(conn))
                return false;
        }
        return true;
    }

    @Override
    public FaceConnections clone() {
        FaceConnections newConns = new FaceConnections();
        for (FaceConnection conn : this.connections)
            newConns.connections.add(new FaceConnection(conn.one, conn.two));
        return newConns;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append('[');
        for (FaceConnection conn : connections) {
            builder.append(conn.one).append("<->").append(conn.two).append(' ');
        }
        return builder.append(']').toString();
    }

    public int size() {
        return connections.size();
    }

    class FaceConnection {
        final String one, two;
        FaceConnection(final String one, final String two) {
            if (one==null)
                throw new AssertionError("first item is null");
            else if (two==null)
                throw new AssertionError("second item is null");

            final String oneTrimmed = one.trim();
            final String twoTrimmed = two.trim();
            if ("".equals(oneTrimmed))
                throw new AssertionError("first item is blank");
            else if ("".equals(twoTrimmed))
                throw new AssertionError("second item is blank");
            if (oneTrimmed.compareTo(twoTrimmed)>0) {
                this.two = oneTrimmed;
                this.one = twoTrimmed;
            } else {
                this.one = oneTrimmed;
                this.two = twoTrimmed;
            }
        }
        @Override
        public boolean equals(Object other) {
            if (other==null)
                return false;
            FaceConnection obj = (FaceConnection) other;
            return this.one.equals(obj.one) && this.two.equals(obj.two);
        }
    }
}
