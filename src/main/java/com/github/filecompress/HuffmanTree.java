package com.github.filecompress;

import java.util.*;

public class HuffmanTree {
    /**
     * @param list 传入所有节点的 list
     * @return 返回生成的哈夫曼树的根
     */
    public static Node createHuffmanTree(List<Node> list) {
        while (list.size() > 1) {
            Collections.sort(list);
            // 获取权值最小的两个节点
            Node left = list.get(0);
            Node right = list.get(1);
            // 生成新节点，新节点的权值为两个子节点权值的和
            Node parent = new Node(null, left.weight + right.weight, left, right);
            // 删除权值最小的两个节点
            list.remove(0);
            list.remove(0);
            // 将新的节点添加到 list 中
            list.add(parent);
        }
        return list.get(list.size() - 1);
    }

    /**
     * 将 map 转换为节点的列表，key 作为 Node.e, value 作为 Node.weight
     *
     * @param map
     * @return
     */
    public static List<Node> mapToListNode(Map<Character, Integer> map) {
        List<Node> res = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            Character key = entry.getKey();
            Integer value = entry.getValue();
            Node node = new Node(key, value);
            res.add(node);
        }
        // 将 res 按照 value 的倒序进行排序
        Collections.sort(res);
        return res;
    }

    public Map<Character, String> treeToHuffmanCode(Node root) {
        Map<Character, String> map = new HashMap<>();
        treeToHuffmanCode(root, map, "");
        return map;
    }

    public void treeToHuffmanCode(Node root, Map<Character, String> map, String code) {
        if (root != null) {
            treeToHuffmanCode(root.left, map, code + "0");
            if (root.left == null && root.right == null) {
                map.put((Character) root.e, code);
            }
            treeToHuffmanCode(root.right, map, code + "1");
        }

    }
}
