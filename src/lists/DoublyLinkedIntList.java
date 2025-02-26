package lists;

import java.util.Iterator;
import interfaces.IntList;

/**
 * Creates a doubly linked list and implements methods in IntList
 *
 * @author tobygoetz
 * @version 1.0
 */
public class DoublyLinkedIntList implements IntList {

    // Fields
    private Node front;
    private Node back;
    private int size;

    /**
     * Constructor for DoublyLinkedIntList
      */
    public DoublyLinkedIntList() {
        // an empty list has two sentinel (dummy) nodes that serve as bookends
        front = new Node(0);
        back = new Node(0);
        front.next = back;
        back.prev = front;
        size = 0;
    }

    private class Node {
        int data;
        Node next;  // address of the node 'after' this one in line
        Node prev;  // addres of the node 'before' this one in line

        public Node(int dataValue) {
            data = dataValue;
            next = null;
            prev = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    ", prev=" + prev +
                    '}';
        }
    }

    /**
     * Prepends (inserts) the specified value at the front of the list (at index 0).
     * Shifts the value currently at the front of the list (if any) and any
     * subsequent values to the right.
     *
     * @param value value to be inserted
     */
    @Override
    public void addFront(int value) {

        // Create new node with int value;
        Node addedToFront = new Node(value);

        // Assign new Node prev and next locators
        addedToFront.prev = front;
        addedToFront.next = front.next;

        // Connect new Node to list
        front.next.prev = addedToFront;
        front.next = addedToFront;

        size++;
    }

    /**
     * Appends (inserts) the specified value at the back of the list (at index size()-1).
     *
     * @param value value to be inserted
     */
    @Override
    public void addBack(int value) {
        Node theLastNode = back.prev;

        // set up my new node and fill it out (data, prev, next)
        Node theNewNode = new Node(value);
        theNewNode.next = back;
        theNewNode.prev = theLastNode;

        // go to the end of the list's sentinel and update it's prev
        back.prev = theNewNode;

        // go to the node before the new one and update it's next
        theLastNode.next = theNewNode;

        size++;
    }

    /**
     * Inserts the specified value at the specified position in this list.
     * Shifts the value currently at that position (if any) and any subsequent
     * values to the right.
     *
     * @param index index at which the specified value is to be inserted
     * @param value value to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void add(int index, int value) {

        // Counter
        int dex = 0;
        // Create new Node
        Node nodeAtIndex = new Node(value);
        Node current = front.next;

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index must be in the Range 0-" + (size - 1));
        } else {
            // Conditional: If index is size of List skip loop and add to back
            if (index == size) {
                this.addBack(value);
            } else {
                while (current != back) {
                    if (index == dex) {
                        // Assign the new node's prev and next locators
                        nodeAtIndex.prev = current.prev;
                        nodeAtIndex.next = current;
                        // Connect new Node to list
                        current.prev.next = nodeAtIndex;
                        current.prev = nodeAtIndex;
                    }
                    dex++;
                    current = current.next;
                } size++;
            }
        }
    }

    /**
     * Removes the value located at the front of the list
     * (at index 0), if it is present.
     * Shifts any subsequent values to the left.
     */
    @Override
    public void removeFront() {

        if (size > 0) {
            // Assign Front next locator to second Node
            front.next = front.next.next;
            // Assign the new first node's prev locator to front
            front.next.prev = front;
            // Decrement size
            size--;
        }
    }

    /**
     * Removes the value located at the back of the list
     * (at index size()-1), if it is present.
     */
    @Override
    public void removeBack() {

        if (size > 0) {
            // set up a temp variable for convenience
            Node theOneToRemove = back.prev;

            theOneToRemove.prev.next = back;
            back.prev = theOneToRemove.prev;

            // optional to clean up
            theOneToRemove.next = null;
            theOneToRemove.prev = null;
            theOneToRemove.data = 0;

            size--;
        }
    }

    /**
     * Removes the value at the specified position in this list.
     * Shifts any subsequent values to the left. Returns the value
     * that was removed from the list.
     *
     * @param index the index of the value to be removed
     * @return the value previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public int remove(int index) {

        int dex = 0;
        Node current = front.next;

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index must be in the Range 0-" + (size - 1));
        } else {
            // Conditional to skip looping if index is first node
            if (index == 0) {
                int removedValue = current.data;
                removeFront();
                return removedValue;
            // Conditional to skip looping if index is last node
            } else if (index == size - 1) {
                int removedValue = back.prev.data;
                removeBack();
                return removedValue;
            // Loop to find the node at index position
            } else {
                while (current != back) {
                    if (index == dex) {
                        int removedValue = current.data;
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
                        size--;
                        return removedValue;
                    } else {
                        current = current.next;
                        dex++;
                    }
                }
            }
        } return 0;
    }

    /**
     * Returns the value at the specified position in the list.
     *
     * @param index index of the value to return
     * @return the value at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public int get(int index) {

        // Counter
        int dex = 0;
        // Position
        Node current = front.next;

        if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(
                    "Index must be in the Range 0-" + (size - 1));
        } else {
            //Skip looping if index is at front of list
            if (index == 0) {
                return current.data;
                // Skip looping if index is at back of list
            } else if (index == size - 1) {
                return back.prev.data;
                // If index is in acceptable range loop to find Node at index value
            } else {
                while (current != back) {
                    if (index == dex) {
                        return current.data;
                    } else {
                        dex++;
                        current = current.next;
                    }
                } return current.data;
            }
        }
    }

    /**
     * Returns true if this list contains the specified value.
     *
     * @param value value whose presence in this list is to be searched for
     * @return true if this list contains the specified value
     */
    @Override
    public boolean contains(int value) {

        Node current = front.next;

        while (current != back) {
            if (current.data == value) {
                return true;
            } else {
                current = current.next;
            }
        } return false;
    }

    /**
     * Returns the index of the first occurrence of the specified value
     * in this list, or -1 if this list does not contain the value.
     *
     * @param value value to search for
     * @return the index of the first occurrence of the specified value in this list
     * or -1 if this list does not contain the value
     */
    @Override
    public int indexOf(int value) {

        int dex = 0;
        Node current = front.next;

        if (contains(value)) {
             while (current.data != value) {
                 dex++;
                 current = current.next;
             } return dex;
        } return -1;
    }

    /**
     * Returns true if this list contains no values.
     * @return true if this list contains no values
     */
    @Override
    public boolean isEmpty() {
        return front.next == back && back.prev == front;
    }

    /**
     * Returns the number of values in this list.
     * @return the number of values in this list
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes all the values from this list.
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        front.next = back;
        back.prev = front;
        size = 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    /**
     * Prints Entire list
     */
    public void printList()
    {
        Node current = this.front.next;
        System.out.print("Size: " + size + "     ");

        while (current.next != null)
        {
            if (current.next != back) {
                System.out.print(current.data + " -> ");
            } else {
                System.out.print(current.data);
            }
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public String toString() {
        Node current = this.front.next;
        String list = ("DoublyLinkedIntList{Size: " + size + ", list [");

        while (current.next != null) {
            if (current.next != back) {
                list += (current.data + " -> ");
            } else {
                list += (current.data);
            }
            current = current.next;
        }
        return list + "]}";
    }
}
