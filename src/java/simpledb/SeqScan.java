package simpledb;

import java.util.*;

/**
 * SeqScan is an implementation of a sequential scan access method that reads
 * each tuple of a table in no particular order (e.g., as they are laid out on
 * disk).
 */
public class SeqScan implements OpIterator {

    private static final long serialVersionUID = 1L;
    private final TransactionId tid;
    private int tableId;
    private String tableAlias;
    private DbFileIterator iterator;

    // create class attributes
    /**
     * Creates a sequential scan over the specified table as a part of the
     * specified transaction.
     *
     * @param tid
     *                   The transaction this scan is running as a part of.
     * @param tableid
     *                   the table to scan.
     * @param tableAlias
     *                   the alias of this table (needed by the parser); the
     *                   returned
     *                   tupleDesc should have fields with name tableAlias.fieldName
     *                   (note: this class is not responsible for handling a case
     *                   where
     *                   tableAlias or fieldName are null. It shouldn't crash if
     *                   they
     *                   are, but the resulting name can be null.fieldName,
     *                   tableAlias.null, or null.null).
     */
    public SeqScan(TransactionId tid, int tableid, String tableAlias) {
        // some code goes here
        this.tid = tid;
        this.tableAlias = tableAlias;
        this.tableId = tableid;
    }

    /**
     * @return
     *         return the table name of the table the operator scans. This should
     *         be the actual name of the table in the catalog of the database
     */
    public String getTableName() {
        // some code goes here
        // get the table name from the catalog
        return Database.getCatalog().getTableName(this.tableId);
    }

    /**
     * @return Return the alias of the table this operator scans.
     */
    public String getAlias() {
        // some code goes here
        return this.tableAlias;
    }

    /**
     * Reset the tableid, and tableAlias of this operator.
     * 
     * @param tableid
     *                   the table to scan.
     * @param tableAlias
     *                   the alias of this table (needed by the parser); the
     *                   returned
     *                   tupleDesc should have fields with name tableAlias.fieldName
     *                   (note: this class is not responsible for handling a case
     *                   where
     *                   tableAlias or fieldName are null. It shouldn't crash if
     *                   they
     *                   are, but the resulting name can be null.fieldName,
     *                   tableAlias.null, or null.null).
     */
    public void reset(int tableid, String tableAlias) {
        // some code goes here
        // reset by reassigning this attributes
        this.tableAlias = tableAlias;
        this.tableId = tableid;
    }

    public SeqScan(TransactionId tid, int tableId) {
        this(tid, tableId, Database.getCatalog().getTableName(tableId));
    }

    public void open() throws DbException, TransactionAbortedException {
        // some code goes here
        // get the iterator of the database file and open it
        iterator = Database.getCatalog().getDatabaseFile(this.tableId).iterator(this.tid);
        iterator.open();
    }

    /**
     * Returns the TupleDesc with field names from the underlying HeapFile,
     * prefixed with the tableAlias string from the constructor. This prefix
     * becomes useful when joining tables containing a field(s) with the same
     * name. The alias and name should be separated with a "." character
     * (e.g., "alias.fieldName").
     *
     * @return the TupleDesc with field names from the underlying HeapFile,
     *         prefixed with the tableAlias string from the constructor.
     */
    public TupleDesc getTupleDesc() {
        // some code goes here
        // return a new TupleDesc object
        /*
         * The new TupleDesc object is the same as the original TupleDesc for the table,
         * except for the FieldNames
         * Field names must be prefixed with "tableAlist.Field name"
         * For example, if the table alias is t1 and the field name is age, the new
         * field name should be t1.age
         *
         */
        TupleDesc td = Database.getCatalog().getTupleDesc(tableId);
        String[] newNames = new String[td.numFields()];
        Type[] newTypes = new Type[td.numFields()];
        for (int i = 0; i < td.numFields(); i++) {
            newNames[i] = tableAlias + "." + td.getFieldName(i);
            newTypes[i] = td.getFieldType(i);
        }
        return new TupleDesc(newTypes, newNames);
    }

    public boolean hasNext() throws TransactionAbortedException, DbException {
        // some code goes here
        // hasNext of the database file iterator
        if (iterator == null)
            return false;
        return iterator.hasNext();
    }

    public Tuple next() throws NoSuchElementException,
            TransactionAbortedException, DbException {
        // some code goes here
        // next of the database file iterator
        if (iterator != null)
            return iterator.next();
        else
            throw new NoSuchElementException("NoSuchElementException");
    }

    public void close() {
        // some code goes here
        // close the database file iterator
        if (iterator != null)
            iterator.close();
    }

    public void rewind() throws DbException, NoSuchElementException,
            TransactionAbortedException {
        // some code goes here
        // rewind the database file iterator
        if (iterator != null)
            iterator.rewind();
        else
            throw new DbException("DbException");
    }
}