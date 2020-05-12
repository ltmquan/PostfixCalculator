/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 1
 * I took this from lab 7
 */
public class URQueue<AnyType> implements Queue<AnyType>{
	
URLinkedList<AnyType> dataList;
	
	public URQueue() {
		dataList = new URLinkedList<AnyType>();
	}

	@Override
	public boolean isEmpty() {
		return dataList.size() == 0;
	}

	@Override
	public void enqueue(AnyType x) {
		dataList.add(x);
	}

	@Override
	public AnyType dequeue() {
		if (isEmpty()) {
			return null;
		}
		return dataList.pollFirst();
	}

	@Override
	public AnyType peek() {
		return dataList.peekFirst();
	}
	
	//test case
	public static void main(String[] args) {
		URQueue<Integer> stack = new URQueue<Integer>();
		
		System.out.println(stack.isEmpty());
		
		stack.enqueue(23);
		stack.enqueue(11);
		stack.enqueue(2001);
		
		System.out.println(stack.isEmpty());
		
		System.out.println(stack.peek());
		System.out.println(stack.dequeue());
		
		System.out.println(stack.peek());
		System.out.println(stack.dequeue());
		
		System.out.println(stack.peek());
		System.out.println(stack.dequeue());
	}
}
