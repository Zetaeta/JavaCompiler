package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnotatedTreeNode extends TreeNodeWithModifiers {
    protected List<Annotation> annotations;
    
    protected AnnotatedTreeNode(Modifiers mods, List<Annotation> annot) {
        super(mods);
        if (annot == null) {
            annot = Collections.emptyList();
        }
        annotations = annot;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        List<TreeNode> list = new ArrayList<>();
        list.addAll(super.getChildList());
        list.addAll(annotations);
        return list;
    }
}
