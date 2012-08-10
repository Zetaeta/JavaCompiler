package net.zetaeta.tools.java.compiler.ast;

import java.util.Iterator;
import java.util.List;

public abstract class TreeNode implements Iterable<TreeNode> {
    
    public static final String TAB_SIZE = "\t";
    
    public Iterator<TreeNode> iterator() {
        return getChildList().iterator();
    }
    
    protected abstract List<TreeNode> getChildList(); 
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" {\n");
        for (TreeNode tn : getChildList()) {
            for (String s : tn.toString().split(" ")) {
                sb.append(TAB_SIZE).append(s).append('\n');
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
    
//    class ChildIterator implements Iterator<TreeNode> {
//
//        private Iterator<Field> fields;
//        
//        public ChildIterator() {
//            Field[] allFields = TreeNode.this.getClass().getDeclaredFields();
//            List<Field> fieldList = new ArrayList<Field>();
//            for (Field field : allFields) {
//                if (TreeNode.class.isAssignableFrom(field.getType())) {
//                    field.setAccessible(true);
//                    fieldList.add(field);
//                }
//            }
//            fields = fieldList.iterator();
//        }
//        
//        @Override
//        public boolean hasNext() {
//            return fields.hasNext();
//        }
//
//        @Override
//        public TreeNode next() {
//            try {
//                return (TreeNode) fields.next().get(TreeNode.this);
//            } catch (IllegalArgumentException | IllegalAccessException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        public void remove() {
//            throw new UnsupportedOperationException();
//        }
//        
//    }
}
