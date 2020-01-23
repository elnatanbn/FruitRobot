package algorithms;

import java.util.Comparator;
import dataStructure.node_data;

public class NodeComperator implements Comparator<node_data>{

	@Override
	public int compare(node_data node1, node_data node2) {
		/**Compare between 2 nodes by there distance from source node
		 * @return positive number if  the first one is bigger
		 * @return negative number if the second one is bigger
		 * @return 0 if equals
		 */
		double weight1 = node1.getWeight();
		double weight2 = node2.getWeight();
		return ((int)(weight1-weight2));
	}

}