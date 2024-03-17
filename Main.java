import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Dijkstra {
    static List<Integer>[] cnt;
    static int speed = 10;
    static int consumeSpeed = 6;
    static int retryCount = 0;

    public static int reComputeSleepTime(int retryCount) {
        System.out.println("准备重试第" + retryCount + "次");
        int unitTime = 10;
        return unitTime * (int) Math.pow(2, retryCount);
    }

    public static void main(String[] args) {
        // 创建一个有向图
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 4);
        graph.addEdge(1, 5, 4);
        graph.addEdge(1, 4, 2);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 5, 4);
        graph.addEdge(4, 3, 1);
        graph.addEdge(4, 5, 2);
        graph.addEdge(3, 5, 1);

//        graph.addEdge(0, 1, 1);
//        graph.addEdge(0, 2, 1);
//        graph.addEdge(0, 3, 1);
//        graph.addEdge(1, 4, 1);
//        graph.addEdge(2, 3, 1);
//        graph.addEdge(2, 5, 1);
//        graph.addEdge(4, 6, 1);
//        graph.addEdge(3, 4, 1);
//        graph.addEdge(3, 5, 1);
//        graph.addEdge(5, 6, 1);
//        graph.addEdge(0, 2, 800);
//        graph.addEdge(2, 4, 800);
//        graph.addEdge(4, 5, 900);
//        graph.addEdge(2, 3, 900);
//        graph.addEdge(3, 5, 900);
//        graph.addEdge(1, 3, 900);

        // 找到源节点到其他节点的最短路径
        cnt = findShortestPaths(graph);
        dfs(graph.getNodesCount() - 1, cnt[graph.getNodesCount() - 1], new ArrayList<>());

        //打印出最优路径
        for (List<Integer> integers : bestPaths) {
            System.out.println(integers);
        }
        getDisjointPaths(bestPaths, 0);
        System.out.println("没有公共节点的最优路径：");
        int count = 1;

        //getRandomPaths();
        int i = getQuantumRandomCode(3);
        System.out.println("Qiskit产生的量子随机数为:"+i);
        System.out.println("最少公共节点路径为：");
        System.out.println(disJointPaths.get(i));
        System.out.println("随机最优路径为:" + randomPath);
        Collections.sort(disJointPaths, new Comparator<List<List<Integer>>>() {
            @Override
            public int compare(List<List<Integer>> o1, List<List<Integer>> o2) {
                return o2.size() - o1.size();
            }
        });
        //打印出不包含公共可信中继的最优路径
        for (List<List<Integer>> temp : disJointPaths) {
            System.out.println("第" + (count++) + "组");
            for (List<Integer> tempPath : temp) {
                System.out.println(tempPath);
            }
        }

        List<List<Integer>> normalPaths = findNormalPath(graph);
        System.out.println("所有可达路径为:");
        for (List<Integer> path : normalPaths) {
            System.out.println(path);
        }


        Thread useDisJointPathThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //用于判断链路是否还能继续传输密钥
                boolean flag = true;

                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int resultCode = consumeKeys(graph, disJointPaths.get(0), consumeSpeed);
                    if (resultCode < 0) {
                        flag = false;
                        System.out.println("密钥不足，任务无法继续");
                    }
                }
            }
        });
        Thread useMultiPathThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //用于判断链路是否还能继续传输密钥
                boolean flag = true;

                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int resultCode = consumeKeys(graph, bestPaths, consumeSpeed);
                    if (resultCode < 0) {
                        flag = false;
                        System.out.println("密钥不足，任务无法继续");
                    }
                }
            }
        });
        Thread useRandomPathThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //用于判断链路是否还能继续传输密钥
                boolean flag = true;
                List<List<Integer>> list = new ArrayList<>();
                list.add(randomPath);
                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int resultCode = consumeKeys(graph, list, consumeSpeed);
                    if (resultCode < 0) {
                        flag = false;
                        System.out.println("密钥不足，任务无法继续");
                    }
                }

            }
        });
        List<List<Integer>> concurrentPaths = new ArrayList<>();
        List<Integer> concurrentPath1 = new ArrayList<>();
        concurrentPath1.add(0);
        concurrentPath1.add(2);
        concurrentPath1.add(3);
        concurrentPath1.add(5);
