package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclaration extends ClassOrInterfaceDeclaration {
    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<>();
    }
}
