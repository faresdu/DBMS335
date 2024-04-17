package simpledb;

import java.io.Serializable;
import java.util.*;
/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

//1- create a list of TDItems
    /**
     * A help class to facilitate organizing the information of each field
     * */
	
	private List<TDItem> list_of_TDItem = new ArrayList<>();
	//private int counter = 0;
	
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;
        
        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
    	
        // some code goes here
    	//return an iterator
    	
    	Iterator<TDItem> iterator = new Iterator<TupleDesc.TDItem>() {
			
    		private int index = 0;
    		
			@Override
			public TDItem next() {
				// TODO Auto-generated method stub
				return list_of_TDItem.get(index++);
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return index <  list_of_TDItem.size() - 1 && list_of_TDItem.get(index) != null;
			}
		};
    	
        return iterator;
        
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) { 
        // some code goes here
    	//2- populate the list of TDItems
    	for(int i = 0; i < typeAr.length; i++) 
    		list_of_TDItem.add(new TDItem(typeAr[i], fieldAr[i]));	
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // some code goes here
    	//3- populate the list of TDItems
    	for(int i = 0; i < typeAr.length; i++)
    		list_of_TDItem.add(new TDItem(typeAr[i], "unnamed"));
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return list_of_TDItem.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
    	//4- return field name of item at index i
    	// if i is not valid, throw NoSuchElementException
    	if(i >=0 && i < list_of_TDItem.size()) {
    		String name = list_of_TDItem.get(i).fieldName;
    		return name;
    	}
    	else
    		throw new NoSuchElementException("Invalid index");
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
    	//5- return field type of item at index i
    	// if i is not valid, throw NoSuchElementException
    	if(i >=0 && i < list_of_TDItem.size()) {
    		Type type = list_of_TDItem.get(i).fieldType;
    		return type;
    	}
    	else
    		throw new NoSuchElementException("Invalid index");
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
    	// 6- return the index of item with name
    	// throw NoSuchElementException if not exist
    	
    	for(int i = 0; i < list_of_TDItem.size(); i++)
    		if(name != null && name.equals(list_of_TDItem.get(i).fieldName))
    			return i;
    	
    	throw new NoSuchElementException("Not found");
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
    	 // some code goes here
    	//7- hint: use fieldType.getLen()
    	int size = 0;
    	
    	for(int i = 0; i < list_of_TDItem.size(); i++)
    		if(list_of_TDItem.get(i).fieldType != null)
    			size += list_of_TDItem.get(i).fieldType.getLen();
    	
    	return size;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here
    	//8- Create new TupleDesc that merge both td1 and td2
    	
    	int total = td1.numFields() + td2.numFields();
    	
    	Type[] temp = new Type[total];
    	String[] name = new String[total];
    	
    	int counter = 0;
    	
    	for(int i = 0; i < total; i++)
    		if(i < td1.numFields()) {
	    		temp[counter] = td1.getFieldType(i);
	    		name[counter++] = td1.getFieldName(i);
    		}
    	
    	for(int i = 0; i < total; i++)
    		if(i < td2.numFields()) {
    			temp[counter] = td2.getFieldType(i);
    			name[counter++] = td2.getFieldName(i);
    		}

    	TupleDesc td3 = new TupleDesc(temp, name);
    	
    	
    	return td3;
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they have the same number of items
     * and if the i-th type in this TupleDesc is equal to the i-th type in o
     * for every i.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */

    public boolean equals(Object o) {
        // some code goes here
    	//9- check type of o 
    	if(this == o)
    		return true;
    	else {
    		if(o == null || getClass() != o.getClass())
    			return false;
    		else {
    			TupleDesc newO = (TupleDesc) o;
    			if (list_of_TDItem.size() != newO.list_of_TDItem.size())
    				return false;
    			else {
    				int i = 0;
    				while (i < list_of_TDItem.size()) {
    				    TDItem here = list_of_TDItem.get(i);
    				    TDItem otherItem = newO.list_of_TDItem.get(i);
    				    if (!Objects.equals(here.fieldName, otherItem.fieldName) || here.fieldType != otherItem.fieldType) 
    				        return false;
    				    i++;
    				}
    				return true;
    			}
    		}
    	}
   }

    public int hashCode() {
    	// not required for lab1
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here
    	StringBuilder sb =  new StringBuilder();
    
    	for (int i = 0; i < list_of_TDItem.size(); i++)
    		sb.append(list_of_TDItem.get(i).fieldType+"("+list_of_TDItem.get(i).fieldName+"), ");
    	
    	return sb.toString();
    }
}