//        List<Integer> concurrentPath2 = new ArrayList<>();
//        concurrentPath2.add(1);
//        concurrentPath2.add(3);
//        concurrentPath2.add(5);
        concurrentPaths.add(concurrentPath1);
        //concurrentPaths.add(concurrentPath2);
        List<Integer> result1 = new ArrayList<>();
        List<Integer> result2 = new ArrayList<>();
        Thread useConcurrentThread = new Thread(new Runnable() {
            @Override
            public void run() {

                //用于判断链路是否还能继续传输密钥
                boolean flag = true;

                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    int resultCode = consumeKeys(graph, concurrentPaths, consumeSpeed);
                    if (resultCode < 0) {
                        if (retryCount == 16) {
                            flag = false;
                            System.out.println("重试了16次，还是不行哦");
                            break;
                        }
                        retryCount += 1;
                        int sleepTime = reComputeSleepTime(retryCount);
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        //System.out.println("密钥不足，任务无法继续");
//                        System.out.println(result1);
//                        System.out.println(result2);
                    } else {
                        retryCount = 0;
                        System.out.println("此次消耗的密钥为:" + resultCode);
                    }
                }

            }
        });

        //useConcurrentThread.start();
        Thread generateKeyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    generateKeys(graph, speed);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
        //generateKeyThread.start();
        //useRandomPathThread.start();
        //useMultiPathThread.start();
        //useDisJointPathThread.start();
