import java.util.ArrayList;
import java.util.List;

public class Graph {
    // 节点列表
    private List<Integer> nodes;
    // 边列表
    private List<Edge> edges;

    public Graph(int i) {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            addNode(j);
        }
    }

    public void addNode(int node) {
        nodes.add(node);
    }

    public void addEdge(int source, int destination, int weight) {
        edges.add(new Edge(source, destination, weight));
    }

    public List<Integer> getNodes() {
        return nodes;
    }

    public int getNodesCount() {
        return nodes.size();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getEdgeWeight(int current, int neighbor) {
        for (Edge edge : edges) {
            if (edge.getSource() == current && edge.getDestination() == neighbor) {
                return edge.weight;
            }
        }
        return -1;
    }

    public Edge getEdge(int current, int neighbor) {
        for (Edge edge : edges) {
            if (edge.getSource() == current && edge.getDestination() == neighbor) {
                return edge;
            }
        }
        return null;
    }

    public List<Integer> getNeighbors(int node) {
        List<Integer> neighbors = new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.getSource() == node) {
                neighbors.add(edge.getDestination());
            }
        }

        return neighbors;
    }

    // 有向图类中的边类
    static class Edge {
        // 边的起始节点
        private int source;
        // 边的目标节点
        private int destination;
        // 当前密钥池的数量
        private int weight;
        //密钥池上限
        private int upperRange = 1000;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public int getSource() {
            return source;
        }

        public int getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }

        public int getUpperRange() {
            return upperRange;
        }

        public void setUpperRange(int upperRange) {
            this.upperRange = upperRange;
        }

        synchronized public void setWeight(int weight) {
            if (weight > upperRange) {
                weight = upperRange;
            }
            this.weight = weight;
        }
    }
}