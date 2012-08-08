package net.zetaeta.tools.java.compiler.ast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNode implements Iterable<TreeNode> {
    
    public ChildIterator iterator() {
        return new ChildIterator();
    }
    
    class ChildIterator implements Iterator<TreeNode> {

        private Iterator<Field> fields;
        
        public ChildIterator() {
            Field[] allFields = TreeNode.this.getClass().getDeclaredFields();
            List<Field> fieldList = new ArrayList<Field>();
            for (Field field : allFields) {
                if (TreeNode.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    fieldList.add(field);
                }
            }
            fields = fieldList.iterator();
        }
        
        @Override
        public boolean hasNext() {
            return fields.hasNext();
        }

        @Override
        public TreeNode next() {
            try {
                return (TreeNode) fields.next().get(TreeNode.this);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
}