//        int policy = jurgePolicy(graph, consumeSpeed, 0);
//        System.out.println("policy is " + policy);
//        switch (policy) {
//            case 0:
//                System.out.println("量子随机路径算法启动!");
//                useRandomPathThread.start();
//                break;
//            case 1:
//                System.out.println("最少公共路径节点算法启动!");
//                useDisJointPathThread.start();
//                break;
//            case 2:
//                System.out.println("多路径算法启动!");
//                useMultiPathThread.start();
//                break;
//        }


        Thread lookAtLinkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long startTime = System.currentTimeMillis();
                while (true) {
                    System.out.println("当前时间：");
                    System.out.println(System.currentTimeMillis() - startTime);

                    System.out.println("查看链路密钥情况：");
                    int sum = 0;
                    List<Graph.Edge> edges = lookAtLink(graph);
                    for (Graph.Edge edge : edges) {
                        System.out.println("from " + edge.getSource() + " to " + edge.getDestination());
                        if (edge.getSource() == 3 && edge.getDestination() == 5) {
                            result1.add(edge.getWeight());
                        }
                        if (edge.getSource() == 2 && edge.getDestination() == 4) {
                            result2.add(edge.getWeight());
                        }
                        System.out.print(edge.getWeight() + " ");
                        sum += edge.getWeight();
                    }
                    System.out.println("此时链路密钥总量为:" + sum);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });
        lookAtLinkThread.start();
    }

    public static int jurgePolicy(Graph graph, int k, int level) {
        if (level == 1 || k < speed * 0.5) {
            return 2;
        }
        if (k >= speed) {
            return 0;
        }
        if (k >= speed * 0.5) {
            return 1;
        }
        return -1;
    }

    public static List<Integer> randomPath = new ArrayList<>();

    //使用qiskit获取量子随机数来制作随机最优路径
    public static void getRandomPaths() {
        int quantumRandomNumber = getQuantumRandomCode(bestPaths.size());
        System.out.println("得到的量子随机数为:" + quantumRandomNumber);
        randomPath = bestPaths.get(quantumRandomNumber);
    }

    //最优路径
    public static List<List<Integer>> bestPaths = new ArrayList<>();

    //没有公共节点的最优路径组合
    public static List<List<List<Integer>>> disJointPaths = new ArrayList<>();

    //用全排列+剪枝从最优路径中解析出不包含公共可信中继的路径
    public static void getDisjointPaths(List<List<Integer>> path, int count) {

        if (path.isEmpty() || count == path.size()) {
            return;
        }
        List<Integer> target = path.get(count);
        List<List<Integer>> tempPath = new ArrayList<>();
        tempPath.add(target);
        for (int i = count + 1; i < path.size(); i++) {
            boolean flag = true;
            for (int j = 1; j < path.get(i).size() - 1; j++) {
                if (target.contains(path.get(i).get(j))) {
                    flag = false;
                }
            }
            if (flag) {
                tempPath.add(path.get(i));
            }
        }
        disJointPaths.add(tempPath);
        count += 1;
        getDisjointPaths(path, count);
    }

    //通过DFS从将最优路径解析成方便理解的形式
    //原本的形式：[[], [0], [0], [2, 4], [1], [1, 4, 3]]
    //解析之后的形式：
    //[0, 1, 5]
    //[0, 1, 4, 5]
    //[0, 2, 3, 5]
    //[0, 1, 4, 3, 5]
    public static void dfs(int end, List<Integer> points, List<Integer> tempPath) {
        if (end == 0) {
            tempPath.add(end);
            Collections.reverse(tempPath);
            bestPaths.add(tempPath);
            return;
        }
        tempPath.add(end);
        List<Integer> backup = new ArrayList<>();
        backup.addAll(tempPath);
        for (int i = 0; i < points.size(); i++) {
            if (i != 0) {
                List<Integer> newList = new ArrayList<>();
                newList.addAll(backup);
                dfs(points.get(i), cnt[points.get(i)], newList);
            } else {
                dfs(points.get(i), cnt[points.get(i)], tempPath);
            }
        }
    }

    //用于辅助优先队列的Edge类
    //在Edge类中，to表示从bridge前往某个顶点，len表示前往这个顶点的代价
    static class Edge {
        int to;
        int len;

        Edge(int to, int len) {
            this.to = to;
            this.len = len;
        }
    }


    // 使用基于堆优化的优先队列，结合改进版本的迪杰斯特拉算法，
    // 用于保存待处理的节点找到源节点到其他节点的最短路径
    public static List<Integer>[] findShortestPaths(Graph graph) {
        int INF = (int) 1e12;
        // 存储从源节点到每个节点的最短距离和前一个节点
        int[] dis = new int[graph.getNodesCount()];
        Arrays.fill(dis, INF);
        dis[0] = 0;
        List<Integer>[] previousNodes = new List[graph.getNodesCount()];
        for (int i = 0; i < graph.getNodesCount(); i++) {
            previousNodes[i] = new ArrayList<>();
        }
        int[] cnt = new int[graph.getNodesCount()];
        // 优先队列
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Long.compare(a.len, b.len));
        pq.offer(new Edge(0, 0));
        // 标记已处理的节点
        boolean[] processed = new boolean[graph.getNodesCount()];
        cnt[0] = 1;
        while (!pq.isEmpty()) {
            Edge p = pq.poll();
            //接下来你作为桥梁，该从你出发了
            int bridge = p.to;
            if (processed[bridge])
                continue;
            // 标记当前节点为已处理
            processed[bridge] = true;
            // 遍历当前节点的邻居节点
            for (int neighbor : graph.getNeighbors(bridge)) {
                // 计算从源节点到邻居节点的距离
                int distance = dis[bridge] + graph.getEdgeWeight(bridge, neighbor);
                if (distance < dis[neighbor]) {
                    dis[neighbor] = distance;
                    cnt[neighbor] = cnt[bridge];
                    if (!previousNodes[neighbor].isEmpty()) {
                        previousNodes[neighbor].remove(previousNodes[neighbor].size() - 1);
                        previousNodes[neighbor].add(bridge);
                    } else {
                        previousNodes[neighbor].add(bridge);
                    }
                    pq.offer(new Edge(neighbor, distance));
                } else if (distance == dis[neighbor]) {
                    cnt[neighbor] = cnt[neighbor] + cnt[bridge];
                    previousNodes[neighbor].add(bridge);
                }
            }
        }
        return previousNodes;
    }

    public static List<List<Integer>> normalAnswers = new ArrayList<List<Integer>>();
    public static Deque<Integer> stack = new ArrayDeque<Integer>();

    public static List<List<Integer>> findNormalPath(Graph graph) {
        stack.offerLast(0);
        normalDFS(graph, 0, graph.getNodesCount() - 1);
        return normalAnswers;
    }

    public static void normalDFS(Graph graph, int x, int n) {
        if (x == n) {
            normalAnswers.add(new ArrayList<Integer>(stack));
            return;
        }
        for (int y : graph.getNeighbors(x)) {
            stack.offerLast(y);
            normalDFS(graph, y, n);
            stack.pollLast();
        }


    }

    public static int getQuantumRandomCode(int range) {
        return Integer.parseInt(executePythonCode("/Users/liu/IdeaProjects/untitled2/src/qrng.py", range));
    }

    //通过python调用IBM qiskit去获取生成的量子随机数
    public static String executePythonCode(String pythonCode, int range) {
        try {
            // 创建一个 ProcessBuilder 对象，指定要执行的 Python 解释器和代码
            ProcessBuilder processBuilder = new ProcessBuilder("python3", pythonCode, range + "");

            // 启动 Python 进程
            Process process = processBuilder.start();

            // 等待 Python 进程执行完毕，并获取其输出结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();

            // 等待 Python 进程结束
            int exitCode = process.waitFor();
            // 检查 Python 进程是否成功结束
            if (exitCode != 0) {
                throw new RuntimeException("Python 进程执行失败，退出码：" + exitCode);
            }

            // 返回 Python 进程的输出结果
            return result.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException("执行 Python 代码时发生错误：" + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException("等待 Python 进程结束时被中断");
        }
    }

    //生成密钥,已经默认设置了密钥池上限为1000单位
    public static void generateKeys(Graph graph, int generateSpeed) {
        if (graph == null) {
            return;
        }
        List<Graph.Edge> edges = graph.getEdges();
        for (Graph.Edge edge : edges) {
            int originalKey = edge.getWeight();
            edge.setWeight(originalKey + generateSpeed);
        }

    }

    //消耗密钥，考虑到可能是多路径算法，用了两层list嵌套的路径
    public static int consumeKeys(Graph graph, List<List<Integer>> paths, int consumeSpeed) {
        int sum = 0;
        for (int i = 0; i < paths.size(); i++) {
            for (int j = 0; j < paths.get(i).size(); j++) {
                if (j == paths.get(i).size() - 1) {
                    continue;
                }
                Graph.Edge edge = graph.getEdge(paths.get(i).get(j), paths.get(i).get(j + 1));
                int keysLeft = edge.getWeight();
                int result = keysLeft - consumeSpeed;
                if (result < 0) {
                    System.out.println
                            ("因为" + edge.getSource() + "到" + edge.getDestination() + "的密钥余量为" + edge.getWeight() + ". 不足以支撑业务");
                    System.out.println("虽然失败了，但是消耗了"+sum);
                    return -1;
                } else {
                    sum += consumeSpeed;
                }
                edge.setWeight(result);
            }
        }
        return sum;
    }

    //查看当前所有链路上的密钥剩余情况
    public static List<Graph.Edge> lookAtLink(Graph graph) {
        List<Graph.Edge> edges = graph.getEdges();
        return edges;
    }
}
