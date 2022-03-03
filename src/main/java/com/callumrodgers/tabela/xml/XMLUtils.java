package com.callumrodgers.tabela.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedList;
import java.util.List;

public class XMLUtils {

    public static List<Node> getElementNodesFromList(NodeList list) {
        List<Node> result = new LinkedList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                result.add(node);
            }
        }
        return result;
    }
}
