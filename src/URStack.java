/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 1
 * I took this from lab 7
 */
public class URStack<AnyType> implements Stack<AnyType>{
	
	URLinkedList<AnyType> dataList;
	
	public URStack() {
		dataList = new URLinkedList<AnyType>();
	}
	
	//check is stack is empty
	@Override
	public boolean isEmpty() {
		return dataList.size() == 0;
	}
	
	//add to the front
	@Override
	public void push(AnyType x) {
		dataList.addFirst(x);
	}
	
	//remove and return the front
	@Override
	public AnyType pop() {
		if (isEmpty()) {
			return null;
		}
		return dataList.pollFirst();
	}

	//return the front
	@Override
	public AnyType peek() {
		return dataList.peekFirst();
	}
	
	//test case
	public static void main(String[] args) {
		URStack<Integer> stack = new URStack<Integer>();
		
		System.out.println(stack.isEmpty());
		
		stack.push(23);
		stack.push(11);
		stack.push(2001);
		
		System.out.println(stack.isEmpty());
		
		System.out.println(stack.peek());
		System.out.println(stack.pop());
		
		System.out.println(stack.peek());
		System.out.println(stack.pop());
		
		System.out.println(stack.peek());
		System.out.println(stack.pop());
	}
}
