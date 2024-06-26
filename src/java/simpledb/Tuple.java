package simpledb;

import java.io.Serializable;
import java.util.Iterator;


/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {
	//1- a tuple consists of an array of fields, TupleDesc and RecordId
	
    private static final long serialVersionUID = 1L;

    private Field[] arrayOfFields;
    private TupleDesc tupleDesc;
    private RecordId recordID;
    
    /**
     * Create a new tuple with the specified schema (type).
     *
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
        // some code goes here
    	//2- assign td and initialize the array of fields
    	if(td.numFields() > 0) {
    		this.tupleDesc = td;
    		arrayOfFields = new Field[td.numFields()];
    	}
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
        return tupleDesc;
    }
    
    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        // some code goes here
        return recordID;
    }

    /**
     * Set the RecordId information for this tuple.
     *
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
        // some code goes here
    	this.recordID = rid;
    }

    /**
     * Change the value of the ith field of this tuple.
     *
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
        // some code goes here
    	// 3- set Field of i index to f
    	if(i >= 0)
    		this.arrayOfFields[i] = f;
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     *
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
        // some code goes here
    	if(i >=0) {
    		if(arrayOfFields[i] == null)
    			return null;
    		else
    			return arrayOfFields[i];
    	}
    	return null;
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     *
     * column1\tcolumn2\tcolumn3\t...\tcolumnN
     *
     * where \t is any whitespace (except a newline)
     */
    public String toString() {
    	
        // some code goes here
    	
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < arrayOfFields.length; i++) {
    		if(arrayOfFields[i] != null)
    			sb.append(arrayOfFields[i].toString()+"\t");
    	}
    	
        return sb.toString();
    }

    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields()
    {
        // some code goes here
    	// 4- return an iterator of fields
    	
    	Iterator<Field> iterator = new Iterator<Field>() {
			
    		private int index = 0;
    		
			@Override
			public Field next() {
				// TODO Auto-generated method stub
				return arrayOfFields[index++];
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return index <  arrayOfFields.length - 1 && arrayOfFields[index] != null;
			}
		};
    	
    	return iterator;
    }

    /**
     * reset the TupleDesc of this tuple (only affecting the TupleDesc)
     * */
    public void resetTupleDesc(TupleDesc td)
    {
        // some code goes here
    	//5- reassign TupleDesc to td
    	this.tupleDesc = td;
    }
}