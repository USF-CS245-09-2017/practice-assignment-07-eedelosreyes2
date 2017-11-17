import java.util.ArrayList;

public class Hashtable {
	private String key;
	private String value;
    private ArrayList<HashNode> bucketArray;
    private int numBuckets; // Current capacity of array list	 
    private int size; // Current size of array list

	public Hashtable() {
		bucketArray = new ArrayList<HashNode>();
		numBuckets = 2250;
		size = 0;
		for (int i = 0; i < numBuckets; i++) {
			bucketArray.add(null);
		}
	}
	
	public boolean containsKey(String key) {
		int bucketIndex = getBucketIndex(key);
		HashNode head = bucketArray.get(bucketIndex);
		while (head != null) {
			if (head.key.equals(key))
				return true;
			head = head.next;
		} return false;
		
	}
	
	public String get(String key) {
        int bucketIndex = getBucketIndex(key);
        HashNode head = bucketArray.get(bucketIndex);
        while (head != null) {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        } return null;
	}
	
	public void put(String key, String value) {
        int bucketIndex = getBucketIndex(key);
        HashNode head = bucketArray.get(bucketIndex);
        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        size++;
        head = bucketArray.get(bucketIndex);
        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);

        if ((1.0*size)/numBuckets >= 0.7) {
            ArrayList<HashNode> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(null);
 
            for (HashNode headNode : temp) {
                while (headNode != null) {
                    put(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }
	
	public String remove(String key) {
        int bucketIndex = getBucketIndex(key);
        HashNode head = bucketArray.get(bucketIndex);
        HashNode prev = null;
        while (head != null) {
            if (head.key.equals(key))
                break;
            prev = head;
            head = head.next;
        }
        if (head == null)
            return null;
        size--;
        if (prev != null)
            prev.next = head.next;
        else
            bucketArray.set(bucketIndex, head.next);
        return head.value;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int getBucketIndex(String key) {
		int hashCode = Math.abs(key.hashCode());
		int index = hashCode % numBuckets;
		return index;
	}
	
	class HashNode {
		String key;
		String value;
		HashNode next;
		
		public HashNode(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
	
}
