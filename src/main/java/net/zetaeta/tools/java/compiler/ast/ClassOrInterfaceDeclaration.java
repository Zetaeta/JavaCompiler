package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassOrInterfaceDeclaration extends AnnotatedTreeNode {

    protected ClassOrInterfaceDeclaration(Modifiers mods, List<Annotation> annot) {
        super(mods, annot);
    }

    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<>();
    }
}
