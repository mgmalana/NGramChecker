package morphinas.Stemmer.Trials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurenz on 15/02/2017.
 */
public class TreeTest
{
	public TreeTest(){}

	public void something()
	{
	}

	public class Tree<T>
	{
		private Node<T> root;

		public Tree(T rootData)
		{
			root = new Node<T>();
			root.data = rootData;
			root.children = new ArrayList<Node<T>>();
		}

		public class Node<T>
		{
			private T data;
			private Node<T> parent;
			private List<Node<T>> children;
		}
	}
}
