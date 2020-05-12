/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 1
 * I took this from lab 7
 */
public interface Queue<AnyType> {
	public boolean isEmpty();
	public void enqueue(AnyType x);
	public AnyType dequeue();
	public AnyType peek();
}
